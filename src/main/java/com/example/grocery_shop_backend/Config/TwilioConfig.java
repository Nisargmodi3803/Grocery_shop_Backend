package com.example.grocery_shop_backend.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twilio")
public class TwilioConfig
{
    private String accountSid;
    private String authToken;
    private String trailNumber;

    public TwilioConfig() {}

    public TwilioConfig(String accountSid, String authToken, String trailNumber) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.trailNumber = trailNumber;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getTrailNumber() {
        return trailNumber;
    }

    public void setTrailNumber(String trailNumber) {
        this.trailNumber = trailNumber;
    }
}
