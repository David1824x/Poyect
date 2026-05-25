package com.rednova.dao;

import com.rednova.util.Conexion;
import java.sql.*;

public class VentaDAO {
    // Registra la venta y sus detalles usando una transacción
    public void registrarVentaCompleta(int idUsuario, double subtotal, double total, String metodoPago, int idProducto, int cantidad, double precio) throws SQLException {
        Connection conn = Conexion.conectar();
        try {
            conn.setAutoCommit(false); // Inicia transacción

            // 1. Insertar Venta
            String sqlVenta = "INSERT INTO Venta (Anio, Mes, Dia, subtotal, total, metodoPago, idUsuario) VALUES (2026, 5, 24, ?, ?, ?, ?)";
            PreparedStatement psVenta = conn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
            psVenta.setDouble(1, subtotal);
            psVenta.setDouble(2, total);
            psVenta.setString(3, metodoPago);
            psVenta.setInt(4, idUsuario);
            psVenta.executeUpdate();

            // Obtener el ID generado
            ResultSet rs = psVenta.getGeneratedKeys();
            int idVenta = 0;
            if (rs.next()) idVenta = rs.getInt(1);

            // 2. Insertar Detalle
            String sqlDetalle = "INSERT INTO DetalleVenta (cantidad, precioAplicado, tipoConcepto, idVenta, idProducto) VALUES (?, ?, 'producto', ?, ?)";
            PreparedStatement psDetalle = conn.prepareStatement(sqlDetalle);
            psDetalle.setInt(1, cantidad);
            psDetalle.setDouble(2, precio);
            psDetalle.setInt(3, idVenta);
            psDetalle.setInt(4, idProducto);
            psDetalle.executeUpdate();

            // 3. Actualizar Puntos de Lealtad (Regla: 1 punto por cada 10 pesos)
            int puntos = (int) (total / 10);
            String sqlPuntos = "UPDATE Usuario SET puntosLealtad = puntosLealtad + ? WHERE idUsuario = ?";
            PreparedStatement psPuntos = conn.prepareStatement(sqlPuntos);
            psPuntos.setInt(1, puntos);
            psPuntos.setInt(2, idUsuario);
            psPuntos.executeUpdate();

            conn.commit(); // Todo salió bien
        } catch (SQLException e) {
            conn.rollback(); // Algo falló, deshacer todo
            throw e;
        } finally {
            conn.close();
        }
    }
}