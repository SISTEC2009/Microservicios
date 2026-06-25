package com.whasheng.Apis_Gateway.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class JwtAuthFilter implements Filter {

    @Autowired
    private JwtUtil jwtUtil;

    private static final String RUTA_PEDIDOS = "/Pedidos";
    private static final String RUTA_RESERVAS = "/Reservas";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();
        String method = request.getMethod();

        boolean requiereAdmin = calcularSiRequiereAdmin(path, method);

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

    private boolean calcularSiRequiereAdmin(String path, String method) {

        boolean esLectura = "GET".equalsIgnoreCase(method);

        if (path.startsWith(RUTA_PEDIDOS)) {
            return !esLectura;
        }

        if (path.startsWith(RUTA_RESERVAS)) {
            // Reservas: solo POST es libre (crear una reserva nueva).
            // GET, PUT y DELETE requieren ADMIN.
            boolean esCreacion = "POST".equalsIgnoreCase(method);
            return !esCreacion;

        }

        return false;
    }

    private void rechazar(HttpServletResponse response, int status, String mensaje) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\": \"" + mensaje + "\"}");
    }
}