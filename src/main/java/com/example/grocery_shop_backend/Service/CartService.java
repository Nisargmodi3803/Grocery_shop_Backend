package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Entities.Cart;
import com.example.grocery_shop_backend.Entities.Customer;
import com.example.grocery_shop_backend.Entities.Product;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.CartRepository;
import com.example.grocery_shop_backend.Repository.CustomerRepository;
import com.example.grocery_shop_backend.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CartService
{
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    // Get Cart by CustomerId Service
    public List<Cart> getByCustomerId(int customerId)
    {
        List<Cart> carts = cartRepository.findByCustomerId(customerId);
        if (carts.isEmpty())
            throw new objectNotFoundException("Cart associated with customer id " + customerId + " not found");
        return carts;
    }

    // Add to Cart Service
    public void addToCart(int customerId,int productId,int productQuantity)
    {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new objectNotFoundException("Customer id " + customerId + " not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new objectNotFoundException("Product id " + productId + " not found"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentDateTime = now.format(formatter);

        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setProduct(product);
        cart.setProductQuantity(productQuantity);
        cart.setIs_deleted(1);
        cart.setC_date(currentDateTime);
        cartRepository.save(cart);
    }

    // Remove from Cart
    public boolean removeFromCart(int customerId,int productId)
    {
        Cart cart = cartRepository.findByCustomerIdProductId(customerId,productId);
        if(cart == null)
        {
            return false;
        }
        else
        {
            cart.setIs_deleted(2);
            cartRepository.save(cart);
            return true;
        }
    }

    // Increment one item at a time Service
    public boolean incrementItem(int customerId,int productId)
    {
        Cart cart = cartRepository.findByCustomerIdProductId(customerId,productId);
        if(cart == null)
            return false;
        else
        {
            cart.setProductQuantity(cart.getProductQuantity()+1);
            cartRepository.save(cart);
            return true;
        }
    }

    // Decrement one item at a time Service
    public boolean decrementItem(int customerId,int productId)
    {
        Cart cart = cartRepository.findByCustomerIdProductId(customerId,productId);
        if (cart == null)
            return false;
        else
        {
            if(cart.getProductQuantity()==1)
                cart.setIs_deleted(2);
            else
                cart.setProductQuantity(cart.getProductQuantity()-1);
            cartRepository.save(cart);
            return true;
        }
    }
}
