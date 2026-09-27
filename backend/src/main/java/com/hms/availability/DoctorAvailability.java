package com.hms.availability;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "doctor_availability")
public class DoctorAvailability {
    @Id private UUID id;
    @Column(nullable = false) private UUID tenantId;
    @Column(nullable = false) private UUID doctorId;
    @Column(nullable = false) private short dayOfWeek;
    @Column(nullable = false) private LocalTime startTime;
    @Column(nullable = false) private LocalTime endTime;
    @Column(nullable = false) private int slotDurationMinutes;
    @Column(length = 100) private String sessionName;
    @Column(length = 180) private String location;
    @Column(nullable = false) private boolean active;
    @Column(nullable = false) private OffsetDateTime createdAt;
    @Column(nullable = false) private OffsetDateTime updatedAt;
    protected DoctorAvailability() { }
    public DoctorAvailability(UUID tenantId, UUID doctorId, CreateAvailabilityRequest request) {
        this.id = UUID.randomUUID(); this.tenantId = tenantId; this.doctorId = doctorId;
        this.dayOfWeek = request.dayOfWeek(); this.startTime = request.startTime(); this.endTime = request.endTime();
        this.slotDurationMinutes = request.slotDurationMinutes(); this.sessionName = request.sessionName();
        this.location = request.location(); this.active = true; this.createdAt = OffsetDateTime.now(); this.updatedAt = this.createdAt;
    }
    public UUID getId() { return id; } public UUID getTenantId() { return tenantId; } public UUID getDoctorId() { return doctorId; }
    public short getDayOfWeek() { return dayOfWeek; } public LocalTime getStartTime() { return startTime; } public LocalTime getEndTime() { return endTime; }
    public int getSlotDurationMinutes() { return slotDurationMinutes; } public String getSessionName() { return sessionName; }
    public String getLocation() { return location; } public boolean isActive() { return active; }
}
