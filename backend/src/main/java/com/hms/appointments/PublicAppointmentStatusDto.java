package com.hms.appointments;
import com.hms.queue.TokenStatus; import java.time.OffsetDateTime; import java.util.UUID;
public record PublicAppointmentStatusDto(UUID appointmentId, OffsetDateTime startsAt, AppointmentStatus appointmentStatus, TokenStatus tokenStatus, Integer tokenNumber, boolean priority) {}
