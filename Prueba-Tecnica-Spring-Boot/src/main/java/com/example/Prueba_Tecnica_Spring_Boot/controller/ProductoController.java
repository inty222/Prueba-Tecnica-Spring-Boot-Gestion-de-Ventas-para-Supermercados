package com.example.Prueba_Tecnica_Spring_Boot.controller;

import com.example.Prueba_Tecnica_Spring_Boot.dto.ProductoDto;
import com.example.Prueba_Tecnica_Spring_Boot.dto.VentaItemResponseDto;
import com.example.Prueba_Tecnica_Spring_Boot.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {


    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDto>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    @PostMapping("/nuevoproducto")
    public ResponseEntity<String> crearProducto(@RequestBody ProductoDto dto) {
        String respuesta = productoService.saveProducto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PutMapping("/actualizarproducto/{id}")
    public ResponseEntity<String> actualizarProducto(
            @PathVariable Long id,
            @RequestBody ProductoDto dto
    ) {
        String respuesta = productoService.updateProducto(id, dto);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/eliminarproducto/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/productomasvendido")
    public ResponseEntity<ProductoDto> productoMasVendido() {
        ProductoDto productoDto = productoService.findProductoMasVendido();
        if (productoDto != null){
            return ResponseEntity.ok(productoDto);
        }
        return ResponseEntity.notFound().build();
    }
}
    

