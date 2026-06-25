package com.whasheng.Reservas.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Reserva {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idReserva;
	private String nombreReserva;
	private String telefonoReserva;
	private String emailReserva;
	private String localReserva;
	private LocalDate fechaReserva;
	private String horaReserva;
	private Integer cantidadReserva;
	private String preferenciasReserva;

	public Reserva() {
		// TODO Auto-generated constructor stub
	}

	public Long getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Long idReserva) {
		this.idReserva = idReserva;
	}

	public String getNombreReserva() {
		return nombreReserva;
	}

	public void setNombreReserva(String nombreReserva) {
		this.nombreReserva = nombreReserva;
	}

	public String getTelefonoReserva() {
		return telefonoReserva;
	}

	public void setTelefonoReserva(String telefonoReserva) {
		this.telefonoReserva = telefonoReserva;
	}

	public String getEmailReserva() {
		return emailReserva;
	}

	public void setEmailReserva(String emailReserva) {
		this.emailReserva = emailReserva;
	}

	public String getLocalReserva() {
		return localReserva;
	}

	public void setLocalReserva(String localReserva) {
		this.localReserva = localReserva;
	}

	public LocalDate getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(LocalDate fechaReserva) {
		this.fechaReserva = fechaReserva;
	}

	public String getHoraReserva() {
		return horaReserva;
	}

	public void setHoraReserva(String horaReserva) {
		this.horaReserva = horaReserva;
	}

	public Integer getCantidadReserva() {
		return cantidadReserva;
	}

	public void setCantidadReserva(Integer cantidadReserva) {
		this.cantidadReserva = cantidadReserva;
	}

	public String getPreferenciasReserva() {
		return preferenciasReserva;
	}

	public void setPreferenciasReserva(String preferenciasReserva) {
		this.preferenciasReserva = preferenciasReserva;
	}

}
