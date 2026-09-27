package com.hms.doctors;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    List<Doctor> findAllByTenantIdOrderByDisplayName(UUID tenantId);
}
