package com.rednova.dao;

import com.rednova.model.Producto;
import com.rednova.util.Conexion;
import java.sql.*;

public class ProductoDAO {

    // Buscar por ID (El método que te causaba el error)
    public Producto buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Producto WHERE idProducto = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Producto(
                    rs.getInt("idProducto"),
                    rs.getString("nombreProducto"),
                    rs.getString("categoria"),
                    rs.getDouble("precioUnitario"),
                    rs.getDouble("costoUnitario"),
                    rs.getInt("stockActual"),
                    rs.getInt("stockMinimo")
                );
            }
        }
        return null;
    }

    public void insertar(Producto p) throws SQLException {
        String sql = "INSERT INTO Producto (nombreProducto, categoria, precioUnitario, costoUnitario, stockActual, stockMinimo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombreProducto());
            ps.setString(2, p.getCategoria());
            ps.setDouble(3, p.getPrecioUnitario());
            ps.setDouble(4, p.getCostoUnitario());
            ps.setInt(5, p.getStockActual());
            ps.setInt(6, p.getStockMinimo());
            ps.executeUpdate();
        }
    }

    public void actualizar(Producto p) throws SQLException {
        String sql = "UPDATE Producto SET nombreProducto = ?, precioUnitario = ?, stockActual = ? WHERE idProducto = ?";
        try (Connection conn = Conexion.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombreProducto());
            ps.setDouble(2, p.getPrecioUnitario());
            ps.setInt(3, p.getStockActual());
            ps.setInt(4, p.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Producto WHERE idProducto = ?";
        try (Connection conn = Conexion.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}