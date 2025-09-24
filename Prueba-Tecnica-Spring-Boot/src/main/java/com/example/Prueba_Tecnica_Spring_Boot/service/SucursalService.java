package com.example.Prueba_Tecnica_Spring_Boot.service;

import com.example.Prueba_Tecnica_Spring_Boot.dto.SucursalCreateDTO;
import com.example.Prueba_Tecnica_Spring_Boot.dto.SucursalDTO;
import com.example.Prueba_Tecnica_Spring_Boot.exception.SucursalNotFoundException;
import com.example.Prueba_Tecnica_Spring_Boot.model.Sucursal;
import com.example.Prueba_Tecnica_Spring_Boot.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SucursalService {

    private final SucursalRepository sucursalRepository;

    private SucursalDTO mapToDTO(Sucursal sucursal) {
        SucursalDTO dto = new SucursalDTO();
        dto.setId(sucursal.getId());
        dto.setNombre(sucursal.getNombre());
        dto.setDireccion(sucursal.getDireccion());
        return dto;
    }

    public List<SucursalDTO> listarSucursales() {
        return sucursalRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public SucursalDTO obtenerSucursalPorId(Long id) {
        return sucursalRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new SucursalNotFoundException(id));
    }

    public SucursalDTO crearSucursal(SucursalCreateDTO dto) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(dto.getNombre());
        sucursal.setDireccion(dto.getDireccion());
        return mapToDTO(sucursalRepository.save(sucursal));
    }

    public SucursalDTO actualizarSucursal(Long id, SucursalCreateDTO dto) {
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new SucursalNotFoundException(id));
        sucursal.setNombre(dto.getNombre());
        sucursal.setDireccion(dto.getDireccion());
        return mapToDTO(sucursalRepository.save(sucursal));
    }

    public void eliminarSucursal(Long id) {
        if (!sucursalRepository.existsById(id)) {
            throw new SucursalNotFoundException(id);
        }
        sucursalRepository.deleteById(id);
    }
}
