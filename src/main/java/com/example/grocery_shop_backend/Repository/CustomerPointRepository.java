package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.CustomerPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerPointRepository extends JpaRepository<CustomerPoint, Integer>
{
    @Query("SELECT point FROM CustomerPoint point WHERE point.customerPointType=1 AND point.customer.isDeleted=1")
    List<CustomerPoint> findAllInCustomerPoints();

    @Query("SELECT point FROM CustomerPoint point WHERE point.customerPointType=2 AND point.customer.isDeleted=1")
    List<CustomerPoint> findALlOutCustomerPoints();

    @Query("SELECT point FROM CustomerPoint point WHERE point.customer.customerId = :customerId AND point.customer.isDeleted=1")
    List<CustomerPoint> findPointsByCustomerId(int customerId);
}
