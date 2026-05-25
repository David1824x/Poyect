package com.rednova.dao;

import com.rednova.model.Usuario;
import com.rednova.util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // 1. REGISTRAR USUARIO (C - Create)
    public void registrar(Usuario u) throws SQLException {
        String sql = "INSERT INTO Usuario (numeroControl, correoInstitucional, nombre, tipoUsuario, fechaRegistro, puntosLealtad, nivelMembresia) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNumeroControl());
            ps.setString(2, u.getCorreoInstitucional());
            ps.setString(3, u.getNombre());
            ps.setString(4, u.getTipoUsuario());
            ps.setDate(5, Date.valueOf(u.getFechaRegistro())); // Asume que es un LocalDate
            ps.setInt(6, u.getPuntosLealtad());
            ps.setString(7, u.getNivelMembresia());
            ps.executeUpdate();
        }
    }

    // 2. BUSCAR TODOS (R - Read)
    public List<Usuario> buscarTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try (Connection conn = Conexion.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Usuario(
                    rs.getInt("idUsuario"),
                    rs.getString("numeroControl"),
                    rs.getString("correoInstitucional"),
                    rs.getString("nombre"),
                    rs.getString("tipoUsuario"),
                    rs.getDate("fechaRegistro").toLocalDate(),
                    rs.getInt("puntosLealtad"),
                    rs.getString("nivelMembresia")
                ));
            }
        }
        return lista;
    }

    // 3. BUSCAR POR ID (Para editar o consultar puntos)
    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Usuario WHERE idUsuario = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Usuario(
                    rs.getInt("idUsuario"),
                    rs.getString("numeroControl"),
                    rs.getString("correoInstitucional"),
                    rs.getString("nombre"),
                    rs.getString("tipoUsuario"),
                    rs.getDate("fechaRegistro").toLocalDate(),
                    rs.getInt("puntosLealtad"),
                    rs.getString("nivelMembresia")
                );
            }
        }
        return null;
    }

    // 4. ACTUALIZAR (U - Update)
    public void actualizar(Usuario u) throws SQLException {
        String sql = "UPDATE Usuario SET nombre = ?, tipoUsuario = ?, puntosLealtad = ?, nivelMembresia = ? WHERE idUsuario = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getTipoUsuario());
            ps.setInt(3, u.getPuntosLealtad());
            ps.setString(4, u.getNivelMembresia());
            ps.setInt(5, u.getId());
            ps.executeUpdate();
        }
    }

    // 5. ELIMINAR (D - Delete)
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Usuario WHERE idUsuario = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}