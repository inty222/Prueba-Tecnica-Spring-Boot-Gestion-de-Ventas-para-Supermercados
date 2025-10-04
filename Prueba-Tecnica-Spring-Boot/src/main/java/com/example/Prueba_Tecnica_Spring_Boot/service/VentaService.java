package com.example.Prueba_Tecnica_Spring_Boot.service;

import com.example.Prueba_Tecnica_Spring_Boot.dto.IngresoTotalDto;
import com.example.Prueba_Tecnica_Spring_Boot.dto.TopProductoDto;
import com.example.Prueba_Tecnica_Spring_Boot.model.Venta;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VentaService {

    // Crea una venta aplicando reglas de negocio.
    Venta registrarVenta(Venta venta);

    // Lista todas las ventas activas (no anuladas).
    List<Venta> listarVentasActivas();

    // Busca ventas activas por fecha exacta (yyyy-MM-dd).
    List<Venta> buscarPorFechaActivas(LocalDate fecha);

    // Busca ventas activas por sucursal en rango [desde, hasta].
    List<Venta> buscarPorSucursalYFechasActivas(Long sucursalId, LocalDate desde, LocalDate hasta);

    // Busca ventas activas por sucursal sin imponer rango de fechas.
    List<Venta> buscarPorSucursalActivas(Long sucursalId);

    // Anula (borrado lógico) una venta por id.
    void anularVenta(Long id);

    // Obtiene una venta activa por id.
    Optional<Venta> obtenerPorIdActiva(Long id);

    // Calcula ingresos totales de una sucursal en el rango [desde, hasta].
    IngresoTotalDto ingresosTotales(Long sucursalId, LocalDate desde, LocalDate hasta);

    // Devuelve el top de productos (cantidad e importe) en el rango indicado, limitado por 'limit'.
    List<TopProductoDto> topProductos(LocalDate desde, LocalDate hasta, int limit);
}
