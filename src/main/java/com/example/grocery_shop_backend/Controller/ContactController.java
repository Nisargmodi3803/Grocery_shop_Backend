package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.ContactDTO;
import com.example.grocery_shop_backend.Entities.Contact;
import com.example.grocery_shop_backend.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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
}
