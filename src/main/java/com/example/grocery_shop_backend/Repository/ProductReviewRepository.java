package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Dto.ProductReviewDTO;
import com.example.grocery_shop_backend.Dto.ProductReviewsByProductDTO;
import com.example.grocery_shop_backend.Entities.ProductInquiry;
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

    @Query("SELECT review FROM ProductReview review WHERE review.product.slug_title = :productSlug AND review.isDeleted=1 AND review.status=2")
    List<ProductReview> findProductReviewsByProductSlug(String productSlug);

    @Query("SELECT review FROM ProductReview review WHERE review.customer.customerId = :customerId AND review.isDeleted=1 AND review.customer.isDeleted=1")
    List<ProductReview> findProductReviewsByCustomerId(@Param("customerId") int customerId);

    @Query("SELECT review FROM ProductReview review WHERE review.ratingId = :ratingId AND review.isDeleted=1")
    ProductReview findProductReviewByRatingId(@Param("ratingId") int ratingId);

    @Query("SELECT reviews FROM ProductReview reviews WHERE reviews.isDeleted=1 ORDER BY reviews.ratingId DESC ")
    List<ProductReview> findAllProductReviews();

    @Query("SELECT c FROM ProductReview c " +
            "WHERE c.isDeleted = 1 AND " +
            "LOWER(c.customer.customerName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.product.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.product.variantName) LIKE LOWER(CONCAT('%', :keyword, '%')) " )
    List<ProductReview> searchReviewsByKeyword(@Param("keyword") String keyword);

    @Query("SELECT reviews FROM ProductReview reviews WHERE reviews.status=1 AND reviews.isDeleted=1 ORDER BY reviews.ratingId DESC")
    List<ProductReview> findAllProductReviewsByPendingStatus();

    @Query("SELECT reviews FROM ProductReview reviews WHERE reviews.status=2 AND reviews.isDeleted=1 ORDER BY reviews.ratingId DESC")
    List<ProductReview> findAllProductReviewsByApprovedStatus();

    @Query("SELECT reviews FROM ProductReview reviews WHERE reviews.status=3 AND reviews.isDeleted=1 ORDER BY reviews.ratingId DESC")
    List<ProductReview> findByProductReviewRejectedStatus();
}
