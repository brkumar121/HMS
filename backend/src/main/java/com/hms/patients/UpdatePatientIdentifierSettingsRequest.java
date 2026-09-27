package com.hms.patients;
import jakarta.validation.constraints.*;
public record UpdatePatientIdentifierSettingsRequest(@NotBlank @Size(max=80) String hospitalIdentifierLabel,boolean hospitalIdentifierRequired,boolean hospitalIdentifierVisible,@Size(max=120) String hospitalIdentifierFormat,boolean abhaEnabled,boolean abhaRequired,@Size(max=1000) String abhaConsentText){}
