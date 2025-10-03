package com.example.Prueba_Tecnica_Spring_Boot.controller;

import com.example.Prueba_Tecnica_Spring_Boot.dto.VentaCreateDto;
import com.example.Prueba_Tecnica_Spring_Boot.dto.VentaResponseDto;
import com.example.Prueba_Tecnica_Spring_Boot.model.Venta;
import com.example.Prueba_Tecnica_Spring_Boot.service.VentaMapper;
import com.example.Prueba_Tecnica_Spring_Boot.service.VentaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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
    public VentaResponseDto registrarVenta(@RequestBody VentaCreateDto dto) {
        Venta venta = ventaMapper.toEntity(dto);
        Venta guardada = ventaService.registrarVenta(venta);
        return ventaMapper.toResponse(guardada);
    }


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
            ventas = ventaService.buscarPorSucursalYFechasActivas(
                    sucursalId,
                    LocalDate.MIN,
                    LocalDate.MAX
            );
        } else if (fecha != null) {
            ventas = ventaService.buscarPorFechaActivas(fecha);
        } else {
            ventas = ventaService.listarVentasActivas();
        }
        return ventas.stream().map(ventaMapper::toResponse).toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void anularVenta(@PathVariable Long id) {
        ventaService.anularVenta(id);
    }

}
