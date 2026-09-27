package com.hms.availability;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, UUID> {
    List<DoctorAvailability> findAllByTenantIdAndDoctorIdOrderByDayOfWeekAscStartTimeAsc(UUID tenantId, UUID doctorId);
}
