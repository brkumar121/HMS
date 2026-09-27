package com.hms.notifications;
import jakarta.validation.constraints.*; import java.util.UUID;
public record CreateNotificationRequest(UUID patientId,UUID appointmentId,@NotNull NotificationChannel channel,@NotBlank @Size(max=100) String templateKey,@NotBlank @Size(max=180) String recipient,@Size(max=180) String subject,@NotBlank @Size(max=4000) String body,boolean consentRequired){}
