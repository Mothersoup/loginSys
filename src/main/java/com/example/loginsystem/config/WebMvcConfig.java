package com.example.loginsystem.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
/*
in general we setting CORS in the security config, but here we set it in the WebMvcConfig


 */
public class WebMvcConfig implements WebMvcConfigurer {

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")    // only allow /api/** paths
                .allowedOriginPatterns(
                        "http://localhost:[*]",
                        "http://127.0.0.1:[*]",
                        "http://localhost:3000" )         // allow local user for now
                .allowedHeaders("Authorization", "Content-Type", "Accept")      // allow JWT auth header
                .allowedMethods("GET", "POST", "PUT", "DELETE")                 // restrict to specific methods
                .allowCredentials(true);                // allow cookies

    }

}
