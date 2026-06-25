package com.whasheng.Reservas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whasheng.Reservas.model.Reserva;
import com.whasheng.Reservas.service.ReservaService;

@Controller
@RequestMapping("/reservas")
public class ReservasController {
	
	@Autowired
	private ReservaService reservaService;

    @GetMapping("/listar")
    public String listarReserva(Model model) {

    	model.addAttribute("newReserva", new Reserva());
        return "Reservas/reserva.html";
    }
    
    @GetMapping("/listado")
    public String listadoReserva(Model model) {
	    model.addAttribute("lstReserva", reservaService.ListaTodo());
        return "Reservas/reservalst.html";
    }
    
    @PostMapping("/guardar")
    public String guardaReserva(@ModelAttribute("newReserva") Reserva reserva,RedirectAttributes redirectAttrs) {
        reservaService.save(reserva); // o lo que hagas para guardar
		redirectAttrs.addFlashAttribute("guardadoExitoso","Reserva guarda exitosamente");
        return "redirect:/reservas/listar";
    }
	public ReservasController() {
		// TODO Auto-generated constructor stub
	}

}
