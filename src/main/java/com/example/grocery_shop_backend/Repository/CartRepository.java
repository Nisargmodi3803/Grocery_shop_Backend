package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>
{
    @Query("SELECT cart FROM Cart cart WHERE cart.customer.customerId = :customerId AND cart.is_deleted=1")
    List<Cart> findByCustomerId(@Param("customerId") int customerId);

    @Query("SELECT cart FROM Cart cart WHERE cart.customer.customerEmail = :customerEmail AND cart.is_deleted=1")
    List<Cart> findByCustomerEmail(@Param("customerEmail") String customerEmail);

    @Query("SELECT cart FROM Cart cart WHERE (cart.customer.customerEmail = :customerEmail AND cart.product.id = :productId) AND cart.is_deleted=1")
    Cart findByCustomerEmailProductId(@Param("customerEmail") String customerEmail, @Param("productId") int productId);

    @Query("SELECT cart FROM Cart cart WHERE (cart.customer.customerEmail = :customerEmail AND cart.product.id = :productId) AND cart.is_deleted=2")
    Cart findByCustomerEmail(@Param("customerEmail") String customerEmail, @Param("productId") int productId);
}
