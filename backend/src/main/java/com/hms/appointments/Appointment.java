package com.hms.appointments;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
@Entity @Table(name="appointments")
public class Appointment {
 @Id private UUID id; @Column(nullable=false) private UUID tenantId; @Column(nullable=false) private UUID patientId; @Column(nullable=false) private UUID doctorId;
 @Column(nullable=false) private OffsetDateTime startsAt; @Column(nullable=false) private OffsetDateTime endsAt;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private AppointmentStatus status;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private AppointmentSource source;
 @Column(length=500) private String reason; @Column(length=1000) private String notes;
 @Column(nullable=false) private OffsetDateTime createdAt; @Column(nullable=false) private OffsetDateTime updatedAt;
 protected Appointment() {}
 public Appointment(UUID tenantId, UUID patientId, CreateAppointmentRequest r) { this.id=UUID.randomUUID(); this.tenantId=tenantId; this.patientId=patientId; this.doctorId=r.doctorId(); this.startsAt=r.startsAt(); this.endsAt=r.endsAt(); this.status=AppointmentStatus.REQUESTED; this.source=r.source(); this.reason=r.reason(); this.notes=r.notes(); this.createdAt=OffsetDateTime.now(); this.updatedAt=createdAt; }
 public UUID getId(){return id;} public UUID getPatientId(){return patientId;} public UUID getDoctorId(){return doctorId;} public OffsetDateTime getStartsAt(){return startsAt;} public OffsetDateTime getEndsAt(){return endsAt;} public AppointmentStatus getStatus(){return status;} public AppointmentSource getSource(){return source;} public String getReason(){return reason;}
}
