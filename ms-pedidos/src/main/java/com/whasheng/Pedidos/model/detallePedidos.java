package com.whasheng.Pedidos.model;


import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_pedido")
public class detallePedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    @JsonBackReference
    private pedidos pedido;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private producto producto;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private double precioUnitario;

    @Column(name = "subtotal", nullable = false)
    private double subtotal;

    public detallePedidos() {}

    public detallePedidos(pedidos pedido, producto producto, int cantidad, double precioUnitario) {
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = cantidad * this.precioUnitario;
    }

}