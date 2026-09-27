package com.hms.billing;
import jakarta.validation.constraints.*; import java.time.LocalDate;
public record UpdateSubscriptionRequest(@NotBlank @Size(max=80) String planKey,@NotNull SubscriptionStatus status,LocalDate currentPeriodEnd,@PositiveOrZero Integer appointmentLimit) {}
