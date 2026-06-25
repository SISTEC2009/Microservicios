package com.whasheng.Pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.whasheng.Pedidos.model.categoria;

@Repository
public interface categoriaRepository extends JpaRepository<categoria, Long> {
}