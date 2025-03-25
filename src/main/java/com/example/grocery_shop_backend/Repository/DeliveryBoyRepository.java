package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.Customer;
import com.example.grocery_shop_backend.Entities.DeliveryBoy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryBoyRepository extends JpaRepository<DeliveryBoy, Integer>
{
    @Query("SELECT boys FROM DeliveryBoy boys WHERE boys.isDeleted=1")
    List<DeliveryBoy> getDeliveryBoys();

    @Query("SELECT boy FROM DeliveryBoy boy WHERE boy.deliveryBoyId= :id AND boy.isDeleted=1")
    DeliveryBoy getDeliveryBoyById(int id);

    @Query("SELECT c FROM DeliveryBoy c " +
            "WHERE c.isDeleted = 1 AND (" +
            "CAST(c.deliveryBoyId AS string) LIKE %:keyword% OR " +
            "LOWER(c.deliveryBoyName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "c.deliveryBoyEmail LIKE %:keyword% OR " +
            "LOWER(c.deliveryVehicleNo) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.deliveryRoute) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "c.deliveryBoyMobileNo LIKE %:keyword%)")
    List<DeliveryBoy> searchDeliveryBoyByKeyword(@Param("keyword") String keyword);
}
