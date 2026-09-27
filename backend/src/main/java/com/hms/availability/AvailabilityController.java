package com.hms.availability;

import com.hms.doctors.Doctor;
import com.hms.doctors.DoctorRepository;
import com.hms.tenancy.Tenant;
import com.hms.tenancy.TenantNotFoundException;
import com.hms.tenancy.TenantRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hospitals/{tenantSlug}/doctors/{doctorId}")
public class AvailabilityController {
    private final TenantRepository tenants; private final DoctorRepository doctors; private final DoctorAvailabilityRepository availability;
    private final DoctorLeavePeriodRepository leaves;
    public AvailabilityController(TenantRepository tenants, DoctorRepository doctors, DoctorAvailabilityRepository availability, DoctorLeavePeriodRepository leaves) {
        this.tenants = tenants; this.doctors = doctors; this.availability = availability; this.leaves = leaves;
    }
    @GetMapping("/availability")
    public List<DoctorAvailability> listAvailability(@PathVariable String tenantSlug, @PathVariable UUID doctorId) {
        UUID tenantId = doctorTenant(tenantSlug, doctorId); return availability.findAllByTenantIdAndDoctorIdOrderByDayOfWeekAscStartTimeAsc(tenantId, doctorId);
    }
    @PostMapping("/availability") @ResponseStatus(HttpStatus.CREATED)
    public DoctorAvailability addAvailability(@PathVariable String tenantSlug, @PathVariable UUID doctorId, @Valid @RequestBody CreateAvailabilityRequest request) {
        UUID tenantId = doctorTenant(tenantSlug, doctorId);
        if (!request.endTime().isAfter(request.startTime())) throw new IllegalArgumentException("endTime must be after startTime");
        return availability.save(new DoctorAvailability(tenantId, doctorId, request));
    }
    @GetMapping("/leave-periods")
    public List<DoctorLeavePeriod> listLeaves(@PathVariable String tenantSlug, @PathVariable UUID doctorId) {
        UUID tenantId = doctorTenant(tenantSlug, doctorId); return leaves.findAllByTenantIdAndDoctorIdOrderByStartsAt(tenantId, doctorId);
    }
    @PostMapping("/leave-periods") @ResponseStatus(HttpStatus.CREATED)
    public DoctorLeavePeriod addLeave(@PathVariable String tenantSlug, @PathVariable UUID doctorId, @Valid @RequestBody CreateLeaveRequest request) {
        UUID tenantId = doctorTenant(tenantSlug, doctorId);
        if (!request.endsAt().isAfter(request.startsAt())) throw new IllegalArgumentException("endsAt must be after startsAt");
        return leaves.save(new DoctorLeavePeriod(tenantId, doctorId, request));
    }
    private UUID doctorTenant(String slug, UUID doctorId) {
        UUID tenantId = tenants.findBySlug(slug).map(Tenant::getId).orElseThrow(() -> new TenantNotFoundException(slug));
        Doctor doctor = doctors.findById(doctorId).orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        if (!doctor.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Doctor does not belong to hospital");
        return tenantId;
    }
}
