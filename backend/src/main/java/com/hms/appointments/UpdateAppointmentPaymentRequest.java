package com.hms.appointments;
import jakarta.validation.constraints.*;
public record UpdateAppointmentPaymentRequest(@NotNull PaymentStatus status,@Size(max=180) String reference) {}
