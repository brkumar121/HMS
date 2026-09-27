package com.hms.services;
import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface HospitalServiceRepository extends JpaRepository<HospitalService,UUID>{List<HospitalService> findAllByTenantIdOrderByNameAsc(UUID tenantId);}
