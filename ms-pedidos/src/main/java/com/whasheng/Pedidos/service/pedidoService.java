package com.whasheng.Pedidos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.whasheng.Pedidos.model.pedidos;
import com.whasheng.Pedidos.model.detallePedidos;
import com.whasheng.Pedidos.repository.pedidoRepository;

@Service
public class pedidoService {

    @Autowired
    private pedidoRepository pedidoRepository;

    public List<pedidos> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<pedidos> obtenerPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public pedidos obtener(Long id) {
        return pedidoRepository.findById(id).orElseThrow();
    }

    public void eliminar(Long id) {
        pedidoRepository.deleteById(id);
    }

    public pedidos guardarPedido(pedidos pedido) {
        return pedidoRepository.save(pedido);
    }

    public void guardarPedidoConDetalles(pedidos pedido, List<detallePedidos> detalles) {
        for (detallePedidos detalle : detalles) {
            detalle.setPedido(pedido);
        }
        pedido.setDetalles(detalles);
        pedidoRepository.save(pedido);
    }
}