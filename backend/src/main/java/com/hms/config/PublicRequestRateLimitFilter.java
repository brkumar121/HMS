package com.hms.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Clock;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class PublicRequestRateLimitFilter extends OncePerRequestFilter {
    private final Map<String, Window> windows = new ConcurrentHashMap<>();
    private final Clock clock = Clock.systemUTC();
    private final int maxRequests;
    private final long windowMillis;

    public PublicRequestRateLimitFilter(Environment environment) {
        maxRequests = Integer.parseInt(environment.getProperty("hms.public-rate-limit.max-requests", "30"));
        windowMillis = Long.parseLong(environment.getProperty("hms.public-rate-limit.window-ms", "60000"));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !"POST".equalsIgnoreCase(request.getMethod()) || !request.getRequestURI().matches("/api/public/hospitals/[^/]+/appointments");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        long now = clock.millis();
        String key = clientIp(request) + "|" + request.getRequestURI();
        Window window = windows.compute(key, (ignored, current) -> current == null || now - current.startedAt >= windowMillis ? new Window(now) : current);
        if (window.count.incrementAndGet() > maxRequests) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setHeader("Retry-After", String.valueOf(Math.max(1, (windowMillis - (now - window.startedAt)) / 1000)));
            response.setContentType("application/json");
            response.getWriter().write("{\"code\":\"RATE_LIMITED\",\"message\":\"Too many appointment requests. Try again later.\"}");
            return;
        }
        chain.doFilter(request, response);
    }

    private String clientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        return forwarded == null || forwarded.isBlank() ? request.getRemoteAddr() : forwarded.split(",")[0].trim();
    }

    private static final class Window {
        private final long startedAt;
        private final AtomicInteger count = new AtomicInteger();
        private Window(long startedAt) { this.startedAt = startedAt; }
    }
}
