package com.rednova.dao;

import com.rednova.model.EquipoTecnologico;
import com.rednova.util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    // 1. REGISTRAR EQUIPO (C - Create)
    public void insertar(EquipoTecnologico eq) throws SQLException {
        String sql = "INSERT INTO EquipoTecnologico (tipoEquipo, especificaciones, tarifaPorHora, estado, fechaAdquisicion) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, eq.getTipoEquipo());
            ps.setString(2, eq.getEspecificaciones());
            ps.setDouble(3, eq.getTarifaPorHora());
            ps.setString(4, eq.getEstado());
            ps.setDate(5, Date.valueOf(eq.getFechaAdquisicion())); // Convierte LocalDate a java.sql.Date
            ps.executeUpdate();
        }
    }

    // 2. BUSCAR TODOS (R - Read)
    public List<EquipoTecnologico> buscarTodos() throws SQLException {
        List<EquipoTecnologico> lista = new ArrayList<>();
        String sql = "SELECT * FROM EquipoTecnologico";
        try (Connection conn = Conexion.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new EquipoTecnologico(
                    rs.getInt("idEquipo"),
                    rs.getString("tipoEquipo"),
                    rs.getString("especificaciones"),
                    rs.getDouble("tarifaPorHora"),
                    rs.getString("estado"),
                    rs.getDate("fechaAdquisicion").toLocalDate()
                ));
            }
        }
        return lista;
    }

    // 3. BUSCAR POR ID (Para monitoreo o edición individual)
    public EquipoTecnologico buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM EquipoTecnologico WHERE idEquipo = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new EquipoTecnologico(
                    rs.getInt("idEquipo"),
                    rs.getString("tipoEquipo"),
                    rs.getString("especificaciones"),
                    rs.getDouble("tarifaPorHora"),
                    rs.getString("estado"),
                    rs.getDate("fechaAdquisicion").toLocalDate()
                );
            }
        }
        return null;
    }

    // 4. ACTUALIZAR (U - Update)
    public void actualizar(EquipoTecnologico eq) throws SQLException {
        String sql = "UPDATE EquipoTecnologico SET tipoEquipo = ?, especificaciones = ?, tarifaPorHora = ?, estado = ?, fechaAdquisicion = ? WHERE idEquipo = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, eq.getTipoEquipo());
            ps.setString(2, eq.getEspecificaciones());
            ps.setDouble(3, eq.getTarifaPorHora());
            ps.setString(4, eq.getEstado());
            ps.setDate(5, Date.valueOf(eq.getFechaAdquisicion()));
            ps.setInt(6, eq.getIdEquipo());
            ps.executeUpdate();
        }
    }

    // 5. ELIMINAR (D - Delete)
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM EquipoTecnologico WHERE idEquipo = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}