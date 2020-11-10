package com.example.donaciones.Data;

import android.media.Image;

public class Fundacion {
    private String nombre;
    private String tipodonacion;
    //private Image icono;
    //private Donacion[] historialDon;
    private String contacto;
    private double longitud;
    private double latitud;

    public Fundacion(String nombre,String tipodonacion,String contacto,double longitud,double latitud) {
        this.nombre = nombre;
        this.tipodonacion = tipodonacion;
        //this.icono = icono;
        //this.historialDon = historialDon;
        this.contacto = contacto;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipodonacion() {
        return tipodonacion;
    }

    public void setTipodonacion(String tipodonacion) {
        this.tipodonacion = tipodonacion;
    }
    /**
    public Image getIcono() {
        return icono;
    }

    public void setIcono(Image icono) {
        this.icono = icono;
    }

    public Donacion[] getHistorialDon() {
        return historialDon;
    }

    public void setHistorialDon(Donacion[] historialDon) {
        this.historialDon = historialDon;
    }
**/
    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void agregarBaseDeDatos(){

    }
}
