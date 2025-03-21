package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.ContactDTO;
import com.example.grocery_shop_backend.Entities.Contact;
import com.example.grocery_shop_backend.Entities.Customer;
import com.example.grocery_shop_backend.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
public class ContactController
{
    @Autowired
    private ContactService contactService;

    // POST API {Insert into Contact}
    @PostMapping("/contact")
    public ResponseEntity<String> insertContact(@RequestBody ContactDTO contactDTO)
    {
        try {
            contactService.insertContact(contactDTO);
            return ResponseEntity.ok("Insert successful");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Insert failed");
        }
    }

    @GetMapping("/search-contact")
    public ResponseEntity<List<Contact>> searchContact(@RequestParam String keyword) {
        try {
            return ResponseEntity.ok(contactService.searchContact(keyword));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> findAllContacts() {
        try {
            return ResponseEntity.ok(contactService.findAllContacts());
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/delete-contact/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable int id) {
        try {
            contactService.deleteContact(id);
            return ResponseEntity.ok("Delete successful");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Delete failed");
        }
    }
}
