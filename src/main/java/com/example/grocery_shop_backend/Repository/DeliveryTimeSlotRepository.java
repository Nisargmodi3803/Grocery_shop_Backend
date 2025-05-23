package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.DeliveryTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryTimeSlotRepository extends JpaRepository<DeliveryTimeSlot, Integer>
{
    @Query("SELECT slot FROM DeliveryTimeSlot slot WHERE slot.isDeleted=1")
    List<DeliveryTimeSlot> getDeliveryTimeSlots();

    @Query("SELECT slot FROM DeliveryTimeSlot slot WHERE slot.deliveryTimeSlotId = :id AND slot.isDeleted=1")
    DeliveryTimeSlot getDeliveryTimeSlotById(int id);

    @Query("SELECT slot FROM DeliveryTimeSlot slot WHERE LOWER(slot.deliveryTime) LIKE LOWER(CONCAT('%', :slot ,'%')) AND slot.isDeleted=1")
    List<DeliveryTimeSlot> searchDeliveryTime(String slot);

    @Query("SELECT slot FROM DeliveryTimeSlot slot WHERE slot.deliveryTime= :slot AND slot.isDeleted=1")
    List<DeliveryTimeSlot> getDeliveryTimeSlotByDeliveryTime(String slot);
}
