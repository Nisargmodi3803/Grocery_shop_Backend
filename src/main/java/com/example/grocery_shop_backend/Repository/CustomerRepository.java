package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer>
{
    @Query("SELECT customer FROM Customer customer WHERE customer.customerId = :id AND customer.isDeleted=1")
    public Customer findCustomerById(int id);

    @Query("SELECT customer FROM Customer customer WHERE customer.customerMobile = :mobile AND customer.isDeleted=1")
    public Customer findCustomerByMobile(String mobile);

    @Query("SELECT customer.customerPassword FROM Customer customer WHERE customer.customerEmail = :email AND customer.isDeleted=1")
    public String getHashedPassword(@Param("email") String email);

    @Query("SELECT customer FROM Customer customer WHERE customer.customerEmail = :customerEmail AND customer.isDeleted=1")
    public Customer findCustomerByEmail(String customerEmail);

    @Query("SELECT customer.customerReferralCode FROM Customer customer WHERE customer.customerReferralCode = :code AND customer.isDeleted=1")
    public String getReferralCode(String code);

    @Query("SELECT customer FROM Customer customer WHERE customer.customerReferralCode = :referralCode AND customer.isDeleted=1")
    public Customer findCustomerByReferralCode(String referralCode);

    @Query("SELECT customer FROM Customer customer WHERE customer.isDeleted=1")
    public List<Customer> findAllCustomers();

    @Query("SELECT c FROM Customer c " +
            "WHERE c.isDeleted = 1 AND (" +
            "CAST(c.customerId AS string) LIKE %:keyword% OR " +
            "LOWER(c.customerName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "c.customerEmail LIKE %:keyword% OR " +
            "c.customerMobile LIKE %:keyword%)")
    List<Customer> searchCustomerByKeyword(@Param("keyword") String keyword);

}
