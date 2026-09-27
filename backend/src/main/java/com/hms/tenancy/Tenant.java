package com.hms.tenancy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tenants")
public class Tenant {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true, length = 80)
    private String slug;

    @Column(nullable = false, length = 180)
    private String name;

    @Column(length = 180)
    private String legalName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TenantStatus status;

    @Column(length = 180)
    private String primaryEmail;

    @Column(length = 40)
    private String primaryPhone;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    protected Tenant() {
    }

    public Tenant(UUID id, String slug, String name, String legalName, TenantStatus status, String primaryEmail, String primaryPhone) {
        this.id = id;
        this.slug = slug;
        this.name = name;
        this.legalName = legalName;
        this.status = status;
        this.primaryEmail = primaryEmail;
        this.primaryPhone = primaryPhone;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    public String getLegalName() {
        return legalName;
    }

    public TenantStatus getStatus() {
        return status;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }
}
