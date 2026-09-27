package com.hms.tenancy;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/platform/tenants")
public class TenantController {

    private final TenantRepository tenantRepository;

    public TenantController(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @GetMapping
    public List<TenantDto> listTenants() {
        return tenantRepository.findAll().stream()
                .map(TenantDto::from)
                .toList();
    }

    @GetMapping("/{slug}")
    public TenantDto getTenant(@PathVariable String slug) {
        return tenantRepository.findBySlug(slug)
                .map(TenantDto::from)
                .orElseThrow(() -> new TenantNotFoundException(slug));
    }
}
