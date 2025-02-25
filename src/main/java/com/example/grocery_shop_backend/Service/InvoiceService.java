package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.OrderDTO;
import com.example.grocery_shop_backend.Dto.UpdateDeliveryAddressDTO;
import com.example.grocery_shop_backend.Entities.*;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.*;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    public void addOrder(String customerEmail, OrderDTO orderDTO) throws BadRequestException {
        Customer customer = customerRepository.findCustomerByEmail(customerEmail);

        // Date and time formatting
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter yearFormat = DateTimeFormatter.ofPattern("yyyy");
        DateTimeFormatter cDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime localDateTime = LocalDateTime.now();
        String invoiceDate = localDateTime.format(dateFormat);
        String timeDate = localDateTime.format(timeFormat);
        String financialYear = localDateTime.format(yearFormat);
        String cDate = localDateTime.format(cDateFormat);

        int invoiceNum = invoiceRepository.findMaxInvoiceNum();

        if (customer != null)
        {
            DeliveryTimeSlot deliveryTimeSlot = deliveryTimeSlotRepository.getDeliveryTimeSlotById(orderDTO.getDeliveryTimeSlotId());
            if (deliveryTimeSlot == null) {
                throw new objectNotFoundException("Delivery time slot not found");
            }

            City city = cityRepository.findCityById(orderDTO.getCityId());
            if (city == null) {
                throw new objectNotFoundException("City not found");
            }

            CouponCode couponCode = null;
            if (orderDTO.getCouponId() != null) {
                couponCode = couponCodeRepository.findCouponById(orderDTO.getCouponId());
            }

            Invoice invoice = new Invoice();
            invoice.setInvoicePrefix("BI - ");
            invoice.setInvoiceNum(invoiceNum+1);
            invoice.setInvoiceDate(invoiceDate);
            invoice.setInvoiceTime(timeDate);
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

            if (orderDTO.getPaymentMode() == 1) { // COD
                invoice.setInvoiceRemainingAmount(orderDTO.getTotalAmount());
                invoice.setInvoiceReceivedAmount(0.00);
            } else if (orderDTO.getPaymentMode() == 2) { // Online
                invoice.setInvoiceRemainingAmount(0.00);
                invoice.setInvoiceReceivedAmount(orderDTO.getTotalAmount());
            } else {
                throw new BadRequestException("Invalid payment mode");
            }

            invoice.setInvoicePincode(orderDTO.getPincode());

            if (orderDTO.getTotalAmount() >= 500) {
                invoice.setInvoiceDeliveryCharges(0);
            } else {
                invoice.setInvoiceDeliveryCharges(15);
            }

            invoice.setCouponCode(couponCode);
            if (couponCode == null) {
                invoice.setInvoiceCouponCodeDiscount(0.00);
            } else {
                invoice.setInvoiceCouponCodeDiscount(orderDTO.getCouponDiscount());
            }

            invoice.setInvoiceSpecialInstruction(orderDTO.getSpecialInstructions());
            invoice.setDeliveryTimeSlot(deliveryTimeSlot);
            invoice.setInvoiceIsHold(2);
            invoice.setInvoiceStatus(1); // Pending
            invoice.setIsDeleted(1);
            invoice.setcDate(cDate);

            invoiceRepository.save(invoice);
        } else {
            throw new objectNotFoundException("Customer not found");
        }
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
