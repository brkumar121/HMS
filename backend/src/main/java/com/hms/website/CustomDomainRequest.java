package com.hms.website;
import jakarta.validation.constraints.NotBlank;
public record CustomDomainRequest(@NotBlank String domain) {}
