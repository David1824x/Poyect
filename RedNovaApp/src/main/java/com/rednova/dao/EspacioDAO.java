package com.rednova.dao;

import com.rednova.model.Espacio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspacioDAO {

    private Connection obtenerConexion() throws SQLException {
        Connection cn = com.rednova.util.Conexion.conectar(); 
        if (cn == null) {
            throw new SQLException("La conexión a la base de datos 'rednova_db' es nula. Revisa tus credenciales.");
        }
        return cn;
    }

    public List<Espacio> buscarTodos() throws Exception {
        List<Espacio> lista = new ArrayList<>();
        String sql = "SELECT idEspacio, tipoEspacio, capacidadPersonas, estado, precioHora FROM espacio";
        
        try (Connection cn = obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Espacio esp = new Espacio(
                    rs.getInt("idEspacio"),
                    rs.getString("tipoEspacio"),
                    rs.getInt("capacidadPersonas"),
                    rs.getString("estado"),
                    rs.getDouble("precioHora") // Extrae el precio de la BD
                );
                lista.add(esp);
            }
        }
        return lista;
    }

    public void registrar(Espacio esp) throws Exception {
        String sql = "INSERT INTO espacio (tipoEspacio, capacidadPersonas, estado, precioHora) VALUES (?, ?, ?, ?)";
        
        try (Connection cn = obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, esp.getTipoEspacio());
            ps.setInt(2, esp.getCapacidadPersonas());
            ps.setString(3, esp.getEstado());
            ps.setDouble(4, esp.getPrecioHora()); // Envía el precio a la BD
            ps.executeUpdate();
        }
    }

    public void actualizar(Espacio esp) throws Exception {
        String sql = "UPDATE espacio SET tipoEspacio = ?, capacidadPersonas = ?, estado = ?, precioHora = ? WHERE idEspacio = ?";
        
        try (Connection cn = obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setString(1, esp.getTipoEspacio());
            ps.setInt(2, esp.getCapacidadPersonas());
            ps.setString(3, esp.getEstado());
            ps.setDouble(4, esp.getPrecioHora());
            ps.setInt(5, esp.getIdEspacio());
            ps.executeUpdate();
        }
    }

    public void eliminar(int idEspacio) throws Exception {
        String sql = "DELETE FROM espacio WHERE idEspacio = ?";
        
        try (Connection cn = obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, idEspacio);
            ps.executeUpdate();
        }
    }
}