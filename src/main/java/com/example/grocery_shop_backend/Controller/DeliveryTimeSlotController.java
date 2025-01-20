package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Entities.DeliveryTimeSlot;
import com.example.grocery_shop_backend.Service.DeliveryTimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeliveryTimeSlotController
{
    @Autowired
    private DeliveryTimeSlotService deliveryTimeSlotService;

    // GET API {Get all Time Slots}
    @GetMapping("/time-slot")
    public List<DeliveryTimeSlot> getAllTimeSlot()
    {
        return deliveryTimeSlotService.getAllTimeSlot();
    }
}
