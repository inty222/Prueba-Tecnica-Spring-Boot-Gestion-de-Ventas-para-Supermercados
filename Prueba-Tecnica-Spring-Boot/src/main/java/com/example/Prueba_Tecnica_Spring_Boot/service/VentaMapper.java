package com.example.Prueba_Tecnica_Spring_Boot.service;

import com.example.Prueba_Tecnica_Spring_Boot.dto.*;
import com.example.Prueba_Tecnica_Spring_Boot.model.Venta;
import com.example.Prueba_Tecnica_Spring_Boot.model.VentaItems;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class VentaMapper {

    private VentaMapper() {}
    @Autowired
    private static ProductoService productoService;

    public static Venta toEntity(VentaCreateDto dto) {
        var venta = new Venta();
        // fecha opcional; si viene null, el Service pondrá LocalDate.now()
        venta.setFecha(dto.fecha());

        // anulada por defecto false (la entidad ya lo tiene a false, por si acaso lo dejamos)
        venta.setAnulada(Boolean.FALSE);

        // Mapear líneas de detalle a la entidad
        List<VentaItems> items = new ArrayList<>();
        for (VentaItemCreateDto linea : dto.detalle()) {
            var item = new VentaItems();
            // Ajusta estas líneas a tu entidad VentaItems
            // Si tu VentaItems tiene un campo productoId
            item.setProducto(productoService.getProductoById(linea.productoId()));
            // Si en vez de productoId usas @ManyToOne Producto, aquí deberías enlazar el Producto.

            item.setCantidad(linea.cantidad());
            item.setVenta(venta);  // mantener la relación bidireccional
            items.add(item);
        }
        venta.setVentaItems(items);
        return venta;
    }

    public static VentaResponseDto toResponse(Venta venta) {
        var detalle = new ArrayList<VentaItemResponseDto>();
        if (venta.getVentaItems() != null) {
            for (VentaItems it : venta.getVentaItems()) {
                // precioUnitario y subtotal: null por ahora (hasta integrar con Producto/precios)
                detalle.add(new VentaItemResponseDto(
                        // Ajusta si tu entidad no tiene productoId sino Producto
                        it.getVenta().getId(),
                        it.getProducto().getNombreProducto(),
                        it.getCantidad(),
                        it.getProducto().getPrecio(),
                        it.getProducto().getPrecio()* it.getCantidad()
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

    public static List<VentaResponseDto> toResponseList(List<Venta> ventas) {
        return ventas.stream().map(VentaMapper::toResponse).toList();
    }
}
