package com.rednova.dao;

import com.rednova.model.ReservaEspacio;
import com.rednova.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaEspacioDAO {

    // REGISTRAR RESERVA
    public void registrar(ReservaEspacio r) throws SQLException {

        String sql = "INSERT INTO reserva_espacio " +
                     "(idUsuario, idEspacio, cantidadHoras, precioTotal, fechaReserva) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getIdUsuario());
            ps.setInt(2, r.getIdEspacio());
            ps.setInt(3, r.getCantidadHoras());
            ps.setDouble(4, r.getPrecioTotal());
            ps.setDate(5, r.getFechaReserva());

            ps.executeUpdate();
        }
    }

    // BUSCAR TODAS LAS RESERVAS
    public List<ReservaEspacio> buscarTodos() throws SQLException {

        List<ReservaEspacio> lista = new ArrayList<>();

        String sql = "SELECT * FROM reserva_espacio";

        try (Connection conn = Conexion.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                ReservaEspacio r = new ReservaEspacio();

                r.setIdReservaEspacio(rs.getInt("idReservaEspacio"));
                r.setIdUsuario(rs.getInt("idUsuario"));
                r.setIdEspacio(rs.getInt("idEspacio"));
                r.setCantidadHoras(rs.getInt("cantidadHoras"));
                r.setPrecioTotal(rs.getDouble("precioTotal"));
                r.setFechaReserva(rs.getDate("fechaReserva"));

                lista.add(r);
            }
        }

        return lista;
    }

    // CONSULTA MÚLTIPLE JOIN
    public void imprimirReporteReservas() throws SQLException {

        String sql =
            "SELECT RE.idReservaEspacio, U.nombre, E.tipoEspacio, " +
            "RE.cantidadHoras, RE.precioTotal, RE.fechaReserva " +
            "FROM reserva_espacio RE " +
            "JOIN usuario U ON RE.idUsuario = U.idUsuario " +
            "JOIN espacio E ON RE.idEspacio = E.idEspacio";

        try (Connection conn = Conexion.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()) {

                System.out.println(
                    "Reserva: " + rs.getInt("idReservaEspacio") +
                    " | Cliente: " + rs.getString("nombre") +
                    " | Espacio: " + rs.getString("tipoEspacio") +
                    " | Horas: " + rs.getInt("cantidadHoras") +
                    " | Total: $" + rs.getDouble("precioTotal") +
                    " | Fecha: " + rs.getDate("fechaReserva")
                );
            }
        }
    }
}