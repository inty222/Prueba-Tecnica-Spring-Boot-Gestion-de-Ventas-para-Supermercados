package com.example.Prueba_Tecnica_Spring_Boot.service;

import com.example.Prueba_Tecnica_Spring_Boot.model.Venta;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VentaService {

    Venta registrarVenta(Venta venta);

    List<Venta> listarVentasActivas();

    List<Venta> buscarPorFechaActivas(LocalDate fecha);

    Optional<Venta> obtenerPorIdActiva(Long id);

    void anularVenta(Long id);

    List<Venta> buscarPorSucursalYFechasActivas(Long sucursalId, LocalDate desde, LocalDate hasta);

    IngresoTotalDto ingresosTotales(Long sucursalId, LocalDate desde, LocalDate hasta);

    List<TopProductoDto> topProductos(LocalDate desde, LocalDate hasta, int limit);
}
