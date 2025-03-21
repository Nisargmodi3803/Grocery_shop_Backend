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
import java.util.List;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentDateTime = now.format(formatter);

        Contact contact = new Contact();
        contact.setName(contactDTO.getName());
        contact.setEmail(contactDTO.getEmail());
        contact.setMessage(contactDTO.getMessage());
        contact.setMobile(contactDTO.getMobile());
        contact.setC_date(currentDateTime);
        contact.setIsDeleted(1);
        contactRepository.save(contact);
    }

    // Find All Contacts
    public List<Contact>  findAllContacts(){
        List<Contact> contacts = contactRepository.findAllContacts();
        if (contacts.isEmpty())
            throw new objectNotFoundException("No contacts found");
        return contacts;
    }

    // Delete Contacts
    public void deleteContact(int id){
        Contact contact = contactRepository.findContactById(id);
        if(contact == null){
            throw new objectNotFoundException("No contact found");
        }
        contact.setIsDeleted(2);
        contactRepository.save(contact);
    }

    // Search Contact Service
    public List<Contact> searchContact(String searchText) {
        List<Contact> contacts = contactRepository.searchCustomerByKeyword(searchText);
        if(contacts.isEmpty()) {
            throw new objectNotFoundException("Customer list is empty");
        }
        return contacts;
    }
}
