package com.example.grocery_shop_backend.Controller;

import com.example.grocery_shop_backend.Dto.TransactionDTO;
import com.example.grocery_shop_backend.Entities.Transaction;
import com.example.grocery_shop_backend.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class TransactionController
{
    @Autowired
    private TransactionService transactionService;

    // GET API {Find All 'In' type Transaction}
    @GetMapping("/in-transaction")
    public List<Transaction> findAllInTransaction()
    {
        return transactionService.findAllInTransaction();
    }

    // GET API {Find All 'Out' type Transaction}
    @GetMapping("/out-transaction")
    public List<Transaction> findAllOutTransaction()
    {
        return transactionService.findAllOutTransaction();
    }

    // GET API {Find All 'Purchase' type Transaction}
    @GetMapping("/purchase-transaction")
    public List<Transaction> findAllPurchaseTransaction()
    {
        return transactionService.findAllPurchaseTransaction();
    }

//    // GET API {Find All 'Other' type Transaction}
//    public List<Transaction> findAllOtherTransaction()
//    {
//        return transactionService.findAllOtherTransaction();
//    }
//
//    // Get API {Find All 'Invoice' type Transaction}
//    public List<Transaction> findAllInvoiceTransaction()
//    {
//        return transactionService.findAllInvoiceTransaction();
//    }

    // POST API {Add Record in Transaction}
    @PostMapping("/add-transaction")
    public ResponseEntity<String> addRecord(@RequestBody TransactionDTO transactionDTO)
    {
        try
        {
            transactionService.addRecord(transactionDTO);
            return new ResponseEntity<>("Record added", HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
