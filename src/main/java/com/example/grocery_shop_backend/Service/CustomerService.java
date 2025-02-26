package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.*;
import com.example.grocery_shop_backend.Entities.City;
import com.example.grocery_shop_backend.Entities.Customer;
import com.example.grocery_shop_backend.Exception.MobileNumberAlreadyExistsException;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.CityRepository;
import com.example.grocery_shop_backend.Repository.CustomerRepository;
import com.google.api.client.util.DateTime;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CustomerPointService customerPointService;

    @Value("${upload.dir}")
    private String uploadDir; // Directory where images will be stored

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom random = new SecureRandom();

    public String generateUniqueReferralCode(){
        String code;

        do{
            code = generateReferralCode();
        }while (customerRepository.getReferralCode(code) == code);

        return code;
    }

    public String generateReferralCode(){
        StringBuilder referralCode = new StringBuilder(8);
        for(int i = 0; i < 8; i++){
            referralCode.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return referralCode.toString();
    }

    public Customer getCustomerById(int id) {
        Customer customer = customerRepository.findCustomerById(id);
        if (customer == null)
            throw new objectNotFoundException("Customer with id " + id + " not found");
        return customer;
    }

    @Transactional
    public boolean saveCustomer(CustomerRegistrationDTO customerRegistrationDTO) {
        Customer existingCustomer = customerRepository.findCustomerByEmail(customerRegistrationDTO.getCustomerEmail());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String cDate = now.format(formatter);

        if (existingCustomer != null) {
            throw new MobileNumberAlreadyExistsException("Customer with Email " + customerRegistrationDTO.getCustomerEmail() + " already exists");
        }

        String encodedPassword = passwordEncoder.encode(customerRegistrationDTO.getCustomerPassword());
        String referralCode = generateUniqueReferralCode();
        Customer customer = new Customer();
        customer.setCustomerName(customerRegistrationDTO.getCustomerName());
        customer.setCustomerMobile(customerRegistrationDTO.getCustomerMobile());
        customer.setCustomerEmail(customerRegistrationDTO.getCustomerEmail());
        customer.setCustomerPassword(encodedPassword);
        customer.setCustomerOtp(customerRegistrationDTO.getOtp());
        customer.setCustomerImage("default.png");
        customer.setCustomerReferralCode(referralCode);
        customer.setIsDeleted(1);
        customer.setcDate(cDate);
        if(customerRegistrationDTO.getReferralCode() != ""){
            Customer refferedCustomer = customerRepository.findCustomerByReferralCode(customerRegistrationDTO.getReferralCode());
            if(refferedCustomer == null){
                throw new objectNotFoundException("Customer with referral code " + customerRegistrationDTO.getReferralCode() + " do not exists");
            }
            customer.setCustomerReferralBy(refferedCustomer);
//            customer.setCustomerPoint(25.00);

//            refferedCustomer.setCustomerPoint(refferedCustomer.getCustomerPoint() + 15.00);
            customerRepository.save(refferedCustomer);
        }else{
//            customer.setCustomerPoint(25.00);
        }
        customerRepository.save(customer);

        // Return the customer data directly (no token needed)
        return true;
    }

    public boolean login(CustomerLoginDTO loginDTO) {
//        System.out.println("Email : "+loginDTO.getEmail());
//        System.out.println("Password : "+loginDTO.getPassword());
        String hashPassword = customerRepository.getHashedPassword(loginDTO.getEmail());
//        System.out.println(hashPassword);
        if (hashPassword == null) {
            throw new objectNotFoundException("Email not found");
        }

        if (passwordEncoder.matches(loginDTO.getPassword(), hashPassword)) {
            return true; // Return the customer data directly (no token needed)
        } else {
            return false;
        }
    }

    // Update Basic Customer Details Service
    @Transactional
    public Customer updateCustomerBasicDetails(String customerEmail, CustomerBasicDetailsDTO updateDTO) {
        Customer existingCustomer = customerRepository.findCustomerByEmail(customerEmail);

        if (existingCustomer != null) {
            if (updateDTO.getCustomerName() != null)
                existingCustomer.setCustomerName(updateDTO.getCustomerName());

            if (updateDTO.getCustomerAddress() != null)
                existingCustomer.setCustomerAddress(updateDTO.getCustomerAddress());

            if (updateDTO.getCustomerEmail() != null)
                existingCustomer.setCustomerEmail(updateDTO.getCustomerEmail());

            if (updateDTO.getCustomerGender() != 0)
                existingCustomer.setCustomerGender(updateDTO.getCustomerGender());

            if (updateDTO.getCustomerDob() != null)
                existingCustomer.setCustomerDob(updateDTO.getCustomerDob());
        } else {
            throw new objectNotFoundException("Customer with mobile number " + customerEmail + " not found");
        }

        return customerRepository.save(existingCustomer);
    }

    // Change Password Service
    @Transactional
    public boolean changePassword(String customerEmail, String oldPassword, String newPassword) {
        Customer existingCustomer = customerRepository.findCustomerByEmail(customerEmail);
        if (existingCustomer != null) {
            if (passwordEncoder.matches(oldPassword, existingCustomer.getCustomerPassword())) {
                existingCustomer.setCustomerPassword(passwordEncoder.encode(newPassword));
                return true;
            } else {
                return false;
            }
        } else {
            throw new objectNotFoundException("Customer with Email " + customerEmail + " not found");
        }
    }

    // Change Profile Image Service
    @Transactional
    public String changeProfileImage(String customerEmail, MultipartFile file) throws IOException {
        Customer existingCustomer = customerRepository.findCustomerByEmail(customerEmail);
        if (existingCustomer != null) {
            // Create the upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate a unique filename or use the customer's email as the filename
            String fileName = customerEmail + "-" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // Save the image to the upload directory
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Update the customer profile image name in the database
            existingCustomer.setCustomerImage(fileName);
            customerRepository.save(existingCustomer);  // Save changes to the database

            return "Profile updated successfully";
        } else {
            throw new objectNotFoundException("Customer with Email " + customerEmail + " not found");
        }
    }

    // Get Basic Details using Mobile Number Service
    public CustomerBasicDetailsDTO getBasicDetails(String customerEmail) {
        Customer customer = customerRepository.findCustomerByEmail(customerEmail);
        if (customer == null) {
            throw new objectNotFoundException("Customer with mobile number " + customerEmail + " not found");
        }

        CustomerBasicDetailsDTO customerBasicDetailsDTO = new CustomerBasicDetailsDTO();
        customerBasicDetailsDTO.setCustomerName(customer.getCustomerName());
        customerBasicDetailsDTO.setCustomerAddress(customer.getCustomerAddress());
        customerBasicDetailsDTO.setCustomerEmail(customer.getCustomerEmail());
        customerBasicDetailsDTO.setCustomerGender(customer.getCustomerGender());
        customerBasicDetailsDTO.setCustomerDob(customer.getCustomerDob());
        customerBasicDetailsDTO.setCustomerImage(customer.getCustomerImage());
        customerBasicDetailsDTO.setCustomerMobile(customer.getCustomerMobile());
        customerBasicDetailsDTO.setCustomerPoints(customer.getCustomerPoint());
        return customerBasicDetailsDTO;
    }

    // Get Customer by MobileNumber Service
    public Customer getCustomerByMobile(String customerMobile) {
        Customer customer = customerRepository.findCustomerByMobile(customerMobile);
        if (customer == null)
            throw new objectNotFoundException("Customer with mobile number " + customerMobile + " not found");
        return customer;
    }

    // Find Customer by Email Service
    public Customer getCustomerByEmail(String customerEmail) {
        Customer customer = customerRepository.findCustomerByEmail(customerEmail);
        if (customer == null)
            throw new objectNotFoundException("Customer with email " + customerEmail + " not found");
        return customer;
    }

    // Delete Customer Service
    public void deleteCustomer(int customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);

        if (customer != null) {
            customer.setIsDeleted(2);
            customerRepository.save(customer);
        } else {
            throw new objectNotFoundException("Customer with mobile number " + customerId + " not found");
        }
    }

    // Retrieve Customer Service
    public boolean retrieveCustomer(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new objectNotFoundException("Customer with mobile number " + customerId + " not found"));

        if (customer.getIsDeleted() == 2) {
            customer.setIsDeleted(1);
            customerRepository.save(customer);
            return true;
        } else {
            return false;
        }
    }

    // Forgot Password using OTP
    @Transactional
    public boolean forgotPassword(CustomerLoginDTO customerLoginDTO)
    {
        Customer customer = customerRepository.findCustomerByEmail(customerLoginDTO.getEmail());
        if (customer == null) {
//            throw new objectNotFoundException("Customer with mobile number " + customerLoginDTO.getEmail() + " not found");
            return false;
        }
        else
        {
            customer.setCustomerPassword(passwordEncoder.encode(customerLoginDTO.getPassword()));
            customerRepository.save(customer);
            return true;
        }
    }

    // Delete Profile Image Service
    @Transactional
    public void deleteProfileImage(String customerEmail) {
        Customer customer = customerRepository.findCustomerByEmail(customerEmail);
        if (customer == null) {
            throw new objectNotFoundException("Customer with Email " + customerEmail + " not found");
        }

        String imageName = customer.getCustomerImage();

        // Ensure the image is not the default one before deleting
        if (imageName != null && !imageName.equals("default.png")) {
            Path imagePath = Paths.get(uploadDir, imageName);

            try {
                Files.deleteIfExists(imagePath); // Delete the file if it exists
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete profile image: " + imageName, e);
            }
        }

        // Set profile image to default and update in DB
        customer.setCustomerImage("default.png");
        customerRepository.save(customer);
    }

    // Check Referral Code Exists or not Service
    public boolean checkReferralCode(String referralCode) {
        Customer customer = customerRepository.findCustomerByReferralCode(referralCode);
        if(customer == null) {
            return false;
        }else{
            return true;
        }
    }

    // Get Customer on the basis of Referral Code Service
    public Customer getCustomerByReferralCode(String referralCode) {
        Customer customer = customerRepository.findCustomerByReferralCode(referralCode);
        if(customer == null) {
            throw new objectNotFoundException("Customer with Referral Code : " + referralCode + " not found");
        }
        return customer;
    }

    @Transactional
    public void updateCustomer(String customerEmail,CustomerUpdatePlaceOrderDTO customerUpdatePlaceOrderDTO) {
        Customer customer = customerRepository.findCustomerByEmail(customerEmail);

        if (customer == null) {
            throw new objectNotFoundException("Customer with email " + customerEmail + " not found");
        }

        if(customerUpdatePlaceOrderDTO != null) {
            if(customerUpdatePlaceOrderDTO.getCityId()!=0){
                City city = cityRepository.findCityById(customerUpdatePlaceOrderDTO.getCityId());
                if(city == null) {
                    throw new objectNotFoundException("City with id " + customerUpdatePlaceOrderDTO.getCityId() + " not found");
                }
                customer.setCustomerCity(city);
            }
        }

        if(customerUpdatePlaceOrderDTO.getPincode()!=null){
            customer.setCustomerPincode(customerUpdatePlaceOrderDTO.getPincode());
        }

        if(customerUpdatePlaceOrderDTO.getPoints()!=0){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime now = LocalDateTime.now();
            String date = now.format(formatter);

            CustomerPointDTO customerPointDTO = new CustomerPointDTO();
            customerPointDTO.setPoints(customerUpdatePlaceOrderDTO.getPoints());
            customerPointDTO.setPointType(2);
            customerPointDTO.setDetails(customerUpdatePlaceOrderDTO.getPoints()+" are used in shopping on "+date);

            customerPointService.addCustomerPoint(customerEmail,customerPointDTO);
        }
    }
}
