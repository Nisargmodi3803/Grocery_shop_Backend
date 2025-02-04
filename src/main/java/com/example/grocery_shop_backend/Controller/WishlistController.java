package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Entities.Wishlist;
import com.example.grocery_shop_backend.Service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class WishlistController
{
    @Autowired
    private WishlistService wishlistService;

    //GET API {get Wishlist}
    @GetMapping("/wishlist/{id}")
    public List<Wishlist> getWishlist(@PathVariable int id)
    {
        return wishlistService.getWishlist(id);
    }

    //POST API {Add to Wishlist}
    @PostMapping("/add-wishlist")
    public ResponseEntity<String> addToWishlist(@RequestParam int customerId, @RequestParam int productId) {
        wishlistService.addToWishlist(customerId, productId);
        return ResponseEntity.ok("Product added to wishlist successfully!");
    }

    //PATCH API {Remove from Wishlist}
    @PatchMapping("/remove-wishlist")
    public ResponseEntity<String> removeFromWishlist(@RequestParam int customerId, @RequestParam int productId) {
        boolean removed = wishlistService.removeFromWishlist(customerId, productId);
        if (removed) {
            return ResponseEntity.ok("Product removed from wishlist successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in wishlist.");
    }
}
