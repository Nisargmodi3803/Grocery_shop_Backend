package com.example.grocery_shop_backend.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class RazorpayService {

    @Value("${razorpay.key_id}")
    private String RAZORPAY_KEY;

    @Value("${razorpay.key_secret}")
    private String RAZORPAY_SECRET;

    public String createOrder(Double amount) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(RAZORPAY_KEY, RAZORPAY_SECRET);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount); // Amount in paise
        orderRequest.put("currency", "INR");
        orderRequest.put("payment_capture", 1);

        Order order = razorpay.orders.create(orderRequest);
        return order.get("id");
    }


    public boolean verifyPayment(String orderId, String paymentId, String razorpaySignature) {
        try {
            // ✅ Step 1: Create the payload
            String payload = orderId + "|" + paymentId;

            // ✅ Step 2: Generate HMAC-SHA256 Signature in HEX
            String generatedSignature = hmacSHA256(payload, RAZORPAY_SECRET);
//            System.out.println("Generated signature: " + generatedSignature);
            // ✅ Step 3: Compare the HEX signatures
//            System.out.println(generatedSignature.equals(razorpaySignature));
            return generatedSignature.equals(razorpaySignature);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Corrected HMAC-SHA256 function with HEX Encoding
    private String hmacSHA256(String data, String key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(data.getBytes());

        // Convert byte array to HEX string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hmacBytes) {
            hexString.append(String.format("%02x", b)); // ✅ Convert each byte to hex
        }
        return hexString.toString(); // ✅ Return HEX string (not Base64)
    }


}
