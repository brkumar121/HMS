package com.hms.queue;
import jakarta.validation.constraints.NotNull; import jakarta.validation.constraints.Size;
public record CreateTokenPriorityRequest(@NotNull PriorityReason reason,@Size(max=500) String note){}
