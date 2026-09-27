package com.hms.availability;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;
public record CreateAvailabilityRequest(@Min(1) @Max(7) short dayOfWeek, @NotNull LocalTime startTime,
        @NotNull LocalTime endTime, @Min(5) @Max(480) int slotDurationMinutes,
        @Size(max = 100) String sessionName, @Size(max = 180) String location) { }
