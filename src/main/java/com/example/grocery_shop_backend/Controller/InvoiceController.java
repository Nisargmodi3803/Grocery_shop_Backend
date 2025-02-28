package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.OrderDTO;
import com.example.grocery_shop_backend.Dto.UpdateDeliveryAddressDTO;
import com.example.grocery_shop_backend.Dto.VerifyPaymentDTO;
import com.example.grocery_shop_backend.Entities.Invoice;
import com.example.grocery_shop_backend.Service.InvoiceService;
import com.example.grocery_shop_backend.Service.RazorpayService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.apache.coyote.BadRequestException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class InvoiceController
{
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    RazorpayService razorpayService;

    // GET API {Find Order List by Customer ID}
    @GetMapping("/order-customer-id/{id}")
    public List<Invoice> findOrderListByCustomerId(@PathVariable int id)
    {
        return invoiceService.findOrderListByCustomerId(id);
    }

    // GET API {Find Order List by Customer Mobile}
    @GetMapping("/order-customer-mobile/{mobile}")
    public List<Invoice> findOrderListByCustomerMobile(@PathVariable String mobile)
    {
        return invoiceService.findOrderListByCustomerMobile(mobile);
    }

    // GET API {Find Order List by Customer Email}
    @GetMapping("/orders/{customerEmail}")
    public ResponseEntity<List<Invoice>> findOrderListByCustomerEmail(@PathVariable String customerEmail){
        try {
            return new ResponseEntity<>(invoiceService.findOrderListByCustomerEmail(customerEmail), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // PATCH API {Update Delivery Address}
    @PatchMapping("/update-delivery-address/{invoiceNum}")
    public Invoice updateDeliveryAddress(@PathVariable int invoiceNum,@RequestBody UpdateDeliveryAddressDTO deliveryAddress)
    {
        return invoiceService.updateDeliveryAddress(invoiceNum, deliveryAddress);
    }



    // GET API {Find Order by Invoice Num}
    @GetMapping("/order/{invoiceNum}")
    public Invoice findOrderByInvoiceNum(@PathVariable int invoiceNum)
    {
        return invoiceService.findOrderByInvoiceNum(invoiceNum);
    }

    // PATCH API {Cancel Order}
    @PatchMapping("/cancel-order/{invoiceNum}")
    public ResponseEntity<String> cancelOrder(@PathVariable int invoiceNum)
    {
        boolean status = invoiceService.cancelOrder(invoiceNum);
        if (status)
            return new ResponseEntity<>("Order cancelled", HttpStatus.OK);
        else
            return new ResponseEntity<>("Order Already Cancelled", HttpStatus.BAD_REQUEST);
    }

    // PATCH API {Delete Order}
    @PatchMapping("/delete-order/{invoiceNum}")
    public ResponseEntity<String> deleteOrder(@PathVariable int invoiceNum)
    {
        invoiceService.deleteOrder(invoiceNum);
        return ResponseEntity.status(HttpStatus.OK).body("Order deleted successfully");
    }

    // ✅ For COD & Online Payment (Unified API)
    @PostMapping("/add-order/{customerEmail}")
    public ResponseEntity<Map<String, Object>> addOrder(@PathVariable String customerEmail, @RequestBody OrderDTO orderDTO) {
        try {
            Map<String, Object> response = invoiceService.addOrder(customerEmail, orderDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/create-order")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Map<String, Object> request) {
        try {
            int amount = (int) request.get("amount"); // Ensure amount is in paise
            String currency = (String) request.get("currency");

            // 🔴 Check if Razorpay API keys are correct
            RazorpayClient razorpay = new RazorpayClient("rzp_test_pWCWqEM13KbeBP", "jsUjMcH33tKgEiumJd6Gs3A1");

            // ✅ Convert amount to paise (INR * 100)
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100); // Razorpay works with paise
            orderRequest.put("currency", currency);
            orderRequest.put("payment_capture", 1); // Auto-capture payment

            Order order = razorpay.orders.create(orderRequest);

            Map<String, Object> response = new HashMap<>();
            response.put("id", order.get("id"));
            response.put("amount", order.get("amount"));
            response.put("currency", order.get("currency"));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // 🔴 Print the error to logs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Payment Gateway Error: " + e.getMessage()));
        }
    }



    // ✅ Step 2: Verify Razorpay Payment
    @PostMapping("/verify-payment")
    public ResponseEntity<String> verifyPayment(@RequestBody VerifyPaymentDTO verifyPaymentDTO) {
        try {
//            System.out.println("Invoice number : " + verifyPaymentDTO.getInvoiceNum());
//            System.out.println("Razor Order id : " + verifyPaymentDTO.getRazorpay_order_id());
//            System.out.println("Razor Payment id : " + verifyPaymentDTO.getRazorpay_payment_id());
//            System.out.println("Razor Signature : " + verifyPaymentDTO.getRazorpay_signature());

            invoiceService.verifyPayment(verifyPaymentDTO.getInvoiceNum(), verifyPaymentDTO.getRazorpay_order_id(), verifyPaymentDTO.getRazorpay_payment_id(), verifyPaymentDTO.getRazorpay_signature());
            return ResponseEntity.ok("Payment verified successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment verification failed: " + e.getMessage());
        }
    }

}