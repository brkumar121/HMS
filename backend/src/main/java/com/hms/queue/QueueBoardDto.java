package com.hms.queue;
import java.time.LocalDate; import java.util.List;
public record QueueBoardDto(LocalDate date,List<QueueTokenDto> tokens) {}
