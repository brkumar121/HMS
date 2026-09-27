package com.hms.social;
import jakarta.validation.constraints.*;
public record CreateSocialConnectionRequest(@NotNull SocialPlatform platform,@NotBlank @Size(max=180) String handle,@Size(max=500) String profileUrl){}
