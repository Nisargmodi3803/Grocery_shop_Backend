package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.OrderDTO;
import com.example.grocery_shop_backend.Dto.UpdateDeliveryAddressDTO;
import com.example.grocery_shop_backend.Entities.Invoice;
import com.example.grocery_shop_backend.Service.InvoiceService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class InvoiceController
{
    @Autowired
    private InvoiceService invoiceService;

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

    //POST API {Add Order}
    @PostMapping("/add-order/{customerId}")
    public ResponseEntity<String> addOrder(@PathVariable int customerId, @RequestBody OrderDTO orderDTO)
    {
        try
        {
            invoiceService.addOrder(customerId,orderDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Order added successfully");
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }
    }

    // PATCH API {Delete Order}
    @PatchMapping("/delete-order/{invoiceNum}")
    public ResponseEntity<String> deleteOrder(@PathVariable int invoiceNum)
    {
        invoiceService.deleteOrder(invoiceNum);
        return ResponseEntity.status(HttpStatus.OK).body("Order deleted successfully");
    }
}
