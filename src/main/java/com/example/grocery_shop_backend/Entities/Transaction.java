package com.example.grocery_shop_backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_transaction")
public class Transaction
{
    @Id
    @Column(name = "transaction_id")
    private int transactionId;

    @Column(name = "transaction_in_out")
    private int transactionInOut; // 1 => IN & 2 => OUT

    @Column(name = "transaction_amount")
    private String transactionAmount;

    @Column(name = "transaction_remark")
    private String transactionRemark;

    @Column(name = "transaction_date")
    private String transactionDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "c_date")
    private String cDate;

    @Column(name = "is_deleted")
    private int isDeleted; // 1 => Not deleted & 2 => deleted

    public Transaction(){}

    public Transaction(int transactionId, int transactionInOut, String transactionAmount, String transactionRemark, String transactionDate, TransactionType transactionType, String cDate, int isDeleted) {
        this.transactionId = transactionId;
        this.transactionInOut = transactionInOut;
        this.transactionAmount = transactionAmount;
        this.transactionRemark = transactionRemark;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.cDate = cDate;
        this.isDeleted = isDeleted;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getTransactionInOut() {
        return transactionInOut;
    }

    public void setTransactionInOut(int transactionInOut) {
        this.transactionInOut = transactionInOut;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionRemark() {
        return transactionRemark;
    }

    public void setTransactionRemark(String transactionRemark) {
        this.transactionRemark = transactionRemark;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}
