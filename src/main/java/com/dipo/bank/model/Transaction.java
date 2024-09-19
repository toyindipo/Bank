package com.dipo.bank.model;

import com.dipo.bank.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private BigDecimal amount;
    private LocalDateTime date;
    private Long accountId;
    private BigDecimal balance;
    private TransactionType transactionType;

    public Transaction() {
    }

    protected Transaction(Long accountId, BigDecimal amount, TransactionType transactionType, BigDecimal balance) {
        this.accountId = accountId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.balance = balance;
        this.date = LocalDateTime.now();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}


