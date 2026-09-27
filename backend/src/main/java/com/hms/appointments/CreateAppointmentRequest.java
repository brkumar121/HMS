package com.hms.appointments;
import jakarta.validation.constraints.*;
import java.time.OffsetDateTime;
import java.util.UUID;
public record CreateAppointmentRequest(@NotBlank @Size(max=180) String fullName, @NotBlank @Size(max=40) String phone,
 @Size(max=100) String hospitalPatientId, @Size(max=100) String abhaId, @NotNull UUID doctorId,
 @NotNull OffsetDateTime startsAt, @NotNull OffsetDateTime endsAt, @NotNull AppointmentSource source,
 @Size(max=500) String reason, @Size(max=1000) String notes) {}
