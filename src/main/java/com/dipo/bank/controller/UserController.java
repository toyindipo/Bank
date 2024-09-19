package com.dipo.bank.controller;

import com.dipo.bank.dto.LoginDetails;
import com.dipo.bank.dto.UserDTO;
import com.dipo.bank.exception.AuthException;
import com.dipo.bank.exception.DataException;
import com.dipo.bank.model.Account;
import com.dipo.bank.service.AccountService;
import com.dipo.bank.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController extends IController {
    private final UserService userService;
    private final AccountService accountService;

    public UserController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @GetMapping(value ={"/", "/login"})
    public String loginPage(Model model) {
        model.addAttribute("login", new LoginDetails());
        return "users/login";
    }

    @PostMapping(value = "/users/login")
    public String login(HttpSession session, @ModelAttribute LoginDetails login, Model model, RedirectAttributes redirectAttrs) {
        try {
            UserDTO userDTO = userService.loginUser(login);
            model.addAttribute("user", userDTO);

            session.setAttribute("login_id", userDTO.getUsername());
            return redirectHome(userService,userDTO);
        } catch (AuthException e) {
            return processAuthError(e, redirectAttrs);
        }
    }

    @GetMapping(value = "/users/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("login_id");

        return  "redirect:/login";
    }

    @GetMapping(value = "/users/home")
    public String homePage(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            UserDTO userDTO = retrieveUserFromSession(session, userService);
            if (userService.hasAdminRole(userDTO)) {
                return adminHomePage(session, model, redirectAttributes);
            }
            Account account = accountService.findUserAccount(userDTO.getId());
            userDTO.setAccount(account);
            model.addAttribute("user", userDTO);
        } catch (AuthException e) {
            return processAuthError(e, redirectAttributes);
        }
        return "users/home";
    }

    @GetMapping(value = "/admin/home")
    public String adminHomePage(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            checkForAdminRole(session, userService);
            UserDTO userDTO = retrieveUserFromSession(session, userService);
            Account account = accountService.findUserAccount(userDTO.getId());
            userDTO.setAccount(account);
            model.addAttribute("user", userDTO);
        } catch (AuthException e) {
            return processAuthError(e, redirectAttributes);
        }
        return "admin/home";
    }

    @GetMapping(value = "/admin/users")
    public String viewUsers(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            checkForAdminRole(session, userService);
            UserDTO userDTO = retrieveUserFromSession(session, userService);
            Account account = accountService.findUserAccount(userDTO.getId());
            userDTO.setAccount(account);
            model.addAttribute("user", userDTO);

            model.addAttribute("users", userService.findAllUsers());

        } catch (AuthException e) {
            return processAuthError(e, redirectAttributes);
        }
        return "admin/users";
    }

    @GetMapping(value = "/admin/users/create")
    public String createAccountPage(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            checkForAdminRole(session, userService);

            model.addAttribute("user", new UserDTO());

            return "users/create";
        } catch (AuthException e) {
            return processAuthError(e, redirectAttributes);
        }
    }

    @PostMapping(value = "/admin/users/create")
    public String createAccount(HttpSession session, @ModelAttribute UserDTO userDTO, Model model, RedirectAttributes redirectAttrs) {
        try {
            checkForAdminRole(session, userService);

            UserDTO user = userService.createUser(userDTO);
            Account account = accountService.createAccount(user);
            model.addAttribute("account", account);

            redirectAttrs.addFlashAttribute("login_id", userDTO.getUsername());

            return "redirect:/admin/users";
        } catch (AuthException e) {
            return processAuthError(e, redirectAttrs);
        }
    }

    @PostMapping(value = "/admin/users/{username}/status")
    public String deactivateOrDeactivateAccount(HttpSession session, @PathVariable String username, RedirectAttributes redirectAttributes) {
        try {
            checkForAdminRole(session, userService);
            userService.toggleUserStatus(username);
            return "redirect:/admin/users";
        } catch (AuthException e) {
            return processAuthError(e, redirectAttributes);
        } catch (DataException e) {
            return processError(e, redirectAttributes, "redirect:/admin/users");
        }
    }
}
