package com.hms.queue;
import jakarta.validation.constraints.NotNull;
public record UpdateTokenStatusRequest(@NotNull TokenStatus status) {}
