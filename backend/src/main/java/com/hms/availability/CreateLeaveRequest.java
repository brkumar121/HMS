package com.hms.availability;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
public record CreateLeaveRequest(@NotNull OffsetDateTime startsAt, @NotNull OffsetDateTime endsAt, @Size(max = 300) String reason) { }
