package com.dipo.bank.service;

import com.dipo.bank.exception.AuthException;
import com.dipo.bank.dto.LoginDetails;
import com.dipo.bank.dto.UserDTO;
import com.dipo.bank.exception.DataException;

import java.util.List;

public interface UserService {
    UserDTO loginUser(LoginDetails loginDetails) throws AuthException;
    boolean hasAdminRole(UserDTO user);
    UserDTO createUser(UserDTO user);
    UserDTO findUserByUsername(String username) throws DataException;
    void toggleUserStatus(String username) throws DataException;
    List<UserDTO> findAllUsers();
}
