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

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    public ResponseEntity<VentaResponseDto> crear(@Valid @RequestBody VentaCreateDto request) {
        Venta venta = VentaMapper.toEntity(request);
        Venta creada = ventaService.registrarVenta(venta);
        return ResponseEntity
                .created(URI.create("/api/ventas/" + creada.getId()))
                .body(VentaMapper.toResponse(creada));
    }

    @GetMapping
    public ResponseEntity<List<VentaResponseDto>> listar(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        var ventas = (fecha == null)
                ? ventaService.listarVentasActivas()
                : ventaService.buscarPorFechaActivas(fecha);

        return ResponseEntity.ok(VentaMapper.toResponseList(ventas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDto> obtenerPorId(@PathVariable Long id) {
        return ventaService.obtenerPorIdActiva(id)
                .map(v -> ResponseEntity.ok(VentaMapper.toResponse(v)))
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
