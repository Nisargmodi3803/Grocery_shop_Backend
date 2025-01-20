package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.DeliveryTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryTimeSlotRepository extends JpaRepository<DeliveryTimeSlot, Integer>
{
    @Query("SELECT slot FROM DeliveryTimeSlot slot")
    List<DeliveryTimeSlot> getDeliveryTimeSlots();
}
