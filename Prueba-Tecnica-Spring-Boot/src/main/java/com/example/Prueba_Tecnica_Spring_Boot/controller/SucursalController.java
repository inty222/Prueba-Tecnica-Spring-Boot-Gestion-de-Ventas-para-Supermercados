package com.example.Prueba_Tecnica_Spring_Boot.controller;

import com.example.Prueba_Tecnica_Spring_Boot.dto.SucursalCreateDTO;
import com.example.Prueba_Tecnica_Spring_Boot.dto.SucursalDTO;
import com.example.Prueba_Tecnica_Spring_Boot.service.SucursalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST para manejar operaciones relacionadas con sucursales
@RestController
@RequestMapping("/api/sucursales") // URL base para las solicitudes relacionadas con sucursales
@RequiredArgsConstructor // Genera automáticamente el constructor con argumentos finales (inyección de dependencias)
public class SucursalController {

    private final SucursalService sucursalService;

    // Endpoint para listar todas las sucursales
    @GetMapping
    public ResponseEntity<List<SucursalDTO>> listarSucursales() {
        return ResponseEntity.ok(sucursalService.listarSucursales());
    }

    // Endpoint para obtener una sucursal por su id
    @GetMapping("/{id}")
    public ResponseEntity<SucursalDTO> obtenerSucursal(@PathVariable Long id) {
        return ResponseEntity.ok(sucursalService.obtenerSucursalPorId(id));
    }

    // Endpoint para crear una nueva sucursal
    @PostMapping
    public ResponseEntity<SucursalDTO> crearSucursal(@RequestBody SucursalCreateDTO dto) {
        return ResponseEntity.ok(sucursalService.crearSucursal(dto));
    }

    // Endpoint para actualizar una sucursal existente
    @PutMapping("/{id}")
    public ResponseEntity<SucursalDTO> actualizarSucursal(@PathVariable Long id,
                                                          @RequestBody SucursalCreateDTO dto) {
        return ResponseEntity.ok(sucursalService.actualizarSucursal(id, dto));
    }

    // Endpoint para eliminar una sucursal por su id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable Long id) {
        sucursalService.eliminarSucursal(id);
        return ResponseEntity.noContent().build();
    }
}


