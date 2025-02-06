package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.OTPRequestDTO;
import com.example.grocery_shop_backend.Dto.OTPResponseDTO;
import com.example.grocery_shop_backend.Dto.OtpStatus;
import com.example.grocery_shop_backend.Entities.Customer;
import com.example.grocery_shop_backend.Exception.ExpireOTPException;
import com.example.grocery_shop_backend.Exception.InvalidOTPException;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.CustomerRepository;
import com.example.grocery_shop_backend.Service.CustomerService;
import com.example.grocery_shop_backend.Service.EmailOTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/*
    EMAIL TYPE [OTP VERIFICATION]

    1 => Registration time Email OTP.
    2 => Login with OTP.
    3 => Forgot Password.
*/

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class EmailOTPController
{
    @Autowired
    private EmailOTPService emailOTPService;

    @Autowired
    private CustomerRepository customerRepository;

    // POST API {Send Email for Registration}
    @PostMapping("/send-email-registration")
    public ResponseEntity<Mono<OTPResponseDTO>> sendEmailRegistration(@RequestBody OTPRequestDTO otpRequestDTO)
    {
        Customer customer = customerRepository.findCustomerByEmail(otpRequestDTO.getEmail());
        if(customer == null) {
            Mono<OTPResponseDTO> response = emailOTPService.emailBody(otpRequestDTO, 1);
            return ResponseEntity.ok(response);
        }
        else{
            OTPResponseDTO otpResponseDTO = new OTPResponseDTO(OtpStatus.FAILED, "Email Already Registered!!! Try a different Email");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Mono.just(otpResponseDTO));

        }
    }

    // POST API {Send Email for Login with OTP}
    @PostMapping("/send-email-login")
    public ResponseEntity<Mono<OTPResponseDTO>> sendEmailLogin(@RequestBody OTPRequestDTO otpRequestDTO)
    {
        Mono<OTPResponseDTO> response = emailOTPService.emailBody(otpRequestDTO, 2);
        return ResponseEntity.ok(response);
    }

    // POST API {Send Email for Forgot Password}
    @PostMapping("/send-email-forgot-password")
    public ResponseEntity<Mono<OTPResponseDTO>> sendEmailForgotPassword(@RequestBody OTPRequestDTO otpRequestDTO)
    {
        Mono<OTPResponseDTO> response = emailOTPService.emailBody(otpRequestDTO, 3);
        return ResponseEntity.ok(response);
    }

    // POST API {Verify OTP}
    @PostMapping("/verify-otp")
    public Mono<ResponseEntity<String>> verifyOTP(@RequestBody OTPRequestDTO otpRequestDTO) {
        return emailOTPService.verifyOTP(otpRequestDTO)
                .map(response -> ResponseEntity.ok(response)) // OTP is valid (200 OK)

                .onErrorResume(InvalidOTPException.class, ex ->
                        Mono.just(ResponseEntity.badRequest().body(ex.getMessage())) // 400 Bad Request
                )

                .onErrorResume(ExpireOTPException.class, ex ->
                        Mono.just(ResponseEntity.status(HttpStatus.GONE).body(ex.getMessage())) // 410 Gone (Expired OTP)
                )

                .onErrorResume(objectNotFoundException.class, ex ->
                        Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage())) // 404 Not Found
                );
    }


}
