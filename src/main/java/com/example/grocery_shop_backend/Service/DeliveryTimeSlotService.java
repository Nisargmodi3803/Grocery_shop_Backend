package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Entities.DeliveryTimeSlot;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.DeliveryTimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryTimeSlotService
{
    @Autowired
    private DeliveryTimeSlotRepository deliveryTimeSlotRepository;

    // Get All Time Slots Service
    public List<DeliveryTimeSlot> getAllTimeSlot()
    {
        List<DeliveryTimeSlot> deliveryTimeSlots = deliveryTimeSlotRepository.findAll();
        if (deliveryTimeSlots.isEmpty())
            throw new objectNotFoundException("No delivery slots found");
        return deliveryTimeSlots;
    }

    // Find Slot by ID
    public DeliveryTimeSlot findSlotById(int id)
    {
        DeliveryTimeSlot deliveryTimeSlot = deliveryTimeSlotRepository.getDeliveryTimeSlotById(id);

        if (deliveryTimeSlot == null)
            throw new objectNotFoundException("No delivery slot found");
        return deliveryTimeSlot;
    }
}
