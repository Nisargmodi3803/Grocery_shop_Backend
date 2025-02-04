package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.OTPRequestDTO;
import com.example.grocery_shop_backend.Dto.OTPResponseDTO;
import com.example.grocery_shop_backend.Dto.OtpStatus;
import com.example.grocery_shop_backend.Entities.Customer;
import com.example.grocery_shop_backend.Exception.InvalidOTPException;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.CustomerRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Service
public class EmailOTPService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CustomerRepository customerRepository;

    private OTPResponseDTO otpResponseDTO;

    // Using ConcurrentHashMap for thread safety
    private final ConcurrentHashMap<String, OtpDetails> otpMap = new ConcurrentHashMap<>();

    private static final Logger logger = Logger.getLogger(EmailOTPService.class.getName());

    public String generateOTP() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }

    public static class OtpDetails {
        private final String OTP;
        private final long timestamp;

        public OtpDetails(String OTP, long timestamp) {
            this.OTP = OTP;
            this.timestamp = timestamp;
        }

        public String getOTP() {
            return OTP;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - timestamp > 600000; // 10 min expiry
        }
    }

    public void sendMail(String to, String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body, true);
        mailSender.send(mimeMessage);
    }

    public Mono<OTPResponseDTO> emailBody(OTPRequestDTO otpRequestDTO, int mailType) {
        String to = otpRequestDTO.getEmail();
        Customer customer = customerRepository.findCustomerByEmail(to);
        String customerName = "";

        if (customer == null && mailType!=1) {
            throw new objectNotFoundException("Customer with Email address " + to + " not found");
        }

        if(mailType==1)
            customerName = otpRequestDTO.getName();
        else
            customerName = customer.getCustomerName();

        try {
            String OTP = generateOTP();
            String subject = "OTP Verification Email";
            String body = generateEmailBody(mailType, customerName, OTP);

            otpMap.put(to, new OtpDetails(OTP, System.currentTimeMillis()));
            sendMail(to, subject, body);

            logger.info("OTP generated and sent to: " + to);
            otpResponseDTO = new OTPResponseDTO(OtpStatus.DELIVERED, "OTP Sent Successfully!!!");
        } catch (MessagingException e) {
            otpResponseDTO = new OTPResponseDTO(OtpStatus.FAILED, e.getMessage());
        }

        return Mono.just(otpResponseDTO);
    }

    private String generateEmailBody(int mailType, String customerName, String OTP) {
        String body = "";
        switch (mailType) {
            case 1 -> body = String.format("""
                    <p>Dear %s,</p>
                    <br>
                    <p>Welcome! Your OTP for registration is:</p>
                    <h2 style="color:blue;">%s</h2>
                    <p>This OTP is valid for 10 minutes.</p>
                    <br>
                    <p>Best regards,<br>Support Team</p>
                    """, customerName, OTP);
            case 2 -> body = String.format("""
                    <p>Dear %s,</p>
                    <br>
                    <p>Your OTP for login is:</p>
                    <h2 style="color:blue;">%s</h2>
                    <p>This OTP is valid for 10 minutes.</p>
                    <br>
                    <p>Best regards,<br>Support Team</p>
                    """, customerName, OTP);
            case 3 -> body = String.format("""
                    <p>Dear %s,</p>
                    <br?
                    <p>Your OTP for password reset is:</p>
                    <h2 style="color:red;">%s</h2>
                    <p>This OTP is valid for 10 minutes.</p>
                    <br>
                    <p>Best regards,<br>Support Team</p>
                    """, customerName, OTP);
            default -> throw new IllegalArgumentException("Invalid mailType");
        }
        return body;
    }

    public Mono<String> verifyOTP(OTPRequestDTO otpRequestDTO) {
        String userOTP = otpRequestDTO.getOtp();
        String email = otpRequestDTO.getEmail();
        OtpDetails otpDetails = otpMap.get(email);

        if (otpDetails == null) {
            return Mono.error(new InvalidOTPException("OTP not found! Please request a new one."));
        }
        if (otpDetails.isExpired()) {
            otpMap.remove(email);
            return Mono.error(new InvalidOTPException("OTP has expired! Please request a new one."));
        }
        if (userOTP.equals(otpDetails.getOTP())) {
//            Customer customer = customerRepository.findCustomerByEmail(email);
//            if (customer == null) {
//                return Mono.error(new objectNotFoundException("Customer with Email " + email + " not found."));
//            }
//            customer.setCustomerOtp(userOTP);
//            customerRepository.save(customer);
//            otpMap.remove(email);
//            logger.info("OTP verified successfully for: " + email);
            return Mono.just("Your OTP is valid");
        } else {
            return Mono.error(new InvalidOTPException("Invalid OTP! Please try again."));
        }
    }
}
