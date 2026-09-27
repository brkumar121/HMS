package com.hms.website;
import jakarta.validation.constraints.*;
public record UpdateWebsiteSettingsRequest(@Size(max=500) String logoUrl,@Pattern(regexp="^$|^#[0-9A-Fa-f]{6}$") String primaryColor,@Pattern(regexp="^$|^#[0-9A-Fa-f]{6}$") String secondaryColor,@Size(max=100) String fontFamily,@Size(max=300) String tagline,@Size(max=40) String contactPhone,@Email @Size(max=180) String contactEmail,@Size(max=500) String address,@Size(max=500) String facebookUrl,@Size(max=500) String instagramUrl,@Size(max=500) String youtubeUrl,boolean published){}
