package com.hms.doctors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id private UUID id;
    @Column(nullable = false) private UUID tenantId;
    @Column(nullable = false, length = 180) private String displayName;
    @Column(length = 160) private String specialty;
    @Column(length = 500) private String photoUrl;
    @Column(length = 500) private String qualifications;
    private Integer experienceYears;
    @Column(length = 500) private String languages;
    @Column(length = 1000) private String consultationTimings;
    @Column(nullable = false) private boolean publicVisible;
    @Column(nullable = false) private boolean active;
    @Column(nullable = false) private OffsetDateTime createdAt;
    @Column(nullable = false) private OffsetDateTime updatedAt;

    protected Doctor() { }

    public Doctor(UUID tenantId, CreateDoctorRequest request) {
        this.id = UUID.randomUUID();
        this.tenantId = tenantId;
        this.displayName = request.displayName().trim();
        this.specialty = clean(request.specialty());
        this.photoUrl = clean(request.photoUrl());
        this.qualifications = clean(request.qualifications());
        this.experienceYears = request.experienceYears();
        this.languages = clean(request.languages());
        this.consultationTimings = clean(request.consultationTimings());
        this.publicVisible = request.publicVisible();
        this.active = true;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = this.createdAt;
    }

    private static String clean(String value) { return value == null || value.isBlank() ? null : value.trim(); }
    public UUID getId() { return id; }
    public UUID getTenantId() { return tenantId; }
    public String getDisplayName() { return displayName; }
    public String getSpecialty() { return specialty; }
    public String getPhotoUrl() { return photoUrl; }
    public String getQualifications() { return qualifications; }
    public Integer getExperienceYears() { return experienceYears; }
    public String getLanguages() { return languages; }
    public String getConsultationTimings() { return consultationTimings; }
    public boolean isPublicVisible() { return publicVisible; }
    public boolean isActive() { return active; }
}
