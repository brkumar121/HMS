package com.hms.queue;
import java.time.LocalDate; import java.util.UUID;
public record PublicCheckInDto(UUID appointmentId,UUID tokenId,int tokenNumber,LocalDate tokenDate,TokenStatus status) {}
