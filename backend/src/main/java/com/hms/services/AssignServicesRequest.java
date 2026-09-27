package com.hms.services;
import jakarta.validation.constraints.NotNull; import java.util.Set; import java.util.UUID;
public record AssignServicesRequest(@NotNull Set<UUID> serviceIds){}
