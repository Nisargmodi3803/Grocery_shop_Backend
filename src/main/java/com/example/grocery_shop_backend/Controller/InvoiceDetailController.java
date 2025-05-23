package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.AddProductOrderDTO;
import com.example.grocery_shop_backend.Dto.ProductOrderListDTO;
import com.example.grocery_shop_backend.Entities.Customer;
import com.example.grocery_shop_backend.Entities.InvoiceDetail;
import com.example.grocery_shop_backend.Service.InvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
public class InvoiceDetailController
{
    @Autowired
    private InvoiceDetailService invoiceDetailService;

    // GET API {Find Product Order List by Invoice Num}
    @GetMapping("/product-order/{invoiceId}")
    public ResponseEntity<List<InvoiceDetail>> findProductOrderByInvoiceNum(@PathVariable int invoiceId)
    {
        try {
            return new ResponseEntity<>(invoiceDetailService.findProductOrderByInvoiceNum(invoiceId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // POST API {Insert into Product Order}
//    @PostMapping("/add-product-order")
//    public ResponseEntity<String> addProductOrder(@RequestBody AddProductOrderDTO addProductOrderDTO)
//    {
//        try {
//            invoiceDetailService.addProductOrder(addProductOrderDTO);
//            return new ResponseEntity<>("Success", HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/search-invoice")
    public ResponseEntity<List<InvoiceDetail>> searchCustomer(@RequestParam String keyword) {
        try {
            return ResponseEntity.ok(invoiceDetailService.searchInvoice(keyword));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
