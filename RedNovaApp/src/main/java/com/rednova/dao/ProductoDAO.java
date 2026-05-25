package com.rednova.dao;

import com.rednova.model.Producto;
import com.rednova.util.Conexion;
import java.sql.*;
import java.util.ArrayList; //comentario Importacion necesaria para crear la lista de productos
import java.util.List;      //comentario Importacion necesaria para manejar la lista en la interfaz

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

    //comentario Nuevo metodo que recupera todos los productos de la base de datos para mostrarlos en la tabla flotante
    public List<Producto> buscarTodos() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Producto";
        try (Connection conn = Conexion.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getInt("idProducto"),
                    rs.getString("nombreProducto"),
                    rs.getString("categoria"),
                    rs.getDouble("precioUnitario"),
                    rs.getDouble("costoUnitario"),
                    rs.getInt("stockActual"),
                    rs.getInt("stockMinimo")
                );
                lista.add(p);
            }
        }
        return lista;
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
        //comentario Se ajusto el query SQL para incluir categoria, costoUnitario y stockMinimo que faltaban por actualizar
        String sql = "UPDATE Producto SET nombreProducto = ?, categoria = ?, precioUnitario = ?, costoUnitario = ?, stockActual = ?, stockMinimo = ? WHERE idProducto = ?";
        try (Connection conn = Conexion.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombreProducto());
            ps.setString(2, p.getCategoria());
            ps.setDouble(3, p.getPrecioUnitario());
            ps.setDouble(4, p.getCostoUnitario());
            ps.setInt(5, p.getStockActual());
            ps.setInt(6, p.getStockMinimo());
            ps.setInt(7, p.getIdProducto());
            ps.executeUpdate();
        }
    }
    
    public void actualizarStock(int idProducto, int nuevoStock) throws SQLException {
    String sql = "UPDATE Producto SET stockActual = ? WHERE idProducto = ?";
    try (Connection conn = Conexion.conectar(); 
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, nuevoStock);
        ps.setInt(2, idProducto);
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