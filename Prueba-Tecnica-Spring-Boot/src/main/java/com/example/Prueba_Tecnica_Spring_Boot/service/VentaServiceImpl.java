package com.example.Prueba_Tecnica_Spring_Boot.service;

import com.example.Prueba_Tecnica_Spring_Boot.dto.IngresoTotalDto;
import com.example.Prueba_Tecnica_Spring_Boot.dto.TopProductoDto;
import com.example.Prueba_Tecnica_Spring_Boot.exception.StockInsuficienteException;
import com.example.Prueba_Tecnica_Spring_Boot.model.Producto;
import com.example.Prueba_Tecnica_Spring_Boot.model.Sucursal;
import com.example.Prueba_Tecnica_Spring_Boot.model.Venta;
import com.example.Prueba_Tecnica_Spring_Boot.model.VentaItems;
import com.example.Prueba_Tecnica_Spring_Boot.repository.VentaRepository;
import com.example.Prueba_Tecnica_Spring_Boot.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoMapper productoMapper;

    @Autowired
    private SucursalRepository sucursalRepository;


    @Override
    public Venta registrarVenta(Venta venta) {
        if (venta == null) {
            throw new IllegalArgumentException("La venta no puede ser null.");
        }
        if (venta.getSucursal() == null) {
            throw new IllegalArgumentException("La venta debe tener una sucursal.");
        }
        if (venta.getFecha() == null) {
            venta.setFecha(LocalDate.now());
        }
        if (venta.getAnulada() == null) {
            venta.setAnulada(Boolean.FALSE);
        }
        if (venta.getVentaItems() != null) {
            for (VentaItems item : venta.getVentaItems()) {
                if (item != null) {
                    Producto producto = item.getProducto();
                    if(item.getCantidad()<= producto.getStock()){
                        item.setVenta(venta);
                        producto.setStock(producto.getStock() - item.getCantidad());
                        productoService.updateProducto(producto.getId(), productoMapper.fromEntity(producto));
                    }else{
                        throw new StockInsuficienteException("El producto " + producto.getNombreProducto() + " no tiene stock suficente.");
                    }
                }
            }
        }
        return ventaRepository.save(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarVentasActivas() {
        return ventaRepository.findByAnuladaFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarPorFechaActivas(LocalDate fecha) {
        Objects.requireNonNull(fecha, "La fecha no puede ser null");
        return ventaRepository.findByFechaAndAnuladaFalse(fecha);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarPorSucursalYFechasActivas(Long sucursalId, LocalDate desde, LocalDate hasta) {
        if (sucursalId == null || desde == null || hasta == null) {
            throw new IllegalArgumentException("sucursalId, desde y hasta son obligatorios");
        }
        return ventaRepository.findBySucursal_IdAndFechaBetweenAndAnuladaFalse(sucursalId, desde, hasta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarPorSucursalActivas(Long sucursalId) {
        if (sucursalId == null) {
            throw new IllegalArgumentException("El parámetro sucursalId no puede ser null");
        }
        return ventaRepository.findBySucursal_IdAndAnuladaFalseOrderByFechaDesc(sucursalId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> obtenerPorIdActiva(Long id) {
        return ventaRepository.findByIdAndAnuladaFalse(id);
    }

    @Override
    public void anularVenta(Long id) {
        Venta v = ventaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Venta no encontrada: " + id));
        if (Boolean.TRUE.equals(v.getAnulada())) {
            return;
        }
        v.setAnulada(true);
        ventaRepository.save(v);
    }

    @Override
    @Transactional(readOnly = true)
    public IngresoTotalDto ingresosTotales(Long sucursalId, LocalDate desde, LocalDate hasta) {

        if (sucursalId == null || desde == null || hasta == null) {
            throw new IllegalArgumentException("sucursalId, desde y hasta son obligatorios para la consulta de ingresos totales.");
        }

        String nombreSucursal = sucursalRepository.findById(sucursalId)
                .map(Sucursal::getNombre)
                .orElse("Sucursal Desconocida");

        Double ingresos = ventaRepository.ingresosTotalesBySucursal(sucursalId, desde, hasta);

        if (ingresos == null || ingresos.doubleValue() == 0.0) {
            throw new NoSuchElementException("La sucursal '" + nombreSucursal + "' (ID: " + sucursalId +
                    ") no registra ventas activas en el período seleccionado.");
        }

        return new IngresoTotalDto(
                sucursalId,
                nombreSucursal,
                ingresos
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<TopProductoDto> topProductos(LocalDate desde, LocalDate hasta, int limit) {
        if (desde == null || hasta == null) {
            throw new IllegalArgumentException("desde y hasta son obligatorios");
        }
        int n = Math.max(1, limit);
        return ventaRepository.topProductos(desde, hasta, PageRequest.of(0, n));
    }
}