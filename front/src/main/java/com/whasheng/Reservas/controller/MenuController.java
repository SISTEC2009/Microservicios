package com.whasheng.Reservas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menus")
public class MenuController {

    @GetMapping("/dashboard")
    public String DashBoard() {
        return "Administrador/MenuPrincipal";
    }
}