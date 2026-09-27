package com.hms.appointments;
import java.time.OffsetDateTime; import java.util.UUID;
public record AppointmentDto(UUID id, UUID doctorId, String patientName, String patientPhone, String hospitalPatientId, String abhaId, OffsetDateTime startsAt, OffsetDateTime endsAt, AppointmentStatus status, AppointmentSource source, String reason) {
 public static AppointmentDto from(Appointment a, Patient p){return new AppointmentDto(a.getId(),a.getDoctorId(),p.getFullName(),p.getPhone(),p.getHospitalPatientId(),p.getAbhaId(),a.getStartsAt(),a.getEndsAt(),a.getStatus(),a.getSource(),a.getReason());}
}
