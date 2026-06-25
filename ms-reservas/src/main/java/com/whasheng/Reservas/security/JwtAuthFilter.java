package com.whasheng.Reservas.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthFilter implements Filter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String method = request.getMethod();

        boolean requiereAdmin = "PUT".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method);

        if (!requiereAdmin) {
            chain.doFilter(req, res);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            rechazar(response, HttpServletResponse.SC_UNAUTHORIZED, "Falta el token de autenticacion");
            return;
        }

        String token = authHeader.substring(7);
        Claims claims = jwtUtil.validarYObtenerClaims(token);

        if (claims == null) {
            rechazar(response, HttpServletResponse.SC_UNAUTHORIZED, "Token invalido o expirado");
            return;
        }

        String rol = jwtUtil.extraerRol(claims);

        if (!"ADMIN".equalsIgnoreCase(rol)) {
            rechazar(response, HttpServletResponse.SC_FORBIDDEN, "Se requiere rol ADMIN para esta accion");
            return;
        }

        chain.doFilter(req, res);
    }

    private void rechazar(HttpServletResponse response, int status, String mensaje) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\": \"" + mensaje + "\"}");
    }
}