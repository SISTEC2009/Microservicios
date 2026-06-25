package com.whasheng.Reservas.util;

import com.whasheng.Reservas.controller.LoginController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class JwtSessionHolder {

    private JwtSessionHolder() {
    }

    public static String obtenerToken() {
        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attrs == null) {
            return null;
        }

        HttpServletRequest request = attrs.getRequest();
        HttpSession session = request.getSession(false);

        if (session == null) {
            return null;
        }

        return (String) session.getAttribute(LoginController.SESSION_TOKEN);
    }
}