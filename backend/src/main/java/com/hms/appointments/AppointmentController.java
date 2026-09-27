package com.hms.appointments;
import com.hms.doctors.Doctor;
import com.hms.doctors.DoctorRepository;
import com.hms.tenancy.*;
import jakarta.validation.Valid;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/hospitals/{tenantSlug}/appointments")
public class AppointmentController {
 private final TenantRepository tenants; private final DoctorRepository doctors; private final PatientRepository patients; private final AppointmentRepository appointments;
 public AppointmentController(TenantRepository tenants, DoctorRepository doctors, PatientRepository patients, AppointmentRepository appointments){this.tenants=tenants;this.doctors=doctors;this.patients=patients;this.appointments=appointments;}
 @GetMapping public List<Appointment> list(@PathVariable String tenantSlug){return appointments.findAllByTenantIdOrderByStartsAtAsc(tenantId(tenantSlug));}
 @PatchMapping("/{appointmentId}/status")
 public Appointment updateStatus(@PathVariable String tenantSlug,@PathVariable UUID appointmentId,@Valid @RequestBody UpdateAppointmentStatusRequest request){
  Appointment appointment=appointments.findById(appointmentId).orElseThrow(()->new IllegalArgumentException("Appointment not found"));
  if(!appointment.getTenantId().equals(tenantId(tenantSlug))) throw new IllegalArgumentException("Appointment does not belong to hospital");
  appointment.updateStatus(request.status()); return appointments.save(appointment);
 }
 @PostMapping @ResponseStatus(HttpStatus.CREATED)
 public Appointment create(@PathVariable String tenantSlug,@Valid @RequestBody CreateAppointmentRequest request){
  UUID tenantId=tenantId(tenantSlug);
  if(!request.endsAt().isAfter(request.startsAt())) throw new IllegalArgumentException("endsAt must be after startsAt");
  Doctor doctor=doctors.findById(request.doctorId()).orElseThrow(()->new IllegalArgumentException("Doctor not found"));
  if(!doctor.getTenantId().equals(tenantId)) throw new IllegalArgumentException("Doctor does not belong to hospital");
  if(appointments.existsByTenantIdAndDoctorIdAndStartsAtAndStatusNot(tenantId,request.doctorId(),request.startsAt(),AppointmentStatus.CANCELLED)) throw new IllegalArgumentException("Doctor slot is already booked");
  Patient patient=patients.findFirstByTenantIdAndPhoneOrderByCreatedAtAsc(tenantId,request.phone()).orElseGet(()->patients.save(new Patient(tenantId,request)));
  return appointments.save(new Appointment(tenantId,patient.getId(),request));
 }
 private UUID tenantId(String slug){return tenants.findBySlug(slug).map(Tenant::getId).orElseThrow(()->new TenantNotFoundException(slug));}
}
