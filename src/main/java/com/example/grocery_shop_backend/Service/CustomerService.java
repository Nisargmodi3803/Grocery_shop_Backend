package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.CustomerBasicDetailsDTO;
import com.example.grocery_shop_backend.Dto.CustomerLoginDTO;
import com.example.grocery_shop_backend.Dto.CustomerRegistrationDTO;
import com.example.grocery_shop_backend.Entities.Customer;
import com.example.grocery_shop_backend.Exception.MobileNumberAlreadyExistsException;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.CustomerRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
    EMAIL TYPE [OTP VERIFICATION]

    1 => Registration time Email OTP.
    2 => Login with OTP.
    3 => Forgot Password.
 */


@Service
public class CustomerService
{
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmailOTPService emailOTPService;

    // BCrypt Object
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Get Customer By ID
    public Customer getCustomerById(int id)
    {
        Customer customer = customerRepository.findCustomerById(id);
        if(customer == null)
            throw new objectNotFoundException("Customer with id "+id+" not found");
        return customer;
    }

    //

    // Save Customer {Registration}
    @Transactional
    public void saveCustomer(CustomerRegistrationDTO customerRegistrationDTO) {
        Customer existingCustomer = customerRepository.findCustomerByEmail(customerRegistrationDTO.getCustomerEmail());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String cDate = now.format(formatter);

        if(existingCustomer != null)
        {
            throw new MobileNumberAlreadyExistsException("Customer with Email "+customerRegistrationDTO.getCustomerEmail()+" already exists");
        }
        else
        {
            String encodedPassword = passwordEncoder.encode(customerRegistrationDTO.getCustomerPassword());
            Customer customer = new Customer();
            customer.setCustomerName(customerRegistrationDTO.getCustomerName());
            customer.setCustomerMobile(customerRegistrationDTO.getCustomerMobile());
            customer.setCustomerEmail(customerRegistrationDTO.getCustomerEmail());
            customer.setCustomerPassword(encodedPassword);
//            customer.setCustomerOtp(customerRegistrationDTO.getCustomerOtp());
            customer.setCustomerImage("http://localhost:9001/default.png");
            customer.setIsDeleted(1);
            customer.setcDate(cDate);
            customerRepository.save(customer);
        }
    }

    // Login Service
    public String login(CustomerLoginDTO loginDTO)
    {
        String hashPassword = customerRepository.getHashedPassword(loginDTO.getCustomerEmail());

        if (hashPassword == null)
        {
            return "Mobile number not found";
        }
        if(passwordEncoder.matches(loginDTO.getCustomerPassword(), hashPassword))
        {
            return "Login successful";
        }
        else
        {
            return "Password Does Not Match";
        }
    }

    // Update Basic Customer Details Service
    @Transactional
    public Customer updateCustomerBasicDetails(String customerMobile, CustomerBasicDetailsDTO updateDTO)
    {
        Customer existingCustomer = customerRepository.findCustomerByMobile(customerMobile);

        if(existingCustomer!=null)
        {
            if(updateDTO.getCustomerName() != null)
                existingCustomer.setCustomerName(updateDTO.getCustomerName());

            if (updateDTO.getCustomerAddress() != null)
                existingCustomer.setCustomerAddress(updateDTO.getCustomerAddress());

            if(updateDTO.getCustomerEmail() != null)
                existingCustomer.setCustomerEmail(updateDTO.getCustomerEmail());

            if (updateDTO.getCustomerGender()!=0)
                existingCustomer.setCustomerGender(updateDTO.getCustomerGender());

            if(updateDTO.getCustomerDob() != null)
                existingCustomer.setCustomerDob(updateDTO.getCustomerDob());
        }
        else
        {
            throw new objectNotFoundException("Customer with mobile number "+customerMobile+" not found");
        }

        return customerRepository.save(existingCustomer);
    }

    // Change Password Service
    @Transactional
    public String changePassword(String customerEmail,String oldPassword,String newPassword)
    {
        Customer existingCustomer = customerRepository.findCustomerByEmail(customerEmail);
        if(existingCustomer!=null)
        {
            if(passwordEncoder.matches(oldPassword, existingCustomer.getCustomerPassword()))
            {
                existingCustomer.setCustomerPassword(passwordEncoder.encode(newPassword));
                return "Password updated successfully";
            }
            else
            {
                return "Old Password Does Not Match";
            }
        }
        else
        {
            throw new objectNotFoundException("Customer with Email "+customerEmail+" not found");
        }
    }

    // Change Profile Image Service
    @Transactional
    public String changeProfileImage(String customerMobile,String profileImage)
    {
        Customer existingCustomer = customerRepository.findCustomerByMobile(customerMobile);
        if(existingCustomer!=null)
        {
            existingCustomer.setCustomerImage(profileImage);
            return "Profile updated successfully";
        }
        else
        {
            throw new objectNotFoundException("Customer with mobile number "+customerMobile+" not found");
        }
    }

    // Get Basic Details using Mobile Number Service
    public CustomerBasicDetailsDTO getBasicDetails(String customerMobile)
    {
        Customer customer = customerRepository.findCustomerByMobile(customerMobile);
        CustomerBasicDetailsDTO customerBasicDetailsDTO = new CustomerBasicDetailsDTO();
        customerBasicDetailsDTO.setCustomerName(customer.getCustomerName());
        customerBasicDetailsDTO.setCustomerAddress(customer.getCustomerAddress());
        customerBasicDetailsDTO.setCustomerEmail(customer.getCustomerEmail());
        customerBasicDetailsDTO.setCustomerGender(customer.getCustomerGender());
        customerBasicDetailsDTO.setCustomerDob(customer.getCustomerDob());
        customerBasicDetailsDTO.setCustomerImage(customer.getCustomerImage());
        customerBasicDetailsDTO.setCustomerMobile(customer.getCustomerMobile());
        return customerBasicDetailsDTO;
    }

    // Get Customer by MobileNumber Service
    public Customer getCustomerByMobile(String customerMobile)
    {
        Customer customer = customerRepository.findCustomerByMobile(customerMobile);
        if (customer == null)
            throw new objectNotFoundException("Customer with mobile number "+customerMobile+" not found");
        return customer;
    }

    // Find Customer by Email Service
    public Customer getCustomerByEmail(String customerEmail)
    {
        Customer customer = customerRepository.findCustomerByEmail(customerEmail);
        if(customer == null)
            throw new objectNotFoundException("Customer with email "+customerEmail+" not found");
        return customer;
    }

    // Delete Customer Service
    public void deleteCustomer(int customerId)
    {
        Customer customer = customerRepository.findCustomerById(customerId);

        if(customer!=null)
        {
            customer.setIsDeleted(2);
            customerRepository.save(customer);
        }
        else
            throw new objectNotFoundException("Customer with mobile number "+customerId+" not found");
    }

    // Retrieve Customer Service
    public boolean retrieveCustomer(int customerId)
    {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new objectNotFoundException("Customer with mobile number "+customerId+" not found"));

        if(customer.getIsDeleted()==2)
        {
            customer.setIsDeleted(1);
            customerRepository.save(customer);
            return true;
        }
        else
            return false;
    }
}
