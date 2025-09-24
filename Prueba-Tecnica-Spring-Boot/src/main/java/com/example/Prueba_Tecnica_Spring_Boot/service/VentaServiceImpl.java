package com.example.Prueba_Tecnica_Spring_Boot.service;

import com.example.Prueba_Tecnica_Spring_Boot.model.Venta;
import com.example.Prueba_Tecnica_Spring_Boot.model.VentaItems;
import com.example.Prueba_Tecnica_Spring_Boot.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional // Por defecto transaccional para métodos de escritura
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
        // Defaults seguros
        if (venta.getFecha() == null) {
            venta.setFecha(LocalDate.now());
        }
        if (venta.getAnulada() == null) {
            venta.setAnulada(false);
        }
        // Mantener la relación bidireccional: cada item conoce su venta
        if (venta.getVentaItems() != null) {
            for (VentaItems item : venta.getVentaItems()) {
                item.setVenta(venta);
            }
        }
        return ventaRepository.save(venta); // cascade = ALL hará el resto
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
            // si no pasan fecha, devolvemos todas las activas
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
            return; // ya está anulada (idempotente)
        }
        v.setAnulada(true);
        ventaRepository.save(v); // borrado lógico
    }
}
