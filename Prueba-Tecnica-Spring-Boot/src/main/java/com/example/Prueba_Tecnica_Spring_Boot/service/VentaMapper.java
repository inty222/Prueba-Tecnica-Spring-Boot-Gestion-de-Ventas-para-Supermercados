package com.example.Prueba_Tecnica_Spring_Boot.service;

import com.example.Prueba_Tecnica_Spring_Boot.dto.*;
import com.example.Prueba_Tecnica_Spring_Boot.model.Producto;
import com.example.Prueba_Tecnica_Spring_Boot.model.Venta;
import com.example.Prueba_Tecnica_Spring_Boot.model.VentaItems;
import com.example.Prueba_Tecnica_Spring_Boot.repository.SucursalRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class VentaMapper {

    private final ProductoService productoService;

    // Repositorio para cargar la sucursal a partir del sucursalId del DTO.
    private final SucursalRepository sucursalRepository;

    public VentaMapper(ProductoService productoService,
                       SucursalRepository sucursalRepository) {
        this.productoService = productoService;
        this.sucursalRepository = sucursalRepository;
    }

    public Venta toEntity(VentaCreateDto dto) {
        if (dto == null) return null;

        Venta venta = new Venta();
        try {
            venta.setFecha(dto.fecha() != null ? dto.fecha() : LocalDate.now());
        } catch (Exception ignored) {
        }
        try {
            venta.setAnulada(Boolean.FALSE);
        } catch (Exception ignored) {}

        List<VentaItems> items = new ArrayList<>();
        if (dto.detalle() != null) {
            for (VentaItemCreateDto linea : dto.detalle()) {
                Producto p = productoService.getProductoById(linea.productoId());
                VentaItems it = new VentaItems();
                it.setVenta(venta);
                it.setProducto(p);
                it.setCantidad(linea.cantidad());
                items.add(it);
            }
        }
        try {
            venta.setVentaItems(items);
        } catch (Exception ignored) {

        }
        return venta;
    }

    public VentaResponseDto toResponse(Venta venta) {
        if (venta == null) return null;

        List<VentaItemResponseDto> detalle = new ArrayList<>();
        if (venta.getVentaItems() != null) {
            for (VentaItems it : venta.getVentaItems()) {
                String nombreProducto = it.getProducto() != null ? it.getProducto().getNombreProducto() : null;
                Double precioUnitario = it.getProducto() != null ? it.getProducto().getPrecio() : null;
                Double subtotal = (precioUnitario != null)
                        ? precioUnitario * it.getCantidad()
                        : null;
                detalle.add(new VentaItemResponseDto(
                        venta.getId(),
                        nombreProducto,
                        it.getCantidad(),
                        precioUnitario,
                        subtotal
                ));
            }
        }

        return new VentaResponseDto(
                venta.getId(),
                venta.getFecha(),
                venta.getAnulada(),
                detalle
        );
    }

    public List<VentaResponseDto> toResponseList(List<Venta> ventas) {
        return ventas == null ? List.of() : ventas.stream().map(this::toResponse).toList();
    }
}
