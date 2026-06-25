package com.whasheng.Pedidos.controller;

import com.whasheng.Pedidos.model.producto;
import com.whasheng.Pedidos.service.categoriaService;
import com.whasheng.Pedidos.service.productoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class productoController {

    @Autowired
    private productoService productoService;

    @Autowired
    private categoriaService categoriaService;

    @Value("${app.upload-dir}")
    private String uploadDir;

    @GetMapping
    public ResponseEntity<List<producto>> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Long categoria,
            @RequestParam(required = false) String estado) {

        if (nombre != null || categoria != null || estado != null) {
            return ResponseEntity.ok(productoService.filtrar(nombre, categoria, estado));
        }

        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<producto> obtener(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<producto> crear(
            @ModelAttribute producto p,
            @RequestParam(name = "archivo", required = false) MultipartFile archivo) throws IOException {

        guardarImagenSiCorresponde(p, archivo);
        producto creado = productoService.guardar(p);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<producto> actualizar(
            @PathVariable Long id,
            @ModelAttribute producto datos,
            @RequestParam(name = "archivo", required = false) MultipartFile archivo) throws IOException {

        if (!productoService.existe(id)) {
            return ResponseEntity.notFound().build();
        }

        datos.setId(id);
        guardarImagenSiCorresponde(datos, archivo);

        producto actualizado = productoService.guardar(datos);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!productoService.existe(id)) {
            return ResponseEntity.notFound().build();
        }

        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private void guardarImagenSiCorresponde(producto p, MultipartFile archivo) throws IOException {
        if (archivo == null || archivo.isEmpty()) {
            return;
        }

        Path root = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(root);

        String original = StringUtils.cleanPath(archivo.getOriginalFilename());
        String filename = System.currentTimeMillis() + "_" + original;
        Path destino = root.resolve(filename);

        Files.copy(archivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
        p.setImagen(filename);
    }
}