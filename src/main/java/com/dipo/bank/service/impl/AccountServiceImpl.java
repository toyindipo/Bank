package com.dipo.bank.service.impl;

import com.dipo.bank.dto.AccountPosting;
import com.dipo.bank.dto.UserDTO;
import com.dipo.bank.service.AccountService;
import com.dipo.bank.datastore.Database;
import com.dipo.bank.exception.DataException;
import com.dipo.bank.model.Account;
import com.dipo.bank.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private Database database;

    public AccountServiceImpl(final Database database) {
        this.database = database;
    }

    @Override
    public Account findUserAccount(Long userId) {
        Optional<Account> account = database.findUserAccount(userId);
        return account.orElse(new Account());
    }

    @Override
    public Account createAccount(UserDTO user) {
        Account account = new Account(database.generateId(), user.getId());
        database.saveAccount(account);
        return account;
    }

    @Override
    public Transaction depositMoney(AccountPosting accountPosting) throws DataException {
        Account account = findUserAccount(accountPosting.getUserId());
        validateAccountDetails(account);
        Transaction transaction = account.deposit(accountPosting.getAmount());
        return transaction;
    }

    @Override
    public Transaction withdrawMoney(AccountPosting accountPosting) throws DataException {
        Account account = findUserAccount(accountPosting.getUserId());
        validateAccountDetails(account);
        Transaction transaction = account.withdrawal(accountPosting.getAmount());
        return transaction;
    }

    private void validateAccountDetails(Account account) throws DataException {
        if (Objects.isNull(account.getAccountNumber())) {
            throw new DataException("Invalid account details");
        }
    }
}
