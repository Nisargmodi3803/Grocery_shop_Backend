package com.example.grocery_shop_backend.Config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {
    private static final Long MAX_AGE = 3600L;
    private static final int CORS_FILTER_ORDER = -102;

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow credentials (for cookies & Authorization headers)
        config.setAllowCredentials(true);

        // Dynamic allowed origins (Use "*" if you don't need credentials)
        config.setAllowedOriginPatterns(List.of("http://localhost:3000", "http://localhost:3001", "https://your-production-domain.com"));


        // Allowed headers
        config.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "Accept",
                "Origin",
                "X-Requested-With"
        ));

        // Expose headers to frontend (needed for JWT tokens)
        config.setExposedHeaders(List.of("Authorization"));

        // Allowed HTTP Methods
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));

        // Set cache max age
        config.setMaxAge(MAX_AGE);

        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));

        // Ensure CORS is processed before Spring Security
        bean.setOrder(CORS_FILTER_ORDER);
        return bean;
    }
}
