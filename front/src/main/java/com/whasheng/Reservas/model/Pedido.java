package com.whasheng.Reservas.model;

import java.time.LocalDate;
import java.util.List;

public class Pedido {

	private Long idPedido;
	private String nombrePedido;
	private int telefonoPedido;
	private String tipoPedido;
	private String direccionPedido;
	private String notasPedido;
	private String metodoPagoPedido;
	private int cantidadPedido;
	private double totalPedido;
	private LocalDate fechaPedido;
	private String estadoPedido;
	private List<DetallePedido> detalles;

	public Pedido() {
	}

	public Pedido(Long idPedido, String nombrePedido, int telefonoPedido, String tipoPedido, String direccionPedido,
				  String notasPedido, String metodoPagoPedido, int cantidadPedido, double totalPedido,
				  LocalDate fechaPedido, String estadoPedido) {
		this.idPedido = idPedido;
		this.nombrePedido = nombrePedido;
		this.telefonoPedido = telefonoPedido;
		this.tipoPedido = tipoPedido;
		this.direccionPedido = direccionPedido;
		this.notasPedido = notasPedido;
		this.metodoPagoPedido = metodoPagoPedido;
		this.cantidadPedido = cantidadPedido;
		this.totalPedido = totalPedido;
		this.fechaPedido = fechaPedido;
		this.estadoPedido = estadoPedido;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public String getNombrePedido() {
		return nombrePedido;
	}

	public void setNombrePedido(String nombrePedido) {
		this.nombrePedido = nombrePedido;
	}

	public int getTelefonoPedido() {
		return telefonoPedido;
	}

	public void setTelefonoPedido(int telefonoPedido) {
		this.telefonoPedido = telefonoPedido;
	}

	public String getTipoPedido() {
		return tipoPedido;
	}

	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

	public String getDireccionPedido() {
		return direccionPedido;
	}

	public void setDireccionPedido(String direccionPedido) {
		this.direccionPedido = direccionPedido;
	}

	public String getNotasPedido() {
		return notasPedido;
	}

	public void setNotasPedido(String notasPedido) {
		this.notasPedido = notasPedido;
	}

	public String getMetodoPagoPedido() {
		return metodoPagoPedido;
	}

	public void setMetodoPagoPedido(String metodoPagoPedido) {
		this.metodoPagoPedido = metodoPagoPedido;
	}

	public double getTotalPedido() {
		return totalPedido;
	}

	public void setTotalPedido(double totalPedido) {
		this.totalPedido = totalPedido;
	}

	public int getCantidadPedido() {
		return cantidadPedido;
	}

	public void setCantidadPedido(int cantidadPedido) {
		this.cantidadPedido = cantidadPedido;
	}

	public LocalDate getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(LocalDate fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public String getEstadoPedido() {
		return estadoPedido;
	}

	public void setEstadoPedido(String estadoPedido) {
		this.estadoPedido = estadoPedido;
	}

	public List<DetallePedido> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetallePedido> detalles) {
		this.detalles = detalles;
	}
}