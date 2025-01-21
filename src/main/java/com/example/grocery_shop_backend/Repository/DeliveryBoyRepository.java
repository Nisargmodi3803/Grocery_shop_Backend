package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.DeliveryBoy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryBoyRepository extends JpaRepository<DeliveryBoy, Integer>
{
    @Query("SELECT boys FROM DeliveryBoy boys WHERE boys.isDeleted=1")
    List<DeliveryBoy> getDeliveryBoys();
}
