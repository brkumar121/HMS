package com.hms.waitlist;
import jakarta.validation.constraints.*; import java.time.LocalDate; import java.util.UUID;
public record CreateWaitlistRequest(@NotBlank @Size(max=180) String fullName,@NotBlank @Size(max=40) String phone,@NotNull UUID doctorId,@NotNull LocalDate requestedDate,@Size(max=500) String reason,boolean priority) {}
