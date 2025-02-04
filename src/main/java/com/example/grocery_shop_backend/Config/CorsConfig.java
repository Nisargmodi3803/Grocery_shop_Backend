package com.example.grocery_shop_backend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Allow requests from your frontend URL
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));

        // Allow credentials (required for cookies, like the JWT token)
        config.setAllowCredentials(true);

        // Allowed HTTP methods
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allowed headers
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));

        // Expose the necessary headers to the frontend
        config.setExposedHeaders(Arrays.asList("Authorization", "authToken"));

        // Allow all headers for client-side requests
        config.addAllowedHeader("*");

        // Allow all origins if required (Not recommended for production without restriction)
        // config.addAllowedOrigin("*");

        // Set the max age for pre-flight requests (in seconds)
        config.setMaxAge(3600L); // 1 hour

        // Register the configuration with a URL pattern
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
