package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.Customer;
import com.example.grocery_shop_backend.Entities.ProductInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInquiryRepository extends JpaRepository<ProductInquiry, Integer>
{
    @Query("SELECT inquiry FROM ProductInquiry inquiry WHERE inquiry.isDeleted=1 AND (inquiry.customer.customerEmail = :customerEmail AND inquiry.customer.isDeleted=1 AND inquiry.product.isDeleted=1)")
    List<ProductInquiry> findInquiriesByCustomerEmail(String customerEmail);

    @Query("SELECT inquiry FROM ProductInquiry inquiry WHERE inquiry.isDeleted=1 AND (inquiry.product.id = :productId AND inquiry.product.isDeleted=1 AND inquiry.customer.isDeleted=1)")
    List<ProductInquiry> findInquiriesByProductId(@Param("productId") int productId);

    @Query("SELECT inquiry FROM ProductInquiry inquiry WHERE inquiry.isDeleted=1 AND (inquiry.customer.customerId = :customerId AND inquiry.customer.isDeleted=1) AND (inquiry.product.id = :productId AND inquiry.product.isDeleted=1)")
    List<ProductInquiry> findInquiryByProductIdAndCustomerId(@Param("productId") int productId, @Param("customerId") int customerId);

    @Query("SELECT inquiry FROM ProductInquiry inquiry WHERE inquiry.isDeleted = 1 ORDER BY inquiry.inquiryId DESC")
    List<ProductInquiry> findAllInquiry();


    @Query("SELECT i FROM ProductInquiry i WHERE i.inquiryId= :id AND i.isDeleted=1")
    ProductInquiry findProductInquiryById(@Param("id") int id);

    @Query("SELECT c FROM ProductInquiry c " +
            "WHERE c.isDeleted = 1 AND (" +
            "CAST(c.inquiryId AS string) LIKE %:keyword% OR " +
            "LOWER(c.customer.customerName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.product.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.product.variantName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "c.customer.customerMobile LIKE %:keyword%)")
    List<ProductInquiry> searchInquiryByKeyword(@Param("keyword") String keyword);
}