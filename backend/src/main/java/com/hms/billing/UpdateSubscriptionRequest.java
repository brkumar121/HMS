package com.hms.billing;
import jakarta.validation.constraints.*; import java.time.LocalDate;
public record UpdateSubscriptionRequest(@NotBlank @Size(max=80) String planKey,@NotNull SubscriptionStatus status,LocalDate currentPeriodEnd,@PositiveOrZero Integer appointmentLimit,@PositiveOrZero Integer doctorLimit,@PositiveOrZero Integer userLimit,@PositiveOrZero Integer branchLimit,@PositiveOrZero Integer pageLimit,@PositiveOrZero Integer socialLimit,@PositiveOrZero Integer notificationLimit) {}
