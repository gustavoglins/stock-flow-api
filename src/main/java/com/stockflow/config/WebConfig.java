package com.stockflow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**") // Allow CORS in all endpoints
                .allowedOrigins("gustavoglins.com") // Allowed domains
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed methods http
                .allowedHeaders("*") // Allowed headers (Authorization, Content-Type, ...)
                .allowCredentials(true); // Allow cookies
    }
}
