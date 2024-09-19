package com.dipo.bank.datastore.impl;

import com.dipo.bank.datastore.Database;
import com.dipo.bank.enums.Role;
import com.dipo.bank.model.Account;
import com.dipo.bank.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class DatabaseImpl implements Database {
    private final Map<String, User> userStore;
    private final Map<Long, Account> accountStore;
    private final AtomicLong idGenerator;

    public DatabaseImpl() {
        userStore = new HashMap<>();
        accountStore = new HashMap<>();
        idGenerator = new AtomicLong(1);
    }

    @Override
    public Long generateId() {
        return idGenerator.getAndIncrement();
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return Optional.ofNullable(userStore.get(username));
    }

    @Override
    public Optional<Account> findUserAccount(Long userId) {
        return Optional.ofNullable(accountStore.get(userId));
    }

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(userStore.values());
    }

    @Override
    public void saveUser(User user) {
        userStore.put(user.getUsername(), user);
    }

    @Override
    public void saveAccount(Account account) {
        accountStore.put(account.getUserId(), account);
    }

    public void seedDatabase() {
        User admin = new User(generateId(), "admin", "Admin", "admin", Role.ADMIN);
        saveUser(admin);
        Account account = new Account(generateId(), admin.getId());
        saveAccount(account);
    }
}
