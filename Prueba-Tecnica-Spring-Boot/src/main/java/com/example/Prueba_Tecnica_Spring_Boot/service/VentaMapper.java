package com.example.Prueba_Tecnica_Spring_Boot.service;

import com.example.Prueba_Tecnica_Spring_Boot.dto.*;
import com.example.Prueba_Tecnica_Spring_Boot.model.Producto;
import com.example.Prueba_Tecnica_Spring_Boot.model.Sucursal;
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

    public VentaMapper(ProductoService productoService, SucursalRepository sucursalRepository) {
        this.productoService = productoService;
        this.sucursalRepository = sucursalRepository;
    }

    public Venta toEntity(VentaCreateDto dto) {
        if (dto == null) return null;

        Venta venta = new Venta();
        venta.setFecha(dto.fecha() != null ? dto.fecha() : LocalDate.now());
        venta.setAnulada(Boolean.FALSE);

        // Cargar y asignar la sucursal (requiere que VentaCreateDto tenga Long sucursalId).
        // Usamos getReferenceById para evitar un SELECT si no hace falta el estado completo.
        Sucursal sucursal = sucursalRepository.getReferenceById(dto.sucursalId());
        venta.setSucursal(sucursal);

        // Construir líneas (detalle) enlazando bidireccionalmente.
        List<VentaItems> items = new ArrayList<>();
        if (dto.detalle() != null) {
            for (VentaItemCreateDto linea : dto.detalle()) {
                Producto producto = productoService.getProductoById(linea.productoId());

                VentaItems item = new VentaItems();
                item.setVenta(venta);              // relación Bidireccional: hijo -> padre
                item.setProducto(producto);        // FK al producto existente
                item.setCantidad(linea.cantidad()); // cantidad vendida

                items.add(item);                   // añadimos a la colección temporal
            }
        }

        // Asignamos la colección al padre (activará cascade al persistir).
        venta.setVentaItems(items);

        return venta;
    }

    // Convierte una entidad Venta a su DTO de respuesta.
    // - Recorre las líneas y calcula subtotal = precioUnitario * cantidad.
    // - Evita exponer entidades JPA en la API pública.
    public VentaResponseDto toResponse(Venta venta) {
        if (venta == null) return null;

        List<VentaItemResponseDto> detalle = new ArrayList<>();

        if (venta.getVentaItems() != null) {
            for (VentaItems it : venta.getVentaItems()) {
                String nombreProducto = (it.getProducto() != null)
                        ? it.getProducto().getNombreProducto()
                        : null;

                // Precio unitario derivado del producto (ver implementación en VentaItems).
                Double precioUnitario = (it.getProducto() != null)
                        ? it.getProducto().getPrecio()
                        : null;

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

    // Utilidad para listas: convierte una lista de ventas a su lista de DTOs de respuesta.
    public List<VentaResponseDto> toResponseList(List<Venta> ventas) {
        return (ventas == null) ? List.of() : ventas.stream().map(this::toResponse).toList();
    }
}