package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.UpdateDeliveryAddressDTO;
import com.example.grocery_shop_backend.Entities.Invoice;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService
{
    @Autowired
    private InvoiceRepository invoiceRepository;

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
    public Invoice updateDeliveryAddress(int invoiceNum, UpdateDeliveryAddressDTO deliveryAddress)
    {
        Invoice invoice = invoiceRepository.findByInvoiceNum(invoiceNum);
        if (invoice!=null)
        {
            if(deliveryAddress!=null)
                invoice.setInvoiceAddress(deliveryAddress.getDeliveryAddress());
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
}
