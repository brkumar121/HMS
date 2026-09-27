package com.hms.followups;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface FollowUpRepository extends JpaRepository<FollowUp,UUID>{List<FollowUp> findAllByTenantIdOrderByRecommendedFromAsc(UUID tenantId);}
