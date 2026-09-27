package com.hms.staff;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface HospitalStaffRepository extends JpaRepository<HospitalStaff,UUID> { List<HospitalStaff> findAllByTenantIdOrderByDisplayNameAsc(UUID tenantId); boolean existsByTenantIdAndEmailAndRole(UUID tenantId,String email,StaffRole role); }
