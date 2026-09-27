package com.hms.appointments;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface PatientRepository extends JpaRepository<Patient,UUID> { Optional<Patient> findFirstByTenantIdAndPhoneOrderByCreatedAtAsc(UUID tenantId,String phone); List<Patient> findAllByTenantId(UUID tenantId); }
