package com.dipo.bank.service.impl;

import com.dipo.bank.exception.AuthException;
import com.dipo.bank.service.UserService;
import com.dipo.bank.datastore.Database;
import com.dipo.bank.dto.LoginDetails;
import com.dipo.bank.dto.UserDTO;
import com.dipo.bank.enums.Role;
import com.dipo.bank.exception.DataException;
import com.dipo.bank.model.Account;
import com.dipo.bank.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private Database database;

    public UserServiceImpl(final Database database) {
        this.database = database;
    }

    @Override
    public UserDTO loginUser(LoginDetails loginDetails) throws AuthException {
        Optional<User> userOptional = database.findUserByUsername(loginDetails.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.isDeactivated()) {
                throw new AuthException("Account deactivated");
            } else if (user.getPassword().equals(loginDetails.getPassword())) {
                return user.userDTO();
            }
        }
        throw new AuthException("Invalid login details");
    }

    @Override
    public boolean hasAdminRole(UserDTO user) {
        return user.getRole() == Role.ADMIN;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userDTO.toUser(database.generateId());
        user.setRole(Role.NON_ADMIN);
        database.saveUser(user);
        return user.userDTO();
    }

    @Override
    public UserDTO findUserByUsername(String username) throws DataException {
        User user = findUser(username);

        return user.userDTO();
    }

    @Override
    public void toggleUserStatus(String username) throws DataException {
        User user = findUser(username);
        user.setDeactivated(!user.isDeactivated());
    }

    private User findUser(String username) throws DataException {
        Optional<User> user = database.findUserByUsername(username);
        if (user.isEmpty()) {
            throw new DataException("User not found");
        }
        return user.get();
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return database.findAllUsers().stream().map(u -> {
            UserDTO dto = u.userDTO();
            dto.setAccount(database.findUserAccount(u.getId()).orElse(new Account()));
            return dto;
        }).collect(Collectors.toList());
    }
}
