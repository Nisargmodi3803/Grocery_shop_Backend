package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.ContactDTO;
import com.example.grocery_shop_backend.Entities.Contact;
import com.example.grocery_shop_backend.Entities.Customer;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.ContactRepository;
import com.example.grocery_shop_backend.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ContactService
{
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // add to Contact
    @Transactional
    public void insertContact(ContactDTO contactDTO)
    {
        Customer customer = customerRepository.findById(contactDTO.getCustomerId())
                .orElseThrow(() -> new objectNotFoundException("Customer not found"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentDateTime = now.format(formatter);

        Contact contact = new Contact();
        contact.setCustomer(customer);
        contact.setName(contactDTO.getName());
        contact.setEmail(contactDTO.getEmail());
        contact.setMessage(contactDTO.getMessage());
        contact.setMobile(contactDTO.getMobile());
        contact.setC_date(currentDateTime);
        contactRepository.save(contact);
    }
}
