package com.hms.website;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomDomainRepository extends JpaRepository<CustomDomain,UUID>{List<CustomDomain> findAllByTenantIdOrderByCreatedAtDesc(UUID tenantId);Optional<CustomDomain> findByIdAndTenantId(UUID id,UUID tenantId);}
