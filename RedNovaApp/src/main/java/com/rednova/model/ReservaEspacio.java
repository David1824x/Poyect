package com.rednova.model;

import java.sql.Date;

public class ReservaEspacio {

    private int idReservaEspacio;
    private int idUsuario;
    private int idEspacio;
    private int cantidadHoras;
    private double precioTotal;
    private Date fechaReserva;

    public ReservaEspacio() {}

    public ReservaEspacio(int idUsuario, int idEspacio,
                           int cantidadHoras, double precioTotal,
                           Date fechaReserva) {
        this.idUsuario = idUsuario;
        this.idEspacio = idEspacio;
        this.cantidadHoras = cantidadHoras;
        this.precioTotal = precioTotal;
        this.fechaReserva = fechaReserva;
    }

    public int getIdReservaEspacio() {
        return idReservaEspacio;
    }

    public void setIdReservaEspacio(int idReservaEspacio) {
        this.idReservaEspacio = idReservaEspacio;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(int idEspacio) {
        this.idEspacio = idEspacio;
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