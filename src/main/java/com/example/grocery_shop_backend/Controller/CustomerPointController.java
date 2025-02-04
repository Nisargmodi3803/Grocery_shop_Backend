package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.CustomerPointDTO;
import com.example.grocery_shop_backend.Entities.CustomerPoint;
import com.example.grocery_shop_backend.Service.CustomerPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CustomerPointController
{
    @Autowired
    private CustomerPointService customerPointService;

    // GET API {Find All In Customer points}
    @GetMapping("/in-points")
    public List<CustomerPoint> findAllInCustomerPoints()
    {
        return customerPointService.findAllCustomerPoints();
    }

    // GET API {Find All Out Customer Points}
    @GetMapping("/out-points")
    public List<CustomerPoint> findAllOutCustomerPoints()
    {
        return customerPointService.findAllOutCustomerPoints();
    }

    // GET API {Find All Customer Points by Customer ID}
    @GetMapping("/points/{customerId}")
    public List<CustomerPoint> findPointsByCustomerId(@PathVariable int customerId)
    {
        return customerPointService.findPointsByCustomerId(customerId);
    }

    // POST API {Insert into Customer Point}
    @PostMapping("/customer-point/{customerId}")
    public ResponseEntity<String> insertIntoCustomerPoint(@PathVariable int customerId, @RequestBody CustomerPointDTO customerPointDTO)
    {
        customerPointService.addCustomerPoint( customerId, customerPointDTO );
        return ResponseEntity.ok("Success");
    }
}
