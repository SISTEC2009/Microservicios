package com.whasheng.Pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.whasheng.Pedidos.model.pedidos;

@Repository
public interface pedidoRepository extends JpaRepository<pedidos, Long> { }