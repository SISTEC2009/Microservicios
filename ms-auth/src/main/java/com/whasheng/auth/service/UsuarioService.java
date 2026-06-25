package com.whasheng.auth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.whasheng.auth.entity.Usuario;
import com.whasheng.auth.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<Usuario> listar(){

        return repository.findAll();

    }

    public Optional<Usuario> buscar(Long id){

        return repository.findById(id);

    }

    public Usuario guardar(Usuario usuario){

        usuario.setPassword(

                passwordEncoder.encode(

                        usuario.getPassword()

                )

        );

        return repository.save(usuario);

    }

    public Usuario actualizar(Long id, Usuario usuario){

        Usuario u = repository.findById(id).orElse(null);

        if(u!=null){

            u.setUsername(usuario.getUsername());

            u.setRol(usuario.getRol());

            return repository.save(u);

        }

        return null;

    }

    public void eliminar(Long id){

        repository.deleteById(id);

    }

}