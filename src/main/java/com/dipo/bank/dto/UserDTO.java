package com.dipo.bank.dto;

import com.dipo.bank.enums.Role;
import com.dipo.bank.model.Account;
import com.dipo.bank.model.User;

import java.util.Objects;

public class UserDTO {
    private Long id;
    private String username;
    private String name;
    private Role role;
    private String password;
    private boolean deactivated;
    private Account account;

    public UserDTO() {
    }

    public UserDTO(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public UserDTO(Long id, String username, String name, Role role, boolean deactivated) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.username = username;
        this.deactivated = deactivated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDeactivated() {
        return deactivated;
    }

    public void setDeactivated(boolean deactivated) {
        this.deactivated = deactivated;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public User toUser(Long id) {
        return new User(id, username, name, password, role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) && Objects.equals(username, userDTO.username) && Objects.equals(name, userDTO.name) && role == userDTO.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, name, role);
    }
}
