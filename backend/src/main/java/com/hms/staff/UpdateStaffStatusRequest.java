package com.hms.staff;
import jakarta.validation.constraints.NotNull;
public record UpdateStaffStatusRequest(@NotNull StaffStatus status) {}
