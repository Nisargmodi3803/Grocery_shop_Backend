package com.example.grocery_shop_backend;

import com.example.grocery_shop_backend.Config.TwilioConfig;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@Slf4j
public class GroceryShopBackendApplication {

    @Autowired
    private TwilioConfig twilioConfig;

    @PostConstruct
    public void initTwilio() {
        if (twilioConfig.getAccountSid() != null && twilioConfig.getAuthToken() != null) {
            Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
//            log.info("Twilio initialized with SID: {}", twilioConfig.getAccountSid());
        } else {
//            log.error("Twilio configuration is missing accountSid or authToken.");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(GroceryShopBackendApplication.class, args);
    }
}
