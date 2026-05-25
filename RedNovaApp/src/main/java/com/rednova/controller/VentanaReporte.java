package com.rednova.controller;

import com.rednova.model.ReporteVenta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.sql.*;

public class VentanaReporte {

    // Paleta de Colores de RedNova OS
    private final String COLOR_BG = "#121214";
    private final String COLOR_CARD = "#1A1A1E";
    private final String COLOR_ACCENT = "#C3073F";
    private final String COLOR_TEXT_PRIMARY = "#FFFFFF";
    private final String COLOR_TEXT_MUTED = "#A0A0A5";

    // Estilos de componentes para mantener simetría
    private final String STYLE_TABLE = 
        "-fx-background-color: #1A1A1E; " +
        "-fx-control-inner-background: #1A1A1E; " +
        "-fx-base: #121214; " +
        "-fx-table-cell-border-color: #27272A; " +
        "-fx-text-fill: white;";

    public void mostrarReporte() {
        Stage escenarioReporte = new Stage();
        escenarioReporte.setTitle("RedNova OS - Consola Analítica de Ventas");

        // --- CONTENEDOR PRINCIPAL ---
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + COLOR_BG + ";");
        mainLayout.setPadding(new Insets(24));

        // --- ENCABEZADO (Header) ---
        VBox headerBox = new VBox(4);
        headerBox.setPadding(new Insets(0, 0, 20, 0));
        
        Label lblTitle = new Label("REPORTE HISTÓRICO DE VENTAS");
        lblTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        lblTitle.setStyle("-fx-text-fill: " + COLOR_TEXT_PRIMARY + "; -fx-letter-spacing: 1px;");
        
        Label lblSubtitle = new Label("Consulta inteligente y cruce relacional (JOIN MULTIPLE)");
        lblSubtitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        lblSubtitle.setStyle("-fx-text-fill: " + COLOR_ACCENT + ";");
        
        headerBox.getChildren().addAll(lblTitle, lblSubtitle);
        mainLayout.setTop(headerBox);

        // --- CUERPO PRINCIPAL (TableView Avanzado) ---
        TableView<ReporteVenta> tablaReporte = new TableView<>();
        tablaReporte.setStyle(STYLE_TABLE);
        // Distribuye proporcionalmente las columnas al ancho total
        tablaReporte.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Definición de columnas con estilos oscuros
        TableColumn<ReporteVenta, String> colProducto = new TableColumn<>("Especificación del Producto");
        colProducto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));

        TableColumn<ReporteVenta, Integer> colCantidad = new TableColumn<>("Cant.");
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colCantidad.setMaxWidth(1000); // Hacerla más estrecha por consistencia numérica

        TableColumn<ReporteVenta, Double> colPrecio = new TableColumn<>("Precio Aplicado");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioAplicado"));

        TableColumn<ReporteVenta, String> colFecha = new TableColumn<>("Fecha de Emisión");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaCompleta"));

        tablaReporte.getColumns().addAll(colProducto, colCantidad, colPrecio, colFecha);
        mainLayout.setCenter(tablaReporte);

        // --- CONTENEDORES DE INFORME ANALÍTICO (Footer) ---
        VBox footerBox = new VBox(15);
        footerBox.setPadding(new Insets(20, 0, 0, 0));

        // Panel de KPIs / Tarjetas de información resumida
        HBox kpiContainer = new HBox(15);
        kpiContainer.setAlignment(Pos.CENTER);

        VBox cardIngresos = crearTarjetaKPI("INGRESOS TOTALES", "$0.00 MXN", "#10B981"); // Verde esmeralda analítico
        VBox cardUnidades = crearTarjetaKPI("UNIDADES VENDIDAS", "0 u.", COLOR_ACCENT);

        kpiContainer.getChildren().addAll(cardIngresos, cardUnidades);
        HBox.setHgrow(cardIngresos, Priority.ALWAYS);
        HBox.setHgrow(cardUnidades, Priority.ALWAYS);

        // Botonera de control
        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);

        Button btnRegresar = new Button("Cerrar Consola");
        btnRegresar.setStyle("-fx-background-color: #27272A; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 8 20; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-color: #3F3F46; -fx-border-radius: 4;");
        btnRegresar.setOnMouseEntered(e -> btnRegresar.setStyle("-fx-background-color: #3F3F46; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 8 20; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-color: #52525B; -fx-border-radius: 4;"));
        btnRegresar.setOnMouseExited(e -> btnRegresar.setStyle("-fx-background-color: #27272A; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 8 20; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-color: #3F3F46; -fx-border-radius: 4;"));
        btnRegresar.setOnAction(e -> escenarioReporte.close());

        buttonBar.getChildren().add(btnRegresar);
        footerBox.getChildren().addAll(kpiContainer, buttonBar);
        mainLayout.setBottom(footerBox);

        // --- INYECCIÓN Y CÁLCULO DE DATOS ---
        cargarDatosYMetricas(tablaReporte, (Label) cardIngresos.getChildren().get(1), (Label) cardUnidades.getChildren().get(1));

        // Configuración de la escena
        escenarioReporte.setScene(new Scene(mainLayout, 680, 500)); // Ajuste de tamaño proporcional para legibilidad
        escenarioReporte.setResizable(false);
        escenarioReporte.show();
    }

    /**
     * Helper para construir tarjetas de datos dinámicas con diseño unificado
     */
    private VBox crearTarjetaKPI(String titulo, String valorInicial, String colorResaltado) {
        VBox card = new VBox(4);
        card.setPadding(new Insets(12, 16, 12, 16));
        card.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 6; -fx-border-color: #27272A; -fx-border-radius: 6;", COLOR_CARD));
        card.setAlignment(Pos.CENTER_LEFT);

        Label lblTitulo = new Label(titulo);
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        lblTitulo.setStyle("-fx-text-fill: " + COLOR_TEXT_MUTED + ";");

        Label lblValor = new Label(valorInicial);
        lblValor.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        lblValor.setStyle("-fx-text-fill: " + colorResaltado + ";");

        card.getChildren().addAll(lblTitulo, lblValor);
        return card;
    }

    /**
     * Mapea el ResultSet, inyecta los elementos en el TableView e interactúa con el footer analítico
     */
    private void cargarDatosYMetricas(TableView<ReporteVenta> tabla, Label lblTotalIngresos, Label lblTotalUnidades) {
        String consultaJoin = "SELECT p.nombreProducto, dv.cantidad, dv.precioAplicado, v.Dia, v.Mes, v.Anio " +
                              "FROM Venta v " +
                              "JOIN DetalleVenta dv ON v.idVenta = dv.idVenta " +
                              "JOIN Producto p ON dv.idProducto = p.idProducto";

        ObservableList<ReporteVenta> listaVentas = FXCollections.observableArrayList();
        
        double acumuladorIngresos = 0;
        int acumuladorUnidades = 0;

        try (Connection conexionBaseDatos = com.rednova.util.Conexion.conectar();
             PreparedStatement sentenciaPreparada = conexionBaseDatos.prepareStatement(consultaJoin);
             ResultSet resultadoConsulta = sentenciaPreparada.executeQuery()) {

            while (resultadoConsulta.next()) {
                String fecha = resultadoConsulta.getString("Dia") + "/" + 
                               resultadoConsulta.getString("Mes") + "/" + 
                               resultadoConsulta.getString("Anio");
                
                int cantidad = resultadoConsulta.getInt("cantidad");
                double precio = resultadoConsulta.getDouble("precioAplicado");

                // Cálculo financiero en tiempo de ejecución
                acumuladorUnidades += cantidad;
                acumuladorIngresos += (cantidad * precio);
                
                listaVentas.add(new ReporteVenta(
                    resultadoConsulta.getString("nombreProducto"),
                    cantidad,
                    precio,
                    fecha
                ));
            }
            tabla.setItems(listaVentas);

            // Pintar los resultados formateados en las tarjetas de analítica
            lblTotalUnidades.setText(acumuladorUnidades + " unidades");
            lblTotalIngresos.setText(String.format("$%,.2f MXN", acumuladorIngresos));

        } catch (SQLException excepcionSql) {
            new Alert(Alert.AlertType.ERROR, "Fallo de Interconexión en Reportes: " + excepcionSql.getMessage()).show();
            excepcionSql.printStackTrace();
        }
    }
}