package com.hms.tenancy;

import java.util.UUID;

public record TenantDto(
        UUID id,
        String slug,
        String name,
        String legalName,
        TenantStatus status,
        String primaryEmail,
        String primaryPhone
) {
    public static TenantDto from(Tenant tenant) {
        return new TenantDto(
                tenant.getId(),
                tenant.getSlug(),
                tenant.getName(),
                tenant.getLegalName(),
                tenant.getStatus(),
                tenant.getPrimaryEmail(),
                tenant.getPrimaryPhone()
        );
    }
}
