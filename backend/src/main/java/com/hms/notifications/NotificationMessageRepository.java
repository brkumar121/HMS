package com.hms.notifications;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface NotificationMessageRepository extends JpaRepository<NotificationMessage,UUID>{List<NotificationMessage> findAllByTenantIdOrderByCreatedAtDesc(UUID tenantId);}
