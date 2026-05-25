package com.rednova.dao;

import com.rednova.model.ReservaEquipo;
import com.rednova.util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaEquipoDAO {

    // REGISTRAR RENTA
    public void registrar(ReservaEquipo r) throws SQLException {

        String sql = "INSERT INTO reserva_equipo " +
                     "(idUsuario, idEquipo, cantidadHoras, precioTotal, fechaReserva) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getIdUsuario());
            ps.setInt(2, r.getIdEquipo());
            ps.setInt(3, r.getCantidadHoras());
            ps.setDouble(4, r.getPrecioTotal());
            ps.setDate(5, r.getFechaReserva());

            ps.executeUpdate();
        }
    }

    // BUSCAR TODAS LAS RENTAS
    public List<ReservaEquipo> buscarTodos() throws SQLException {

        List<ReservaEquipo> lista = new ArrayList<>();

        String sql = "SELECT * FROM reserva_equipo";

        try (Connection conn = Conexion.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                ReservaEquipo r = new ReservaEquipo();

                r.setIdReservaEquipo(rs.getInt("idReservaEquipo"));
                r.setIdUsuario(rs.getInt("idUsuario"));
                r.setIdEquipo(rs.getInt("idEquipo"));
                r.setCantidadHoras(rs.getInt("cantidadHoras"));
                r.setPrecioTotal(rs.getDouble("precioTotal"));
                r.setFechaReserva(rs.getDate("fechaReserva"));

                lista.add(r);
            }
        }

        return lista;
    }

    // CONSULTA MÚLTIPLE JOIN
    public void imprimirReporteRentas() throws SQLException {

        String sql =
            "SELECT R.idReservaEquipo, U.nombre, E.especificaciones, " +
            "R.cantidadHoras, R.precioTotal, R.fechaReserva " +
            "FROM reserva_equipo R " +
            "JOIN usuario U ON R.idUsuario = U.idUsuario " +
            "JOIN equipotecnologico E ON R.idEquipo = E.idEquipo";

        try (Connection conn = Conexion.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while(rs.next()) {

                System.out.println(
                    "Renta: " + rs.getInt("idReservaEquipo") +
                    " | Cliente: " + rs.getString("nombre") +
                    " | Equipo: " + rs.getString("especificaciones") +
                    " | Horas: " + rs.getInt("cantidadHoras") +
                    " | Total: $" + rs.getDouble("precioTotal") +
                    " | Fecha: " + rs.getDate("fechaReserva")
                );
            }
        }
    }
}