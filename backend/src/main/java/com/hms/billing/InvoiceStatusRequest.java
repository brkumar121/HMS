package com.hms.billing;
import jakarta.validation.constraints.NotNull; import jakarta.validation.constraints.Size;
public record InvoiceStatusRequest(@NotNull InvoiceStatus status,@Size(max=500) String failureReason) {}
