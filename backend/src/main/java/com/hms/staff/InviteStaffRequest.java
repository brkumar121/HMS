package com.hms.staff;
import jakarta.validation.constraints.*;
public record InviteStaffRequest(@Email @NotBlank String email,@NotBlank @Size(max=160) String displayName,@NotNull StaffRole role) {}
