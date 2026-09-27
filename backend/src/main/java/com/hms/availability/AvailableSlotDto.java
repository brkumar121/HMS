package com.hms.availability;
import java.time.OffsetDateTime;
public record AvailableSlotDto(OffsetDateTime startsAt, OffsetDateTime endsAt, String sessionName, String location) { }
