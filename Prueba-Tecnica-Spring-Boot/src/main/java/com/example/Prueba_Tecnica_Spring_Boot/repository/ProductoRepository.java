package com.example.Prueba_Tecnica_Spring_Boot.repository;

import com.example.Prueba_Tecnica_Spring_Boot.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    @Query(value = "SELECT p.* " +
            "FROM venta_items vi " +
            "JOIN producto p ON vi.producto_id = p.id " +
            "GROUP BY p.id " +
            "ORDER BY SUM(vi.cantidad) DESC " +
            "LIMIT 1", nativeQuery = true)
    Producto findProductoMasVendido();
    boolean existsByNombreProducto(String nombreProducto);
}
