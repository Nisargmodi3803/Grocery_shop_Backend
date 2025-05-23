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
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
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
    public ResponseEntity<Invoice> findOrderByInvoiceNum(@PathVariable int invoiceNum)
    {
        try {
            return new ResponseEntity<>(invoiceService.findOrderByInvoiceNum(invoiceNum), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // PATCH API {Cancel Order}
    @PatchMapping("/cancel-order/{invoiceNum}")
    public ResponseEntity<String> cancelOrder(@PathVariable int invoiceNum)
    {
        try {
            boolean status = invoiceService.cancelOrder(invoiceNum);
            if (status)
                return new ResponseEntity<>("Order cancelled", HttpStatus.OK);
            else
                return new ResponseEntity<>("Order Already Cancelled", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // PATCH API {Delete Order}
    @PatchMapping("/delete-order/{invoiceNum}")
    public ResponseEntity<String> deleteOrder(@PathVariable int invoiceNum)
    {
        try {
            invoiceService.deleteOrder(invoiceNum);
            return ResponseEntity.status(HttpStatus.OK).body("Order deleted successfully");
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
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

    // GET API {Find Invoices Between Dates}
    @GetMapping("/invoices-between-dates")
    public ResponseEntity<List<Invoice>> findInvoicesBetweenDates(@RequestParam String startDate, @RequestParam String endDate) {
        try {
            return ResponseEntity.ok(invoiceService.findAllInvoiceBetweenDates(startDate,endDate));
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // PATCH API {Assign Delivery Boy}
    @PatchMapping("/admin/assign-boy")
    public ResponseEntity<String> assignDeliveryBoy(@RequestParam int deliveryBoyId,@RequestParam int invoiceNum){
        try {
            invoiceService.AssignDeliveryBoy(deliveryBoyId,invoiceNum);
            return ResponseEntity.ok("Assign Delivery Boy successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assign Delivery Boy failed: " + e.getMessage());
        }
    }

    // GET API {Count Total Invoice}
    @GetMapping("/admin/count-invoice")
    public ResponseEntity<Integer> countInvoice() {
        return ResponseEntity.ok(invoiceService.countInvoice());
    }

    // GET API {Count Total Pending Invoice}
    @GetMapping("/admin/count-pending-invoice")
    public ResponseEntity<Integer> countPendingInvoice() {
        return ResponseEntity.ok(invoiceService.countPendingInvoice());
    }

    // GET API {Count Total Delivered Invoice}
    @GetMapping("/admin/count-delivered-invoice")
    public ResponseEntity<Integer> countDeliveredInvoice() {
        return ResponseEntity.ok(invoiceService.countDeliveredInvoice());
    }

    // GET API {Count Total Confirm Invoice}
    @GetMapping("/admin/count-confirm-invoice")
    public ResponseEntity<Integer> countConfirmInvoice() {
        return ResponseEntity.ok(invoiceService.countConfirmInvoice());
    }

    // GET API {Count Total Dispatched Invoice}
    @GetMapping("/admin/count-dispatched-invoice")
    public ResponseEntity<Integer> countDispatchedInvoice() {
        return ResponseEntity.ok(invoiceService.countDispatchedInvoice());
    }

    // GET API {Count Total Rejected Invoice}
    @GetMapping("/admin/count-rejected-invoice")
    public ResponseEntity<Integer> countRejectedInvoice() {
        return ResponseEntity.ok(invoiceService.countRejectedInvoice());
    }

    // GET API {Count Total  Canceled Invoice}
    @GetMapping("/admin/count-canceled-invoice")
    public ResponseEntity<Integer> countCanceledInvoice() {
        return ResponseEntity.ok(invoiceService.countCanceledInvoice());
    }

    // PATCH API {Change Status of Invoice}
    @PatchMapping("/change-status")
    public ResponseEntity<String> changeStatusAndDeliveryDate(@RequestParam int invoiceNum,@RequestParam int status,@RequestParam String date){
        try {
            invoiceService.changeStatusAndDeliveryDate(invoiceNum,status,date);
            return ResponseEntity.ok("Change status successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Change status failed: " + e.getMessage());
        }
    }

    // GET API {Today's Pending Orders}
    @GetMapping("/admin/today-pending")
    public ResponseEntity<List<Invoice>> findInvoiceByDatePending(@RequestParam String date) {
        try {
            return ResponseEntity.ok(invoiceService.findInvoiceByDatePending(date));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonList(new Invoice()));
        }
    }

    // GET API {Today's Confirm Orders}
    @GetMapping("/admin/today-confirm")
    public ResponseEntity<List<Invoice>> findInvoiceByDateConfirm(@RequestParam String date) {
        try {
            return ResponseEntity.ok(invoiceService.findInvoiceByDateConfirm(date));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonList(new Invoice()));
        }
    } // GET API {Today's Delivered Orders}
    @GetMapping("/admin/today-delivered")
    public ResponseEntity<List<Invoice>> findInvoiceByDateDelivered(@RequestParam String date) {
        try {
            return ResponseEntity.ok(invoiceService.findInvoiceByDateDelivered(date));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonList(new Invoice()));
        }
    }

//    // GET API {Find All Last 30 days' Invoice}
//    @GetMapping("/invoice-30-days/customerEmail")
//    public ResponseEntity<List<Invoice>> findAllLast30DaysInvoice(@PathVariable String customerEmail){
//        System.out.println("Email"+customerEmail);
//            return new ResponseEntity<>(invoiceService.findAllLast30DaysInvoice(customerEmail), HttpStatus.OK);
//
//    }
//
//
//    // GET API {Find All Last 3 Months' Invoice}
//    @GetMapping("/invoice-90-days/customerEmail")
//    public ResponseEntity<List<Invoice>> findAllLast90DaysInvoice(@PathVariable String customerEmail){
//        try {
//            return new ResponseEntity<>(invoiceService.findAllLast90DaysInvoice(customerEmail), HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    // GET API {Find All Last 3 Months' Invoice}
//    @GetMapping("/invoice-180-days/customerEmail")
//    public ResponseEntity<List<Invoice>> findAllLast180DaysInvoice(@PathVariable String customerEmail){
//        try {
//            return new ResponseEntity<>(invoiceService.findAllLast180DaysInvoice(customerEmail), HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }


}