package com.hms.doctors;

import com.hms.departments.Department;
import com.hms.departments.DepartmentRepository;
import com.hms.tenancy.Tenant;
import com.hms.tenancy.TenantNotFoundException;
import com.hms.tenancy.TenantRepository;
import com.hms.billing.PlanLimitService;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hospitals/{tenantSlug}/doctors")
public class DoctorController {
    private final TenantRepository tenants;
    private final DoctorRepository doctors;
    private final DepartmentRepository departments;
    private final DoctorDepartmentRepository mappings; private final PlanLimitService planLimits;

    public DoctorController(TenantRepository tenants, DoctorRepository doctors, DepartmentRepository departments,
                            DoctorDepartmentRepository mappings, PlanLimitService planLimits) {
        this.tenants = tenants; this.doctors = doctors; this.departments = departments; this.mappings = mappings; this.planLimits = planLimits;
    }

    @GetMapping
    public List<DoctorDto> list(@PathVariable String tenantSlug) {
        UUID tenantId = tenantId(tenantSlug);
        planLimits.checkDoctor(tenantId);
        return doctors.findAllByTenantIdOrderByDisplayName(tenantId).stream().map(this::dto).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDto create(@PathVariable String tenantSlug, @Valid @RequestBody CreateDoctorRequest request) {
        UUID tenantId = tenantId(tenantSlug);
        Set<UUID> departmentIds = request.departmentIds() == null ? Set.of() : request.departmentIds();
        List<Department> validDepartments = departments.findAllById(departmentIds).stream()
                .filter(department -> department.getTenantId().equals(tenantId)).toList();
        if (validDepartments.size() != departmentIds.size()) {
            throw new IllegalArgumentException("All departmentIds must belong to the hospital");
        }
        Doctor doctor = doctors.save(new Doctor(tenantId, request));
        validDepartments.forEach(department -> mappings.save(new DoctorDepartment(doctor.getId(), department.getId())));
        return dto(doctor);
    }

    private DoctorDto dto(Doctor doctor) {
        Set<UUID> departmentIds = new HashSet<>();
        mappings.findAll().stream().filter(mapping -> mapping.getKey().getDoctorId().equals(doctor.getId()))
                .forEach(mapping -> departmentIds.add(mapping.getKey().getDepartmentId()));
        return new DoctorDto(doctor.getId(), doctor.getDisplayName(), doctor.getSpecialty(), doctor.getPhotoUrl(),
                doctor.getQualifications(), doctor.getExperienceYears(), doctor.getLanguages(), doctor.getConsultationTimings(),
                doctor.isPublicVisible(), doctor.isActive(), departmentIds);
    }

    private UUID tenantId(String slug) { return tenants.findBySlug(slug).map(Tenant::getId).orElseThrow(() -> new TenantNotFoundException(slug)); }
}
