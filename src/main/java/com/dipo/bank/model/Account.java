package com.dipo.bank.model;

import com.dipo.bank.enums.TransactionType;
import com.dipo.bank.exception.DataException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private Long userId;
    @JsonIgnore
    private List<Transaction> transactions;

    public Account() {
    }

    public Account(Long id, Long userId) {
       this(id, userId, BigDecimal.ZERO);
    }

    public Account(Long id, Long userId, BigDecimal initialBalance) {
        this.id = id;
        this.userId = userId;
        this.accountNumber = generateAccountNumber(id);
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    public Transaction deposit(BigDecimal amount) throws DataException {
        validateAmount(amount);
        balance = balance.add(amount);
        Transaction deposit = addTransaction(id, amount, TransactionType.DEPOSIT, balance);
        transactions.add(deposit);
        return deposit;
    }

    public Transaction withdrawal(BigDecimal amount) throws DataException {
        validateAmount(amount);
        validateBalance(amount);
        balance = balance.subtract(amount);
        Transaction withdrawal = addTransaction(id, amount, TransactionType.WITHDRAWAL, balance);
        transactions.add(withdrawal);
        return withdrawal;
    }

    private void validateAmount(BigDecimal amount) throws DataException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DataException("Invalid deposit amount");
        }
    }

    private void validateBalance(BigDecimal amount) throws DataException {
        if (balance.subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new DataException("Insufficient balance");
        }
    }

    private Transaction addTransaction(Long id, BigDecimal amount, TransactionType transactionType, BigDecimal balance) {
        Transaction deposit = new Transaction(id, amount, transactionType, balance);
        transactions.add(deposit);
        return deposit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    private static String generateAccountNumber(Long id) {
      return String.format("%08d", id);
    }
}