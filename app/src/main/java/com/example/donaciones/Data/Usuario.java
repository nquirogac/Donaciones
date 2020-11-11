package com.example.donaciones.Data;

public class Usuario {
    private String nombre;
    private String email;
    private String password;
    private Donacion[] historialDon;

    public Usuario(String nombre,String email,String password, Donacion[] historialDon) {
        this.nombre = nombre;
        this.email = email;
        this.password =password;
        //this.historialDon = historialDon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Donacion[] getHistorialDon() {
        return historialDon;
    }

    public void setHistorialDon(Donacion[] historialDon) {
        this.historialDon = historialDon;
    }
}
