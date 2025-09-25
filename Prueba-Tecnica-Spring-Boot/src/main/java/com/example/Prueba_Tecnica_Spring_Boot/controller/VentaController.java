package com.example.Prueba_Tecnica_Spring_Boot.controller;

import com.example.Prueba_Tecnica_Spring_Boot.dto.VentaCreateDto;
import com.example.Prueba_Tecnica_Spring_Boot.dto.VentaResponseDto;
import com.example.Prueba_Tecnica_Spring_Boot.model.Venta;
import com.example.Prueba_Tecnica_Spring_Boot.service.VentaMapper;
import com.example.Prueba_Tecnica_Spring_Boot.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
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

    @PostMapping
    public ResponseEntity<VentaResponseDto> crear(@Valid @RequestBody VentaCreateDto request) {
        Venta venta = ventaMapper.toEntity(request);
        Venta creada = ventaService.registrarVenta(venta);
        return ResponseEntity
                .created(URI.create("/api/ventas/" + creada.getId()))
                .body(ventaMapper.toResponse(creada));
    }

    @GetMapping
    public ResponseEntity<List<VentaResponseDto>> listar(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        var ventas = (fecha == null)
                ? ventaService.listarVentasActivas()
                : ventaService.buscarPorFechaActivas(fecha);

        return ResponseEntity.ok(ventaMapper.toResponseList(ventas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDto> obtenerPorId(@PathVariable Long id) {
        return ventaService.obtenerPorIdActiva(id)
                .map(venta -> ResponseEntity.ok(ventaMapper.toResponse(venta)))
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> anular(@PathVariable Long id) {
        try {
            ventaService.anularVenta(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

