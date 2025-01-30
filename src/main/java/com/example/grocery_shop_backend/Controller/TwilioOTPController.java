package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.OTPRequestDTO;
import com.example.grocery_shop_backend.Dto.OtpStatus;
import com.example.grocery_shop_backend.Dto.OTPResponseDTO;
import com.example.grocery_shop_backend.Service.TwilioOTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")
public class TwilioOTPController
{
    @Autowired
    private TwilioOTPService twilioOTPService;

    // POST API {Validate OTP} [Same for All APIs]
    @PostMapping("/validate-OTP")
    public ResponseEntity<String> validatePasswordResetOtp(@RequestBody OTPRequestDTO otpRequestDTO) {
        return twilioOTPService.validateOTP(otpRequestDTO)
                .map(response -> ResponseEntity.ok().body(response))
                .onErrorResume(e -> {
                    // Handle error for invalid OTP, expiry, etc.
                    return Mono.just(ResponseEntity.badRequest().body(e.getMessage()));
                }).block();
    }

    // POST API {Reset Password Twilio OTP}
    @PostMapping("/send-reset-password-OTP")
    public ResponseEntity<OTPResponseDTO> sendOTP(@RequestBody OTPRequestDTO passwordResetRequestDTO)
    {
        return twilioOTPService.sendOTPForPasswordReset(passwordResetRequestDTO)
                .map(response -> ResponseEntity.ok().body(response))
                .onErrorResume(e -> {
                    // Handle any errors like invalid phone number or Twilio failure
                    return Mono.just(ResponseEntity.status(500).body(new OTPResponseDTO(OtpStatus.FAILED, e.getMessage())));
                }).block();
    }


}
