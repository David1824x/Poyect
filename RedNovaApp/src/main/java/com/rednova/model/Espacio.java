package com.rednova.model;

public class Espacio {
    
    private int idEspacio;
    private String tipoEspacio;      // Cubículo Individual, Sala Grupal, Área Lounge, etc.
    private int capacidadPersonas;   // Cantidad máxima de usuarios admitidos
    private String estado;           // Disponible, Ocupado, Mantenimiento
    private double precioHora;       // Costo de renta por hora (Añadido)

    // Constructor vacío por buenas prácticas
    public Espacio() {
    }

    // Constructor completo para mapeo del DAO (Actualizado con precioHora)
    public Espacio(int idEspacio, String tipoEspacio, int capacidadPersonas, String estado, double precioHora) {
        this.idEspacio = idEspacio;
        this.tipoEspacio = tipoEspacio;
        this.capacidadPersonas = capacidadPersonas;
        this.estado = estado;
        this.precioHora = precioHora;
    }

    // ==========================================
    //          GETTERS Y SETTERS
    // ==========================================

    public int getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(int idEspacio) {
        idEspacio = idEspacio;
    }

    public String getTipoEspacio() {
        return tipoEspacio;
    }

    public void setTipoEspacio(String tipoEspacio) {
        this.tipoEspacio = tipoEspacio;
    }

    public int getCapacidadPersonas() {
        return capacidadPersonas;
    }

    public void setCapacidadPersonas(int capacidadPersonas) {
        this.capacidadPersonas = capacidadPersonas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(double precioHora) {
        this.precioHora = precioHora;
    }

    // ==========================================
    //          MÉTODO TOSTRING
    // ==========================================

    @Override
    public String toString() {
        return "Espacio{" +
                "idEspacio=" + idEspacio +
                ", tipoEspacio='" + tipoEspacio + '\'' +
                ", capacidadPersonas=" + capacidadPersonas +
                ", estado='" + estado + '\'' +
                ", precioHora=" + precioHora +
                '}';
    }
}