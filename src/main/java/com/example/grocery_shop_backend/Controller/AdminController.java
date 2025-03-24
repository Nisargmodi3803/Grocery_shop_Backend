package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.AdminDTO;
import com.example.grocery_shop_backend.Dto.CustomerLoginDTO;
import com.example.grocery_shop_backend.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController
{
    @Autowired
    private AdminService adminService;

    // POST API {New Admin}
    @PostMapping("/admin/register")
    public ResponseEntity<String> newAdmin(@RequestBody AdminDTO adminDTO){
        try {
            adminService.addAdmin(adminDTO);
            return ResponseEntity.ok("Admin registered successfully");
        }catch (Exception e){
            return new ResponseEntity<>("Admin already exists", HttpStatus.CONFLICT);
        }
    }

    // GET API {Login}
    @PostMapping("/admin/login")
    public ResponseEntity<String> loginAdmin(@RequestBody AdminDTO adminDTO) {
        boolean result = adminService.loginAdmin(adminDTO);
        if (result) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.badRequest().body("UserName or Password is incorrect");
        }
    }

    //PATCH API {Change Password}
    @PatchMapping("/admin/change-password")
    public ResponseEntity<String> changePassword(@RequestParam String userName, @RequestParam String oldPassword, @RequestParam String newPassword) {
        try {
            boolean result = adminService.changePassword(userName, oldPassword, newPassword);
            if (result) {
                return ResponseEntity.ok("Password changed successfully");
            }else {
                return new ResponseEntity<>("Password doesn't match", HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            return new ResponseEntity<>("Password doesn't match", HttpStatus.NOT_FOUND);
        }
    }

    //GET API {Check User Name}
    @GetMapping("/admin/check-username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {
        boolean result = adminService.checkIfAdminExists(username);
        if (result) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
}
