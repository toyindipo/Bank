package com.dipo.bank.service;

import com.dipo.bank.dto.AccountPosting;
import com.dipo.bank.dto.UserDTO;
import com.dipo.bank.exception.DataException;
import com.dipo.bank.model.Account;
import com.dipo.bank.model.Transaction;

public interface AccountService {
    Account findUserAccount(Long userId);
    Account createAccount(UserDTO user);
    Transaction depositMoney(AccountPosting accountPosting) throws DataException;
    Transaction withdrawMoney(AccountPosting accountPosting) throws DataException;
}
