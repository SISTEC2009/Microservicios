package com.whasheng.Reservas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.whasheng.Reservas.model.Reserva;
import com.whasheng.Reservas.service.ReservaService;

@RestController
@RequestMapping("/api/reservas")
public class ReservasController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public List<Reserva> listar() {
        return reservaService.ListaTodo();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> buscar(@PathVariable Long id) {
        Reserva reserva = reservaService.buscarPorId(id);
        if (reserva == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reserva);
    }

    @PostMapping
    public ResponseEntity<Reserva> guardar(@RequestBody Reserva reserva) {
        Reserva creada = reservaService.save(reserva);
        return ResponseEntity.status(201).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizar(@PathVariable Long id, @RequestBody Reserva reserva) {
        Reserva existente = reservaService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        existente.setNombreReserva(reserva.getNombreReserva());
        existente.setTelefonoReserva(reserva.getTelefonoReserva());
        existente.setEmailReserva(reserva.getEmailReserva());
        existente.setLocalReserva(reserva.getLocalReserva());
        existente.setFechaReserva(reserva.getFechaReserva());
        existente.setHoraReserva(reserva.getHoraReserva());
        existente.setCantidadReserva(reserva.getCantidadReserva());
        existente.setPreferenciasReserva(reserva.getPreferenciasReserva());

        return ResponseEntity.ok(reservaService.save(existente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (reservaService.buscarPorId(id) == null) {
            return ResponseEntity.notFound().build();
        }
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}