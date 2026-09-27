package com.hms.social;
public interface SocialProvider { SocialPlatform platform(); void sync(SocialConnection connection); }
