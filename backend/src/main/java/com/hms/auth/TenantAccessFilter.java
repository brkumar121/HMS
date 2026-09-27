package com.hms.auth;

import com.hms.tenancy.TenantRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** Prevents an authenticated hospital user from crossing tenant boundaries via a different slug. */
@Component
public class TenantAccessFilter extends OncePerRequestFilter {
    private final TenantRepository tenants;
    private final boolean enforce;

    public TenantAccessFilter(TenantRepository tenants, Environment environment) {
        this.tenants = tenants;
        this.enforce = Boolean.parseBoolean(environment.getProperty("hms.security.enforce", "false"));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String slug = tenantSlug(request.getRequestURI());
        if (!enforce || slug == null || request.getRequestURI().startsWith("/api/public/")) {
            chain.doFilter(request, response);
            return;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            chain.doFilter(request, response);
            return;
        }
        UUID requestedTenant = tenants.findBySlug(slug).map(t -> t.getId()).orElse(null);
        boolean allowed = requestedTenant != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("TENANT_" + requestedTenant));
        if (!allowed) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"code\":\"TENANT_ACCESS_DENIED\",\"message\":\"The account cannot access this hospital\"}");
            return;
        }
        chain.doFilter(request, response);
    }

    private String tenantSlug(String uri) {
        String prefix = "/api/hospitals/";
        if (!uri.startsWith(prefix)) return null;
        String remainder = uri.substring(prefix.length());
        int slash = remainder.indexOf('/');
        return slash < 0 ? remainder : remainder.substring(0, slash);
    }
}
