package com.hms.website;
import jakarta.validation.constraints.*;
public record CreateContentRequest(@NotNull ContentCategory category,@NotBlank @Size(max=240) String title,@NotBlank @Pattern(regexp="[a-z0-9-]+") @Size(max=240) String slug,@Size(max=500) String summary,@NotBlank @Size(max=12000) String body,@Size(max=500) String imageUrl,@NotNull ContentStatus status){}
