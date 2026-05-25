package com.rednova.controller;

import com.rednova.model.ReporteVenta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.sql.*;

public class VentanaReporte {

    // Paleta de diseño RedNova OS
    private final String COLOR_BG = "#121214";
    private final String COLOR_CARD = "#1A1A1E";
    private final String COLOR_ACCENT = "#C3073F";
    private final String COLOR_TEXT_PRIMARY = "#FFFFFF";
    private final String COLOR_TEXT_MUTED = "#A0A0A5";

    private final String STYLE_TABLE
            = "-fx-background-color: #1A1A1E; -fx-control-inner-background: #1A1A1E; "
            + "-fx-base: #121214; -fx-table-cell-border-color: #27272A; -fx-text-fill: white;";

    // COMPONENTES GLOBALES (Para poder limpiarlos y actualizarlos desde afuera)
    private TableView<ReporteVenta> tablaVentas;
    private TableView<ReporteVenta> tablaEspacios;
    private TableView<ReporteVenta> tablaEquipos;

    // Etiquetas de métricas (KPIs) de auditoría
    private Label lblSubtotalVentas;
    private Label lblSubtotalEspacios;
    private Label lblSubtotalEquipos;
    private Label lblGananciasFinales;

    public void mostrarReporte() {
        Stage escenarioReporte = new Stage();
        escenarioReporte.setTitle("RedNova OS - Tablero Analítico Consolidador");

        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + COLOR_BG + ";");
        mainLayout.setPadding(new Insets(20));

        // --- ENCABEZADO ---
        VBox headerBox = new VBox(2);
        headerBox.setPadding(new Insets(0, 0, 15, 0));
        Label lblTitle = new Label("CONSOLA DE INGRESOS AUDITADOS");
        lblTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        lblTitle.setStyle("-fx-text-fill: " + COLOR_TEXT_PRIMARY + ";");
        Label lblSubtitle = new Label("Desglose departamental asíncrono de cajas e inventarios");
        lblSubtitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 11));
        lblSubtitle.setStyle("-fx-text-fill: " + COLOR_ACCENT + ";");
        headerBox.getChildren().addAll(lblTitle, lblSubtitle);
        mainLayout.setTop(headerBox);

        // --- CUERPO CENTRAL (Asignación a variables globales) ---
        VBox tablasContainer = new VBox(20);
        tablasContainer.setStyle("-fx-background-color: transparent;");

        // 1. SECCIÓN: VENTAS DE PRODUCTOS
        tablaVentas = crearEstructuraTabla("Concepto / Producto");
        lblSubtotalVentas = new Label("Ganancia Ventas: $0.00 MXN");
        lblSubtotalVentas.setStyle("-fx-text-fill: #10B981; -fx-font-weight: bold; -fx-font-size: 12px;");
        VBox boxVentas = construirSeccion("1. CONTROL DE VENTAS EN MOSTRADOR", tablaVentas, lblSubtotalVentas);

        // 2. SECCIÓN: RENTA DE ESPACIOS
        tablaEspacios = crearEstructuraTabla("Espacio Reservado");
        lblSubtotalEspacios = new Label("Ganancia Espacios: $0.00 MXN");
        lblSubtotalEspacios.setStyle("-fx-text-fill: #3B82F6; -fx-font-weight: bold; -fx-font-size: 12px;");
        VBox boxEspacios = construirSeccion("2. RESERVACIONES DE ESPACIOS DE TRABAJO", tablaEspacios, lblSubtotalEspacios);

        // 3. SECCIÓN: RENTA DE EQUIPOS
        tablaEquipos = crearEstructuraTabla("Equipo Informático / Periférico");
        lblSubtotalEquipos = new Label("Ganancia Equipos: $0.00 MXN");
        lblSubtotalEquipos.setStyle("-fx-text-fill: #F59E0B; -fx-font-weight: bold; -fx-font-size: 12px;");
        VBox boxEquipos = construirSeccion("3. ALQUILER DE EQUIPAMIENTO TECNOLÓGICO", tablaEquipos, lblSubtotalEquipos);

        tablasContainer.getChildren().addAll(boxVentas, boxEspacios, boxEquipos);

        // ScrollPane adaptativo
        ScrollPane scrollCuerpo = new ScrollPane(tablasContainer);
        scrollCuerpo.setFitToWidth(true);
        scrollCuerpo.setStyle("-fx-background: #121214; -fx-background-color: transparent; -fx-viewport-background: #121214;");
        scrollCuerpo.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainLayout.setCenter(scrollCuerpo);

        // --- FOOTER CON GANANCIAS FINALES ---
        VBox footerBox = new VBox(15);
        footerBox.setPadding(new Insets(15, 0, 0, 0));

        HBox cardGananciasFinales = new HBox();
        cardGananciasFinales.setPadding(new Insets(15));
        cardGananciasFinales.setAlignment(Pos.CENTER_LEFT);
        cardGananciasFinales.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 6; -fx-border-color: %s; -fx-border-width: 1.5;", COLOR_CARD, COLOR_ACCENT));

        VBox datosFinalesBox = new VBox(4);
        Label lblFinalTitle = new Label("AUDITORÍA DE GANANCIAS FINALES CONSOLIDADAS");
        lblFinalTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        lblFinalTitle.setStyle("-fx-text-fill: " + COLOR_TEXT_MUTED + ";");

        lblGananciasFinales = new Label("$0.00 MXN");
        lblGananciasFinales.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        lblGananciasFinales.setStyle("-fx-text-fill: #FFFFFF;");

        datosFinalesBox.getChildren().addAll(lblFinalTitle, lblGananciasFinales);
        cardGananciasFinales.getChildren().add(datosFinalesBox);

        HBox buttonBar = new HBox();
        buttonBar.setAlignment(Pos.CENTER_RIGHT);

        // BOTÓN ACTUALIZAR MANUAL (Por si acaso el usuario quiere forzar el refresco)
        Button btnActualizar = new Button("Actualizar");
        btnActualizar.setStyle("-fx-background-color: #C3073F; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 8 16; -fx-font-weight: bold; -fx-cursor: hand; -fx-margin-right: 10;");
        btnActualizar.setOnAction(e -> refrescarDatos());

        Button btnCerrar = new Button("Cerrar Auditoría");
        btnCerrar.setStyle("-fx-background-color: #27272A; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 8 24; -fx-font-weight: bold; -fx-cursor: hand;");
        btnCerrar.setOnAction(e -> escenarioReporte.close());

        buttonBar.setSpacing(10);
        buttonBar.getChildren().addAll(btnActualizar, btnCerrar);

        footerBox.getChildren().addAll(cardGananciasFinales, buttonBar);
        mainLayout.setBottom(footerBox);

        // Primera carga inicial al abrir
        refrescarDatos();

        escenarioReporte.setScene(new Scene(mainLayout, 840, 700));
        escenarioReporte.show();
    }

    // MÉTODO PÚBLICO: Cualquier controlador externo con la instancia de esta ventana puede llamarlo
    public void refrescarDatos() {
        procesarTodoElFlujoFinanciero(this.tablaVentas, this.tablaEspacios, this.tablaEquipos);
    }

    private TableView<ReporteVenta> crearEstructuraTabla(String conceptoHeader) {
        TableView<ReporteVenta> tabla = new TableView<>();
        tabla.setStyle(STYLE_TABLE);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla.setPrefHeight(160);

        TableColumn<ReporteVenta, String> colConcepto = new TableColumn<>(conceptoHeader);
        colConcepto.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getConcepto()));

        TableColumn<ReporteVenta, String> colCant = new TableColumn<>("Cant. / Horas");
        colCant.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getCantidad()));
        colCant.setPrefWidth(105);

        TableColumn<ReporteVenta, String> colPrecio = new TableColumn<>("Precio / Costo");
        colPrecio.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.format("$%,.2f MXN", c.getValue().getPrecio())));
        colPrecio.setPrefWidth(130);

        TableColumn<ReporteVenta, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getFecha()));
        colFecha.setPrefWidth(120);

        tabla.getColumns().addAll(colConcepto, colCant, colPrecio, colFecha);
        return tabla;
    }

    private VBox construirSeccion(String tituloSeccion, TableView<ReporteVenta> tabla, Label lblSubtotal) {
        VBox sectionBox = new VBox(6);
        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label(tituloSeccion);
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        title.setStyle("-fx-text-fill: " + COLOR_TEXT_MUTED + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topRow.getChildren().addAll(title, spacer, lblSubtotal);
        sectionBox.getChildren().addAll(topRow, tabla);
        return sectionBox;
    }

    private boolean tieneColumna(ResultSet rs, String nombreColumna) {
        try {
            rs.findColumn(nombreColumna);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private void procesarTodoElFlujoFinanciero(TableView<ReporteVenta> tVentas, TableView<ReporteVenta> tEspacios, TableView<ReporteVenta> tEquipos) {
        // Validación de nulidad por si se manda a llamar antes de construir la UI
        if (tVentas == null || tEspacios == null || tEquipos == null) {
            return;
        }

        final double[] subtotales = new double[3];

        try (Connection con = com.rednova.util.Conexion.conectar()) {
            if (con == null) {
                throw new SQLException("La conexión devuelta por el pool es nula.");
            }

            // =========================================================================
            // 1. COMPONENTE: VENTAS EN MOSTRADOR
            // =========================================================================
            try {
                String sqlVentas = "SELECT p.nombreProducto, dv.cantidad, dv.precioAplicado, v.Dia, v.Mes, v.Anio "
                        + "FROM venta v "
                        + "INNER JOIN detalleventa dv ON v.idVenta = dv.idVenta "
                        + "INNER JOIN producto p ON dv.idProducto = p.idProducto";

                ObservableList<ReporteVenta> listaVentas = FXCollections.observableArrayList();
                try (PreparedStatement ps = con.prepareStatement(sqlVentas); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String concepto = rs.getString("nombreProducto");
                        int cant = rs.getInt("cantidad");
                        double precio = rs.getDouble("precioAplicado");
                        String fecha = String.format("%02d/%02d/%d", rs.getInt("Dia"), rs.getInt("Mes"), rs.getInt("Anio"));

                        subtotales[0] += (cant * precio);
                        listaVentas.add(new ReporteVenta(concepto, cant + " u.", precio, fecha));
                    }
                    tVentas.setItems(listaVentas);
                }
            } catch (SQLException ex) {
                System.err.println("Aviso: Módulo de Ventas: " + ex.getMessage());
            }
            lblSubtotalVentas.setText(String.format("Ganancia Ventas: $%,.2f MXN", subtotales[0]));

            // =========================================================================
// 2. COMPONENTE: RESERVACIONES DE ESPACIOS
// =========================================================================
            try {

                String sqlEspacios
                        = "SELECT e.tipoEspacio, re.cantidadHoras, "
                        + "re.precioTotal, re.fechaReserva "
                        + "FROM reserva_espacio re "
                        + "INNER JOIN espacio e ON re.idEspacio = e.idEspacio";

                ObservableList<ReporteVenta> listaEspacios
                        = FXCollections.observableArrayList();

                try (
                        PreparedStatement ps = con.prepareStatement(sqlEspacios); ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {

                        String concepto
                                = rs.getString("tipoEspacio");

                        int horas
                                = rs.getInt("cantidadHoras");

                        double ingresos
                                = rs.getDouble("precioTotal");

                        String fecha
                                = rs.getDate("fechaReserva").toString();

                        subtotales[1] += ingresos;

                        listaEspacios.add(
                                new ReporteVenta(
                                        concepto,
                                        horas + " hrs",
                                        ingresos,
                                        fecha
                                )
                        );
                    }

                    tEspacios.setItems(listaEspacios);
                }

            } catch (SQLException ex) {

                System.err.println(
                        "Aviso: Módulo de Espacios: " + ex.getMessage()
                );
            }

            lblSubtotalEspacios.setText(
                    String.format(
                            "Ganancia Espacios: $%,.2f MXN",
                            subtotales[1]
                    )
            );

            // =========================================================================
// 3. COMPONENTE: RENTA DE EQUIPOS
// =========================================================================
            try {

                String sqlEquipos
                        = "SELECT e.especificaciones, re.cantidadHoras, "
                        + "re.precioTotal, re.fechaReserva "
                        + "FROM reserva_equipo re "
                        + "INNER JOIN equipotecnologico e "
                        + "ON re.idEquipo = e.idEquipo";

                ObservableList<ReporteVenta> listaEquipos
                        = FXCollections.observableArrayList();

                try (
                        PreparedStatement ps = con.prepareStatement(sqlEquipos); ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {

                        String concepto
                                = rs.getString("especificaciones");

                        int horas
                                = rs.getInt("cantidadHoras");

                        double ingresos
                                = rs.getDouble("precioTotal");

                        String fecha
                                = rs.getDate("fechaReserva").toString();

                        subtotales[2] += ingresos;

                        listaEquipos.add(
                                new ReporteVenta(
                                        concepto,
                                        horas + " hrs",
                                        ingresos,
                                        fecha
                                )
                        );
                    }

                    tEquipos.setItems(listaEquipos);
                }

            } catch (SQLException ex) {

                System.err.println(
                        "Aviso: Módulo de Equipos: " + ex.getMessage()
                );
            }

            lblSubtotalEquipos.setText(
                    String.format(
                            "Ganancia Equipos: $%,.2f MXN",
                            subtotales[2]
                    )
            );

            // =========================================================================
            // 4. GRAN TOTAL FINANCIERO CONSOLIDADO
            // =========================================================================
            double gananciasFinales = subtotales[0] + subtotales[1] + subtotales[2];
            lblGananciasFinales.setText(String.format("$%,.2f MXN", gananciasFinales));

        } catch (SQLException ex) {
            System.err.println("Error general de infraestructura: " + ex.getMessage());
        }
    }
}
