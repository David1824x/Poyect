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

        // --- ENCABEZADO ---
        VBox headerBox = new VBox(4);
        headerBox.setPadding(new Insets(0, 0, 20, 0));
        
        Label lblTitle = new Label("REPORTE HISTÓRICO DE VENTAS");
        lblTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        lblTitle.setStyle("-fx-text-fill: " + COLOR_TEXT_PRIMARY + ";");
        
        Label lblSubtitle = new Label("Consulta inteligente y cruce relacional de órdenes de compra");
        lblSubtitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        lblSubtitle.setStyle("-fx-text-fill: " + COLOR_ACCENT + ";");
        
        headerBox.getChildren().addAll(lblTitle, lblSubtitle);
        mainLayout.setTop(headerBox);

        // --- CUERPO PRINCIPAL (TableView Avanzado) ---
        TableView<ReporteVenta> tablaReporte = new TableView<>();
        tablaReporte.setStyle(STYLE_TABLE);
        tablaReporte.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // 1. Columna Producto - CORREGIDA CON ENLAZADO DIRECTO
        TableColumn<ReporteVenta, String> colProducto = new TableColumn<>("Especificación del Producto");
        colProducto.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombreProducto())
        );

        // 2. Columna Cantidad - CORREGIDA CON ENLAZADO DIRECTO
        TableColumn<ReporteVenta, String> colCantidad = new TableColumn<>("Cant.");
        colCantidad.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getCantidad()))
        );
        colCantidad.setMaxWidth(1200); // Proporción simétrica para RedNova OS

        // 3. Columna Precio - RENDIMIENTO SEGURO
        TableColumn<ReporteVenta, String> colPrecio = new TableColumn<>("Precio Aplicado");
        colPrecio.setCellValueFactory(cellData -> {
            double precio = cellData.getValue().getPrecioAplicado()!= 0 ? cellData.getValue().getPrecioAplicado() : 0;
            // Si el método da problemas, usa directamente cellData.getValue().getPrecioAplicado()
            return new javafx.beans.property.SimpleStringProperty(String.format("$%,.2f MXN", cellData.getValue().getPrecioAplicado()));
        });

        // 4. Columna Fecha - CORREGIDA CON ENLAZADO DIRECTO
        TableColumn<ReporteVenta, String> colFecha = new TableColumn<>("Fecha de Emisión");
        colFecha.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFechaCompleta())
        );

        // Inyectar las columnas corregidas en la rejilla
        tablaReporte.getColumns().addAll(colProducto, colCantidad, colPrecio, colFecha);
        mainLayout.setCenter(tablaReporte);

        // --- CONTENEDORES DE INFORME ANALÍTICO (Footer) ---
        VBox footerBox = new VBox(15);
        footerBox.setPadding(new Insets(20, 0, 0, 0));

        HBox kpiContainer = new HBox(15);
        kpiContainer.setAlignment(Pos.CENTER);

        VBox cardIngresos = crearTarjetaKPI("INGRESOS TOTALES", "$0.00 MXN", "#10B981");
        VBox cardUnidades = crearTarjetaKPI("UNIDADES VENDIDAS", "0 u.", COLOR_ACCENT);

        kpiContainer.getChildren().addAll(cardIngresos, cardUnidades);
        HBox.setHgrow(cardIngresos, Priority.ALWAYS);
        HBox.setHgrow(cardUnidades, Priority.ALWAYS);

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);

        Button btnRegresar = new Button("Cerrar Consola");
        btnRegresar.setStyle("-fx-background-color: #27272A; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 8 20; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-color: #3F3F46; -fx-border-radius: 4;");
        btnRegresar.setOnAction(e -> escenarioReporte.close());

        buttonBar.getChildren().add(btnRegresar);
        footerBox.getChildren().addAll(kpiContainer, buttonBar);
        mainLayout.setBottom(footerBox);

        // --- INYECCIÓN Y CÁLCULO DE DATOS ---
        cargarDatosYMetricas(tablaReporte, (Label) cardIngresos.getChildren().get(1), (Label) cardUnidades.getChildren().get(1));

        escenarioReporte.setScene(new Scene(mainLayout, 720, 520));
        escenarioReporte.setResizable(false);
        escenarioReporte.show();
    }

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

    private void cargarDatosYMetricas(TableView<ReporteVenta> tabla, Label lblTotalIngresos, Label lblTotalUnidades) {
    // Consulta limpia utilizando los nombres de columna reales de tus tablas
    String consultaJoin = "SELECT p.nombreProducto, dv.cantidad, dv.precioAplicado, v.Dia, v.Mes, v.Anio " +
                          "FROM venta v " +
                          "INNER JOIN detalleventa dv ON v.idVenta = dv.idVenta " +
                          "INNER JOIN producto p ON dv.idProducto = p.idProducto";

    ObservableList<ReporteVenta> listaVentas = FXCollections.observableArrayList();
    
    double acumuladorIngresos = 0;
    int acumuladorUnidades = 0;

    try (Connection conexionBaseDatos = com.rednova.util.Conexion.conectar()) {
        
        if (conexionBaseDatos == null) {
            throw new SQLException("La conexión devuelta es nula. Revisa parámetros.");
        }

        try (PreparedStatement sentenciaPreparada = conexionBaseDatos.prepareStatement(consultaJoin);
             ResultSet resultadoConsulta = sentenciaPreparada.executeQuery()) {

            while (resultadoConsulta.next()) {
                // Extracción directa por el nombre real de la columna en la BD
                String nombreProd = resultadoConsulta.getString("nombreProducto");
                int cantidad = resultadoConsulta.getInt("cantidad");
                double precio = resultadoConsulta.getDouble("precioAplicado");
                
                // Construcción de la fecha unificada (Ej: 24/05/2026)
                String fecha = String.format("%02d/%02d/%d", 
                    resultadoConsulta.getInt("Dia"), 
                    resultadoConsulta.getInt("Mes"), 
                    resultadoConsulta.getInt("Anio")
                );
                
                // Métricas globales
                acumuladorUnidades += cantidad;
                acumuladorIngresos += (cantidad * precio);
                
                // Encapsular en el objeto modelo
                listaVentas.add(new ReporteVenta(nombreProd, cantidad, precio, fecha));
            }
            
            tabla.setItems(listaVentas);

            // Actualizar interfaz
            lblTotalUnidades.setText(acumuladorUnidades + " u.");
            lblTotalIngresos.setText(String.format("$%,.2f MXN", acumuladorIngresos));
        }

    } catch (SQLException excepcionSql) {
        new Alert(Alert.AlertType.ERROR, "Error al cargar reporte: " + excepcionSql.getMessage()).show();
        excepcionSql.printStackTrace();
    }
}
}