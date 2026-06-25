package com.whasheng.Pedidos.controller;

import com.whasheng.Pedidos.model.detallePedidos;
import com.whasheng.Pedidos.model.pedidos;
import com.whasheng.Pedidos.service.pedidoService;
import com.whasheng.Pedidos.service.productoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class pedidosController {

    @Autowired
    private pedidoService pedidoService;

    @Autowired
    private productoService productoService;

    @GetMapping
    public ResponseEntity<List<pedidos>> listar() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<pedidos> obtener(@PathVariable Long id) {
        return pedidoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<pedidos> crear(@RequestBody pedidos pedido) {

        if (pedido.getDetalles() != null) {
            for (detallePedidos d : pedido.getDetalles()) {
                d.setPedido(pedido);
            }
        }

        pedido.setFechaPedido(LocalDate.now());
        pedido.setEstadoPedido("Pendiente");

        pedidos creado = pedidoService.guardarPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<pedidos> actualizar(@PathVariable Long id, @RequestBody pedidos pedidoActualizado) {

        return pedidoService.obtenerPorId(id).map(pedido -> {

            pedido.setNombrePedido(pedidoActualizado.getNombrePedido());
            pedido.setTelefonoPedido(pedidoActualizado.getTelefonoPedido());
            pedido.setDireccionPedido(pedidoActualizado.getDireccionPedido());
            pedido.setNotasPedido(pedidoActualizado.getNotasPedido());
            pedido.setTipoPedido(pedidoActualizado.getTipoPedido());
            pedido.setEstadoPedido(pedidoActualizado.getEstadoPedido());

            if (pedidoActualizado.getDetalles() != null) {
                pedido.getDetalles().clear();
                for (detallePedidos d : pedidoActualizado.getDetalles()) {
                    Long idProducto = d.getProducto().getId();
                    d.setProducto(productoService.obtener(idProducto));
                    d.setPedido(pedido);
                    pedido.getDetalles().add(d);
                }
            }

            int cantidadTotal = pedido.getDetalles().stream()
                    .mapToInt(detallePedidos::getCantidad)
                    .sum();
            pedido.setCantidadPedido(cantidadTotal);

            pedidos guardado = pedidoService.guardarPedido(pedido);
            return ResponseEntity.ok(guardado);

        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (pedidoService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/rechazar")
    public ResponseEntity<pedidos> rechazar(@PathVariable Long id) {
        return pedidoService.obtenerPorId(id).map(pedido -> {
            pedido.setEstadoPedido("Rechazado");
            return ResponseEntity.ok(pedidoService.guardarPedido(pedido));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}