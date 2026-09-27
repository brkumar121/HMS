package com.hms.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** Applies coarse route-level RBAC before controller code runs; resource ownership remains tenant-scoped. */
@Component
public class RoleAccessFilter extends OncePerRequestFilter {
    private final boolean enforce;

    public RoleAccessFilter(Environment environment) {
        this.enforce = Boolean.parseBoolean(environment.getProperty("hms.security.enforce", "false"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (!enforce || request.getRequestURI().startsWith("/api/public/") || request.getRequestURI().startsWith("/api/auth/")) {
            chain.doFilter(request, response);
            return;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            chain.doFilter(request, response);
            return;
        }
        String uri = request.getRequestURI();
        Set<String> allowed = allowedRoles(uri, request.getMethod());
        boolean permitted = allowed.isEmpty() || authentication.getAuthorities().stream()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .anyMatch(allowed::contains);
        if (!permitted) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"code\":\"ROLE_ACCESS_DENIED\",\"message\":\"The account role cannot perform this action\"}");
            return;
        }
        chain.doFilter(request, response);
    }

    private Set<String> allowedRoles(String uri, String method) {
        if (uri.startsWith("/api/platform/")) return Set.of("PLATFORM_OWNER");
        if ("GET".equals(method)) return Set.of();
        if (uri.contains("/queue/") || uri.contains("/queue-board")) return Set.of("HOSPITAL_OWNER", "ADMINISTRATOR", "RECEPTION", "DOCTOR");
        if (uri.contains("/appointments") || uri.contains("/patients") || uri.contains("/follow-ups") || uri.contains("/reminders")) {
            return Set.of("HOSPITAL_OWNER", "ADMINISTRATOR", "RECEPTION", "DOCTOR");
        }
        if (uri.contains("/website/") || uri.contains("/social/")) return Set.of("HOSPITAL_OWNER", "ADMINISTRATOR", "CONTENT_MARKETING");
        return Set.of("HOSPITAL_OWNER", "ADMINISTRATOR");
    }
}
