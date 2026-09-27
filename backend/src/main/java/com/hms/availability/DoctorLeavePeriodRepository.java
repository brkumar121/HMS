package com.hms.availability;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DoctorLeavePeriodRepository extends JpaRepository<DoctorLeavePeriod, UUID> {
    List<DoctorLeavePeriod> findAllByTenantIdAndDoctorIdOrderByStartsAt(UUID tenantId, UUID doctorId);
}
