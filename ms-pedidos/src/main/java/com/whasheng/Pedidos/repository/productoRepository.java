package com.whasheng.Pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.whasheng.Pedidos.model.producto;

import java.util.List;

@Repository
public interface productoRepository extends JpaRepository<producto,Long> {
	@Query("""
	           SELECT p FROM producto p
	           WHERE (:nombre IS NULL OR :nombre = '' OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
	             AND (:categoria IS NULL OR p.categoria.id = :categoria)
	             AND (:estado IS NULL OR :estado = '' OR p.estado = :estado)
	           ORDER BY p.nombre ASC
	           """)
	List<producto> filtrar(@Param("nombre") String nombre,
						   @Param("categoria") Long categoria,
						   @Param("estado") String estado);
}