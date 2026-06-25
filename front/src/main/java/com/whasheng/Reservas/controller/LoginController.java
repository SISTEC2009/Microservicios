package com.whasheng.Reservas.controller;

import com.whasheng.Reservas.dto.LoginResponse;
import com.whasheng.Reservas.service.AuthClientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private AuthClientService authClientService;

    public static final String SESSION_TOKEN = "JWT_TOKEN";
    public static final String SESSION_USERNAME = "USERNAME";
    public static final String SESSION_ROL = "ROL";

    @GetMapping("/menus/login")
    public String mostrarLogin() {
        return "Administrador/login";
    }

    @PostMapping("/menus/login")
    public String procesarLogin(@RequestParam String username,
                                @RequestParam String password,
                                HttpServletRequest request,
                                Model model) {

        LoginResponse respuesta = authClientService.login(username, password);

        if (!respuesta.isExito()) {
            model.addAttribute("error", respuesta.getMensaje());
            return "Administrador/login";
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(SESSION_TOKEN, respuesta.getToken());
        session.setAttribute(SESSION_USERNAME, respuesta.getUsername());
        session.setAttribute(SESSION_ROL, respuesta.getRol());

        if ("ADMIN".equalsIgnoreCase(respuesta.getRol())) {
            return "redirect:/menus/dashboard";
        }

        return "redirect:/";
    }

    @GetMapping("/menus/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/menus/login";
    }
}