package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.AddProductOrderDTO;
import com.example.grocery_shop_backend.Dto.ProductOrderListDTO;
import com.example.grocery_shop_backend.Service.InvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class InvoiceDetailController
{
    @Autowired
    private InvoiceDetailService invoiceDetailService;

    // GET API {Find Product Order List by Invoice Num}
    @GetMapping("product-order/{invoiceNum}")
    public List<ProductOrderListDTO> findProductOrderByInvoiceNum(@PathVariable int invoiceNum)
    {
        return invoiceDetailService.findProductOrderByInvoiceNum(invoiceNum);
    }

    // POST API {Insert into Product Order}
    @PostMapping("/add-product-order")
    public ResponseEntity<String> addProductOrder(@RequestBody AddProductOrderDTO addProductOrderDTO)
    {
        invoiceDetailService.addProductOrder(addProductOrderDTO);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
