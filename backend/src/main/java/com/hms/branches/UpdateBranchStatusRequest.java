package com.hms.branches;
import jakarta.validation.constraints.NotNull;
public record UpdateBranchStatusRequest(@NotNull Boolean active) {}
