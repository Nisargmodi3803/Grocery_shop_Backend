package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer>
{
    @Query("SELECT customer FROM Customer customer WHERE customer.customerId = :id AND customer.isDeleted=1")
    public Customer findCustomerById(int id);

    @Query("SELECT customer FROM Customer customer WHERE customer.customerMobile = :mobile AND customer.isDeleted=1")
    public Customer findCustomerByMobile(String mobile);

    @Query("SELECT customer.customerPassword FROM Customer customer WHERE customer.customerMobile = :mobile AND customer.isDeleted=1")
    public String getHashedPassword(@Param("mobile")String mobile);

    @Query("SELECT customer FROM Customer customer WHERE customer.customerEmail = :customerEmail AND customer.isDeleted=1")
    public Customer findCustomerByEmail(String customerEmail);
}
