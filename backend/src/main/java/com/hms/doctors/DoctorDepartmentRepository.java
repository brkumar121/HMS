package com.hms.doctors;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorDepartmentRepository extends JpaRepository<DoctorDepartment, DoctorDepartment.Key> { }
