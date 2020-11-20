package com.example.donaciones.Data;

import java.util.Date;

public class Donacion {
    private String tipoDonacion;
    private String fecha;
    private String especificaciones;
    private String transporte;
    private String estado;
    private Fundacion fundacion;

    public Donacion(String tipoDonacion, String fecha, String especificaciones, String transporte, String estado, Fundacion fundacion) {
        this.tipoDonacion = tipoDonacion;
        this.fecha = fecha;
        this.especificaciones = especificaciones;
        this.transporte = transporte;
        this.estado = estado;
        this.fundacion = fundacion;
    }

    public Fundacion getFundacion() {
        return fundacion;
    }

    public void setFundacion(Fundacion fundacion) {
        this.fundacion = fundacion;
    }

    public String getTipoDonacion() {
        return tipoDonacion;
    }

    public void setTipoDonacion(String tipoDonacion) {
        this.tipoDonacion = tipoDonacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEspecificaciones() {
        return especificaciones;
    }

    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }

    public String getTransporte() {
        return transporte;
    }

    public void setTransporte(String transporte) {
        this.transporte = transporte;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
