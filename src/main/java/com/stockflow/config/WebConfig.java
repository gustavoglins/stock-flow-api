package com.stockflow.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors.originPatterns:default}")
    private String corsOriginPatterns = "http://localhost:3000,http://localhost:8080,https://gustavoglins.com";

    @Override
    public void addCorsMappings(CorsRegistry registry){
        var allowedOrigins = corsOriginPatterns.split(",");
        registry.addMapping("/**") // Allow CORS in all endpoints
                .allowedOrigins(allowedOrigins) // Allowed domains
                .allowedMethods("*") // Allowed methods http
                .allowedHeaders("*") // Allowed headers (Authorization, Content-Type, ...)
                .allowCredentials(true); // Allow cookies and authentication
    }
}
