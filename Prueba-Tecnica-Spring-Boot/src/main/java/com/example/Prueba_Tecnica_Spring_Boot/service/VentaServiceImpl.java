package com.example.Prueba_Tecnica_Spring_Boot.service;

import com.example.Prueba_Tecnica_Spring_Boot.dto.IngresoTotalDto;
import com.example.Prueba_Tecnica_Spring_Boot.dto.TopProductoDto;
import com.example.Prueba_Tecnica_Spring_Boot.model.Venta;
import com.example.Prueba_Tecnica_Spring_Boot.model.VentaItems;
import com.example.Prueba_Tecnica_Spring_Boot.repository.VentaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;

    public VentaServiceImpl(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    // Registra una venta, poniendo valores por defecto
    // fijando la relación bidireccional con VentaItems antes de guardar.
    @Override
    public Venta registrarVenta(Venta venta) {
        if (venta == null) {
            throw new IllegalArgumentException("La venta no puede ser null.");
        }
        try {
            if (venta.getFecha() == null) {
                venta.setFecha(LocalDate.now());
            }
        } catch (Exception ignored) {}
        try {
            if (venta.getAnulada() == null) {
                venta.setAnulada(false);
            }
        } catch (Exception ignored) {}

        if (venta.getVentaItems() != null) {
            for (VentaItems it : venta.getVentaItems()) {
                it.setVenta(venta);
            }
        }
        return ventaRepository.save(venta);
    }

    // Lista todas las ventas activas (no anuladas).
    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarVentasActivas() {
        return ventaRepository.findByAnuladaFalse();
    }

    // Busca ventas activas por fecha exacta. Si fecha es null, devuelve todas las activas.
    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarPorFechaActivas(LocalDate fecha) {
        if (fecha == null) {
            return ventaRepository.findByAnuladaFalse();
        }
        return ventaRepository.findByFechaAndAnuladaFalse(fecha);
    }

    // Obtiene una venta activa por id.
    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> obtenerPorIdActiva(Long id) {
        return ventaRepository.findByIdAndAnuladaFalse(id);
    }

    // Anula una venta si existe y no está ya anulada.
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

    // Busca ventas activas por sucursal en un rango [desde, hasta].
    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarPorSucursalYFechasActivas(Long sucursalId, LocalDate desde, LocalDate hasta) {
        if (sucursalId == null || desde == null || hasta == null) {
            throw new IllegalArgumentException("sucursalId, desde y hasta son obligatorios");
        }
        return ventaRepository.findBySucursal_IdAndFechaBetweenAndAnuladaFalse(sucursalId, desde, hasta);
    }

    // Busca ventas activas de una sucursal ordenadas por fecha descendente (ORDER BY en BD).
    // - Valida sucursalId.
    // - Transacción de solo lectura para optimizar.
    // - La BD aplica el ORDER BY mediante el método derivado con OrderByFechaDesc.
    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarPorSucursalActivas(Long sucursalId) {
        if (sucursalId == null) {
            throw new IllegalArgumentException("El parámetro sucursalId no puede ser null");
        }
        return ventaRepository.findBySucursal_IdAndAnuladaFalseOrderByFechaDesc(sucursalId);
    }

    // Calcula los ingresos totales de una sucursal en el rango [desde, hasta].
    @Override
    @Transactional(readOnly = true)
    public IngresoTotalDto ingresosTotales(Long sucursalId, LocalDate desde, LocalDate hasta) {
        if (sucursalId == null || desde == null || hasta == null) {
            throw new IllegalArgumentException("sucursalId, desde y hasta son obligatorios");
        }
        Double ingresos = ventaRepository.ingresosTotalesBySucursal(sucursalId, desde, hasta);
        return new IngresoTotalDto(sucursalId, null, ingresos != null ? ingresos : 0.0);
    }

    // Devuelve el top de productos (cantidad e importe) en el rango indicado, limitado por 'limit'.
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
