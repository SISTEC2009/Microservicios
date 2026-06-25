package com.whasheng.Pedidos.controller;

import com.whasheng.Pedidos.model.categoria;
import com.whasheng.Pedidos.service.categoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class categoriaController {

    @Autowired
    private categoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<categoria>> listar() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<categoria> obtener(@PathVariable Long id) {
        categoria c = categoriaService.obtenerPorId(id);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(c);
    }

    @PostMapping
    public ResponseEntity<categoria> crear(@RequestBody categoria nueva) {
        categoria creada = categoriaService.guardar(nueva);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<categoria> actualizar(@PathVariable Long id, @RequestBody categoria datos) {
        categoria existente = categoriaService.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        datos.setId(id);
        categoria actualizada = categoriaService.guardar(datos);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoria existente = categoriaService.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}