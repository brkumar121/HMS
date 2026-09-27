package com.hms.availability;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "doctor_leave_periods")
public class DoctorLeavePeriod {
    @Id private UUID id; @Column(nullable = false) private UUID tenantId; @Column(nullable = false) private UUID doctorId;
    @Column(nullable = false) private OffsetDateTime startsAt; @Column(nullable = false) private OffsetDateTime endsAt;
    @Column(length = 300) private String reason; @Column(nullable = false) private OffsetDateTime createdAt;
    protected DoctorLeavePeriod() { }
    public DoctorLeavePeriod(UUID tenantId, UUID doctorId, CreateLeaveRequest request) {
        this.id = UUID.randomUUID(); this.tenantId = tenantId; this.doctorId = doctorId; this.startsAt = request.startsAt();
        this.endsAt = request.endsAt(); this.reason = request.reason(); this.createdAt = OffsetDateTime.now();
    }
    public UUID getId() { return id; } public UUID getDoctorId() { return doctorId; } public OffsetDateTime getStartsAt() { return startsAt; }
    public OffsetDateTime getEndsAt() { return endsAt; } public String getReason() { return reason; }
}
