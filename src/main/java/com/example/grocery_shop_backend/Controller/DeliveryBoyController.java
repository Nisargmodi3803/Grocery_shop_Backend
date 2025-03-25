package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.DeliveryBoyDTO;
import com.example.grocery_shop_backend.Entities.DeliveryBoy;
import com.example.grocery_shop_backend.Service.DeliveryBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
public class DeliveryBoyController
{
    @Autowired
    private DeliveryBoyService deliveryBoyService;

    // GET API {Find All Delivery Boys}
    @GetMapping("/delivery-boys")
    public ResponseEntity<List<DeliveryBoy>> findAll()
    {
        try {
            return new ResponseEntity<>(deliveryBoyService.findAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find by ID}
    @GetMapping("/delivery-boy/{id}")
    public ResponseEntity<DeliveryBoy> findById(@PathVariable int id){
        try {
            return new ResponseEntity<>(deliveryBoyService.findById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST API {Add Delivery Boy}
    @PostMapping("/add-delivery-boy")
    public ResponseEntity<String> addDeliveryBoy(@RequestBody DeliveryBoyDTO deliveryBoyDTO){
        try {
            deliveryBoyService.addDeliveryBoy(deliveryBoyDTO);
            return new ResponseEntity<>("Successfully added deliveryBoy", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // PATCH API{Update Delivery Boy}
    @PatchMapping("/update-delivery-boy/{id}")
    public ResponseEntity<String> updateDeliveryBoy(@RequestBody DeliveryBoyDTO deliveryBoyDTO, @PathVariable int id){
        try {
            deliveryBoyService.updateDeliveryBoy(id, deliveryBoyDTO);
            return new ResponseEntity<>("Successfully updated deliveryBoy", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // PATCH API{Delete Delivery Boy}
    @PatchMapping("/delete-delivery-boy/{id}")
    public ResponseEntity<String> deleteDeliveryBoy(@PathVariable int id){
        try {
            deliveryBoyService.deleteById(id);
            return new ResponseEntity<>("Successfully deleted deliveryBoy", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Search API}
    @GetMapping("/search-delivery-boy")
    public ResponseEntity<List<DeliveryBoy>> searchDeliveryBoy(@RequestParam String keyword){
        try {
            return new ResponseEntity<>(deliveryBoyService.search(keyword), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
