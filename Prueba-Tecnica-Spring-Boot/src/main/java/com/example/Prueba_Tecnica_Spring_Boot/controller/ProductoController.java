package com.example.Prueba_Tecnica_Spring_Boot.controller;

import com.example.Prueba_Tecnica_Spring_Boot.dto.ProductoDto;
import com.example.Prueba_Tecnica_Spring_Boot.dto.VentaItemResponseDto;
import com.example.Prueba_Tecnica_Spring_Boot.model.Producto;
import com.example.Prueba_Tecnica_Spring_Boot.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    /*CRUDs: listar productos, crear productos, filtrar productos por id,
    actualizar productos, eliminar productos */
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDto>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    @PostMapping("/nuevoproducto")
    public ResponseEntity<String> crearProducto(@Valid @RequestBody ProductoDto dto) {
        String respuesta = productoService.saveProducto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PutMapping("/actualizarproducto/{id}")
    public ResponseEntity<String> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody ProductoDto dto
    ) {
        String respuesta = productoService.updateProducto(id, dto);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/eliminarproducto/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long id) {
        String mensaje = productoService.deleteProducto(id);
        return ResponseEntity.ok(mensaje);
    }

    //listar por id x si acaso
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> obtenerProductoPorId(@PathVariable Long id) {
        Producto producto = productoService.getProductoById(id);

        // Convertir la entidad a DTO
        ProductoDto dto = new ProductoDto(
                producto.getId(),
                producto.getNombreProducto(),
                producto.getPrecio(),
                producto.getCategoria(),
                producto.getStock()
        );
        return ResponseEntity.ok(dto);
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
    

