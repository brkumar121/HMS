package com.hms.appointments;
import jakarta.validation.constraints.NotNull;
public record UpdateAppointmentStatusRequest(@NotNull AppointmentStatus status) { }
