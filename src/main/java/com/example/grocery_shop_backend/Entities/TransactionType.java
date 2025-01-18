package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_transaction_type")
public class TransactionType
{
    @Id
    @Column(name = "transaction_type_id")
    private int transactionTypeId;

    @Column(name = "transaction_type_name")
    private String transactionTypeName;

    @Column(name = "transaction_type_in_out")
    private int transactionTypeInOut; // 1 => IN & 2 => OUT

    @Column(name = "is_deleted")
    private int isDeleted; // 1 => Not deleted & 2 => deleted

    @Column(name = "c_date")
    private String cDate;

    public TransactionType(){}

    public TransactionType(int transactionTypeId, String transactionTypeName, int transactionTypeInOut, int isDeleted, String cDate) {
        this.transactionTypeId = transactionTypeId;
        this.transactionTypeName = transactionTypeName;
        this.transactionTypeInOut = transactionTypeInOut;
        this.isDeleted = isDeleted;
        this.cDate = cDate;
    }

    public int getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(int transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public String getTransactionTypeName() {
        return transactionTypeName;
    }

    public void setTransactionTypeName(String transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
    }

    public int getTransactionTypeInOut() {
        return transactionTypeInOut;
    }

    public void setTransactionTypeInOut(int transactionTypeInOut) {
        this.transactionTypeInOut = transactionTypeInOut;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }
}
