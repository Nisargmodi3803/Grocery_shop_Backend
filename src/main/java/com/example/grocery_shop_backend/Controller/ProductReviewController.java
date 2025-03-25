package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.ProductReviewDTO;
import com.example.grocery_shop_backend.Entities.ProductInquiry;
import com.example.grocery_shop_backend.Entities.ProductReview;
import com.example.grocery_shop_backend.Service.ProductReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
public class ProductReviewController
{
    @Autowired
    private ProductReviewService productReviewService;

    // GET API {Find Product Reviews by Product ID}
    @GetMapping("/product-reviews/{slugTitle}")
    public ResponseEntity<List<ProductReview>> findProductReviewsByProductSlug(@PathVariable String slugTitle)
    {
        try {
            return new ResponseEntity<>(productReviewService.findProductReviewsByProductSlug(slugTitle), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find Product Reviews by Customer ID}
    @GetMapping("/product-reviews-customer/{customerId}")
    public List<ProductReview> findProductReviewsByCustomerId(@PathVariable int customerId)
    {
        return productReviewService.findProductReviewByCustomerId(customerId);
    }

    // PATCH API {Delete Product Review}
    @PatchMapping("/delete-product-review/{id}")
    public ResponseEntity<String> deleteProductReview(@PathVariable int id)
    {
        try {
            productReviewService.deleteProductReview(id);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // POST API {Add Product Review}
    @PostMapping("/add-product-review")
    public ResponseEntity<String> addProductReview(@RequestBody ProductReviewDTO productReviewDTO)
    {
        try {
            productReviewService.insertProductReview(productReviewDTO);
            return new ResponseEntity<>("Product Review added", HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // GET API {Find All Product Reviews}
    @GetMapping("/reviews")
    public ResponseEntity<List<ProductReview>> findAllReviews(){
        try {
            return new ResponseEntity<>(productReviewService.findAllReviews(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search-review")
    public ResponseEntity<List<ProductReview>> searchInquiry(@RequestParam String keyword) {
        try {
            return ResponseEntity.ok(productReviewService.searchInquires(keyword));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/pending-reviews")
    public ResponseEntity<List<ProductReview>> findPendingReviews() {
        try {
            return ResponseEntity.ok(productReviewService.findAllPendingReviews());
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/approved-reviews")
    public ResponseEntity<List<ProductReview>> findApprovedReviews() {
        try {
            return ResponseEntity.ok(productReviewService.findAllApprovedReviews());
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/rejected-reviews")
    public ResponseEntity<List<ProductReview>> findRejectedReviews() {
        try {
            return ResponseEntity.ok(productReviewService.findAllRejectedReviews());
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/pending/{id}")
    public ResponseEntity<String> changeToPending(@PathVariable int id) {
        try {
            productReviewService.changeToPendingReview(id);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/approved/{id}")
    public ResponseEntity<String> changeToApproved(@PathVariable int id) {
        try {
            productReviewService.changeToApprovedReview(id);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/rejected/{id}")
    public ResponseEntity<String> changeToRejected(@PathVariable int id) {
        try {
            productReviewService.changeToRejectedReview(id);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
