package com.example.Prueba_Tecnica_Spring_Boot.service;

import com.example.Prueba_Tecnica_Spring_Boot.dto.ProductoDto;
import com.example.Prueba_Tecnica_Spring_Boot.exception.ProductoNoEncontradoException;
import com.example.Prueba_Tecnica_Spring_Boot.model.Producto;
import com.example.Prueba_Tecnica_Spring_Boot.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    //listar
    public List<ProductoDto> listarProductos() {
        return productoRepository.findAll()
                .stream()
                .map(p -> new ProductoDto(
                        p.getId(),
                        p.getNombreProducto(),
                        p.getPrecio(),
                        p.getCategoria(),
                        p.getStock()))
                .collect(Collectors.toList());
    }
    //crear
    public String saveProducto(ProductoDto productoDto) {
        Producto producto = new Producto(null,
                productoDto.getNombreProducto(),
                productoDto.getCategoria(),
                productoDto.getPrecio(),
                productoDto.getStock(),
                new ArrayList<>());
        productoRepository.save(producto);
        return "Producto registrado";
    }

    //actualizar
    public String updateProducto(Long id, ProductoDto dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto " + id + " no encontrado."));
        producto.setNombreProducto(dto.getNombreProducto());
        producto.setCategoria(dto.getCategoria());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());

        productoRepository.save(producto);
        return "Producto actualizado";
    }

    //eliminar
    public void deleteProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto " + id + " no encontrado."));
        productoRepository.delete(producto);
    }

    //listar por id
    public Producto getProductoById(Long id){
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto " + id + " no encontrado."));;
        return producto;
    }


    public ProductoDto findProductoMasVendido() {
        Producto producto = productoRepository.findProductoMasVendido();
        return new ProductoDto(null,
                producto.getNombreProducto(),
                producto.getPrecio(),
                producto.getCategoria(),
                producto.getStock());
    }
}
