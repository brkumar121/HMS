package com.hms.notifications;
import jakarta.validation.constraints.*;
public record UpdateNotificationStatusRequest(@NotNull NotificationStatus status,@Size(max=500) String failureReason) {}
