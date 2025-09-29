package com.example.Prueba_Tecnica_Spring_Boot.repository;

import com.example.Prueba_Tecnica_Spring_Boot.dto.TopProductoDto;
import com.example.Prueba_Tecnica_Spring_Boot.model.Venta;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByFecha(LocalDate fecha);

    List<Venta> findByFechaAndAnuladaFalse(LocalDate fecha);

    Optional<Venta> findByIdAndAnuladaFalse(Long id);

    List<Venta> findByAnuladaFalse();

    List<Venta> findBySucursal_IdAndAnuladaFalse(Long sucursalId);

    List<Venta> findByFechaBetweenAndAnuladaFalse(LocalDate desde, LocalDate hasta);

    List<Venta> findBySucursal_IdAndFechaBetweenAndAnuladaFalse(Long sucursalId, LocalDate desde, LocalDate hasta);

    @Query("""
           select coalesce(sum(vi.cantidad * p.precio), 0)
           from Venta v
           join v.ventaItems vi
           join vi.producto p
           where v.sucursal.id = :sucursalId
             and v.anulada = false
             and v.fecha between :desde and :hasta
           """)
    Double ingresosTotalesBySucursal(Long sucursalId, LocalDate desde, LocalDate hasta);


    @Query("""
           select new com.example.Prueba_Tecnica_Spring_Boot.dto.TopProductoDto(
               p.id,
               p.nombreProducto,
               sum(vi.cantidad),
               sum(vi.cantidad * p.precio)
           )
           from Venta v
           join v.ventaItems vi
           join vi.producto p
           where v.anulada = false
             and v.fecha between :desde and :hasta
           group by p.id, p.nombreProducto
           order by sum(vi.cantidad) desc
           """)
    List<TopProductoDto> topProductos(LocalDate desde, LocalDate hasta, Pageable pageable);
}
