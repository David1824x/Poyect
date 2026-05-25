package com.rednova.model;

public class Espacio {
    
    private int idEspacio;
    private String tipoEspacio;      // Cubículo Individual, Sala Grupal, Área Lounge, etc.
    private int capacidadPersonas;   // Cantidad máxima de usuarios admitidos
    private String estado;           // Disponible, Ocupado, Mantenimiento

    // Constructor vacío por buenas prácticas
    public Espacio() {
    }

    // Constructor completo para mapeo del DAO
    public Espacio(int idEspacio, String tipoEspacio, int capacidadPersonas, String estado) {
        this.idEspacio = idEspacio;
        this.tipoEspacio = tipoEspacio;
        this.capacidadPersonas = capacidadPersonas;
        this.estado = estado;
    }

    // ==========================================
    //          GETTERS Y SETTERS
    // ==========================================

    public int getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(int idEspacio) {
        this.idEspacio = idEspacio;
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

    @Override
    public String toString() {
        return "Espacio{" +
                "idEspacio=" + idEspacio +
                ", tipoEspacio='" + tipoEspacio + '\'' +
                ", capacidadPersonas=" + capacidadPersonas +
                ", estado='" + estado + '\'' +
                '}';
    }
}