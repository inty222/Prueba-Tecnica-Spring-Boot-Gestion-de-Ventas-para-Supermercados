package com.example.Prueba_Tecnica_Spring_Boot.service;

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

    @Override
    public Venta registrarVenta(Venta venta) {
        if (venta == null) {
            throw new IllegalArgumentException("La venta no puede ser null");
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

    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarVentasActivas() {
        return ventaRepository.findByAnuladaFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarPorFechaActivas(LocalDate fecha) {
        if (fecha == null) {
            return ventaRepository.findByAnuladaFalse();
        }
        return ventaRepository.findByFechaAndAnuladaFalse(fecha);
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
    public List<Venta> buscarPorSucursalYFechasActivas(Long sucursalId, LocalDate desde, LocalDate hasta) {
        if (sucursalId == null || desde == null || hasta == null) {
            throw new IllegalArgumentException("sucursalId, desde y hasta son obligatorios");
        }
        return ventaRepository.findBySucursal_IdAndFechaBetweenAndAnuladaFalse(sucursalId, desde, hasta);
    }

    @Override
    @Transactional(readOnly = true)
    public IngresoTotalDto ingresosTotales(Long sucursalId, LocalDate desde, LocalDate hasta) {
        if (sucursalId == null || desde == null || hasta == null) {
            throw new IllegalArgumentException("sucursalId, desde y hasta son obligatorios");
        }
        Double ingresos = ventaRepository.ingresosTotalesBySucursal(sucursalId, desde, hasta);
        return new IngresoTotalDto(sucursalId, null, ingresos != null ? ingresos : 0.0);
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
