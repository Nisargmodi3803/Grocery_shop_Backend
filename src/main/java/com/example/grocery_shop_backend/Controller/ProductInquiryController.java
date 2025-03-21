package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.ProductInquiryDTO;
import com.example.grocery_shop_backend.Entities.Customer;
import com.example.grocery_shop_backend.Entities.ProductInquiry;
import com.example.grocery_shop_backend.Service.ProductInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
public class ProductInquiryController
{
    @Autowired
    private ProductInquiryService productInquiryService;


    // GET API {Find Product Inquires by Product ID}
    @GetMapping("/product-inquires-product-id/{productId}")
    public List<ProductInquiry> findInquiresByProductId(@PathVariable int productId)
    {
        return productInquiryService.findInquiresByProductId(productId);
    }

    // GET API {Find Product Inquires by Product ID & Customer ID}
    @GetMapping("/product-inquires")
    public List<ProductInquiry> findInquiresByProductIdAndCustomerId(@RequestParam int productId, @RequestParam int customerId)
    {
        return productInquiryService.findInquiryByProductIdAndCustomerId(productId, customerId);
    }

    // POST API {Add Product Inquiry}
    @PostMapping("/product-inquiry")
    public ResponseEntity<String> addInquiry(@RequestBody ProductInquiryDTO productInquiryDTO)
    {
        productInquiryService.addInquiry(productInquiryDTO);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/inquiries")
    public ResponseEntity<List<ProductInquiry>> findAllInquires(){
        try {
            return new ResponseEntity<>(productInquiryService.findAllInquires(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search-inquiry")
    public ResponseEntity<List<ProductInquiry>> searchInquiry(@RequestParam String keyword) {
        try {
            return ResponseEntity.ok(productInquiryService.searchInquires(keyword));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/delete-inquiry/{id}")
    public ResponseEntity<String> deleteInquiry(@PathVariable int id) {
        try {
            productInquiryService.deleteInquiry(id);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
