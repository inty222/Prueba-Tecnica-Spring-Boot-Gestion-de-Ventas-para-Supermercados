package com.example.Prueba_Tecnica_Spring_Boot.dto;

public class IngresoTotalDto {

    private Long sucursalId;
    private String nombreSucursal;
    private Double ingresosNetos;

    public IngresoTotalDto(Long sucursalId, String nombreSucursal, Double ingresosNetos) {
        this.sucursalId = sucursalId;
        this.nombreSucursal = nombreSucursal;
        this.ingresosNetos = ingresosNetos;
    }

    public Long getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Long sucursalId) {
        this.sucursalId = sucursalId;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public Double getIngresosNetos() {
        return ingresosNetos;
    }

    public void setIngresosNetos(Double ingresosNetos) {
        this.ingresosNetos = ingresosNetos;
    }
}