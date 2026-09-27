package com.hms.waitlist;
import jakarta.validation.constraints.NotNull;
public record WaitlistStatusRequest(@NotNull WaitlistStatus status) {}
