package com.hms.queue;
import java.time.LocalDate; import java.util.UUID;
public record QueueTokenDto(UUID id, int tokenNumber, LocalDate tokenDate, TokenStatus status, boolean priority, PriorityReason priorityReason) { public static QueueTokenDto from(QueueToken t){return new QueueTokenDto(t.getId(),t.getTokenNumber(),t.getTokenDate(),t.getStatus(),t.isPriority(),t.getPriorityReason());} }
