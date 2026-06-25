package com.whasheng.Pedidos.model;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

@Data
@Entity
@Table(name = "pedido")
public class pedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @Column(name = "nombre_pedido", nullable = false)
    private String nombrePedido;

    @Column(name = "telefono_pedido", nullable = false)
    private int telefonoPedido;

    @Column(name = "tipo_pedido", nullable = false)
    private String tipoPedido;

    @Column(name = "direccion_pedido", nullable = false)
    private String direccionPedido;

    @Column(name = "notas_pedido")
    private String notasPedido;

    @Column(name = "metodo_pago_pedido", nullable = false)
    private String metodoPagoPedido;

    @Column(name = "cantidad_pedido", nullable = false)
    private int cantidadPedido;

    @Column(name = "total_pedido", nullable = false)
    private double totalPedido;

    @Column(name = "fecha_pedido", nullable = false)
    private LocalDate fechaPedido;

    @Column(name = "estado_pedido", nullable = false)
    private String estadoPedido;


    @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<detallePedidos> detalles;

    public List<detallePedidos> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<detallePedidos> detalles) {
        this.detalles = detalles;
    }

}