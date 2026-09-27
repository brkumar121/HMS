package com.hms.appointments;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
@Entity @Table(name="patients") public class Patient {
 @Id private UUID id; @Column(nullable=false) private UUID tenantId; @Column(nullable=false,length=180) private String fullName;
 @Column(nullable=false,length=40) private String phone; @Column(length=100) private String hospitalPatientId; @Column(length=100) private String abhaId;
 private OffsetDateTime abhaConsentAt; @Column(nullable=false) private OffsetDateTime createdAt; @Column(nullable=false) private OffsetDateTime updatedAt;
 protected Patient() {} public Patient(UUID tenantId, CreateAppointmentRequest r) { this.id=UUID.randomUUID(); this.tenantId=tenantId; this.fullName=r.fullName().trim(); this.phone=r.phone().trim(); this.hospitalPatientId=clean(r.hospitalPatientId()); this.abhaId=clean(r.abhaId()); this.abhaConsentAt=r.abhaId()==null?null:OffsetDateTime.now(); this.createdAt=OffsetDateTime.now(); this.updatedAt=createdAt; }
 private static String clean(String s){return s==null||s.isBlank()?null:s.trim();} public UUID getId(){return id;} public UUID getTenantId(){return tenantId;} public String getFullName(){return fullName;} public String getPhone(){return phone;} public String getHospitalPatientId(){return hospitalPatientId;} public String getAbhaId(){return abhaId;}
 public void update(UpdatePatientRequest r){fullName=r.fullName().trim();phone=r.phone().trim();hospitalPatientId=clean(r.hospitalPatientId());abhaId=clean(r.abhaId());if(r.abhaId()!=null&&!r.abhaId().isBlank())abhaConsentAt=OffsetDateTime.now();updatedAt=OffsetDateTime.now();}
}
