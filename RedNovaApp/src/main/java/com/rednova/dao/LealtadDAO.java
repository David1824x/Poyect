package com.rednova.dao;

import com.rednova.util.Conexion;
import java.sql.*;

public class LealtadDAO {

    //Método para realizar un canje (Transaccional)
    public boolean realizarCanje(int idUsuario, int puntosAUsar, double descuento) throws SQLException {
        Connection conn = Conexion.conectar();
        try {
            conn.setAutoCommit(false);// Iniciar transacción

            //1. Insertar el registro en la tabla BeneficioLealtad
            String sqlInsert = "INSERT INTO BeneficioLealtad (puntosUtilizados, descuentoAplicado, Dia, Mes, Anio, idUsuario) VALUES (?, ?, DAY(NOW()), MONTH(NOW()), YEAR(NOW()), ?)";
            PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
            psInsert.setInt(1, puntosAUsar);
            psInsert.setDouble(2, descuento);
            psInsert.setInt(3, idUsuario);
            psInsert.executeUpdate();

            //2. Restar los puntos al Usuario
            String sqlUpdate = "UPDATE Usuario SET puntosLealtad = puntosLealtad - ? WHERE idUsuario = ? AND puntosLealtad >= ?";
            PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
            psUpdate.setInt(1, puntosAUsar);
            psUpdate.setInt(2, idUsuario);
            psUpdate.setInt(3, puntosAUsar); //Validamos que tenga puntos suficientes
            
            int filasActualizadas = psUpdate.executeUpdate();
            
            if (filasActualizadas > 0) {
                conn.commit(); //Todo bien, guardamos cambios
                return true;
            } else {
                conn.rollback(); //Puntos insuficientes, cancelamos
                return false;
            }
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }
}