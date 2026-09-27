package com.hms.auth;
public record AuthResponse(String accessToken,String tokenType,long expiresIn,String displayName,String role) {}
