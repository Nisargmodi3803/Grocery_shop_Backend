package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.ProductInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInquiryRepository extends JpaRepository<ProductInquiry, Integer>
{
    @Query("SELECT inquiry FROM ProductInquiry inquiry WHERE inquiry.isDeleted=1 AND (inquiry.customer.customerId = :customerId AND inquiry.customer.isDeleted=1 AND inquiry.product.is_deleted=1)")
    List<ProductInquiry> findInquiriesByCustomerId(@Param("customerId") int customerId);

    @Query("SELECT inquiry FROM ProductInquiry inquiry WHERE inquiry.isDeleted=1 AND (inquiry.product.id = :productId AND inquiry.product.is_deleted=1 AND inquiry.customer.isDeleted=1)")
    List<ProductInquiry> findInquiriesByProductId(@Param("productId") int productId);

    @Query("SELECT inquiry FROM ProductInquiry inquiry WHERE inquiry.isDeleted=1 AND (inquiry.customer.customerId = :customerId AND inquiry.customer.isDeleted=1) AND (inquiry.product.id = :productId AND inquiry.product.is_deleted=1)")
    List<ProductInquiry> findInquiryByProductIdAndCustomerId(@Param("productId") int productId, @Param("customerId") int customerId);
}
