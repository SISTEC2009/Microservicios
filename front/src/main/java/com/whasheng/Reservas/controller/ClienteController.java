package com.whasheng.Reservas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whasheng.Reservas.model.Cliente;
import com.whasheng.Reservas.service.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	@Autowired
    private ClienteService clienteService;

	@GetMapping("/listar")
	public String listarClientes(Model model) {
	    model.addAttribute("newCliente", new Cliente());
	    model.addAttribute("clientes", clienteService.listar());
	    return "Administrador/clientes";
	}

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("newCliente") Cliente cliente, RedirectAttributes redirectAttrs) {
		if (cliente.getId() != null) {
			clienteService.guardar(cliente);
			redirectAttrs.addFlashAttribute("clienteExitosoEditado","Cliente editado exitosamente");
		} else {
			clienteService.guardar(cliente);
			redirectAttrs.addFlashAttribute("clienteExitoso","Cliente registrado exitosamente");
    	}
        
    	return "redirect:/clientes/listar";
    }
    

	@PostMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) {
	    clienteService.eliminar(id);
	    redirectAttrs.addFlashAttribute("clienteEliminado", "Cliente eliminado exitosamente");
	    return "redirect:/clientes/listar";
	}
}