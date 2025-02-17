package com.example.grocery_shop_backend.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ensure this path is correct and points to your actual uploads directory
        String uploadDir = "file:D:/Sem-8/Grocery_shop_Backend/uploads/";  // Adjust the path

        registry.addResourceHandler("/uploads/**")  // URL mapping
                .addResourceLocations(uploadDir);  // File system location
    }
}
