package com.whasheng.Reservas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whasheng.Reservas.model.Cliente;
import com.whasheng.Reservas.repository.ClienteRepository;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;
	
	//SERVICIOS
	//LISTAR
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}
	
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	//GUARDAR
	public Cliente guardar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	//OBTENER POR ID
		public Cliente obtenerPorId(Long id) {
			return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
		}
	
	//ACTUALIZAR
	public Cliente actualizar(Long id, Cliente cliente) {
		Cliente clienteExistente = clienteRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
		clienteExistente.setNombre(cliente.getNombre());
		clienteExistente.setTelefono(cliente.getTelefono());
		clienteExistente.setEmail(cliente.getEmail());
		clienteExistente.setDireccion(cliente.getDireccion());
		clienteExistente.setDni_ruc(cliente.getDni_ruc());
		return clienteRepository.save(clienteExistente);
	}

	//ELIMINAR
	public void eliminar(Long id) {
		clienteRepository.deleteById(id);
	}
}
