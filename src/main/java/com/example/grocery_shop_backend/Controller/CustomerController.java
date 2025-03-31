package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.*;
import com.example.grocery_shop_backend.Entities.Customer;
import com.example.grocery_shop_backend.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

import static org.springframework.web.servlet.function.ServerResponse.status;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // GET API {Customer By ID}
    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(customerService.getCustomerById(id));
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // POST API {Registration API}
    @PostMapping("/register")
    public ResponseEntity<String> saveCustomer(@RequestBody CustomerRegistrationDTO registrationDTO) {
        boolean result = customerService.saveCustomer(registrationDTO);
        try {
            if (result) {
                return ResponseEntity.ok("Customer registered successfully");
            } else
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Mobile Number already exists");
        }
    }

    // POST API {Login API}
    @PostMapping("/login")
    public ResponseEntity<String> loginCustomer(@RequestBody CustomerLoginDTO loginDTO) {
        try {
            boolean result = customerService.login(loginDTO);
            if (result) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.badRequest().body("Email or Password is incorrect");
            }
        }catch (AccessDeniedException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Your account is blocked. Please contact support.");
        }
    }

    // PATCH API {Update Profile}
    @PatchMapping("/update-profile/{email}")
    public ResponseEntity<String> updateProfile(@PathVariable String email, @RequestBody CustomerBasicDetailsDTO customerBasicDetailsDTO) {
        try{
            customerService.updateCustomerBasicDetails(email, customerBasicDetailsDTO);
            return ResponseEntity.ok("Successfully updated profile");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // PATCH API {Change Password}
    @PatchMapping("/change-password/{email}")
    public ResponseEntity<String> changePassword(@PathVariable String email, @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        try {
            boolean result = customerService.changePassword(email, updatePasswordDTO.getOldPassword(), updatePasswordDTO.getNewPassword());
            if(result){
                return ResponseEntity.ok("Password changed successfully");
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Old Password does not match");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // PATCH API {Change Profile Image}
    @PatchMapping("/change-profile-image/{email}")
    public ResponseEntity<String> changeProfileImage(@PathVariable String email, @RequestParam("file") MultipartFile file) {
        try {
            String result = customerService.changeProfileImage(email, file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }


    // GET API {Profile}
    @GetMapping("/profile/{email}")
    public ResponseEntity<CustomerBasicDetailsDTO> profile(@PathVariable String email) {
        try{
            return ResponseEntity.ok(customerService.getBasicDetails(email));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET API {Customer By Mobile}
    @GetMapping("/customer-mobile/{mobileNumber}")
    public ResponseEntity<Customer> getCustomerByMobile(@PathVariable String mobileNumber) {
        return ResponseEntity.ok(customerService.getCustomerByMobile(mobileNumber));
    }

    // GET API {Customer by Email}
    @GetMapping("/customer-email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        try{
            return ResponseEntity.ok(customerService.getCustomerByEmail(email));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PATCH API {Delete Customer}
    @PatchMapping("/delete-customer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int customerId) {
        try {
            customerService.deleteCustomer(customerId);
            return ResponseEntity.ok("Customer deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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

    //PATCH API {Delete Profile Image}
    @PatchMapping("/delete-profile-image/{email}")
    public ResponseEntity<String> deleteProfileImage(@PathVariable String email) {
        try{
            customerService.deleteProfileImage(email);
            return ResponseEntity.ok("Delete profile image successfully");
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Get API {Check Referral Code Exist or not}
    @GetMapping("/check-referral-code")
    public ResponseEntity<String> checkReferralCode(@RequestParam String referralCode) {
        boolean result = customerService.checkReferralCode(referralCode);
        if (result) {
            return ResponseEntity.ok("Referral code is valid");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Referral code is not valid");
        }
    }

    // GET API {Get Customer by Referral Code}
    @GetMapping("/customer-referral-code")
    public ResponseEntity<Customer> getCustomerReferralCode(@RequestParam String referralCode) {
        try {
            return ResponseEntity.ok(customerService.getCustomerByReferralCode(referralCode));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/customer-update/{customerEmail}")
    public ResponseEntity<String> updateCustomer(@PathVariable String customerEmail, @RequestBody CustomerUpdatePlaceOrderDTO customerUpdatePlaceOrderDTO){
        try {
            customerService.updateCustomer(customerEmail, customerUpdatePlaceOrderDTO);
            return ResponseEntity.ok("Customer updated successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers() {
        try {
            return ResponseEntity.ok(customerService.findAllCustomers());
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search-customer")
    public ResponseEntity<List<Customer>> searchCustomer(@RequestParam String keyword) {
        try {
            return ResponseEntity.ok(customerService.searchCustomers(keyword));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/block-customer/{email}")
    public ResponseEntity<String> blockCustomer(@PathVariable String email) {
        try {
            customerService.blockCustomer(email);
            return ResponseEntity.ok("Customer blocked successfully");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/unblock-customer/{email}")
    public ResponseEntity<String> unblockCustomer(@PathVariable String email) {
        try {
            customerService.unblockCustomer(email);
            return ResponseEntity.ok("Customer unblocked successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/check-block-customer/{customerEmail}")
    public boolean checkBlockCustomer(@PathVariable String customerEmail) throws AccessDeniedException {
        if(customerService.chechBlockCustomer(customerEmail)){
            return true;
        }else{
            return false;
        }
    }

    @GetMapping("/admin/customer-count")
    public ResponseEntity<Integer> getCustomerCount() {
        return ResponseEntity.ok(customerService.getCustomerCount());
    }
}
