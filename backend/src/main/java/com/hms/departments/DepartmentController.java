package com.hms.departments;

import com.hms.tenancy.Tenant;
import com.hms.tenancy.TenantNotFoundException;
import com.hms.tenancy.TenantRepository;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping("/api/hospitals/{tenantSlug}/departments")
public class DepartmentController {

    private final TenantRepository tenantRepository;
    private final DepartmentRepository departmentRepository;

    public DepartmentController(TenantRepository tenantRepository, DepartmentRepository departmentRepository) {
        this.tenantRepository = tenantRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public List<DepartmentDto> list(@PathVariable String tenantSlug) {
        UUID tenantId = tenantId(tenantSlug);
        return departmentRepository.findAllByTenantIdOrderByName(tenantId).stream().map(DepartmentDto::from).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDto create(@PathVariable String tenantSlug, @Valid @RequestBody CreateDepartmentRequest request) {
        UUID tenantId = tenantId(tenantSlug);
        Department department = departmentRepository.save(
                new Department(tenantId, request.code().trim().toUpperCase(), request.name().trim(), request.description()));
        return DepartmentDto.from(department);
    }

    private UUID tenantId(String slug) {
        return tenantRepository.findBySlug(slug).map(Tenant::getId).orElseThrow(() -> new TenantNotFoundException(slug));
    }
}
