package com.whasheng.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.whasheng.auth.entity.Usuario;
import com.whasheng.auth.repository.UsuarioRepository;
import com.whasheng.auth.JWT.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Valida usuario/password y, si son correctos, genera un JWT con el rol embebido.
     * Devuelve Optional.empty() si la autenticacion falla.
     */
    public Optional<String> login(String username, String password) {

        Optional<Usuario> usuarioOpt = repository.findByUsername(username);

        if (usuarioOpt.isEmpty()) {
            return Optional.empty();
        }

        Usuario usuario = usuarioOpt.get();

        boolean passwordCorrecto = passwordEncoder.matches(password, usuario.getPassword());

        if (!passwordCorrecto) {
            return Optional.empty();
        }

        String token = jwtUtil.generarToken(usuario.getUsername(), usuario.getRol());
        return Optional.of(token);
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return repository.findByUsername(username);
    }
}