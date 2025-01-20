package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer>
{

}
