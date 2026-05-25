package com.rednova.controller;

import com.rednova.dao.VentaDAO;
import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class VentanaVentas {

    // Paleta de colores formal (Estilo Dark Premium con el acento Carmesí de RedNova)
    private final String COLOR_BG = "#121214";
    private final String COLOR_CARD = "#1A1A1E";
    private final String COLOR_INPUT = "#26262B";
    private final String COLOR_ACCENT = "#C3073F";
    private final String COLOR_ACCENT_HOVER = "#950530";
    private final String COLOR_TEXT_PRIMARY = "#FFFFFF";
    private final String COLOR_TEXT_MUTED = "#A0A0A5";

    // Reglas CSS reutilizables para homogeneizar componentes
    private final String STYLE_INPUT = String.format(
        "-fx-background-color: %s; -fx-text-fill: %s; -fx-border-color: #3F3F46; " +
        "-fx-border-radius: 4; -fx-background-radius: 4; -fx-padding: 8 12; -fx-font-size: 13px;",
        COLOR_INPUT, COLOR_TEXT_PRIMARY
    );

    private final String STYLE_LABEL = String.format(
        "-fx-text-fill: %s; -fx-font-size: 13px; -fx-font-weight: bold;", COLOR_TEXT_MUTED
    );

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("RedNova OS - Módulo de Ventas");

        // --- CONTENEDOR PRINCIPAL ---
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + COLOR_BG + ";");
        mainLayout.setPadding(new Insets(24));

        // --- HEADER (ENCABEZADO DEL MÓDULO) ---
        VBox headerBox = new VBox(4);
        headerBox.setPadding(new Insets(0, 0, 15, 0));
        
        Label lblTitle = new Label("NUEVA TRANSACCIÓN");
        lblTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        lblTitle.setStyle("-fx-text-fill: " + COLOR_TEXT_PRIMARY + "; -fx-letter-spacing: 0.5;");
        
        Label lblSubtitle = new Label("Terminal de Punto de Venta Integral");
        lblSubtitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        lblSubtitle.setStyle("-fx-text-fill: " + COLOR_ACCENT + ";");
        
        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #27272A; -fx-padding: 6 0 0 0;");
        
        headerBox.getChildren().addAll(lblTitle, lblSubtitle, separator);
        mainLayout.setTop(headerBox);

        // --- COLOFCENTRO: FORMULARIO ALINEADO (GridPane en Tarjeta) ---
        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(14);
        formGrid.setPadding(new Insets(22));
        formGrid.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 6; -fx-border-color: #27272A; -fx-border-radius: 6;", COLOR_CARD));
        formGrid.setAlignment(Pos.TOP_LEFT);

        // Componentes y etiquetas del Formulario
        Label lblUsuarioId = new Label("ID Cliente / Usuario:");
        lblUsuarioId.setStyle(STYLE_LABEL);
        TextField txtUsuarioId = new TextField();
        txtUsuarioId.setPromptText("Ej. 102");
        txtUsuarioId.setStyle(STYLE_INPUT);

        Label lblProductoId = new Label("ID Producto / Ítem:");
        lblProductoId.setStyle(STYLE_LABEL);
        TextField txtProductoId = new TextField();
        txtProductoId.setPromptText("Ej. 45");
        txtProductoId.setStyle(STYLE_INPUT);

        Label lblCantidad = new Label("Cantidad:");
        lblCantidad.setStyle(STYLE_LABEL);
        TextField txtCantidad = new TextField();
        txtCantidad.setPromptText("0");
        txtCantidad.setStyle(STYLE_INPUT);

        Label lblPrecio = new Label("Precio Unitario ($):");
        lblPrecio.setStyle(STYLE_LABEL);
        TextField txtPrecio = new TextField();
        txtPrecio.setPromptText("0.00");
        txtPrecio.setStyle(STYLE_INPUT);

        Label lblMetodo = new Label("Método de Pago:");
        lblMetodo.setStyle(STYLE_LABEL);
        ComboBox<String> comboMetodo = new ComboBox<>(FXCollections.observableArrayList(
            "Efectivo", "Tarjeta de Crédito", "Tarjeta de Débito", "Puntos Lealtad"
        ));
        comboMetodo.setValue("Efectivo");
        comboMetodo.setMaxWidth(Double.MAX_VALUE); // Expandir al ancho total de la celda
        comboMetodo.setStyle(STYLE_INPUT + " -fx-background-color: " + COLOR_INPUT + ";");

        Label lblTotalTexto = new Label("Monto Total:");
        lblTotalTexto.setStyle(STYLE_LABEL);
        Label lblTotalDinero = new Label("$0.00");
        lblTotalDinero.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        lblTotalDinero.setStyle("-fx-text-fill: #10B981;"); // Color verde esmeralda para el balance final

        // Posicionar elementos en las coordenadas de la rejilla (Columna, Fila)
        formGrid.add(lblUsuarioId, 0, 0);   formGrid.add(txtUsuarioId, 1, 0);
        formGrid.add(lblProductoId, 0, 1);  formGrid.add(txtProductoId, 1, 1);
        formGrid.add(lblCantidad, 0, 2);    formGrid.add(txtCantidad, 1, 2);
        formGrid.add(lblPrecio, 0, 3);      formGrid.add(txtPrecio, 1, 3);
        formGrid.add(lblMetodo, 0, 4);      formGrid.add(comboMetodo, 1, 4);
        formGrid.add(lblTotalTexto, 0, 5);  formGrid.add(lblTotalDinero, 1, 5);

        // Controlar el tamaño relativo de las columnas para orden estricto
        ColumnConstraints col1 = new ColumnConstraints(140);
        ColumnConstraints col2 = new ColumnConstraints(220);
        formGrid.getColumnConstraints().addAll(col1, col2);

        mainLayout.setCenter(formGrid);

        // --- LÓGICA DE REACCIÓN EN TIEMPO REAL (Listeners) ---
        Runnable calcularTotalDinamico = () -> {
            try {
                int cant = txtCantidad.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtCantidad.getText().trim());
                double precio = txtPrecio.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtPrecio.getText().trim());
                double total = cant * precio;
                lblTotalDinero.setText(String.format("$%.2f", total));
            } catch (NumberFormatException ex) {
                lblTotalDinero.setText("$ --.--"); // Bandera visual de entrada inválida
            }
        };

        txtCantidad.textProperty().addListener((obs, oldVal, newVal) -> calcularTotalDinamico.run());
        txtPrecio.textProperty().addListener((obs, oldVal, newVal) -> calcularTotalDinamico.run());

        // --- FOOTER (BOTONES DE ACCIÓN FORMALES) ---
        HBox footerBox = new HBox(12);
        footerBox.setAlignment(Pos.CENTER_RIGHT);
        footerBox.setPadding(new Insets(18, 0, 0, 0));

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: transparent; -fx-text-fill: " + COLOR_TEXT_MUTED + 
                             "; -fx-border-color: #3F3F46; -fx-border-radius: 4; -fx-padding: 9 18; -fx-font-weight: bold; -fx-cursor: hand;");
        btnCancelar.setOnAction(e -> stage.close());

        Button btnRegistrar = new Button("Confirmar Transacción");
        btnRegistrar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 10 22; -fx-font-weight: bold; -fx-cursor: hand;", COLOR_ACCENT));
        
        // Manejo de estados dinámicos (Hover) mediante controladores de ratón
        btnRegistrar.setOnMouseEntered(e -> btnRegistrar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 10 22; -fx-font-weight: bold; -fx-cursor: hand;", COLOR_ACCENT_HOVER)));
        btnRegistrar.setOnMouseExited(e -> btnRegistrar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 10 22; -fx-font-weight: bold; -fx-cursor: hand;", COLOR_ACCENT)));

        btnRegistrar.setOnAction(e -> {
            try {
                int uId = Integer.parseInt(txtUsuarioId.getText().trim());
                int pId = Integer.parseInt(txtProductoId.getText().trim());
                int cant = Integer.parseInt(txtCantidad.getText().trim());
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                double total = cant * precio;
                String metodoPago = comboMetodo.getValue();

                // Conexión directa a la capa de persistencia lógica (DAO)
                new VentaDAO().registrarVentaCompleta(uId, total, total, metodoPago, pId, cant, precio);
                
                Alert alertaExito = new Alert(Alert.AlertType.INFORMATION, "La operación se ha procesado correctamente.\nEcosistema RedNova sincronizado.");
                alertaExito.setHeaderText("Transacción Exitosa");
                alertaExito.setTitle("Éxito");
                alertaExito.showAndWait();
                stage.close();
            } catch (Exception ex) {
                Alert alertaError = new Alert(Alert.AlertType.ERROR, "Error de validación: Verifique que todos los ID y cantidades sean números enteros válidos y no se incluyan caracteres de texto.");
                alertaError.setHeaderText("Error en el Formulario");
                alertaError.setTitle("Inconsistencia de Datos");
                alertaError.show();
            }
        });

        footerBox.getChildren().addAll(btnCancelar, btnRegistrar);
        mainLayout.setBottom(footerBox);

        // --- ESCENA Y CONFIGURACIÓN DEL VENTANAL ---
        Scene scene = new Scene(mainLayout, 440, 490);
        stage.setScene(scene);
        stage.setResizable(false); // Evita deformaciones innecesarias en ventanas de captura
        stage.show();
    }
}