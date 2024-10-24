package com.stockflow.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final MediaType MEDIA_TYPE_APPLICATION_YAML = MediaType.valueOf("application/x-yaml");

    @Value("${cors.originPatterns:default}")
    private String corsOriginPatterns = "http://localhost:3000,http://localhost:8080,https://gustavoglins.com";

    // ---------------- CORS Config ----------------
    @Override
    public void addCorsMappings(CorsRegistry registry){
        var allowedOrigins = corsOriginPatterns.split(",");
        registry.addMapping("/**") // Allow CORS in all endpoints
                .allowedOrigins(allowedOrigins) // Allowed domains
                .allowedMethods("*") // Allowed methods http
                .allowedHeaders("*") // Allowed headers (Authorization, Content-Type, ...)
                .allowCredentials(true); // Allow cookies and authentication
    }

    // ---------------- Content Negotiation Config ----------------
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer
            .favorParameter(false) // Set media type via Header, not query parameter
            .ignoreAcceptHeader(false) // Respect the Accept header from the request
            .useRegisteredExtensionsOnly(false) // Allow media type resolution without relying solely on file extensions
            .defaultContentType(MediaType.APPLICATION_JSON) // Set default content type as JSON
                .mediaType("json", MediaType.APPLICATION_JSON) // Define JSON as a supported media type
                .mediaType("xml", MediaType.APPLICATION_XML) // Define XML as a supported media type
                .mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YAML); // Define YAML as a supported media type
}
}
