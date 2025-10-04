package com.example.Prueba_Tecnica_Spring_Boot.service;

import com.example.Prueba_Tecnica_Spring_Boot.dto.ProductoDto;
import com.example.Prueba_Tecnica_Spring_Boot.model.Producto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component

public class ProductoMapper {
    public Producto toResponse(ProductoDto dto){
        if (dto == null) {
            return null;
        }
        Producto producto = new Producto(null,
                dto.getNombreProducto(),
                dto.getCategoria(),
                dto.getPrecio(),
                dto.getStock(),
                new ArrayList<>());
        return producto;
    }
    public ProductoDto fromEntity(Producto producto) {
        if (producto == null) {
            return null;
        }
        return new ProductoDto(
                producto.getId(),
                producto.getNombreProducto(),
                producto.getPrecio(),
                producto.getCategoria(),
                producto.getStock()
        );
    }
}
