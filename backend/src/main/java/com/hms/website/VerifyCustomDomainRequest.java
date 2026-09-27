package com.hms.website;
import jakarta.validation.constraints.NotBlank;
public record VerifyCustomDomainRequest(@NotBlank String observedToken) {}
