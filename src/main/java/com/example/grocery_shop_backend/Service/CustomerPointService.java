package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.CustomerPointDTO;
import com.example.grocery_shop_backend.Entities.Customer;
import com.example.grocery_shop_backend.Entities.CustomerPoint;
import com.example.grocery_shop_backend.Exception.InsufficientPointsException;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.CustomerPointRepository;
import com.example.grocery_shop_backend.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CustomerPointService
{
    @Autowired
    private CustomerPointRepository customerPointRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // Find All In Customer Points Service
    public List<CustomerPoint> findAllCustomerPoints()
    {
        List<CustomerPoint> customerPoints = customerPointRepository.findAllInCustomerPoints();
        if(customerPoints.isEmpty())
            throw new objectNotFoundException("Customer Point Not Found");
        return customerPoints;
    }

    // Find All Out Customer Points Service
    public List<CustomerPoint> findAllOutCustomerPoints()
    {
        List<CustomerPoint> customerPoints = customerPointRepository.findALlOutCustomerPoints();
        if(customerPoints.isEmpty())
            throw new objectNotFoundException("Customer Point Not Found");
        return customerPoints;
    }

    // Find All Customer Points by Customer ID
    public List<CustomerPoint> findPointsByCustomerId(int customerId)
    {
        List<CustomerPoint> customerPoints = customerPointRepository.findPointsByCustomerId(customerId);
        if(customerPoints.isEmpty())
            throw new objectNotFoundException("Customer Point Not Found");
        return customerPoints;
    }

    // Insert Customer Points Service
    @Transactional
    public void addCustomerPoint(String customerEmail,CustomerPointDTO customerPointDTO)
    {
        Customer customer = customerRepository.findCustomerByEmail(customerEmail);

        if(customer == null)
            throw new objectNotFoundException("Customer Not Found with Email : " + customerEmail);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String cDate = now.format(formatter);

        CustomerPoint customerPoint = new CustomerPoint();
        customerPoint.setCustomer(customer);
        customerPoint.setCustomerPoint(customerPointDTO.getPoints());
        customerPoint.setCustomerPointType(customerPointDTO.getPointType());  // 1 = IN & 2 = OUT

        if(customerPointDTO.getPointType()==1) // IN type
        {
            // Add Points
            double currentPoints = customer.getCustomerPoint();
            customerPoint.setCustomerAvailablePoint(currentPoints + customerPointDTO.getPoints());
            customer.setCustomerPoint(currentPoints + customerPointDTO.getPoints());
        }
        else if(customerPointDTO.getPointType()==2) // OUT type
        {
            // Deduct Points
            double currentPoints = customer.getCustomerPoint();
            if(currentPoints < customerPointDTO.getPoints())
                throw new InsufficientPointsException("Insufficient Points with customer Email : " + customerEmail);
            customerPoint.setCustomerAvailablePoint(currentPoints - customerPointDTO.getPoints());
            customer.setCustomerPoint(currentPoints - customerPointDTO.getPoints());
        }
        customerPoint.setCustomerPointDetail(customerPointDTO.getDetails());
        customerPoint.setIsDeleted(1);
        customerPoint.setC_date(cDate);
        customerPointRepository.save(customerPoint);
        customerRepository.save(customer);
    }
}
