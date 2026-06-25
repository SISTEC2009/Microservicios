package com.whasheng.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.whasheng.auth.entity.Usuario;

import com.whasheng.auth.service.UsuarioService;

@RestController

@RequestMapping("/usuarios")

public class UsuarioController {

    @Autowired

    private UsuarioService service;

    @GetMapping

    public List<Usuario> listar(){

        return service.listar();

    }

    @PostMapping

    public Usuario guardar(

            @RequestBody Usuario usuario){

        return service.guardar(usuario);

    }

    @DeleteMapping("/{id}")

    public void eliminar(

            @PathVariable Long id){

        service.eliminar(id);

    }

}