package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.CustomerPointDTO;
import com.example.grocery_shop_backend.Entities.CustomerPoint;
import com.example.grocery_shop_backend.Service.CustomerPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @GetMapping("/admin/points/{customerId}")
    public ResponseEntity<List<CustomerPoint>> findPointsByCustomerId(@PathVariable int customerId)
    {
        try {
            return new ResponseEntity<>(customerPointService.findPointsByCustomerId(customerId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // POST API {Insert into Customer Point}
    @PostMapping("/customer-point/{customerEmail}")
    public ResponseEntity<String> insertIntoCustomerPoint(@PathVariable String customerEmail, @RequestBody CustomerPointDTO customerPointDTO)
    {
        try{
            customerPointService.addCustomerPoint( customerEmail, customerPointDTO );
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/points/{customerEmail}")
    public ResponseEntity<List<CustomerPoint>> findPointsByEmail(@PathVariable String customerEmail){
        try {
            return ResponseEntity.ok(customerPointService.findPointsByEmail(customerEmail));
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
