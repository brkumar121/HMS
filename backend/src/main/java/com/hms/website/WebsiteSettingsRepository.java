package com.hms.website;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface WebsiteSettingsRepository extends JpaRepository<WebsiteSettings,UUID>{Optional<WebsiteSettings> findByTenantId(UUID tenantId);}
