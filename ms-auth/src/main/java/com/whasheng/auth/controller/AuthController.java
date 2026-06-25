package com.whasheng.auth.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.whasheng.auth.dto.LoginRequest;
import com.whasheng.auth.dto.LoginResponse;
import com.whasheng.auth.entity.Usuario;
import com.whasheng.auth.JWT.JwtUtil;
import com.whasheng.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        Optional<String> tokenOpt = service.login(request.getUsername(), request.getPassword());

        if (tokenOpt.isEmpty()) {
            return new LoginResponse(false, "Usuario o contraseña incorrectos");
        }

        String token = tokenOpt.get();
        Usuario usuario = service.buscarPorUsername(request.getUsername()).orElseThrow();

        return new LoginResponse(
                true,
                "Login exitoso",
                token,
                usuario.getUsername(),
                usuario.getRol()
        );
    }

    // Permite validar un token desde otros microservicios (api-gateway, front)
    @GetMapping("/validar")
    public LoginResponse validar(@RequestParam String token) {

        if (!jwtUtil.esTokenValido(token)) {
            return new LoginResponse(false, "Token inválido o expirado");
        }

        String username = jwtUtil.extraerUsername(token);
        String rol = jwtUtil.extraerRol(token);

        return new LoginResponse(true, "Token válido", token, username, rol);
    }
}