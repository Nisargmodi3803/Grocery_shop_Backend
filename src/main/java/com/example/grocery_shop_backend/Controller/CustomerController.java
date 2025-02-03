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

@RestController
@CrossOrigin(origins = "*")
public class CustomerController
{
    @Autowired
    CustomerService customerService;

    // GET API {Customer By ID}
    @GetMapping("/customer/{id}")
    public Customer getCustomerById(@PathVariable int id)
    {
        return customerService.getCustomerById(id);
    }

    // POST API {Registration API} Save Customer
    @PostMapping("/register")
    public String saveCustomer(@RequestBody CustomerRegistrationDTO registrationDTO)
    {
        try
        {
            customerService.saveCustomer(registrationDTO.getCustomerName(),registrationDTO.getCustomerMobile(),registrationDTO.getCustomerPassword(),registrationDTO.getCustomerOtp());
            return "Customer saved successfully!";
        }
        catch (Exception e)
        {
            return "Error saving customer: " + e.getMessage();
        }
    }

    // GET API {Login API}
    @GetMapping("/login")
    public ResponseEntity<String> loginCustomer(@RequestBody CustomerLoginDTO loginDTO) {
        try {
            String result = customerService.login(loginDTO.getCustomerMobile(), loginDTO.getCustomerPassword());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // Return error response with exception message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // PATCH API {Update Profile}
    @PatchMapping("/update-profile/{mobile}")
    public CustomerBasicDetailsDTO updateProfile(@PathVariable String mobile,@RequestBody CustomerBasicDetailsDTO customerBasicDetailsDTO)
    {
        customerService.updateCustomerBasicDetails(mobile, customerBasicDetailsDTO);
        return customerService.getBasicDetails(mobile);
    }

    // PATCH API {Change Password}
    @PatchMapping("/change-password/{mobile}")
    public ResponseEntity<String> changePassword(@PathVariable String mobile, @RequestBody UpdatePasswordDTO updatePasswordDTO)
    {
        try
        {
            String result = customerService.changePassword(mobile,updatePasswordDTO.getOldPassword(),updatePasswordDTO.getNewPassword());
            return ResponseEntity.ok(result);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // PATCH API {Change Profile Image}
    @PatchMapping("/change-profile-image/{email}")
    public ResponseEntity<String> changeProfileImage(@PathVariable String email,@RequestParam String image )
    {
        try
        {
            String result = customerService.changeProfileImage(email,image);
            return ResponseEntity.ok(result);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // GET API {Profile}
    @GetMapping("/profile/{mobile}")
    public CustomerBasicDetailsDTO profile(@PathVariable String mobile)
    {
        return customerService.getBasicDetails(mobile);
    }

    // GET API {Customer By Mobile}
    @GetMapping("/customer-mobile/{mobileNumber}")
    public Customer getCustomerByMobile(@PathVariable String mobileNumber)
    {
        return customerService.getCustomerByMobile(mobileNumber);
    }

    // PATCH API {Delete Customer}
    @PatchMapping("/delete-customer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int customerId)
    {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok("Customer deleted successfully!");
    }

    // PATCH API {Retrieve Customer}
    @PatchMapping("/retrieve-customer/{customerId}")
    public ResponseEntity<String> retrieveCustomer(@PathVariable int customerId)
    {
        boolean success = customerService.retrieveCustomer(customerId);

        if(success)
            return ResponseEntity.ok("Customer retrieved successfully!");
        else
            return new ResponseEntity<>("Customer Already Present in Database", HttpStatus.BAD_REQUEST);
    }
}
