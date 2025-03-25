package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.DeliveryBoyDTO;
import com.example.grocery_shop_backend.Entities.DeliveryBoy;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.DeliveryBoyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryBoyService
{
    @Autowired
    private DeliveryBoyRepository deliveryBoyRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Find All Delivery Boy
    public List<DeliveryBoy> findAll()
    {
        List<DeliveryBoy> deliveryBoys = deliveryBoyRepository.getDeliveryBoys();
        if (deliveryBoys.isEmpty())
            throw new objectNotFoundException("No delivery boys found");
        return deliveryBoys;
    }

    // Find by ID Service
    public DeliveryBoy findById(int id){
        DeliveryBoy deliveryBoy = deliveryBoyRepository.getDeliveryBoyById(id);
        if(deliveryBoy == null)
            throw new objectNotFoundException("No delivery boys found");
        return deliveryBoy;
    }

    //Delete Delivery Boy Service
    public void deleteById(int id){
        DeliveryBoy deliveryBoy = deliveryBoyRepository.getDeliveryBoyById(id);
        if(deliveryBoy == null)
            throw new objectNotFoundException("No delivery boys found");
        deliveryBoy.setIsDeleted(2);
        deliveryBoyRepository.save(deliveryBoy);
    }

    // Search Delivery Boy Service
    public List<DeliveryBoy> search(String keyword){
        List<DeliveryBoy> deliveryBoys = deliveryBoyRepository.searchDeliveryBoyByKeyword(keyword);
        if(deliveryBoys.isEmpty())
            throw new objectNotFoundException("No delivery boys found");
        return deliveryBoys;
    }

    // Add New Delivery Boy Service
    public void addDeliveryBoy(DeliveryBoyDTO deliveryBoyDTO){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String cDate = now.format(formatter);

        String encodedPassword = passwordEncoder.encode(deliveryBoyDTO.getPassword());

        DeliveryBoy deliveryBoy = new DeliveryBoy();
        deliveryBoy.setDeliveryBoyName(deliveryBoyDTO.getName());
        deliveryBoy.setDeliveryBoyEmail(deliveryBoyDTO.getEmail());
        deliveryBoy.setDeliveryBoyMobileNo(deliveryBoyDTO.getMobile());
        deliveryBoy.setDeliveryBoyPassword(encodedPassword);
        deliveryBoy.setDeliveryRoute(deliveryBoyDTO.getRoute());
        deliveryBoy.setDeliveryVehicleNo(deliveryBoyDTO.getVehicleNo());
        deliveryBoy.setIsDeleted(1);
        deliveryBoy.setcDate(cDate);
        deliveryBoyRepository.save(deliveryBoy);
    }

    // Update Delivery Boy Service
    public void updateDeliveryBoy(int id,DeliveryBoyDTO deliveryBoyDTO){
        DeliveryBoy deliveryBoy = deliveryBoyRepository.getDeliveryBoyById(id);

        if(deliveryBoy == null)
            throw new objectNotFoundException("No delivery boys found");

        deliveryBoy.setDeliveryBoyName(deliveryBoyDTO.getName());
        deliveryBoy.setDeliveryBoyEmail(deliveryBoyDTO.getEmail());
        deliveryBoy.setDeliveryBoyMobileNo(deliveryBoyDTO.getMobile());
        deliveryBoy.setDeliveryVehicleNo(deliveryBoyDTO.getVehicleNo());
        deliveryBoy.setDeliveryRoute(deliveryBoyDTO.getRoute());

        if(deliveryBoyDTO.getPassword()!=null) {
            String encodedPassword = passwordEncoder.encode(deliveryBoyDTO.getPassword());
            deliveryBoy.setDeliveryBoyPassword(encodedPassword);
        }

        deliveryBoyRepository.save(deliveryBoy);
    }
}
