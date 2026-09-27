package com.hms.branches;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface HospitalBranchRepository extends JpaRepository<HospitalBranch,UUID>{List<HospitalBranch> findAllByTenantIdOrderByNameAsc(UUID tenantId);}
