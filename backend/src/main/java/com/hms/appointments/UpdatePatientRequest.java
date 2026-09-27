package com.hms.appointments;
import jakarta.validation.constraints.*;
public record UpdatePatientRequest(@NotBlank @Size(max=180) String fullName,@NotBlank @Size(max=40) String phone,@Size(max=100) String hospitalPatientId,@Size(max=100) String abhaId) {}
