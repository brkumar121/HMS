package com.hms.branches;
import java.util.*; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import org.springframework.transaction.annotation.Transactional;
@Transactional
public interface HospitalBranchRepository extends JpaRepository<HospitalBranch,UUID>{List<HospitalBranch> findAllByTenantIdOrderByNameAsc(UUID tenantId); @Modifying @Query("update HospitalBranch b set b.active=:active where b.id=:id and b.tenantId=:tenant") int updateActive(@Param("id")UUID id,@Param("tenant")UUID tenant,@Param("active")boolean active);}
