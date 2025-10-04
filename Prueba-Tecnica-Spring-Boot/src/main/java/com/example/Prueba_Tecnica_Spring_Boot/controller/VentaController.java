package com.example.Prueba_Tecnica_Spring_Boot.controller;

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

    //Lista Ventas con filtros Opcionales por Sucursal y/o fecha exacta.
    @GetMapping
    public List<VentaResponseDto> listarVenta(
            @RequestParam(required = false) Long sucursalId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        List<Venta> ventas;

        // Caso 1: ambos filtros presentes -> rango [fecha, fecha] (mismo día)
        if (sucursalId != null && fecha != null) {
            ventas = ventaService.buscarPorSucursalYFechasActivas(sucursalId, fecha, fecha);

        // Caso 2: solo sucursalId -> rango completo (desde el mínimo hasta el máximo de LocalDate).
        } else if (sucursalId != null) {
            ventas = ventaService.buscarPorSucursalActivas(sucursalId);

        // Caso 3: solo fecha -> buscar por fecha concreta en todas las sucursales.
        } else if (fecha != null) {
            ventas = ventaService.buscarPorFechaActivas(fecha);

        // Caso 4: sin filtros -> listar todas las ventas activas.
        } else {
            ventas = ventaService.listarVentasActivas();
        }

        // Convertimos la lista de entidades a DTOs de respuesta para la API pública.
        return ventas.stream().map(ventaMapper::toResponse).toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> anularVenta(@PathVariable Long id) {
        ventaService.anularVenta(id);
        return ResponseEntity.ok("Venta eliminada.");
    }

}
