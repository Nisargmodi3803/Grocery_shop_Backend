package com.example.grocery_shop_backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@SpringBootApplication
public class GroceryShopBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroceryShopBackendApplication.class, args);
    }

}
