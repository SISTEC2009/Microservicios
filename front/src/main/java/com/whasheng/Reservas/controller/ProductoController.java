package com.whasheng.Reservas.controller;

import com.whasheng.Reservas.model.Producto;
import com.whasheng.Reservas.service.CategoriaService;
import com.whasheng.Reservas.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listar")
    public String listarProductos(Model model) {
        model.addAttribute("listaProductos", productoService.obtenerTodos());
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("newProducto", new Producto());
        return "Administrador/Producto";
    }

    @GetMapping("/filtro")
    public String dashboard(@RequestParam(required = false) String nombre,
                            @RequestParam(required = false) Long categoria,
                            @RequestParam(required = false) String estado,
                            Model model) {

        model.addAttribute("listaProductos", productoService.filtrar(nombre, categoria, estado));
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("newProducto", new Producto());

        model.addAttribute("qNombre", nombre == null ? "" : nombre);
        model.addAttribute("qCategoria", categoria == null ? "" : categoria.toString());
        model.addAttribute("qEstado", estado == null ? "" : estado);

        return "Administrador/Producto";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("newProducto") Producto p,
                          @RequestParam(name = "archivo", required = false) MultipartFile archivo) throws IOException {

        productoService.guardar(p, archivo);
        return "redirect:/productos/listar";
    }

    @PostMapping("/delete/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return "redirect:/productos/listar";
    }
}