package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.CustomerBasicDetailsDTO;
import com.example.grocery_shop_backend.Entities.Customer;
import com.example.grocery_shop_backend.Exception.MobileNumberAlreadyExistsException;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService
{
    @Autowired
    private CustomerRepository customerRepository;

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

    // Save Customer {Registration}
    @Transactional
    public void saveCustomer(String customerName,String customerMobile,String customerPassword,String customerOtp)
    {
        Customer existingCustomer = customerRepository.findCustomerByMobile(customerMobile);

        if(existingCustomer != null)
        {
            throw new MobileNumberAlreadyExistsException("Mobile number "+customerMobile+" already exists");
        }
        else
        {
            String encodedPassword = passwordEncoder.encode(customerPassword);
            Customer customer = new Customer();
            customer.setCustomerName(customerName);
            customer.setCustomerMobile(customerMobile);
            customer.setCustomerPassword(encodedPassword);
            customer.setCustomerOtp(customerOtp);
            customer.setCustomerImage("http://localhost:9001/default.png");
            customerRepository.save(customer);
        }
    }

    // Login Service
    public String login(String customerMobile,String customerPassword)
    {
        String hashPassword = customerRepository.getHashedPassword(customerMobile);

        if (hashPassword == null)
        {
            return "Mobile number not found";
        }
        if(passwordEncoder.matches(customerPassword, hashPassword))
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
    public String changePassword(String customerMobile,String oldPassword,String newPassword)
    {
        Customer existingCustomer = customerRepository.findCustomerByMobile(customerMobile);
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
            throw new objectNotFoundException("Customer with mobile number "+customerMobile+" not found");
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
}
