package com.dipo.bank.datastore;

import com.dipo.bank.model.Account;
import com.dipo.bank.model.User;

import java.util.List;
import java.util.Optional;

public interface Database {
    Long generateId();

    Optional<User> findUserByUsername(String username);

    Optional<Account> findUserAccount(Long userId);

    List<User> findAllUsers();

    void saveUser(User user);
    void saveAccount(Account account);
    void seedDatabase();
}
