package com.dipo.bank.model;

import com.dipo.bank.dto.UserDTO;
import com.dipo.bank.enums.Role;

public class User {
    private Long id;
    private String username;
    private String name;
    private Role role;
    private String password;
    private boolean deactivated;

    public User(Long id, String username, String name, String password, Role role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.role = role;
        this.password = password;
        this.deactivated = false;
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

    public UserDTO userDTO() {
        return new UserDTO(id, username, name, role, deactivated);
    }
}
