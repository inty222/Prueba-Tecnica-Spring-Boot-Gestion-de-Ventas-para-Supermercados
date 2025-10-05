package com.example.Prueba_Tecnica_Spring_Boot.controller;

import com.example.Prueba_Tecnica_Spring_Boot.dto.IngresoTotalDto;
import com.example.Prueba_Tecnica_Spring_Boot.dto.TopProductoDto;
import com.example.Prueba_Tecnica_Spring_Boot.dto.VentaCreateDto;
import com.example.Prueba_Tecnica_Spring_Boot.dto.VentaResponseDto;
import com.example.Prueba_Tecnica_Spring_Boot.model.Venta;
import com.example.Prueba_Tecnica_Spring_Boot.service.VentaMapper;
import com.example.Prueba_Tecnica_Spring_Boot.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;
    private final VentaMapper ventaMapper;

    public VentaController(VentaService ventaService, VentaMapper ventaMapper) {
        this.ventaService = ventaService;
        this.ventaMapper = ventaMapper;
    }

    @PostMapping("/nuevaventa")
    @ResponseStatus(HttpStatus.CREATED)
    public VentaResponseDto registrarVenta(@Valid @RequestBody VentaCreateDto dto) {
        Venta venta = ventaMapper.toEntity(dto);
        Venta guardada = ventaService.registrarVenta(venta);
        return ventaMapper.toResponse(guardada);
    }

    // Lista Ventas con filtros Opcionales por Sucursal y/o fecha exacta
    @GetMapping
    public List<VentaResponseDto> listarVenta(
            @RequestParam(required = false) Long sucursalId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        List<Venta> ventas;

        if (sucursalId != null && fecha != null) {
            ventas = ventaService.buscarPorSucursalYFechasActivas(sucursalId, fecha, fecha);
        } else if (sucursalId != null) {
            ventas = ventaService.buscarPorSucursalActivas(sucursalId);
        } else if (fecha != null) {
            ventas = ventaService.buscarPorFechaActivas(fecha);
        } else {
            ventas = ventaService.listarVentasActivas();
        }

        return ventas.stream().map(ventaMapper::toResponse).toList();
    }
  
    @DeleteMapping("/eliminarventa/{id}")
    @ResponseStatus(HttpStatus.OK) // Se cambia a OK (200) para poder devolver un body con el mensaje
    public ResponseEntity<Map<String, String>> anularVenta(@PathVariable Long id) {
   
        ventaService.anularVenta(id);
        return ResponseEntity.ok(Map.of("mensaje", "Venta anulada correctamente."));
    }

    @GetMapping("/ingresostotales")
    public ResponseEntity<?> obtenerIngresosTotalesPorSucursalYFecha(
            @RequestParam Long sucursalId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {

        try {
            IngresoTotalDto resultado = ventaService.ingresosTotales(sucursalId, desde, hasta);
            return ResponseEntity.ok(resultado);

        } catch (IllegalArgumentException e) {
            // Devuelve un cuerpo JSON de error para Bad Request
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));

        } catch (NoSuchElementException e) {
            // Devuelve un cuerpo JSON de error para Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));

        } catch (Exception e) {
            // Devuelve un cuerpo JSON de error para Internal Server Error
            return ResponseEntity.internalServerError().body(Map.of("error", "Ocurrió un error interno al consultar los ingresos."));
        }
    }

    @GetMapping("/top-productos")
    public ResponseEntity<?> topProductosVendidos( // Se cambia a ResponseEntity<?> para consistencia
                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
                                                   @RequestParam(defaultValue = "5") int limite) {

        try {
            List<TopProductoDto> topProductos = ventaService.topProductos(desde, hasta, limite);

            if (topProductos.isEmpty()) {
                // Si el body da error en 204, se puede usar 200 con lista vacía.
                return ResponseEntity.ok(topProductos);
            }

            return ResponseEntity.ok(topProductos);

        } catch (IllegalArgumentException e) {
            // Devuelve un cuerpo JSON de error para Bad Request
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));

        } catch (Exception e) {
            // Devuelve un cuerpo JSON de error para Internal Server Error
            return ResponseEntity.internalServerError().body(Map.of("error", "Ocurrió un error interno al consultar el ranking."));
        }
    }
}