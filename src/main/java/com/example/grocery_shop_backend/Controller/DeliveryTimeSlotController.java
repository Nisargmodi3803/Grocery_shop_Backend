package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Entities.DeliveryTimeSlot;
import com.example.grocery_shop_backend.Service.DeliveryTimeSlotService;
import com.twilio.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}, allowCredentials = "true")
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

    //GET API {Search Delivery Time Slot}
    @GetMapping("/search-slot")
    public ResponseEntity<List<DeliveryTimeSlot>> searchDeliveryTime(@RequestParam String keyword){
        try {
            return new ResponseEntity<>(deliveryTimeSlotService.searchDeliveryTime(keyword),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add-slot")
    public ResponseEntity<String> addSlot(@RequestParam String slot, @RequestParam int priority){
        try {
            deliveryTimeSlotService.addTimeSlot(slot, priority);
            return new ResponseEntity<>(slot, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/update-slot/{id}")
    public ResponseEntity<String> updateSlot(@RequestParam String slot, @RequestParam int priority, @PathVariable int id){
        try {
            deliveryTimeSlotService.updateTimeSlot(id, slot, priority);
            return new ResponseEntity<>(slot, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/delete-slot/{id}")
    public ResponseEntity<String> deleteSlot(@PathVariable int id){
        try {
            deliveryTimeSlotService.deleteTimeSlot(id);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
