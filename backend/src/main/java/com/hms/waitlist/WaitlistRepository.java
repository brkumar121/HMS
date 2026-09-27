package com.hms.waitlist;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface WaitlistRepository extends JpaRepository<WaitlistEntry,UUID>{List<WaitlistEntry> findAllByTenantIdOrderByPriorityDescCreatedAtAsc(UUID tenantId);Optional<WaitlistEntry> findByIdAndTenantId(UUID id,UUID tenantId);}
