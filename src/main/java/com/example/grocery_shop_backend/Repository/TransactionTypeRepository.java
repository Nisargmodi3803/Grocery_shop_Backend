package com.example.grocery_shop_backend.Repository;

import com.example.grocery_shop_backend.Entities.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Integer>
{
    @Query("SELECT tType FROM TransactionType tType WHERE tType.transactionTypeId=6 AND tType.isDeleted=1")
    public TransactionType findPurchaseType();

    @Query("SELECT tType.transactionTypeInOut FROM TransactionType tType WHERE tType.transactionTypeId=6 AND tType.isDeleted=1")
    public int findTransactionTypeInOut();
}
