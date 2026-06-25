package com.whasheng.Reservas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whasheng.Reservas.model.Categoria;
import com.whasheng.Reservas.service.CategoriaService;

@Controller
@RequestMapping("/Administrador")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/categoria")
    public String listarCategorias(Model model,
                                   @ModelAttribute("success") String success,
                                   @ModelAttribute("error") String error) {
        model.addAttribute("categoria", new Categoria());
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("success", success);
        model.addAttribute("error", error);
        return "categoria";
    }

    @PostMapping("/categoria/guardar")
    public String guardarCategoria(@ModelAttribute Categoria categoria,
                                   RedirectAttributes redirectAttrs) {
        categoriaService.guardar(categoria);
        redirectAttrs.addFlashAttribute("success", "Categoría guardada correctamente.");
        return "redirect:/Administrador/categoria";
    }

    @GetMapping("/categoria/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id,
                                    RedirectAttributes redirectAttrs) {
        try {
            categoriaService.eliminar(id);
            redirectAttrs.addFlashAttribute("success", "Categoría eliminada correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "No se puede eliminar la categoría porque tiene productos asociados.");
        }
        return "redirect:/Administrador/categoria";
    }

    @GetMapping("/categoria/editar/{id}")
    public String editarCategoria(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaService.obtenerPorId(id);
        model.addAttribute("categoria", categoria);
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "categoria";
    }
}