package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Dto.ProductReviewDTO;
import com.example.grocery_shop_backend.Dto.ProductReviewsByProductDTO;
import com.example.grocery_shop_backend.Entities.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {
//    @Query("SELECT new com.example.grocery_shop_backend.Dto.ProductReviewsByProductDTO(review.customer, review.rating, review.review) " +
//            "FROM ProductReview review " +
//            "WHERE review.product.id = :productId " +
//            "AND review.product.is_deleted = 1 " +
//            "AND review.isDeleted = 1")
//    List<ProductReviewsByProductDTO> findProductReviewsByProductId(@Param("productId") int productId);

    @Query("SELECT review FROM ProductReview review WHERE review.product.id = :productId AND review.isDeleted=1 AND review.product.is_deleted=1")
    List<ProductReview> findProductReviewsByProductId(@Param("productId") int productId);

    @Query("SELECT review FROM ProductReview review WHERE review.customer.customerId = :customerId AND review.isDeleted=1 AND review.customer.isDeleted=1")
    List<ProductReview> findProductReviewsByCustomerId(@Param("customerId") int customerId);

    @Query("SELECT review FROM ProductReview review WHERE review.ratingId = :ratingId AND review.isDeleted=1")
    ProductReview findProductReviewByRatingId(@Param("ratingId") int ratingId);
}
