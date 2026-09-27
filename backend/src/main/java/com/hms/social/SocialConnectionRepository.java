package com.hms.social;
import java.util.*; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import org.springframework.transaction.annotation.Transactional;
@Transactional
public interface SocialConnectionRepository extends JpaRepository<SocialConnection,UUID>{List<SocialConnection> findAllByTenantIdOrderByPlatformAsc(UUID tenantId); @Modifying @Query("update SocialConnection s set s.enabled=:enabled where s.id=:id and s.tenantId=:tenant") int updateEnabled(@Param("id")UUID id,@Param("tenant")UUID tenant,@Param("enabled")boolean enabled);}
