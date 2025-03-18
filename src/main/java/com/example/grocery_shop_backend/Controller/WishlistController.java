package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Entities.Wishlist;
import com.example.grocery_shop_backend.Service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
public class WishlistController
{
    @Autowired
    private WishlistService wishlistService;

    //GET API {get Wishlist}
    @GetMapping("/wishlist/{email}")
    public ResponseEntity<List<Wishlist>> getWishlist(@PathVariable String email)
    {
        try{
            return new ResponseEntity<>(wishlistService.getWishlist(email), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    //POST API {Add to Wishlist}
    @PostMapping("/add-wishlist")
    public ResponseEntity<String> addToWishlist(@RequestParam String customerEmail, @RequestParam int productId) {
        try{
            wishlistService.addToWishlist(customerEmail, productId);
            return ResponseEntity.ok("Product added to wishlist successfully!");
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    //PATCH API {Remove from Wishlist}
    @PatchMapping("/remove-wishlist")
    public ResponseEntity<String> removeFromWishlist(@RequestParam String customerEmail, @RequestParam int productId) {
        boolean removed = wishlistService.removeFromWishlist(customerEmail, productId);
        if (removed) {
            return ResponseEntity.ok("Product removed from wishlist successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in wishlist.");
    }
}
