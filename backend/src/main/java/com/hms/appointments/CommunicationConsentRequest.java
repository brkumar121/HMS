package com.hms.appointments;
import jakarta.validation.constraints.NotNull;
public record CommunicationConsentRequest(@NotNull Boolean smsConsent,@NotNull Boolean emailConsent,@NotNull Boolean whatsappConsent) {}
