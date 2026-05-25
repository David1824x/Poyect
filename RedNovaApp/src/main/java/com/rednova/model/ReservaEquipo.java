package com.rednova.model;

import java.sql.Date;

public class ReservaEquipo {

    private int idReservaEquipo;
    private int idUsuario;
    private int idEquipo;
    private int cantidadHoras;
    private double precioTotal;
    private Date fechaReserva;

    public ReservaEquipo() {}

    public ReservaEquipo(int idUsuario, int idEquipo,
                         int cantidadHoras, double precioTotal,
                         Date fechaReserva) {
        this.idUsuario = idUsuario;
        this.idEquipo = idEquipo;
        this.cantidadHoras = cantidadHoras;
        this.precioTotal = precioTotal;
        this.fechaReserva = fechaReserva;
    }

    public int getIdReservaEquipo() {
        return idReservaEquipo;
    }

    public void setIdReservaEquipo(int idReservaEquipo) {
        this.idReservaEquipo = idReservaEquipo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public int getCantidadHoras() {
        return cantidadHoras;
    }

    public void setCantidadHoras(int cantidadHoras) {
        this.cantidadHoras = cantidadHoras;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
}