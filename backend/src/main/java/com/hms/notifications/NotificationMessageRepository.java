package com.hms.notifications;
import java.util.*; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import org.springframework.transaction.annotation.Transactional;
@Transactional
public interface NotificationMessageRepository extends JpaRepository<NotificationMessage,UUID>{List<NotificationMessage> findAllByTenantIdOrderByCreatedAtDesc(UUID tenantId); List<NotificationMessage> findTop100ByStatusOrderByCreatedAtAsc(NotificationStatus status); @Modifying @Query("update NotificationMessage n set n.status=:status, n.failureReason=:reason where n.id=:id and n.tenantId=:tenant") int updateStatus(@Param("id")UUID id,@Param("tenant")UUID tenant,@Param("status")NotificationStatus status,@Param("reason")String reason);}
