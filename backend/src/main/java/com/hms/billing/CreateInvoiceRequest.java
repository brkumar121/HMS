package com.hms.billing;
import jakarta.validation.constraints.*; import java.math.BigDecimal; import java.time.LocalDate;
public record CreateInvoiceRequest(@NotBlank @Size(max=80) String invoiceNumber,@NotBlank @Size(max=80) String planKey,@NotNull @DecimalMin("0.00") BigDecimal subtotal,@NotNull @DecimalMin("0.00") BigDecimal taxAmount,@NotBlank @Size(min=3,max=3) String currency,@NotNull LocalDate issuedOn,@NotNull LocalDate dueOn) {}
