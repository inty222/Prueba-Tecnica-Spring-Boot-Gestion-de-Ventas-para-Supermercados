package com.example.Prueba_Tecnica_Spring_Boot.service;

import com.example.Prueba_Tecnica_Spring_Boot.dto.IngresoTotalDto;
import com.example.Prueba_Tecnica_Spring_Boot.dto.TopProductoDto;
import com.example.Prueba_Tecnica_Spring_Boot.model.Venta;
import com.example.Prueba_Tecnica_Spring_Boot.model.VentaItems;
import com.example.Prueba_Tecnica_Spring_Boot.repository.VentaRepository;
import org.springframework.data.domain.PageRequest; // <- necesario para topProductos
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional // <- escritura por defecto; en lecturas uso readOnly = true.
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;

    public VentaServiceImpl(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    // Registra una venta aplicando defaults y coherencia de relaciones.
    @Override
    public Venta registrarVenta(Venta venta) {
        if (venta == null) {
            throw new IllegalArgumentException("La venta no puede ser null");
        }
        if (venta.getSucursal() == null) {
            throw new IllegalArgumentException("La venta debe tener una sucursal");
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
                    item.setVenta(venta); // asegura relación bidireccional
                }
            }
        }
        return ventaRepository.save(venta);
    }

    // Lista todas las ventas activas.
    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarVentasActivas() {
        return ventaRepository.findByAnuladaFalse();
    }

    // Lista ventas activas por fecha exacta.
    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarPorFechaActivas(LocalDate fecha) {
        Objects.requireNonNull(fecha, "La fecha no puede ser null");
        return ventaRepository.findByFechaAndAnuladaFalse(fecha);
    }

    // Lista ventas activas por sucursal en rango [desde, hasta].
    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarPorSucursalYFechasActivas(Long sucursalId, LocalDate desde, LocalDate hasta) {
        if (sucursalId == null || desde == null || hasta == null) {
            throw new IllegalArgumentException("sucursalId, desde y hasta son obligatorios");
        }
        return ventaRepository.findBySucursal_IdAndFechaBetweenAndAnuladaFalse(sucursalId, desde, hasta);
    }

    // Lista ventas activas por sucursal sin imponer rangos de fechas (MySQL-friendly).
    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarPorSucursalActivas(Long sucursalId) {
        if (sucursalId == null) {
            throw new IllegalArgumentException("El parámetro sucursalId no puede ser null");
        }
        return ventaRepository.findBySucursal_IdAndAnuladaFalseOrderByFechaDesc(sucursalId);
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
            return; // ya estaba anulada
        }
        v.setAnulada(true);
        ventaRepository.save(v);
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
