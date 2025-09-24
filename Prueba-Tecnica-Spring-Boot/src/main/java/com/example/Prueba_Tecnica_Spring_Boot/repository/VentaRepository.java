package com.example.Prueba_Tecnica_Spring_Boot.repository;

import com.example.Prueba_Tecnica_Spring_Boot.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    // Obtener todas las ventas (activas y anuladas) de una fecha concreta
    List<Venta> findByFecha(LocalDate fecha);

    // Solo ventas NO anuladas de una fecha concreta (útil para el GET con filtros)
    List<Venta> findByFechaAndAnuladaFalse(LocalDate fecha);

    // Recuperar una venta por id solo si NO está anulada (para detalles, etc.)
    Optional<Venta> findByIdAndAnuladaFalse(Long id);

    // Listar todas las ventas NO anuladas (p.ej. para listados sin filtro de fecha)
    List<Venta> findByAnuladaFalse();
}

