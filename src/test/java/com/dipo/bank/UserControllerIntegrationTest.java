package com.dipo.bank;

import com.dipo.bank.dto.UserDTO;
import com.dipo.bank.enums.Role;
import com.dipo.bank.service.AccountService;
import com.dipo.bank.service.UserService;
import com.dipo.bank.controller.UserController;
import com.dipo.bank.datastore.Database;
import com.dipo.bank.exception.DataException;
import com.dipo.bank.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserController.class)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AccountService accountService;

    @MockBean
    private Database database;

    @BeforeEach
    void mockServices() throws DataException {
        UserDTO admin = new UserDTO(1L, "admin", "Admin", Role.ADMIN, false);
        Account account = new Account(2L, 1L);
        Mockito.when(userService.findUserByUsername("admin")).
                thenReturn(admin);
        Mockito.when(userService.hasAdminRole(admin)).
                thenReturn(true);

        Mockito.when(accountService.findUserAccount(1L)).
                thenReturn(account);
    }

    @Test
    void shouldLoadLoginPage() throws Exception {
        this.mockMvc
                .perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/login"))
                .andExpect(model().attributeExists("login"));
    }

    @Test
    void redirectToLoginPageWhenNotLoggedIn() throws Exception {
        this.mockMvc
                .perform(get("/users/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }

    @Test
    public void adminShouldAccessAdminPage() throws Exception {
        mockMvc.perform(get("/admin/home").sessionAttr("login_id", "admin")).
                andExpect(status().isOk())
                .andExpect(view().name("admin/home"));
    }
}