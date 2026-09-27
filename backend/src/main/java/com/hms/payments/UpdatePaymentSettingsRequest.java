package com.hms.payments;
import jakarta.validation.constraints.*;
public record UpdatePaymentSettingsRequest(@NotNull PaymentMode mode,@NotBlank @Size(min=3,max=3) String currency,@Size(max=2000) String instructions,@Size(max=180) String bankAccountName,@Size(max=80) String bankAccountNumber,@Size(max=20) String bankIfsc,@Size(max=120) String upiId,@Size(max=500) String paymentLink) {}
