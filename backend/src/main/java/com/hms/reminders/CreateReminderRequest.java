package com.hms.reminders;
import jakarta.validation.constraints.*; import java.time.OffsetDateTime;
public record CreateReminderRequest(@NotNull ReminderChannel channel,@NotNull @Future OffsetDateTime remindAt) {}
