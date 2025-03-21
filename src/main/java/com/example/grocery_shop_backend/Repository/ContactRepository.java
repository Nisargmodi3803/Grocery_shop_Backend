package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.Contact;
import com.example.grocery_shop_backend.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer>
{
    @Query("SELECT contact FROM Contact contact WHERE contact.isDeleted=1")
    List<Contact> findAllContacts();

    @Query("SELECT contact FROM Contact contact WHERE contact.id= :id AND contact.isDeleted=1")
    Contact findContactById(int id);

    @Query("SELECT c FROM Contact c " +
            "WHERE c.isDeleted = 1 AND (" +
            "CAST(c.id AS string) LIKE %:keyword% OR " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "c.email LIKE %:keyword% OR " +
            "c.mobile LIKE %:keyword%)")
    List<Contact> searchCustomerByKeyword(@Param("keyword") String keyword);
}
