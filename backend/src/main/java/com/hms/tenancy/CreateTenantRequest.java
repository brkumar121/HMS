package com.hms.tenancy;
import jakarta.validation.constraints.*;
public record CreateTenantRequest(@NotBlank @Size(max=80) @Pattern(regexp="[a-z0-9-]+") String slug,@NotBlank @Size(max=180) String name,@Size(max=180) String legalName,@Email @Size(max=180) String primaryEmail,@Size(max=40) String primaryPhone) {}
