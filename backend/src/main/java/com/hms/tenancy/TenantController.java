package com.hms.tenancy;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TenantDto createTenant(@Valid @RequestBody CreateTenantRequest request) {
        if (tenantRepository.findBySlug(request.slug()).isPresent()) throw new IllegalArgumentException("Tenant slug already exists");
        return TenantDto.from(tenantRepository.save(new Tenant(java.util.UUID.randomUUID(), request.slug(), request.name(), request.legalName(), TenantStatus.TRIAL, request.primaryEmail(), request.primaryPhone())));
    }
}
