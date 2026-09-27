package com.hms.patients;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface PatientIdentifierSettingsRepository extends JpaRepository<PatientIdentifierSettings,UUID>{Optional<PatientIdentifierSettings> findByTenantId(UUID tenantId);}
