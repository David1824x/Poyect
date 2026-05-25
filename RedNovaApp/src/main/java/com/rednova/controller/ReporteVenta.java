package com.rednova.controller;

// Clase modelo para representar los datos del reporte de ventas en la tabla
public class ReporteVenta {
    
    // Atributos de la clase
    private String nombreProducto;
    private int cantidad;
    private double precioAplicado;
    private String fechaCompleta;

    // Constructor para inicializar los datos del reporte
    public ReporteVenta(String nombreProducto, int cantidad, double precioAplicado, String fechaCompleta) {
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioAplicado = precioAplicado;
        this.fechaCompleta = fechaCompleta;
    }

    // Metodos para acceder a los valores de la clase
    public String getNombreProducto() { return nombreProducto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioAplicado() { return precioAplicado; }
    public String getFechaCompleta() { return fechaCompleta; }
}