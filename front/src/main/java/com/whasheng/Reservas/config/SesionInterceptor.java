package com.whasheng.Reservas.config;

import com.whasheng.Reservas.controller.LoginController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class SesionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        String rol = (session != null)
                ? (String) session.getAttribute(LoginController.SESSION_ROL)
                : null;

        boolean esAdmin = "ADMIN".equalsIgnoreCase(rol);

        if (!esAdmin) {
            response.sendRedirect(request.getContextPath() + "/menus/login");
            return false;
        }

        return true;
    }
}