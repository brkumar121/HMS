package com.hms.social;
import jakarta.validation.constraints.NotNull;
public record UpdateSocialConnectionRequest(@NotNull Boolean enabled) {}
