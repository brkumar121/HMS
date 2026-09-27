package com.hms.tenancy;

public class TenantNotFoundException extends RuntimeException {
    public TenantNotFoundException(String slug) {
        super("Tenant not found for slug: " + slug);
    }
}
