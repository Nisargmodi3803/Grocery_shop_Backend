package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.ProductReviewDTO;
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
    @GetMapping("/product-reviews-product/{productId}")
    public List<ProductReview> findProductReviewsByProductId(@PathVariable int productId)
    {
        return productReviewService.findProductReviewsByProductId(productId);
    }

    // GET API {Find Product Reviews by Customer ID}
    @GetMapping("/product-reviews-customer/{customerId}")
    public List<ProductReview> findProductReviewsByCustomerId(@PathVariable int customerId)
    {
        return productReviewService.findProductReviewByCustomerId(customerId);
    }

    // PATCH API {Delete Product Review}
    @PatchMapping("/delete-product-review/{ratingId}")
    public ResponseEntity<String> deleteProductReview(@PathVariable int ratingId)
    {
        productReviewService.deleteProductReview(ratingId);
        return new ResponseEntity<>("Product Review deleted", HttpStatus.OK);
    }

    // POST API {Add Product Review}
    @PostMapping("add-product-review")
    public ResponseEntity<String> addProductReview(@RequestBody ProductReviewDTO productReviewDTO)
    {
//        try {
            productReviewService.insertProductReview(productReviewDTO);
            return new ResponseEntity<>("Product Review added", HttpStatus.OK);
//        }
//        catch (Exception e)
//        {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
    }
}
