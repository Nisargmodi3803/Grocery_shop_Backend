package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Entities.DeliveryBoy;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.DeliveryBoyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryBoyService
{
    @Autowired
    private DeliveryBoyRepository deliveryBoyRepository;

    // Find All Delivery Boy
    public List<DeliveryBoy> findAll()
    {
        List<DeliveryBoy> deliveryBoys = deliveryBoyRepository.getDeliveryBoys();
        if (deliveryBoys.isEmpty())
            throw new objectNotFoundException("No delivery boys found");
        return deliveryBoys;
    }
}
