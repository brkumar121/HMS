package com.hms.followups;
import jakarta.validation.constraints.NotNull;
public record UpdateFollowUpStatusRequest(@NotNull FollowUpStatus status){}
