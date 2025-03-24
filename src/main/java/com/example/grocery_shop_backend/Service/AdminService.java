package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.AdminDTO;
import com.example.grocery_shop_backend.Entities.Admin;
import com.example.grocery_shop_backend.Entities.Customer;
import com.example.grocery_shop_backend.Exception.MobileNumberAlreadyExistsException;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AdminService
{
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private AdminRepository adminRepository;

    // New Admin Service
    @Transactional
    public void addAdmin(AdminDTO adminDTO){
        Admin existingAdmin = adminRepository.findByUserName(adminDTO.getUserName());

        if(existingAdmin != null){
            throw new MobileNumberAlreadyExistsException("Admin with User Name " + adminDTO.getUserName() + " already exists");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String cDate = now.format(formatter);

        String encodedPassword = passwordEncoder.encode(adminDTO.getPassword());

        Admin admin = new Admin();
        admin.setUserName(adminDTO.getUserName());
        admin.setPassword(encodedPassword);
        admin.setDate(cDate);
        admin.setIsDeleted(1);
        adminRepository.save(admin);
    }

    // Login Admin Service
    public boolean loginAdmin(AdminDTO adminDTO){
        Admin admin = adminRepository.findByUserName(adminDTO.getUserName());
        if(admin == null){
            throw new objectNotFoundException("Admin with User Name " + adminDTO.getUserName() + " Not Found");
        }

        String encodedPassword = adminRepository.findPasswordByUserName(adminDTO.getUserName());
        if (passwordEncoder.matches(adminDTO.getPassword(), encodedPassword)) {
            return true; // Return the customer data directly (no token needed)
        } else {
            return false;
        }
    }

    // Change Password Service
    @Transactional
    public boolean changePassword(String userName, String oldPassword, String newPassword) {
        Admin existingAdmin = adminRepository.findByUserName(userName);
        if (existingAdmin != null) {
            if (passwordEncoder.matches(oldPassword, existingAdmin.getPassword())) {
                existingAdmin.setPassword(passwordEncoder.encode(newPassword));
                return true;
            } else {
                return false;
            }
        } else {
            throw new objectNotFoundException("Admin with User Name " + userName + " not found");
        }
    }

    // Check UserName Service
    public boolean checkIfAdminExists(String userName){
        Admin admin = adminRepository.findByUserName(userName);
        if(admin != null){
            return true;
        }else{
            return false;
        }
    }
}
