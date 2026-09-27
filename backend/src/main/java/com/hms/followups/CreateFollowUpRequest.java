package com.hms.followups;
import jakarta.validation.constraints.*; import java.time.LocalDate; import java.util.UUID;
public record CreateFollowUpRequest(@NotNull UUID patientId,UUID sourceAppointmentId,UUID doctorId,LocalDate recommendedFrom,LocalDate recommendedTo,@NotBlank @Size(max=500) String reason,@NotNull FollowUpPriority priority,@Size(max=160) String responsibleTeam,@Size(max=1000) String notes){}
