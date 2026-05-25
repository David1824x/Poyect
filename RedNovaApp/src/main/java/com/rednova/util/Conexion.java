package com.rednova.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    private static final String DATABASE = "rednova_db";
    // Configuración optimizada para MySQL con codificación y permisos activos
    private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "admin_rednova"; // <-- El usuario nuevo
    private static final String PASSWORD = "david2504"; // <-- Tu contraseña

    public static Connection conectar() {
        Connection cn = null;
        try {
            // Registrar el driver de comunicación
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("¡Conexion exitosa a la base de datos de RedNova!");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontro el driver de MySQL. " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error de conexion: Revisa si el servidor MySQL esta activo o si la contraseña es correcta. " + e.getMessage());
        }
        return cn;
    }
}