package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.OrderDTO;
import com.example.grocery_shop_backend.Dto.UpdateDeliveryAddressDTO;
import com.example.grocery_shop_backend.Entities.*;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.*;
import com.razorpay.RazorpayException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceService
{
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CouponCodeRepository couponCodeRepository;

    @Autowired
    private DeliveryTimeSlotRepository deliveryTimeSlotRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    private InvoiceDetailService invoiceDetailService;

    @Autowired
    private CartService cartService;

    @Autowired
    private RazorpayService razorpayService;

    // find order list by customerId Service
    public List<Invoice> findOrderListByCustomerId(int customerId)
    {
        List<Invoice> invoices = invoiceRepository.findByCustomerId(customerId);
        if (invoices.isEmpty())
            throw new objectNotFoundException("No order list found for this customer");
        return invoices;
    }

    // find order list by customerMobile Service
    public List<Invoice> findOrderListByCustomerMobile(String customerMobile)
    {
        List<Invoice> invoices = invoiceRepository.findByCustomerMobile(customerMobile);
        if (invoices.isEmpty())
            throw new objectNotFoundException("No order list found for this customer");
        return invoices;
    }

    // Update Delivery Address Service
    @Transactional
    public Invoice updateDeliveryAddress(int invoiceNum, UpdateDeliveryAddressDTO deliveryAddress)
    {
        Invoice invoice = invoiceRepository.findByInvoiceNum(invoiceNum);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime dateTime = LocalDateTime.now();
        String date = dateTime.format(dateFormat);

        if (invoice!=null)
        {
            if(deliveryAddress!=null) {
                invoice.setInvoiceAddress(deliveryAddress.getDeliveryAddress());
                invoice.setInvoiceUpdatedDate(date);
            }
            else
                throw new objectNotFoundException("No Update delivery address found for this invoice");
        }
        else
            throw new objectNotFoundException("No Invoice found for order BI - "+invoiceNum);
        return invoiceRepository.save(invoice);
    }

    // Find Order details by Invoice Num Service
    public Invoice findOrderByInvoiceNum(int invoiceNum)
    {
        Invoice invoice = invoiceRepository.findByInvoiceNum(invoiceNum);
        if (invoice==null)
            throw new objectNotFoundException("No Invoice found for order BI - "+invoiceNum);
        return invoice;
    }

    // Cancel order Service
    @Transactional
    public boolean cancelOrder(int invoiceNum)
    {
        Invoice invoice = invoiceRepository.findByInvoiceNum(invoiceNum);
        if (invoice==null) {
            throw new objectNotFoundException("No Invoice found for order BI - " + invoiceNum);
        }
        else
        {
            if(invoice.getInvoiceStatus()==6)
                return false;
            invoice.setInvoiceStatus(6);// 1 => Pending, 2 => Confirm, 3 => Dispatched, 4 => Delivered, 5 => Rejected, 6 => Canceled
            invoiceRepository.save(invoice);
            return true;
        }
    }

    // Add Order Service
    @Transactional
    public Map<String, Object> addOrder(String customerEmail, OrderDTO orderDTO) throws RazorpayException {
        Customer customer = customerRepository.findCustomerByEmail(customerEmail);
        if (customer == null) throw new objectNotFoundException("Customer not found");

        int invoiceNum = invoiceRepository.findMaxInvoiceNum() + 1;

        // ✅ Formatting Date & Time
        LocalDateTime now = LocalDateTime.now();
        String invoiceDate = now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String invoiceTime = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String financialYear = now.format(DateTimeFormatter.ofPattern("yyyy"));
        String cDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // ✅ Fetch Related Entities
        DeliveryTimeSlot deliveryTimeSlot = deliveryTimeSlotRepository.getDeliveryTimeSlotById(orderDTO.getDeliveryTimeSlotId());
        if (deliveryTimeSlot == null) throw new objectNotFoundException("Delivery time slot not found");

        City city = cityRepository.findCityById(orderDTO.getCityId());
        if (city == null) throw new objectNotFoundException("City not found");

        CouponCode couponCode = (orderDTO.getCouponId() != null) ? couponCodeRepository.findCouponById(orderDTO.getCouponId()) : null;

        // ✅ Creating Invoice
        Invoice invoice = new Invoice();
        invoice.setInvoicePrefix("BI - ");
        invoice.setInvoiceNum(invoiceNum);
        invoice.setInvoiceDate(invoiceDate);
        invoice.setInvoiceTime(invoiceTime);
        invoice.setInvoiceFinancialYear(financialYear);
        invoice.setCustomer(customer);
        invoice.setInvoiceMobile(orderDTO.getMobile());
        invoice.setInvoiceName(orderDTO.getName());
        invoice.setInvoiceEmailId(orderDTO.getEmail());
        invoice.setCity(city);
        invoice.setInvoiceAddress(orderDTO.getAddress());
        invoice.setInvoiceTotalAmount(orderDTO.getTotalAmount());
        invoice.setInvoicePayable(orderDTO.getTotalAmount());
        invoice.setInvoicePaymentMode(orderDTO.getPaymentMode());
        invoice.setInvoicePincode(orderDTO.getPincode());
        invoice.setInvoiceDeliveryCharges(orderDTO.getTotalAmount() >= 500 ? 0 : 15);
        invoice.setCouponCode(couponCode);
        invoice.setInvoiceCouponCodeDiscount(couponCode != null ? orderDTO.getCouponDiscount() : 0.00);
        invoice.setInvoiceSpecialInstruction(orderDTO.getSpecialInstructions());
        invoice.setDeliveryTimeSlot(deliveryTimeSlot);
        invoice.setInvoiceIsHold(2);
        invoice.setInvoiceStatus(2); // Pending
        invoice.setIsDeleted(1);
        invoice.setcDate(cDate);

        if (orderDTO.getPaymentMode() == 1) { // COD
            invoice.setInvoiceRemainingAmount(orderDTO.getTotalAmount());
            invoice.setInvoiceReceivedAmount(0.00);
        } else { // Online Payment
            invoice.setInvoiceRemainingAmount(0.00);
            invoice.setInvoiceReceivedAmount(orderDTO.getTotalAmount());
        }

        invoiceRepository.save(invoice);

        // ✅ Save Ordered Products & Clear Cart
        invoiceDetailService.addProductOrder(orderDTO.getCarts(), invoiceNum, orderDTO.getTotalAmount());

        for (Cart cart : orderDTO.getCarts()) {
            cartService.removeFromCart(customerEmail, cart.getProduct().getId());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("invoiceNum", invoiceNum);

        // ✅ Generate Razorpay Order ID (Only for Online Payment)
        if (orderDTO.getPaymentMode() == 2) {
            String razorpayOrderId = razorpayService.createOrder(invoice.getInvoicePayable());
            invoice.setInvoiceRazorpayOrderId(razorpayOrderId);
            invoice.setInvoiceRazorpayStatus(1); // Pending
            invoiceRepository.save(invoice);
            response.put("razorpayOrderId", razorpayOrderId);
        }

        return response;
    }

    public void verifyPayment(int invoiceNum, String razorpay_order_id, String razorpay_payment_id, String razorpay_signature) {
        Invoice invoice = invoiceRepository.findByInvoiceNum(invoiceNum);
        if (invoice == null) throw new objectNotFoundException("Invoice not found");

//        System.out.println("Invoice: " + invoice);

        boolean isValid = razorpayService.verifyPayment(razorpay_order_id, razorpay_payment_id, razorpay_signature);
        if (!isValid) throw new RuntimeException("Invalid payment signature");

//        System.out.println("Is Valid: " + isValid);

        // ✅ Update Invoice After Successful Payment
        invoice.setInvoiceRazorpayPaymentId(razorpay_payment_id);
        invoice.setInvoiceRazorpaySignature(razorpay_signature);
        invoice.setInvoiceRazorpayStatus(2); // Paid
        invoice.setInvoiceStatus(2); // Order Confirmed
        invoiceRepository.save(invoice);
    }

    // Delete Order Service [Admin]
    @Transactional
    public void deleteOrder(int invoiceNum)
    {
        Invoice invoice = invoiceRepository.findByInvoiceNum(invoiceNum);
        if(invoice==null)
            throw new objectNotFoundException("No Invoice found for order BI - "+invoiceNum);
        else
        {
            invoice.setIsDeleted(2);
            invoiceRepository.save(invoice);

            List<InvoiceDetail> invoiceDetails = invoiceDetailRepository.autoDeleteByInvoiceNum(invoiceNum);
            for (InvoiceDetail invoiceDetail : invoiceDetails)
            {
                invoiceDetail.setIsDeleted(2);
                invoiceDetailRepository.save(invoiceDetail);
            }
        }
    }
}
