package com.rednova.model;

/**
 * Modelo: ReporteVenta
 * Representa la entidad lógica resultante del JOIN múltiple entre 
 * Venta, DetalleVenta y Producto para la consola analítica.
 */
public class ReporteVenta {
    
    // Atributos privados correspondientes a las columnas de la interfaz
    private String nombreProducto;
    private int cantidad;
    private double precioAplicado;
    private String fechaCompleta; // Combinación de Dia/Mes/Anio

    /**
     * Constructor vacío por buenas prácticas y requerimientos de frameworks.
     */
    public ReporteVenta() {
    }

    /**
     * Constructor completo utilizado para instanciar los registros recuperados 
     * desde el ResultSet en el controlador de la vista.
     */
    public ReporteVenta(String nombreProducto, int cantidad, double precioAplicado, String fechaCompleta) {
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioAplicado = precioAplicado;
        this.fechaCompleta = fechaCompleta;
    }

    // =========================================================================
    //          GETTERS Y SETTERS (Obligatorios para JavaFX TableView)
    // =========================================================================

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioAplicado() {
        return precioAplicado;
    }

    public void setPrecioAplicado(double precioAplicado) {
        this.precioAplicado = precioAplicado;
    }

    public String getFechaCompleta() {
        return fechaCompleta;
    }

    public void setFechaCompleta(String fechaCompleta) {
        this.fechaCompleta = fechaCompleta;
    }

    /**
     * Sobrescritura del método toString para facilitar tareas de depuración (debugging) 
     * en la consola de desarrollo.
     */
    @Override
    public String toString() {
        return "ReporteVenta{" +
                "nombreProducto='" + nombreProducto + '\'' +
                ", cantidad=" + cantidad +
                ", precioAplicado=" + precioAplicado +
                ", fechaCompleta='" + fechaCompleta + '\'' +
                '}';
    }
}