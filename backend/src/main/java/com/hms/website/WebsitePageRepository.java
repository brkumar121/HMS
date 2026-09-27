package com.hms.website;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface WebsitePageRepository extends JpaRepository<WebsitePage,UUID> { List<WebsitePage> findAllByTenantIdOrderByTitleAsc(UUID tenantId); List<WebsitePage> findAllByTenantIdAndStatusOrderByTitleAsc(UUID tenantId,PageStatus status); Optional<WebsitePage> findByTenantIdAndSlug(UUID tenantId,String slug); }
