package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Entities.Cart;
import com.example.grocery_shop_backend.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CartController {

    @Autowired
    private CartService cartService;

    // GET API {Cart By CustomerID}
    @GetMapping("/cart-customer/{customerID}")
    public ResponseEntity<List<Cart>> getCartByCustomerId(@PathVariable int customerID) {
        try {
            return ResponseEntity.ok(cartService.getByCustomerId(customerID));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // GET API {Cart by Customer Email}
    @GetMapping("/cart/{customerEmail}")
    public ResponseEntity<List<Cart>> getCartByCustomerEmail(@PathVariable String customerEmail) {
        try {
            return ResponseEntity.ok(cartService.findByCustomerEmail(customerEmail));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    //POST API {Add to Cart}
    @PostMapping("/add-cart")
    public ResponseEntity<String> addCart(@RequestParam String customerEmail,@RequestParam int productId)
    {
        try
        {
            cartService.addToCart(customerEmail,productId);
            return ResponseEntity.ok("Product added successfully.");
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //PATCH API {Remove From Cart}
    @PatchMapping("/remove-cart")
    public ResponseEntity<String> removeCart(@RequestParam String customerEmail,@RequestParam int productId)
    {
        boolean success = cartService.removeFromCart(customerEmail,productId);
        if(success)
            return ResponseEntity.ok("Product removed successfully.");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in cart.");
    }

    //PATCH API {Increment one item at a time}
    @PatchMapping("/cart-increment")
    public ResponseEntity<String> incrementItem(@RequestParam String customerEmail,@RequestParam int productId)
    {
        boolean success = cartService.incrementItem(customerEmail,productId);
        if (success)
            return ResponseEntity.ok("Product incremented successfully.");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in cart.");
    }

    //PATCH API {Decrement one item at a time}
    @PatchMapping("/cart-decrement")
    public ResponseEntity<String> decrementItem(@RequestParam String customerEmail,@RequestParam int productId)
    {
        boolean success = cartService.decrementItem(customerEmail,productId);
        System.out.println(success);
        if(success)
            return ResponseEntity.ok("Product decremented successfully.");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in cart.");
    }
}
