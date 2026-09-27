package com.hms.queue;
import jakarta.validation.constraints.*; import java.util.UUID;
public record PublicCheckInRequest(@NotNull UUID appointmentId,@NotBlank @Size(max=40) String phone) {}
