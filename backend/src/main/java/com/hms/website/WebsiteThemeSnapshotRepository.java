package com.hms.website;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface WebsiteThemeSnapshotRepository extends JpaRepository<WebsiteThemeSnapshot,UUID>{List<WebsiteThemeSnapshot> findAllByTenantIdOrderByCreatedAtDesc(UUID tenantId);Optional<WebsiteThemeSnapshot> findByIdAndTenantId(UUID id,UUID tenantId);}
