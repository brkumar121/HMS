package com.hms.availability;

import com.hms.doctors.Doctor;
import com.hms.doctors.DoctorRepository;
import com.hms.appointments.AppointmentRepository;
import com.hms.tenancy.Tenant;
import com.hms.tenancy.TenantNotFoundException;
import com.hms.tenancy.TenantRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hospitals/{tenantSlug}/doctors/{doctorId}")
public class AvailabilityController {
    private final TenantRepository tenants; private final DoctorRepository doctors; private final DoctorAvailabilityRepository availability;
    private final DoctorLeavePeriodRepository leaves; private final AppointmentRepository appointments;
    public AvailabilityController(TenantRepository tenants, DoctorRepository doctors, DoctorAvailabilityRepository availability, DoctorLeavePeriodRepository leaves, AppointmentRepository appointments) {
        this.tenants = tenants; this.doctors = doctors; this.availability = availability; this.leaves = leaves; this.appointments = appointments;
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

    @GetMapping("/available-slots")
    public List<AvailableSlotDto> availableSlots(@PathVariable String tenantSlug, @PathVariable UUID doctorId,
                                                  @RequestParam LocalDate date) {
        UUID tenantId = doctorTenant(tenantSlug, doctorId);
        List<DoctorLeavePeriod> leavePeriods = leaves.findAllByTenantIdAndDoctorIdOrderByStartsAt(tenantId, doctorId); var booked = appointments.findAllByTenantIdOrderByStartsAtAsc(tenantId).stream().filter(a -> a.getDoctorId().equals(doctorId) && a.getStartsAt().toLocalDate().equals(date) && a.getStatus() != com.hms.appointments.AppointmentStatus.CANCELLED).toList();
        List<AvailableSlotDto> result = new ArrayList<>();
        availability.findAllByTenantIdAndDoctorIdOrderByDayOfWeekAscStartTimeAsc(tenantId, doctorId).stream()
                .filter(rule -> rule.isActive() && rule.getDayOfWeek() == date.getDayOfWeek().getValue())
                .forEach(rule -> {
                    OffsetDateTime cursor = date.atTime(rule.getStartTime()).atOffset(ZoneOffset.UTC);
                    OffsetDateTime end = date.atTime(rule.getEndTime()).atOffset(ZoneOffset.UTC);
                    while (!cursor.plusMinutes(rule.getSlotDurationMinutes()).isAfter(end)) {
                        OffsetDateTime slotStart = cursor;
                        OffsetDateTime slotEnd = slotStart.plusMinutes(rule.getSlotDurationMinutes());
                        boolean blocked = leavePeriods.stream().anyMatch(leave -> slotStart.isBefore(leave.getEndsAt()) && slotEnd.isAfter(leave.getStartsAt())) || booked.stream().anyMatch(a -> slotStart.isBefore(a.getEndsAt()) && slotEnd.isAfter(a.getStartsAt()));
                        if (!blocked) result.add(new AvailableSlotDto(slotStart, slotEnd, rule.getSessionName(), rule.getLocation()));
                        cursor = slotEnd;
                    }
                });
        return result;
    }
    private UUID doctorTenant(String slug, UUID doctorId) {
        UUID tenantId = tenants.findBySlug(slug).map(Tenant::getId).orElseThrow(() -> new TenantNotFoundException(slug));
        Doctor doctor = doctors.findById(doctorId).orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        if (!doctor.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Doctor does not belong to hospital");
        return tenantId;
    }
}
