package com.hms.website;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface WebsiteContentRepository extends JpaRepository<WebsiteContentItem,UUID>{List<WebsiteContentItem> findAllByTenantIdOrderByUpdatedAtDesc(UUID tenantId);List<WebsiteContentItem> findAllByTenantIdAndStatusOrderByPublishedAtDesc(UUID tenantId,ContentStatus status);Optional<WebsiteContentItem> findByTenantIdAndSlugAndStatus(UUID tenantId,String slug,ContentStatus status);}
