package com.rednova.model;

import java.time.LocalDate;

public class EquipoTecnologico {
    
    //Atributos alineados con la base de datos y la interfaz
    private int idEquipo;
    private String tipoEquipo;        //Laptop, Proyector, etc.
    private String especificaciones;  //Marca, RAM, Procesador...
    private double tarifaPorHora;     //Costo de renta
    private String estado;            //Disponible, En Uso, Mantenimiento
    private LocalDate fechaAdquisicion;

    //1. Constructor vacío 
    public EquipoTecnologico() {
    }

    //2. Constructor parametrizado completo 
    public EquipoTecnologico(int idEquipo, String tipoEquipo, String especificaciones, double tarifaPorHora, String estado, LocalDate fechaAdquisicion) {
        this.idEquipo = idEquipo;
        this.tipoEquipo = tipoEquipo;
        this.especificaciones = especificaciones;
        this.tarifaPorHora = tarifaPorHora;
        this.estado = estado;
        this.fechaAdquisicion = fechaAdquisicion;
    }

    //GETTERS Y SETTERS  

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(String tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public String getEspecificaciones() {
        return especificaciones;
    }

    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }

    public double getTarifaPorHora() {
        return tarifaPorHora;
    }

    public void setTarifaPorHora(double tarifaPorHora) {
        this.tarifaPorHora = tarifaPorHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    //MétodO toString auxiliar 
    @Override
    public String toString() {
        return "EquipoTecnologico{" +
                "idEquipo=" + idEquipo +
                ", tipoEquipo='" + tipoEquipo + '\'' +
                ", especificaciones='" + especificaciones + '\'' +
                ", tarifaPorHora=" + tarifaPorHora +
                ", estado='" + estado + '\'' +
                ", fechaAdquisicion=" + fechaAdquisicion +
                '}';
    }
}