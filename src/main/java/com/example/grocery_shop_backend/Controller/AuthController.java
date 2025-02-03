package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Service.FirebaseAuthService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private FirebaseAuthService firebaseAuthService;

    @PostMapping("/verify-otp")
    public Map<String, Object> verifyOtp(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String token = request.get("token");

        try {
            // Verify Firebase ID Token
            FirebaseToken decodedToken = firebaseAuthService.verifyToken(token);

            // Get UID from token
            String uid = decodedToken.getUid();

            // Fetch user details from Firebase
            UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);

            // Send success response with user details
            response.put("status", "success");
            response.put("uid", uid);
            response.put("phone", userRecord.getPhoneNumber());
            response.put("email", userRecord.getEmail());  // Optionally include email if needed

        } catch (FirebaseAuthException e) {
            // Log the error for debugging
            System.err.println("Error verifying token: " + e.getMessage());

            // Send error response if token is invalid
            response.put("status", "error");
            response.put("message", "Invalid OTP token");
        }

        return response;
    }
}
