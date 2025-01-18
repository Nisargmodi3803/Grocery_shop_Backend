package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Entities.Cart;
import com.example.grocery_shop_backend.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    // GET API {Cart By CustomerID}
    @GetMapping("/cart/{customerID}")
    public List<Cart> getCartByCustomerId(@PathVariable int customerID) {
        return cartService.getByCustomerId(customerID);
    }

    //POST API {Add to Cart}
    @PostMapping("/add-cart")
    public ResponseEntity<String> addCart(@RequestParam int customerId,@RequestParam int productId,@RequestParam int productQuantity)
    {
        try
        {
            cartService.addToCart(customerId,productId,productQuantity);
            return ResponseEntity.ok("Product added successfully.");
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    //PATCH API {Remove From Cart}
    @PatchMapping("/remove-cart")
    public ResponseEntity<String> removeCart(@RequestParam int customerId,@RequestParam int productId)
    {
        boolean success = cartService.removeFromCart(customerId,productId);
        if(success)
            return ResponseEntity.ok("Product removed successfully.");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in cart.");
    }

    //PATCH API {Increment one item at a time}
    @PatchMapping("/cart-increment")
    public ResponseEntity<String> incrementItem(@RequestParam int customerId,@RequestParam int productId)
    {
        boolean success = cartService.incrementItem(customerId,productId);
        if (success)
            return ResponseEntity.ok("Product incremented successfully.");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in cart.");
    }

    //PATCH API {Decrement one item at a time}
    @PatchMapping("/cart-decrement")
    public ResponseEntity<String> decrementItem(@RequestParam int customerId,@RequestParam int productId)
    {
        boolean success = cartService.decrementItem(customerId,productId);
        System.out.println(success);
        if(success)
            return ResponseEntity.ok("Product decremented successfully.");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in cart.");
    }
}
