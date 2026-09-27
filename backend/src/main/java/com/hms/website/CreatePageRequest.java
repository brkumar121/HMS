package com.hms.website;
import jakarta.validation.constraints.*;
public record CreatePageRequest(@NotBlank @Pattern(regexp="[a-z0-9-]+") @Size(max=120) String slug,@NotBlank @Size(max=180) String title,@NotBlank @Size(max=20000) String body,@NotNull PageStatus status) {}
