package com.hms.staff;
import jakarta.persistence.*; import java.time.OffsetDateTime; import java.util.UUID;
@Entity @Table(name="hospital_staff") public class HospitalStaff {
 @Id private UUID id; @Column(nullable=false) private UUID tenantId; @Column(nullable=false,length=180) private String email; @Column(nullable=false,length=160) private String displayName;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=80) private StaffRole role; @Enumerated(EnumType.STRING) @Column(nullable=false,length=30) private StaffStatus status; @Column(nullable=false) private OffsetDateTime invitedAt; @Column(length=200) private String passwordHash;
 protected HospitalStaff() {} public HospitalStaff(UUID tenantId,String email,String displayName,StaffRole role){this.id=UUID.randomUUID();this.tenantId=tenantId;this.email=email;this.displayName=displayName;this.role=role;this.status=StaffStatus.INVITED;this.invitedAt=OffsetDateTime.now();}
 public UUID getId(){return id;} public UUID getTenantId(){return tenantId;} public String getEmail(){return email;} public String getDisplayName(){return displayName;} public StaffRole getRole(){return role;} public StaffStatus getStatus(){return status;} public OffsetDateTime getInvitedAt(){return invitedAt;}
 public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String value){passwordHash=value;}
 public void updateStatus(StaffStatus next){if(status==StaffStatus.SUSPENDED&&next==StaffStatus.INVITED)throw new IllegalArgumentException("Suspended staff cannot be re-invited");status=next;}
}
