package com.hms.reminders;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface AppointmentReminderRepository extends JpaRepository<AppointmentReminder,UUID>{List<AppointmentReminder> findAllByTenantIdOrderByRemindAtAsc(UUID tenantId);}
