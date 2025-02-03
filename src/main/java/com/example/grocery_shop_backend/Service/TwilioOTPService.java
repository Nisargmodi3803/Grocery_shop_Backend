package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Config.TwilioConfig;
import com.example.grocery_shop_backend.Dto.OTPRequestDTO;
import com.example.grocery_shop_backend.Dto.OtpStatus;
import com.example.grocery_shop_backend.Dto.OTPResponseDTO;
import com.example.grocery_shop_backend.Entities.Customer;
import com.example.grocery_shop_backend.Exception.InvalidOTPException;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.CustomerRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class TwilioOTPService {

    @Autowired
    private TwilioConfig twilioConfig;

    @Autowired
    private CustomerRepository customerRepository;

    private OTPResponseDTO OTPResponseDTO = null;

    // Store OTP details in a map with expiration time tracking
    private Map<String, OtpDetails> otpMap = new HashMap<>();

    // Inner Class for OTP Storing & its Timestamp
    public class OtpDetails {
        private String OTP;
        private long timestamp;

        public OtpDetails() {}

        public OtpDetails(String OTP, long timestamp) {
            this.OTP = OTP;
            this.timestamp = timestamp;
        }

        public String getOTP() {
            return OTP;
        }

        public void setOTP(String OTP) {
            this.OTP = OTP;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - timestamp > 600000; // 10 minutes expiration time
        }
    }

    // Validate OTP
    public Mono<String> validateOTP(OTPRequestDTO otpRequestDTO) {
        String userOTP = otpRequestDTO.getOtp();
        String mobileNumber = otpRequestDTO.getEmail();
        OtpDetails otpDetails = otpMap.get(mobileNumber);

        String sanitizedMobileNumber = mobileNumber.replaceFirst("^\\+91\\s*", "");
        if (otpDetails == null) {
            return Mono.error(new InvalidOTPException("OTP not found! Please request a new one."));
        }
        if (otpDetails.isExpired()) {
            otpMap.remove(mobileNumber); // Clean expired OTP
            return Mono.error(new InvalidOTPException("OTP has expired! Please request a new one."));
        }
        if (userOTP.equals(otpDetails.getOTP())) {
            Customer customer = customerRepository.findCustomerByMobile(sanitizedMobileNumber);
            if (customer == null) {
                return Mono.error(new objectNotFoundException("Customer with Mobile Number " + sanitizedMobileNumber + " not found."));
            } else {
                customer.setCustomerOtp(userOTP);  // Save the OTP in the customer record if needed
                customerRepository.save(customer);
                otpMap.remove(mobileNumber); // Remove OTP after successful validation
                return Mono.just("Your OTP is valid");
            }
        } else {
            return Mono.error(new InvalidOTPException("Invalid OTP! Please try again."));
        }
    }

    // 6 Digit OTP Generator
    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }


     /*
        ******************************************************************************************
                                            SERVICES
        ******************************************************************************************
     */


    // Send OTP For Password Reset Service
    public Mono<OTPResponseDTO> sendOTPForPasswordReset(OTPRequestDTO passwordResetRequestDTO) {
        try {
            PhoneNumber to = new PhoneNumber(passwordResetRequestDTO.getEmail());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrailNumber());

            String OTP = generateOTP();
            String otpMessage = """
                Dear Customer,

                Your One-Time Password (OTP) for resetting your account password is:  %s

                This OTP is valid for 10 minutes. Please do not share it with anyone.

                If you did not request a password reset, please contact our support team immediately.

                Thank you.
                """.formatted(OTP);

            // Send OTP message via Twilio
            Message message = Message.creator(to, from, otpMessage).create();

            // Store OTP details with timestamp
            otpMap.put(passwordResetRequestDTO.getEmail(), new OtpDetails(OTP, System.currentTimeMillis()));

            OTPResponseDTO = new OTPResponseDTO(OtpStatus.DELIVERED, otpMessage);
        } catch (Exception e) {
            OTPResponseDTO = new OTPResponseDTO(OtpStatus.FAILED, e.getMessage());
        }

        return Mono.just(OTPResponseDTO);
    }
}
