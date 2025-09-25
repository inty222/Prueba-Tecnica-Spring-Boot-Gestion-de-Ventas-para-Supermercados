package com.example.Prueba_Tecnica_Spring_Boot.service;

import com.example.Prueba_Tecnica_Spring_Boot.dto.*;
import com.example.Prueba_Tecnica_Spring_Boot.model.Venta;
import com.example.Prueba_Tecnica_Spring_Boot.model.VentaItems;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VentaMapper {

    private final ProductoService productoService;

    public VentaMapper(ProductoService productoService) {
        this.productoService = productoService;
    }

    public Venta toEntity(VentaCreateDto dto) {
        var venta = new Venta();
        venta.setFecha(dto.fecha());
        venta.setAnulada(Boolean.FALSE);

        List<VentaItems> items = new ArrayList<>();
        for (VentaItemCreateDto linea : dto.detalle()) {
            var item = new VentaItems();
            item.setProducto(productoService.getProductoById(linea.productoId()));
            item.setCantidad(linea.cantidad());
            item.setVenta(venta);
            items.add(item);
        }
        venta.setVentaItems(items);
        return venta;
    }

    public VentaResponseDto toResponse(Venta venta) {
        var detalle = new ArrayList<VentaItemResponseDto>();
        if (venta.getVentaItems() != null) {
            for (VentaItems it : venta.getVentaItems()) {
                detalle.add(new VentaItemResponseDto(
                        it.getVenta().getId(),
                        it.getProducto().getNombreProducto(),
                        it.getCantidad(),
                        it.getProducto().getPrecio(),
                        it.getProducto().getPrecio() * it.getCantidad()
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
        return ventas.stream().map(this::toResponse).toList();
    }
}
