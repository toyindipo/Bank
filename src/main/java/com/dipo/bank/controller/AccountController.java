package com.dipo.bank.controller;

import com.dipo.bank.dto.AccountPosting;
import com.dipo.bank.dto.UserDTO;
import com.dipo.bank.exception.AuthException;
import com.dipo.bank.service.AccountService;
import com.dipo.bank.service.UserService;
import com.dipo.bank.exception.DataException;
import com.dipo.bank.model.Account;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountController extends IController {
    private final UserService userService;
    private final AccountService accountService;

    public AccountController(final UserService userService, final AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @GetMapping(value = "/accounts/transactions")
    public String accountPage(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        try {
            UserDTO userDTO = retrieveUserFromSession(session, userService);
            Account account = accountService.findUserAccount(userDTO.getId());
            userDTO.setAccount(account);
            model.addAttribute("user", userDTO);
            model.addAttribute("transactions", account.getTransactions());

            model.addAttribute("accountPosting", new AccountPosting());
        } catch (AuthException e) {
            return processAuthError(e, redirectAttributes);
        }
        return "accounts/transactions";
    }

    @GetMapping(value = "/accounts/deposit")
    public String viewDepositPage(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        return viewTransactionPage(session, model, redirectAttributes, "accounts/deposit");
    }


    @PostMapping(value = "accounts/deposit")
    public String deposit(HttpSession session, @ModelAttribute AccountPosting accountPosting, Model model, RedirectAttributes redirectAttrs) {
        try {
            UserDTO userDTO = retrieveUserFromSession(session, userService);
            accountPosting.setUserId(userDTO.getId());
            accountService.depositMoney(accountPosting);

            return redirectHome(userService,userDTO);
        } catch (AuthException e) {
            return processAuthError(e, redirectAttrs);
        } catch (DataException e) {
            return processError(e, redirectAttrs, "redirect:/accounts/deposit");
        }
    }

    @GetMapping(value = "/accounts/withdraw")
    public String viewWithdrawalPage(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        return viewTransactionPage(session, model, redirectAttributes, "accounts/withdraw");
    }

    @PostMapping(value = "accounts/withdraw")
    public String withdraw(HttpSession session, @ModelAttribute AccountPosting accountPosting, Model model, RedirectAttributes redirectAttrs) {
        try {
            UserDTO userDTO = retrieveUserFromSession(session, userService);
            accountPosting.setUserId(userDTO.getId());
            accountService.withdrawMoney(accountPosting);

            return redirectHome(userService,userDTO);
        } catch (AuthException e) {
            return processAuthError(e, redirectAttrs);
        } catch (DataException e) {
            return processError(e, redirectAttrs, "redirect:/accounts/withdraw");
        }
    }

    private String viewTransactionPage(HttpSession session, Model model, RedirectAttributes redirectAttributes, String page) {
        try {
            UserDTO userDTO = retrieveUserFromSession(session, userService);
            Account account = accountService.findUserAccount(userDTO.getId());
            userDTO.setAccount(account);
            model.addAttribute("user", userDTO);

            model.addAttribute("accountPosting", new AccountPosting());
        } catch (AuthException e) {
            return processAuthError(e, redirectAttributes);
        }
        return page;
    }

}
