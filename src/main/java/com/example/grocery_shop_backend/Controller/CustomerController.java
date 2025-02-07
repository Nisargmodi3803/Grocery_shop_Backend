package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.CustomerLoginDTO;
import com.example.grocery_shop_backend.Dto.CustomerRegistrationDTO;
import com.example.grocery_shop_backend.Dto.CustomerBasicDetailsDTO;
import com.example.grocery_shop_backend.Dto.UpdatePasswordDTO;
import com.example.grocery_shop_backend.Entities.Customer;
import com.example.grocery_shop_backend.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // GET API {Customer By ID}
    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    // POST API {Registration API}
    @PostMapping("/register")
    public ResponseEntity<String> saveCustomer(@RequestBody CustomerRegistrationDTO registrationDTO) {
        boolean result = customerService.saveCustomer(registrationDTO);
        if (result) {
            return ResponseEntity.ok("Customer registered successfully");
        }
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
    }

    // POST API {Login API}
    @PostMapping("/login")
    public ResponseEntity<String> loginCustomer(@RequestBody CustomerLoginDTO loginDTO) {
        boolean result = customerService.login(loginDTO);
        if(result)
        {
            return ResponseEntity.ok("Login successful");
//                return "success";
        }
        else
        {
            return ResponseEntity.badRequest().body("Email or Password is incorrect");
//            return "fail";
        }
    }

    // PATCH API {Update Profile}
    @PatchMapping("/update-profile/{mobile}")
    public ResponseEntity<CustomerBasicDetailsDTO> updateProfile(@PathVariable String mobile, @RequestBody CustomerBasicDetailsDTO customerBasicDetailsDTO) {
        customerService.updateCustomerBasicDetails(mobile, customerBasicDetailsDTO);
        return ResponseEntity.ok(customerService.getBasicDetails(mobile));
    }

    // PATCH API {Change Password}
    @PatchMapping("/change-password/{mobile}")
    public ResponseEntity<String> changePassword(@PathVariable String mobile, @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        try {
            String result = customerService.changePassword(mobile, updatePasswordDTO.getOldPassword(), updatePasswordDTO.getNewPassword());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // PATCH API {Change Profile Image}
    @PatchMapping("/change-profile-image/{email}")
    public ResponseEntity<String> changeProfileImage(@PathVariable String email, @RequestParam String image) {
        try {
            String result = customerService.changeProfileImage(email, image);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // GET API {Profile}
    @GetMapping("/profile/{mobile}")
    public ResponseEntity<CustomerBasicDetailsDTO> profile(@PathVariable String mobile) {
        return ResponseEntity.ok(customerService.getBasicDetails(mobile));
    }

    // GET API {Customer By Mobile}
    @GetMapping("/customer-mobile/{mobileNumber}")
    public ResponseEntity<Customer> getCustomerByMobile(@PathVariable String mobileNumber) {
        return ResponseEntity.ok(customerService.getCustomerByMobile(mobileNumber));
    }

    // GET API {Customer by Email}
    @GetMapping("/customer-email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        return ResponseEntity.ok(customerService.getCustomerByEmail(email));
    }

    // PATCH API {Delete Customer}
    @PatchMapping("/delete-customer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok("Customer deleted successfully!");
    }

    // PATCH API {Retrieve Customer}
    @PatchMapping("/retrieve-customer/{customerId}")
    public ResponseEntity<String> retrieveCustomer(@PathVariable int customerId) {
        boolean success = customerService.retrieveCustomer(customerId);
        if (success) {
            return ResponseEntity.ok("Customer retrieved successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer Already Present in Database");
        }
    }

    // POST API {Logout}
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logout successful! Please remove token from sessionStorage on the frontend.");
    }

    // PATCH API {Forgot Password}
    @PatchMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody CustomerLoginDTO loginDTO) {
        boolean result = customerService.forgotPassword(loginDTO);
        if (result) {
            return ResponseEntity.ok("Password forgot successful");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not Found");
        }
    }
}
