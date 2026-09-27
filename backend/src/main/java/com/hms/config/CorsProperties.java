package com.hms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hms.cors")
public record CorsProperties(String allowedOrigins) {
}
