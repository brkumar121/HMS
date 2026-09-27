package com.hms.auth;
import jakarta.validation.constraints.*;
public record ActivateStaffRequest(@NotBlank @Size(min=8,max=200) String password) {}
