package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.ProductInquiryDTO;
import com.example.grocery_shop_backend.Entities.Customer;
import com.example.grocery_shop_backend.Entities.Product;
import com.example.grocery_shop_backend.Entities.ProductInquiry;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.CustomerRepository;
import com.example.grocery_shop_backend.Repository.ProductInquiryRepository;
import com.example.grocery_shop_backend.Repository.ProductRepository;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ProductInquiryService
{
    @Autowired
    private ProductInquiryRepository productInquiryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // Find Product Inquiries By Customer ID Service
    public List<ProductInquiry> findInquiriesByCustomerId(String customerEmail)
    {
        List<ProductInquiry> inquiries = productInquiryRepository.findInquiriesByCustomerEmail(customerEmail);
        if (inquiries.isEmpty())
            throw new objectNotFoundException("Product Inquiry Not Found with Customer Id: " + customerEmail);
        return inquiries;
    }

    // Find Product Inquires By Product ID Service
    public List<ProductInquiry> findInquiresByProductId(int productId)
    {
        List<ProductInquiry> inquiries = productInquiryRepository.findInquiriesByProductId(productId);
        if(inquiries.isEmpty())
            throw new objectNotFoundException("Product Inquiry Not Found with Product Id: " + productId);
        return inquiries;
    }

    // Find Product Inquires by Product ID & Customer ID Service
    public List<ProductInquiry> findInquiryByProductIdAndCustomerId(int productId, int customerId)
    {
        List<ProductInquiry> inquiries = productInquiryRepository.findInquiryByProductIdAndCustomerId(productId, customerId);
        if (inquiries.isEmpty())
            throw new objectNotFoundException("Product Inquiry with Product Id: " + productId+ " and Customer Id: " + customerId+ " Not Found");
        return inquiries;
    }

    // Add Product Inquiry Service
    @Transactional
    public void addInquiry(ProductInquiryDTO productInquiryDTO)
    {
        Product product = productRepository.findProductById(productInquiryDTO.getProductId());
        Customer customer = customerRepository.findCustomerByEmail(productInquiryDTO.getCustomerEmail());

        if(product == null || customer == null)
            throw new objectNotFoundException("Product or Customer Not Found");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String cDate = now.format(formatter);

        ProductInquiry productInquiry = new ProductInquiry();
        productInquiry.setProduct(product);
        productInquiry.setCustomer(customer);
        productInquiry.setInquiryMessage(productInquiryDTO.getMessage());
        productInquiry.setInquiryQuantity(productInquiryDTO.getQuantity());
        productInquiry.setIsDeleted(1);
        productInquiry.setcDate(cDate);
        productInquiryRepository.save(productInquiry);
    }

    // Find All Inquiries Service
    public List<ProductInquiry> findAllInquires(){
        List<ProductInquiry> inquiries = productInquiryRepository.findAllInquiry();

        if(inquiries.isEmpty()){
            throw new objectNotFoundException("Product Inquiry Not Found");

        }
        return inquiries;
    }

    // Search Inquiry Service
    public List<ProductInquiry> searchInquires(String searchText) {
        List<ProductInquiry> inquiries = productInquiryRepository.searchInquiryByKeyword(searchText);
        if(inquiries.isEmpty()) {
            throw new objectNotFoundException("Customer list is empty");
        }
        return inquiries;
    }

    public void deleteInquiry(int id) {
        ProductInquiry inquiry = productInquiryRepository.findProductInquiryById(id);
        if (inquiry == null)
            throw new objectNotFoundException("Product Inquiry Not Found");
        inquiry.setIsDeleted(2);
        productInquiryRepository.save(inquiry);

    }
}
