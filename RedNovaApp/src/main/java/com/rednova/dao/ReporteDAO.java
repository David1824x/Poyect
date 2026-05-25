package com.rednova.dao;

import com.rednova.util.Conexion;
import java.sql.*;

public class ReporteDAO {
    public void imprimirReporteVentas() throws SQLException {
        String sql = "SELECT V.idVenta, U.nombre AS Cliente, P.nombreProducto, DV.cantidad, V.total " +
                     "FROM Venta V " +
                     "JOIN Usuario U ON V.idUsuario = U.idUsuario " +
                     "JOIN DetalleVenta DV ON V.idVenta = DV.idVenta " +
                     "JOIN Producto P ON DV.idProducto = P.idProducto";
        
        try (Connection conn = Conexion.conectar(); 
             Statement st = conn.createStatement(); 
             ResultSet rs = st.executeQuery(sql)) {
            
            while(rs.next()) {
                System.out.println("Venta: " + rs.getInt("idVenta") + 
                                   " | Cliente: " + rs.getString("Cliente") + 
                                   " | Producto: " + rs.getString("nombreProducto") +
                                   " | Total: $" + rs.getDouble("total"));
            }
        }
    }
}