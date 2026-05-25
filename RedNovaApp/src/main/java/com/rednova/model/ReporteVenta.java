package com.rednova.model;

public class ReporteVenta {
    private String concepto;     // Producto, Espacio o Equipo
    private String cantidad;     // "3 u." o "2 horas" o "5 horas"
    private double precio;        // Costo calculado o precio unitario
    private String fecha;         // Fecha de emisión o reserva

    public ReporteVenta() {}

    public ReporteVenta(String concepto, String cantidad, double precio, String fecha) {
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.fecha = fecha;
    }

    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }

    public String getCantidad() { return cantidad; }
    public void setCantidad(String cantidad) { this.cantidad = cantidad; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}