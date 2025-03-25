package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.ProductReviewDTO;
import com.example.grocery_shop_backend.Dto.ProductReviewsByProductDTO;
import com.example.grocery_shop_backend.Entities.*;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.CustomerRepository;
import com.example.grocery_shop_backend.Repository.InvoiceRepository;
import com.example.grocery_shop_backend.Repository.ProductRepository;
import com.example.grocery_shop_backend.Repository.ProductReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ProductReviewService
{
    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository CustomerRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private CustomerRepository customerRepository;

    // Find Product Review by Product Slug Service [Get Product Review of specific Product for all Customers to See]
    public List<ProductReview> findProductReviewsByProductSlug(String productSlug)
    {
        List<ProductReview> productReviews = productReviewRepository.findProductReviewsByProductSlug(productSlug);

        if(productReviews.isEmpty())
            throw new objectNotFoundException("No Product Reviews found for productSlug: " + productSlug);
        return productReviews;
    }

    // Find Product Review by Customer ID Service [For Customer to see on which products they have commented]
    public List<ProductReview> findProductReviewByCustomerId(int customerId)
    {
        List<ProductReview> productReviews = productReviewRepository.findProductReviewsByCustomerId(customerId);

        if(productReviews.isEmpty())
            throw new objectNotFoundException("No Product Reviews found for customerId: " + customerId);
        return productReviews;
    }

    // Delete Product Review Service
    @Transactional
    public void deleteProductReview(int ratingId)
    {
        ProductReview productReview = productReviewRepository.findProductReviewByRatingId(ratingId);

        if(productReview != null)
        {
            productReview.setIsDeleted(2);
            productReviewRepository.save(productReview);
        }
        else
            throw new objectNotFoundException("No Product Review found for ratingId: " + ratingId);
    }

    // Insert Product Review Service
    @Transactional
    public void insertProductReview(ProductReviewDTO productReviewDTO)
    {
        Customer customer = customerRepository.findCustomerByEmail(productReviewDTO.getCustomerEmail());
        Product product = productRepository.findProductById(productReviewDTO.getProductId());
        Invoice invoice = invoiceRepository.findByInvoiceId(productReviewDTO.getInvoiceId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String cDate = now.format(formatter);

        if(invoice!=null && customer!=null && product!=null)
        {
            ProductReview productReview = new ProductReview();
            productReview.setCustomer(customer);
            productReview.setProduct(product);
            productReview.setInvoice(invoice);
            productReview.setRating(productReviewDTO.getRating());
            productReview.setReview(productReviewDTO.getReview());
            productReview.setStatus(2);
            productReview.setIsDeleted(1);
            productReview.setcDate(cDate);
            productReviewRepository.save(productReview);

            int noOfRatings = 0;
            double averageRating = 0.0;

            if (product.getNo_of_rating() != null && !product.getNo_of_rating().isEmpty()) {
                try {
                    noOfRatings = Integer.parseInt(product.getNo_of_rating());
                    noOfRatings++;

                    if (product.getAverage_rating() != null && !product.getAverage_rating().isEmpty()) {
                        double currentAverageRating = Double.parseDouble(product.getAverage_rating());
                        averageRating = ((currentAverageRating * (noOfRatings - 1)) + productReview.getRating()) / noOfRatings;
                    } else {
                        averageRating = productReview.getRating();
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid numeric value in product data", e);
                }
            } else {
                noOfRatings = 1;
                averageRating = productReview.getRating();
            }

            product.setNo_of_rating(String.valueOf(noOfRatings));
            product.setAverage_rating(String.format("%.2f", averageRating));

            productRepository.save(product);

        }
        else
            throw new objectNotFoundException("Any of Invoice or Product or Customer or All are null");
    }

    // Find All Reviews Service
    public List<ProductReview> findAllReviews(){
        List<ProductReview> productReviews = productReviewRepository.findAllProductReviews();
        if(productReviews.isEmpty()){
            throw new objectNotFoundException("No Product Reviews found");
        }
        return productReviews;
    }

    // Search Reviews Service
    public List<ProductReview> searchInquires(String searchText) {
        List<ProductReview> reviews = productReviewRepository.searchReviewsByKeyword(searchText);
        if(reviews.isEmpty()) {
            throw new objectNotFoundException("Customer list is empty");
        }
        return reviews;
    }

    // Find All Reviews by Status Pending Service
    public List<ProductReview> findAllPendingReviews(){
        List<ProductReview> reviews = productReviewRepository.findAllProductReviewsByPendingStatus();
        if(reviews.isEmpty()) {
            throw new objectNotFoundException("No Product Reviews found");
        }
        return reviews;
    }

    // Find All Reviews by Status Approved Service
    public List<ProductReview> findAllApprovedReviews(){
        List<ProductReview> reviews = productReviewRepository.findAllProductReviewsByApprovedStatus();
        if(reviews.isEmpty()) {
            throw new objectNotFoundException("No Product Reviews found");
        }
        return reviews;
    }

    // Find All Reviews by Status Rejected Service
    public List<ProductReview> findAllRejectedReviews(){
        List<ProductReview> reviews = productReviewRepository.findByProductReviewRejectedStatus();
        if(reviews.isEmpty()) {
            throw new objectNotFoundException("No Product Reviews found");
        }
        return reviews;
    }

    // Change to Pending Review Service
    public void changeToPendingReview(int id){
        ProductReview review = productReviewRepository.findProductReviewByRatingId(id);

        if (review == null) {
            throw new objectNotFoundException("No Product Review found for id: " + id);
        }
        review.setStatus(1);
        productReviewRepository.save(review);
    }

    // Change to Approved Review Service
    public void changeToApprovedReview(int id){
        ProductReview review = productReviewRepository.findProductReviewByRatingId(id);

        if (review == null) {
            throw new objectNotFoundException("No Product Review found for id: " + id);
        }
        review.setStatus(2);
        productReviewRepository.save(review);
    }

    // Change to Rejected Review Service
    public void changeToRejectedReview(int id){
        ProductReview review = productReviewRepository.findProductReviewByRatingId(id);

        if (review == null) {
            throw new objectNotFoundException("No Product Review found for id: " + id);
        }
        review.setStatus(3);
        productReviewRepository.save(review);
    }
}
