package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Entities.DeliveryTimeSlot;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.DeliveryTimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DeliveryTimeSlotService
{
    @Autowired
    private DeliveryTimeSlotRepository deliveryTimeSlotRepository;

    // Get All Time Slots Service
    public List<DeliveryTimeSlot> getAllTimeSlot()
    {
        List<DeliveryTimeSlot> deliveryTimeSlots = deliveryTimeSlotRepository.getDeliveryTimeSlots();
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

    //Search Delivery Time Service
    public List<DeliveryTimeSlot> searchDeliveryTime(String slot){
        List<DeliveryTimeSlot> deliveryTimeSlots = deliveryTimeSlotRepository.searchDeliveryTime(slot);

        if(deliveryTimeSlots.isEmpty()){
            throw new objectNotFoundException("No delivery slots found");
        }
        return deliveryTimeSlots;
    }

    // add Delivery Time Slot
    public void addTimeSlot(String slot,int priority){
        List<DeliveryTimeSlot> slots = deliveryTimeSlotRepository.getDeliveryTimeSlotByDeliveryTime(slot);
        if(slots.isEmpty()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String currentDateTime = now.format(formatter);

            DeliveryTimeSlot deliveryTimeSlot = new DeliveryTimeSlot();
            deliveryTimeSlot.setDeliveryTime(slot);
            deliveryTimeSlot.setDeliveryTimePrioriy(priority);
            deliveryTimeSlot.setcDate(currentDateTime);
            deliveryTimeSlot.setIsDeleted(1);
            deliveryTimeSlotRepository.save(deliveryTimeSlot);
        }else{
            throw new objectNotFoundException("There is already a delivery slot found");
        }
    }

    // update Delivery Time Slot
    public void updateTimeSlot(int id,String slot,int priority){
        DeliveryTimeSlot deliveryTimeSlot = deliveryTimeSlotRepository.getDeliveryTimeSlotById(id);
        if(deliveryTimeSlot == null){
            throw new objectNotFoundException("No delivery slot found");
        }
        deliveryTimeSlot.setDeliveryTime(slot);
        deliveryTimeSlot.setDeliveryTimePrioriy(priority);
        deliveryTimeSlotRepository.save(deliveryTimeSlot);
    }

    // Delete Delivery Time Slot
    public void deleteTimeSlot(int id){
        DeliveryTimeSlot deliveryTimeSlot = deliveryTimeSlotRepository.getDeliveryTimeSlotById(id);
        if(deliveryTimeSlot == null){
            throw new objectNotFoundException("No delivery slot found");
        }
        deliveryTimeSlot.setIsDeleted(2);
        deliveryTimeSlotRepository.save(deliveryTimeSlot);
    }
}
