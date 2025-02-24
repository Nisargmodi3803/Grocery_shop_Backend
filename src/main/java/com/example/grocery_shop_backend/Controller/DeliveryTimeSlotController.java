package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Entities.DeliveryTimeSlot;
import com.example.grocery_shop_backend.Service.DeliveryTimeSlotService;
import com.twilio.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class DeliveryTimeSlotController
{
    @Autowired
    private DeliveryTimeSlotService deliveryTimeSlotService;

    // GET API {Get all Time Slots}
    @GetMapping("/time-slot")
    public ResponseEntity<List<DeliveryTimeSlot>> getAllTimeSlot()
    {
        try{
            return new ResponseEntity<>(deliveryTimeSlotService.getAllTimeSlot(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Find Slot by ID}
    @GetMapping("/time-slot/{id}")
    public ResponseEntity<DeliveryTimeSlot> findSlotById(@PathVariable int id)
    {
        try{
            return new ResponseEntity<>(deliveryTimeSlotService.findSlotById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
