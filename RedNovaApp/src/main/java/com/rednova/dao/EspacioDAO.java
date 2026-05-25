package com.rednova.dao;

import com.rednova.model.Espacio;
import com.rednova.util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspacioDAO {

    // 1. REGISTRAR ESPACIO (C - Create)
    public void registrar(Espacio esp) throws SQLException {
        String sql = "INSERT INTO Espacio (tipoEspacio, capacidadPersonas, estado) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, esp.getTipoEspacio());
            ps.setInt(2, esp.getCapacidadPersonas());
            ps.setString(3, esp.getEstado());
            ps.executeUpdate();
        }
    }

    // 2. BUSCAR TODOS (R - Read)
    public List<Espacio> buscarTodos() throws SQLException {
        List<Espacio> lista = new ArrayList<>();
        String sql = "SELECT * FROM Espacio";
        try (Connection conn = Conexion.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Espacio(
                    rs.getInt("idEspacio"),
                    rs.getString("tipoEspacio"),
                    rs.getInt("capacidadPersonas"),
                    rs.getString("estado")
                ));
            }
        }
        return lista;
    }

    // 3. BUSCAR POR ID (Para asignaciones de reserva o consultas rápidas)
    public Espacio buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Espacio WHERE idEspacio = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Espacio(
                        rs.getInt("idEspacio"),
                        rs.getString("tipoEspacio"),
                        rs.getInt("capacidadPersonas"),
                        rs.getString("estado")
                    );
                }
            }
        }
        return null;
    }

    // 4. ACTUALIZAR (U - Update)
    public void actualizar(Espacio esp) throws SQLException {
        String sql = "UPDATE Espacio SET tipoEspacio = ?, capacidadPersonas = ?, estado = ? WHERE idEspacio = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, esp.getTipoEspacio());
            ps.setInt(2, esp.getCapacidadPersonas());
            ps.setString(3, esp.getEstado());
            ps.setInt(4, esp.getIdEspacio());
            ps.executeUpdate();
        }
    }

    // 5. ELIMINAR (D - Delete)
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Espacio WHERE idEspacio = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}