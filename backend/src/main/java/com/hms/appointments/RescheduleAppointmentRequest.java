package com.hms.appointments;
import jakarta.validation.constraints.NotNull; import java.time.OffsetDateTime;
public record RescheduleAppointmentRequest(@NotNull OffsetDateTime startsAt,@NotNull OffsetDateTime endsAt) {}
