package com.whasheng.Reservas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whasheng.Reservas.model.Reserva;
import com.whasheng.Reservas.repository.ReservaRepository;

@Service
public class ReservaService {

	@Autowired
	private ReservaRepository reservaRepository;
	
	public List<Reserva> ListaTodo() {
		return reservaRepository.findAll();
	}
	
	public Reserva save(Reserva reserva) {
		return reservaRepository.save(reserva);
	}

    public Reserva buscarPorId(Long id) {
        return reservaRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        reservaRepository.deleteById(id);
    }

}
