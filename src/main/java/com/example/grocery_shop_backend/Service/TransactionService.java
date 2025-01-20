package com.example.grocery_shop_backend.Service;

import com.example.grocery_shop_backend.Dto.TransactionDTO;
import com.example.grocery_shop_backend.Entities.Transaction;
import com.example.grocery_shop_backend.Entities.TransactionType;
import com.example.grocery_shop_backend.Exception.objectNotFoundException;
import com.example.grocery_shop_backend.Repository.TransactionRepository;
import com.example.grocery_shop_backend.Repository.TransactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TransactionService
{
   @Autowired
   private TransactionRepository transactionRepository;

   @Autowired
   private TransactionTypeRepository transactionTypeRepository;

   // Find All In type Transaction Service
    public List<Transaction> findAllInTransaction()
    {
        List<Transaction> transactions = transactionRepository.findAllInTransaction();

        if (transactions.isEmpty())
            throw new objectNotFoundException("No transactions found");
        return transactions;
    }

    // Find All Out type Transaction Service
    public List<Transaction> findAllOutTransaction()
    {
        List<Transaction> transactions = transactionRepository.findAllOutTransaction();

        if (transactions.isEmpty())
            throw new objectNotFoundException("No transactions found");
        return transactions;
    }

    // Find All Purchase type Transaction Service
    public List<Transaction> findAllPurchaseTransaction()
    {
        List<Transaction> transactions = transactionRepository.findAllPurchaseTransaction();
        if(transactions.isEmpty())
            throw new objectNotFoundException("No transactions found");
        return transactions;
    }

//    // Find All Others type Transaction Service
//    public List<Transaction> findAllOtherTransaction()
//    {
//        List<Transaction> transactions = transactionRepository.findAllOtherTransaction();
//        if (transactions.isEmpty())
//            throw new objectNotFoundException("No transactions found");
//        return transactions;
//    }
//
//    // Find All Invoice type Transaction Service
//    public List<Transaction> findAllInvoiceTransaction()
//    {
//        List<Transaction> transactions = transactionRepository.findAllInvoiceTransaction();
//        if (transactions.isEmpty())
//            throw new objectNotFoundException("No transactions found");
//        return transactions;
//    }

    // Insert Record in Transaction Service
    public void addRecord(TransactionDTO transactionDTO)
    {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime localDateTime = LocalDateTime.now();
        String transactionDate = localDateTime.format(dateFormat);

        DateTimeFormatter cDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime1 = LocalDateTime.now();
        String cDate = localDateTime1.format(cDateFormat);

        TransactionType purchaseType = transactionTypeRepository.findPurchaseType();

        Transaction transaction = new Transaction();
        transaction.setTransactionAmount(transactionDTO.getAmount());
        transaction.setTransactionRemark("Payment of Rs "+transactionDTO.getAmount()+"/- for purchase of "+transactionDTO.getItem()+" paying via "+transactionDTO.getType());
        transaction.setTransactionDate(transactionDate);
        transaction.setcDate(cDate);
        transaction.setTransactionType(purchaseType);
        transaction.setTransactionInOut(transactionTypeRepository.findTransactionTypeInOut());
        transaction.setIsDeleted(1);

        transactionRepository.save(transaction);
    }
}
