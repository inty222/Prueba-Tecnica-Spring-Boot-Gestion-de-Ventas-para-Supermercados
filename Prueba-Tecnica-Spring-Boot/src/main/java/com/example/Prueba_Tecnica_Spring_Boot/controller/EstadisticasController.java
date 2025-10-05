package com.example.Prueba_Tecnica_Spring_Boot.controller;

import com.example.Prueba_Tecnica_Spring_Boot.dto.ProductoDto;
import com.example.Prueba_Tecnica_Spring_Boot.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {
    @Autowired
    private ProductoService productoService;

    @GetMapping("/producto-mas-vendido")
    public ResponseEntity<ProductoDto> productoMasVendido() {
        ProductoDto productoDto = productoService.findProductoMasVendido();
        if (productoDto != null) {
            return ResponseEntity.ok(productoDto);
        }
        return ResponseEntity.notFound().build();
    }
}
