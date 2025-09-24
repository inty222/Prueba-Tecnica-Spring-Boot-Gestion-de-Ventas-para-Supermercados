package com.example.Prueba_Tecnica_Spring_Boot.service;

import com.example.Prueba_Tecnica_Spring_Boot.model.Venta;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VentaService {

    //Registra una venta. Si no trae fecha, se pone LocalDate.now()
    // Si los items existen, se enlazan con la venta para respetar la relación bidireccional
    Venta registrarVenta(Venta venta);

    //Devuelve solo ventas NO anuladas
    List<Venta> listarVentasActivas();

    //Devuelve ventas NO anuladas en una fecha concreta

    List<Venta> buscarPorFechaActivas(LocalDate fecha);

    //Busca una venta por id solo si NO está anulada
    Optional<Venta> obtenerPorIdActiva(Long id);

    //Borrado lógico: marca la venta como anulada = true
    void anularVenta(Long id);
}
