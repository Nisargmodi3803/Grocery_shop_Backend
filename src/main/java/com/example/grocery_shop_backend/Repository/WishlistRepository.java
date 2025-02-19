package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer>
{
    @Query("SELECT wishlist FROM Wishlist wishlist WHERE wishlist.customer.customerEmail = :customerEmail AND wishlist.is_deleted=1")
    List<Wishlist> findWishlistByCustomer(String customerEmail);

    @Query("SELECT wishlist FROM Wishlist wishlist WHERE (wishlist.customer.customerEmail = :customerEmail AND wishlist.product.id = :productId) AND wishlist.is_deleted=1")
    Wishlist findByCustomerProduct(@Param("customerEmail") String customerEmail, @Param("productId") int productId);

}
