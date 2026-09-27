package com.hms.doctors;

import java.util.Set;
import java.util.UUID;

public record DoctorDto(UUID id, String displayName, String specialty, String photoUrl, String qualifications,
                        Integer experienceYears, String languages, String consultationTimings, boolean publicVisible,
                        boolean active, Set<UUID> departmentIds) { }
