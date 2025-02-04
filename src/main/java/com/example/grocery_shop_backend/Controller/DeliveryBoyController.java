package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Entities.DeliveryBoy;
import com.example.grocery_shop_backend.Service.DeliveryBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class DeliveryBoyController
{
    @Autowired
    private DeliveryBoyService deliveryBoyService;

    // GET API {Find All Delievery Boys}
    @GetMapping("/delivery-boys")
    public List<DeliveryBoy> findAll()
    {
        return deliveryBoyService.findAll();
    }
}
