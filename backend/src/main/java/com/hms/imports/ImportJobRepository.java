package com.hms.imports;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface ImportJobRepository extends JpaRepository<ImportJob,UUID>{List<ImportJob> findAllByTenantIdOrderByCreatedAtDesc(UUID tenantId);}
