package com.hms.departments;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    List<Department> findAllByTenantIdOrderByName(UUID tenantId);
    boolean existsByTenantIdAndCode(UUID tenantId, String code);
    boolean existsByTenantIdAndName(UUID tenantId, String name);
}
