package com.hms.services;
import jakarta.validation.constraints.*; import java.math.BigDecimal; import java.util.UUID;
public record CreateServiceRequest(@NotBlank @Size(max=40) String code,@NotBlank @Size(max=180) String name,@Size(max=500) String description,@DecimalMin("0.00") BigDecimal consultationFee,UUID departmentId,boolean publicVisible){}
