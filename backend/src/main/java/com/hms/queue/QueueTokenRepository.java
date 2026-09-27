package com.hms.queue;
import java.time.LocalDate; import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface QueueTokenRepository extends JpaRepository<QueueToken,UUID>{List<QueueToken> findAllByTenantIdAndDoctorIdAndTokenDateOrderByPriorityDescTokenNumberAsc(UUID t,UUID d,LocalDate date); Optional<QueueToken> findFirstByTenantIdAndDoctorIdAndTokenDateOrderByTokenNumberDesc(UUID t,UUID d,LocalDate date); Optional<QueueToken> findByAppointmentId(UUID appointmentId);}
