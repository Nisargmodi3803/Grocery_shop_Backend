package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>
{
    @Query("SELECT transaction FROM Transaction transaction WHERE transaction.transactionInOut=1 AND transaction.isDeleted=1")
    List<Transaction> findAllInTransaction();

    @Query("SELECT transaction FROM Transaction transaction WHERE transaction.transactionInOut=2 AND transaction.isDeleted=2")
    List<Transaction> findAllOutTransaction();

    @Query("SELECT transaction FROM Transaction transaction WHERE (transaction.transactionType.transactionTypeId=6 AND transaction.isDeleted=1) AND transaction.transactionType.isDeleted=1")
    List<Transaction> findAllPurchaseTransaction();

    @Query("SELECT transaction FROM Transaction transaction WHERE (transaction.transactionType.transactionTypeId=7 AND transaction.isDeleted=1) AND transaction.transactionType.isDeleted=1")
    List<Transaction> findAllOtherTransaction();

    @Query("SELECT transaction FROM Transaction transaction WHERE (transaction.transactionType.transactionTypeId=8 AND transaction.isDeleted=1) AND transaction.transactionType.isDeleted=1")
    List<Transaction> findAllInvoiceTransaction();
}
