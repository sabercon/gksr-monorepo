package com.example.web

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.web.cors.CorsConfiguration

@ConfigurationProperties("web.cors")
data class CorsProperties(
    val allowedOriginPatterns: List<String> = listOf(CorsConfiguration.ALL),
    val allowedMethods: List<String> = listOf(CorsConfiguration.ALL),
    val allowedHeaders: List<String> = listOf(CorsConfiguration.ALL),
    val allowCredentials: Boolean = false,
)
