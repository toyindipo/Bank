package com.dipo.bank.controller;

import com.dipo.bank.dto.UserDTO;
import com.dipo.bank.exception.AuthException;
import com.dipo.bank.exception.BankException;
import com.dipo.bank.service.UserService;
import com.dipo.bank.exception.DataException;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public abstract class IController {
    public UserDTO retrieveUserFromSession(HttpSession session, UserService userService) throws AuthException {
        Object loginObject = session.getAttribute("login_id");
        if (loginObject == null) {
            throw new AuthException("No user in session");
        }

        try {
            UserDTO userDTO = userService.findUserByUsername(loginObject.toString());
            return userDTO;
        } catch (DataException e) {
            throw new AuthException(e.getMessage());
        }
    }

    public  void checkForAdminRole(HttpSession session, UserService userService) throws AuthException {
        UserDTO user = retrieveUserFromSession(session, userService);
        if (!userService.hasAdminRole(user)) {
            throw new AuthException("No Admin role found");
        }
    }

    public String processAuthError(AuthException exception, RedirectAttributes redirectAttributes) {
        return processError(exception, redirectAttributes, "redirect:/login");
    }

    public String processError(BankException exception, RedirectAttributes redirectAttributes, String path) {
        redirectAttributes.addFlashAttribute("error", exception.getMessage());
        return path;
    }

    public String redirectHome(UserService userService, UserDTO userDTO) {

        if (userService.hasAdminRole(userDTO)) {
            return "redirect:/admin/home";
        }

        return "redirect:/users/home";
    }
}
