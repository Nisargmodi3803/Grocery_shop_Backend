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
    public List<Wishlist> getWishlist(int id)
    {
        List<Wishlist> wishlists = wishlistRepository.findByCustomerId(id);
        if (wishlists.isEmpty())
        {
            throw new objectNotFoundException("No Wishlist found with present customer with id " + id);
        }

        return wishlists;
    }

    // Add to Wishlist Service
    public void addToWishlist(int customerId, int productId)
    {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new objectNotFoundException("Customer with id " + customerId + " not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new objectNotFoundException("Product with id " + productId + " not found"));

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = now.format(formatter);

        Wishlist wishlist = new Wishlist();
        wishlist.setCustomer(customer);
        wishlist.setProduct(product);
        wishlist.setC_date(formattedTimestamp);
        wishlist.setIs_deleted(1);

        wishlistRepository.save(wishlist);
    }

    // Remove from Wishlist Service
    public Boolean removeFromWishlist(int customerId, int productId)
    {
        Wishlist wishlist = wishlistRepository.findByCustomerIdProductId(customerId, productId);
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
