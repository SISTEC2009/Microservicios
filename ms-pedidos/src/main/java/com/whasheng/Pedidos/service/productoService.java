package com.whasheng.Pedidos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.whasheng.Pedidos.model.producto;
import com.whasheng.Pedidos.repository.productoRepository;

@Service
public class productoService {

    @Autowired
    private productoRepository repo;

    public List<producto> obtenerTodos() {
        return repo.findAll();
    }

    public List<producto> filtrar(String nombre, Long categoria, String estado) {
        return repo.filtrar(nombre, categoria, estado);
    }

    public producto guardar(producto p) {
        return repo.save(p);
    }

    public Optional<producto> obtenerPorId(Long id) {
        return repo.findById(id);
    }

    public producto obtener(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public boolean existe(Long id) {
        return repo.existsById(id);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}