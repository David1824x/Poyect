package com.rednova.model;

import java.time.LocalDate;

public class Usuario {
    private int id;
    private String numeroControl;
    private String correo;
    private String nombre;
    private String tipoUsuario;
    private LocalDate fechaRegistro;
    private int puntosLealtad;
    private String nivelMembresia;

    //Constructor completo para cuando traes datos de la BD
    public Usuario(int id, String numeroControl, String correo, String nombre, 
                   String tipoUsuario, LocalDate fechaRegistro, int puntosLealtad, String nivelMembresia) {
        this.id = id;
        this.numeroControl = numeroControl;
        this.correo = correo;
        this.nombre = nombre;
        this.tipoUsuario = tipoUsuario;
        this.fechaRegistro = fechaRegistro;
        this.puntosLealtad = puntosLealtad;
        this.nivelMembresia = nivelMembresia;
    }

    //Getters
    public int getId() { return id; }
    public String getNumeroControl() { return numeroControl; }
    public String getCorreoInstitucional() { return correo; }
    public String getNombre() { return nombre; }
    public String getTipoUsuario() { return tipoUsuario; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public int getPuntosLealtad() { return puntosLealtad; }
    public String getNivelMembresia() { return nivelMembresia; }

    //Setters (útiles para actualizar objetos antes de guardarlos)
    public void setPuntosLealtad(int puntosLealtad) { this.puntosLealtad = puntosLealtad; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }
    public void setNivelMembresia(String nivelMembresia) { this.nivelMembresia = nivelMembresia; }
}