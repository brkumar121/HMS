package com.hms.auth;
import jakarta.validation.constraints.*;
public record LoginRequest(@Email @NotBlank String email,@NotBlank @Size(min=8,max=200) String password) {}
