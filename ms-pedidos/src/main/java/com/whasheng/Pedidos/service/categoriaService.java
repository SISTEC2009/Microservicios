package com.whasheng.Pedidos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.whasheng.Pedidos.model.categoria;
import com.whasheng.Pedidos.repository.categoriaRepository;

@Service
public class categoriaService {
    @Autowired
    private categoriaRepository categoriaRepository;

    public List<categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public categoria guardar(categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public categoria obtenerPorId(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }
}