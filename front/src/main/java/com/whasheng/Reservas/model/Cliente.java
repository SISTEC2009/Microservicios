package com.whasheng.Reservas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;
	
	@Column(name = "telefono", nullable = false, length = 15)
	private String telefono;
	
	@Column(name = "email", nullable = false, length = 100)
	private String email;
	
	@Column(name = "direccion", nullable = false, length = 200)
	private String direccion;
	
	@Column(name = "dni_ruc", nullable = false, length = 20)
	private String dni_ruc;
	
	public Cliente(Long id, String nombre, String telefono, String email, String direccion, String dni_ruc) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.direccion = direccion;
		this.dni_ruc = dni_ruc;
	}
	
	public Cliente() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDni_ruc() {
		return dni_ruc;
	}

	public void setDni_ruc(String dni_ruc) {
		this.dni_ruc = dni_ruc;
	}	
}
