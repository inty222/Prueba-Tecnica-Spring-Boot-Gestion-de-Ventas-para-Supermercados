package com.example.Prueba_Tecnica_Spring_Boot.repository;

import com.example.Prueba_Tecnica_Spring_Boot.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
