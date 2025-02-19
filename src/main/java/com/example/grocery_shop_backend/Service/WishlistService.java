package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Entities.Customer;
import com.example.grocery_shop_backend.Entities.Product;
import com.example.grocery_shop_backend.Entities.Wishlist;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.CustomerRepository;
import com.example.grocery_shop_backend.Repository.ProductRepository;
import com.example.grocery_shop_backend.Repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class WishlistService
{
    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // Get Wishlist of Customers using MobileNo. Service
    public List<Wishlist> getWishlist(String email)
    {
        List<Wishlist> wishlists = wishlistRepository.findWishlistByCustomer(email);
        if (wishlists.isEmpty())
        {
            throw new objectNotFoundException("No Wishlist found with present customer with email " + email);
        }

        return wishlists;
    }

    // Add to Wishlist Service
    public void addToWishlist(String customerEmail, int productId)
    {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = now.format(formatter);

        Wishlist existingWishlist = wishlistRepository.findByCustomerProduct(customerEmail, productId);

        if (existingWishlist == null) {

            Customer customer = customerRepository.findCustomerByEmail(customerEmail);
            if(customer == null){
                throw new objectNotFoundException("Customer with email " + customerEmail + " not found");
            }

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new objectNotFoundException("Product with id " + productId + " not found"));

            Wishlist wishlist = new Wishlist();
            wishlist.setCustomer(customer);
            wishlist.setProduct(product);
            wishlist.setC_date(formattedTimestamp);
            wishlist.setIs_deleted(1);

            wishlistRepository.save(wishlist);
        }
        else{
            existingWishlist.setIs_deleted(1);
            existingWishlist.setC_date(formattedTimestamp);
            wishlistRepository.save(existingWishlist);
        }
    }

    // Remove from Wishlist Service
    public Boolean removeFromWishlist(String customerEmail, int productId)
    {
        Wishlist wishlist = wishlistRepository.findByCustomerProduct(customerEmail, productId);
        if (wishlist == null)
            return false;
        else
        {
            wishlist.setIs_deleted(2);
            wishlistRepository.save(wishlist);
            return true;
        }
    }


}
