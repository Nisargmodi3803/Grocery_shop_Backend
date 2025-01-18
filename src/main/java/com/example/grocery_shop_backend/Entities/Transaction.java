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
}
