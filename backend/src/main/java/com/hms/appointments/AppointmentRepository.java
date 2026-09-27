package com.hms.appointments;
import java.time.OffsetDateTime;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AppointmentRepository extends JpaRepository<Appointment,UUID> {
 List<Appointment> findAllByTenantIdOrderByStartsAtAsc(UUID tenantId);
 boolean existsByTenantIdAndDoctorIdAndStartsAtAndStatusNot(UUID tenantId, UUID doctorId, OffsetDateTime startsAt, AppointmentStatus status);
}
