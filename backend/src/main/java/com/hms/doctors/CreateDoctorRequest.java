package com.hms.doctors;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

public record CreateDoctorRequest(
        @NotBlank @Size(max = 180) String displayName,
        @Size(max = 160) String specialty,
        @Size(max = 500) String photoUrl,
        @Size(max = 500) String qualifications,
        @Min(0) Integer experienceYears,
        @Size(max = 500) String languages,
        @Size(max = 1000) String consultationTimings,
        boolean publicVisible,
        Set<UUID> departmentIds
) { }
