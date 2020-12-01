package com.example.donaciones.Data;

import java.util.Date;

public class Donacion {

    private String detalle;
    private String donacion;
    private String fecha;
    private Fundacion fundacion;
    private String nombre;
    private String opTransporte;

    public Donacion() {

    }


    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getDonacion() {
        return donacion;
    }

    public void setDonacion(String donacion) {
        this.donacion = donacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Fundacion getFundacion() {
        return fundacion;
    }

    public void setFundacion(Fundacion fundacion) {
        this.fundacion = fundacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOpTransporte() {
        return opTransporte;
    }

    public void setOpTransporte(String opTransporte) {
        this.opTransporte = opTransporte;
    }
}
