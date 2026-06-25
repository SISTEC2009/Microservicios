package com.whasheng.Reservas.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whasheng.Reservas.model.DetallePedido;
import com.whasheng.Reservas.model.Pedido;
import com.whasheng.Reservas.service.CategoriaService;
import com.whasheng.Reservas.service.PedidoService;
import com.whasheng.Reservas.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private ProductoService productoService;

	@GetMapping("/listar")
	public String mostrarFormularioPedido(Model model) {
		model.addAttribute("categorias", categoriaService.listarTodas());
		model.addAttribute("productos", productoService.obtenerTodos());
		model.addAttribute("newPedido", new Pedido());
		return "Pedido/pedido";
	}


	@GetMapping("/gestion")
	public String mostrarTablaPedidos(Model model) {
		model.addAttribute("newPedido", new Pedido());
		List<Pedido> pedidosActivos = pedidoService.findAll()
				.stream()
				.filter(p -> !"Rechazado".equalsIgnoreCase(p.getEstadoPedido()))
				.toList();
		model.addAttribute("pedidos", pedidosActivos);

		return "Administrador/gestionPedidos";
	}


	@PostMapping("/confirmar")
	public String guardarPedido(
			@ModelAttribute("newPedido") Pedido pedido,
			@RequestParam(value = "detallesJson", required = false) String detallesJson,
			RedirectAttributes redirectAttrs) {

		if (detallesJson != null && !detallesJson.trim().isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				DetallePedido[] detalles = mapper.readValue(detallesJson, DetallePedido[].class);
				pedido.setDetalles(Arrays.asList(detalles));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		pedidoService.guardarPedido(pedido);
		redirectAttrs.addFlashAttribute("pedidoExitoso", "Pedido enviado exitosamente");

		return "redirect:/pedidos/listar";
	}

	@GetMapping("/detalle-json/{id}")
	@ResponseBody
	public ResponseEntity<?> obtenerDetallePedido(@PathVariable Long id) {
		Pedido pedido = pedidoService.obtener(id);
		if (pedido == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(pedido);
	}

	@PostMapping("/editar/{id}")
	@ResponseBody
	public ResponseEntity<?> editarPedido(@PathVariable Long id, @RequestBody Pedido pedidoActualizado) {

		pedidoActualizado.setIdPedido(id);
		Pedido actualizado = pedidoService.guardarPedido(pedidoActualizado);

		if (actualizado == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(actualizado);
	}


	@PostMapping("/rechazar/{id}")
	@ResponseBody
	public ResponseEntity<?> rechazarPedido(@PathVariable Long id) {
		Pedido pedido = pedidoService.rechazar(id);
		if (pedido == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(pedido);
	}

}