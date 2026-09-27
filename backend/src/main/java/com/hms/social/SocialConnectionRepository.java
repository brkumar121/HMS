package com.hms.social;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface SocialConnectionRepository extends JpaRepository<SocialConnection,UUID>{List<SocialConnection> findAllByTenantIdOrderByPlatformAsc(UUID tenantId);}
