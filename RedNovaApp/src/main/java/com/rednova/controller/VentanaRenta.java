package com.rednova.controller;

import com.rednova.dao.EquipoDAO;
import com.rednova.model.EquipoTecnologico;
import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.time.LocalDate;

public class VentanaRenta {

    // Paleta de colores unificada de RedNova OS
    private final String COLOR_BG = "#121214";
    private final String COLOR_CARD = "#1A1A1E";
    private final String COLOR_INPUT = "#26262B";
    private final String COLOR_ACCENT = "#C3073F";
    private final String COLOR_ACCENT_HOVER = "#950530";
    private final String COLOR_DANGER = "#EF4444";
    private final String COLOR_TEXT_PRIMARY = "#FFFFFF";
    private final String COLOR_TEXT_MUTED = "#A0A0A5";

    // Estilos CSS Reutilizables
    private final String STYLE_INPUT = String.format(
            "-fx-background-color: %s; -fx-text-fill: %s; -fx-border-color: #3F3F46; "
            + "-fx-border-radius: 4; -fx-background-radius: 4; -fx-padding: 6 10; -fx-font-size: 13px;",
            COLOR_INPUT, COLOR_TEXT_PRIMARY
    );

    private final String STYLE_LABEL = String.format(
            "-fx-text-fill: %s; -fx-font-size: 13px; -fx-font-weight: bold;", COLOR_TEXT_MUTED
    );

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("RedNova OS - Renta Tecnológica");

        // --- CONTENEDOR PRINCIPAL ---
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + COLOR_BG + ";");
        mainLayout.setPadding(new Insets(24));

        // --- ENCABEZADO: TÍTULO Y BÚSQUEDA ---
        VBox headerBox = new VBox(12);
        headerBox.setPadding(new Insets(0, 0, 16, 0));

        VBox titleBox = new VBox(2);
        Label lblTitle = new Label("RENTA TECNOLÓGICA");
        lblTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        lblTitle.setStyle("-fx-text-fill: " + COLOR_TEXT_PRIMARY + ";");

        Label lblSubtitle = new Label("Consola de Monitoreo y Préstamo de Hardware");
        lblSubtitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        lblSubtitle.setStyle("-fx-text-fill: " + COLOR_ACCENT + ";");
        titleBox.getChildren().addAll(lblTitle, lblSubtitle);

        // Barra de búsqueda de hardware
        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setPadding(new Insets(8, 12, 8, 12));
        searchBar.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 4; -fx-border-color: #27272A; -fx-border-radius: 4;", COLOR_CARD));

        Label lblSearch = new Label("ID Equipo:");
        lblSearch.setStyle(STYLE_LABEL);

        TextField txtId = new TextField();
        txtId.setPromptText("Num. ID");
        txtId.setPrefWidth(80);
        txtId.setStyle(STYLE_INPUT);

        Button btnBuscar = new Button("Monitorear");
        btnBuscar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 6 14;", COLOR_INPUT));
        btnBuscar.setOnMouseEntered(e -> btnBuscar.setStyle("-fx-background-color: #3F3F46; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 6 14;"));
        btnBuscar.setOnMouseExited(e -> btnBuscar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 6 14;", COLOR_INPUT)));

        searchBar.getChildren().addAll(lblSearch, txtId, btnBuscar);
        headerBox.getChildren().addAll(titleBox, searchBar);
        mainLayout.setTop(headerBox);

        // --- CUERPO: FICHA TÉCNICA DEL EQUIPO (GridPane) ---
        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(12);
        formGrid.setPadding(new Insets(20));
        formGrid.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 6; -fx-border-color: #27272A; -fx-border-radius: 6;", COLOR_CARD));

        // Campos de control operativo
        Label lblTipo = new Label("Tipo de Equipo:");
        lblTipo.setStyle(STYLE_LABEL);
        ComboBox<String> comboTipo = new ComboBox<>(FXCollections.observableArrayList(
                "Laptop", "Tablet", "Cargador", "Proyector", "Kit de Adaptadores"
        ));
        comboTipo.setPromptText("Seleccionar tipo...");
        comboTipo.setMaxWidth(Double.MAX_VALUE);
        comboTipo.setStyle(STYLE_INPUT + " -fx-background-color: " + COLOR_INPUT + ";");

        Label lblSpecs = new Label("Especificaciones:");
        lblSpecs.setStyle(STYLE_LABEL);
        TextField txtSpecs = new TextField();
        txtSpecs.setPromptText("Ej. ASUS ROG Strix, 16GB RAM, RTX 4060");
        txtSpecs.setStyle(STYLE_INPUT);

        Label lblTarifa = new Label("Tarifa por Hora ($):");
        lblTarifa.setStyle(STYLE_LABEL);
        TextField txtTarifa = new TextField();
        txtTarifa.setPromptText("0.00");
        txtTarifa.setStyle(STYLE_INPUT);

        Label lblEstado = new Label("Estado de Disponibilidad:");
        lblEstado.setStyle(STYLE_LABEL);
        ComboBox<String> comboEstado = new ComboBox<>(FXCollections.observableArrayList(
                "Disponible", "En Uso", "Mantenimiento"
        ));
        comboEstado.setValue("Disponible");
        comboEstado.setMaxWidth(Double.MAX_VALUE);
        comboEstado.setStyle(STYLE_INPUT + " -fx-background-color: " + COLOR_INPUT + ";");

        // Indicador visual premium (Badge reactivo)
        Label lblBadgeTexto = new Label("Estatus Visual:");
        lblBadgeTexto.setStyle(STYLE_LABEL);
        Label lblBadgeVisual = new Label("DISPONIBLE");
        lblBadgeVisual.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        lblBadgeVisual.setStyle("-fx-text-fill: #10B981; -fx-background-color: #064E3B; -fx-padding: 4 10; -fx-background-radius: 4;");

        // Posicionamiento simétrico
        formGrid.add(lblTipo, 0, 0);
        formGrid.add(comboTipo, 1, 0);
        formGrid.add(lblSpecs, 0, 1);
        formGrid.add(txtSpecs, 1, 1);
        formGrid.add(lblTarifa, 0, 2);
        formGrid.add(txtTarifa, 1, 2);
        formGrid.add(lblEstado, 0, 3);
        formGrid.add(comboEstado, 1, 3);
        formGrid.add(lblBadgeTexto, 0, 4);
        formGrid.add(lblBadgeVisual, 1, 4);

        ColumnConstraints col1 = new ColumnConstraints(140);
        ColumnConstraints col2 = new ColumnConstraints(230);
        formGrid.getColumnConstraints().addAll(col1, col2);
        mainLayout.setCenter(formGrid);

        // --- INTERACCIÓN EN TIEMPO REAL (UI UX Listener) ---
        // Cambia el color del Badge según el ComboBox sin necesidad de guardar primero
        comboEstado.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                lblBadgeVisual.setText(newVal.toUpperCase());
                switch (newVal) {
                    case "Disponible":
                        lblBadgeVisual.setStyle("-fx-text-fill: #10B981; -fx-background-color: #064E3B; -fx-padding: 4 10; -fx-background-radius: 4;");
                        break;
                    case "En Uso":
                        lblBadgeVisual.setStyle("-fx-text-fill: #EF4444; -fx-background-color: #451A1A; -fx-padding: 4 10; -fx-background-radius: 4;");
                        break;
                    case "Mantenimiento":
                        lblBadgeVisual.setStyle("-fx-text-fill: #F59E0B; -fx-background-color: #78350F; -fx-padding: 4 10; -fx-background-radius: 4;");
                        break;
                }
            }
        });

        // --- BOTONERA DE CONTROL (Footer) ---
        HBox footerBox = new HBox(10);
        footerBox.setAlignment(Pos.CENTER_RIGHT);
        footerBox.setPadding(new Insets(16, 0, 0, 0));

        Button btnEliminar = new Button("Dar de Baja");
        btnEliminar.setStyle(String.format("-fx-background-color: transparent; -fx-text-fill: %s; -fx-border-color: %s; -fx-border-radius: 4; -fx-padding: 8 14; -fx-cursor: hand;", COLOR_DANGER, COLOR_DANGER));
        btnEliminar.setOnMouseEntered(e -> btnEliminar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-border-color: %s; -fx-border-radius: 4; -fx-padding: 8 14; -fx-cursor: hand;", COLOR_DANGER, COLOR_DANGER)));
        btnEliminar.setOnMouseExited(e -> btnEliminar.setStyle(String.format("-fx-background-color: transparent; -fx-text-fill: %s; -fx-border-color: %s; -fx-border-radius: 4; -fx-padding: 8 14; -fx-cursor: hand;", COLOR_DANGER, COLOR_DANGER)));

        Button btnActualizar = new Button("Actualizar Equipo");
        btnActualizar.setStyle("-fx-background-color: #27272A; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 8 18; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-color: #3F3F46; -fx-border-radius: 4;");
        btnActualizar.setOnMouseEntered(e -> btnActualizar.setStyle("-fx-background-color: #3F3F46; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 8 18; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-color: #52525B; -fx-border-radius: 4;"));
        btnActualizar.setOnMouseExited(e -> btnActualizar.setStyle("-fx-background-color: #27272A; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 8 18; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-color: #3F3F46; -fx-border-radius: 4;"));

        Button btnRegistrar = new Button("Registrar Nuevo");
        btnRegistrar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 9 20; -fx-font-weight: bold; -fx-cursor: hand;", COLOR_ACCENT));
        btnRegistrar.setOnMouseEntered(e -> btnRegistrar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 9 20; -fx-font-weight: bold; -fx-cursor: hand;", COLOR_ACCENT_HOVER)));
        btnRegistrar.setOnMouseExited(e -> btnRegistrar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 9 20; -fx-font-weight: bold; -fx-cursor: hand;", COLOR_ACCENT)));

        footerBox.getChildren().addAll(btnEliminar, btnActualizar, btnRegistrar);
        mainLayout.setBottom(footerBox);

        // --- MANEJO DE ACCIONES DE PERSISTENCIA ---
        // Buscar / Monitorear por ID
        btnBuscar.setOnAction(e -> {
            try {
                if (txtId.getText().trim().isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Ingrese un ID de equipo para consultar su estatus.").show();
                    return;
                }
                EquipoTecnologico eq = new EquipoDAO().buscarPorId(Integer.parseInt(txtId.getText().trim()));
                if (eq != null) {
                    comboTipo.setValue(eq.getTipoEquipo());
                    txtSpecs.setText(eq.getEspecificaciones());
                    txtTarifa.setText(String.valueOf(eq.getTarifaPorHora()));
                    comboEstado.setValue(eq.getEstado()); // El listener actualizará el badge automáticamente
                } else {
                    new Alert(Alert.AlertType.WARNING, "El equipo tecnológico no se encuentra en el inventario.").show();
                }
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error de formato: El identificador de búsqueda debe ser numérico.").show();
            }
        });

        // Registrar Nuevo Equipo
        btnRegistrar.setOnAction(e -> {
            try {
                if (comboTipo.getValue() == null || txtSpecs.getText().trim().isEmpty() || txtTarifa.getText().trim().isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Complete todas las especificaciones y tarifas para el alta de hardware.").show();
                    return;
                }

                EquipoTecnologico eq = new EquipoTecnologico(
                        0,
                        comboTipo.getValue(),
                        txtSpecs.getText().trim(),
                        Double.parseDouble(txtTarifa.getText().trim()),
                        comboEstado.getValue(),
                        LocalDate.now()
                );

                new EquipoDAO().insertar(eq);
                new Alert(Alert.AlertType.INFORMATION, "Hardware indexado exitosamente en el catálogo global.").show();

                // Limpieza de campos
                comboTipo.setValue(null);
                txtSpecs.clear();
                txtTarifa.clear();
                comboEstado.setValue("Disponible");
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error al registrar: Valide que la tarifa por hora contenga un valor decimal válido.").show();
            }
        });

        // Actualizar datos / estado
        btnActualizar.setOnAction(e -> {
            try {
                if (txtId.getText().trim().isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Especifique el ID del hardware en el panel superior para guardar los cambios.").show();
                    return;
                }

                EquipoTecnologico eq = new EquipoTecnologico(
                        Integer.parseInt(txtId.getText().trim()),
                        comboTipo.getValue(),
                        txtSpecs.getText().trim(),
                        Double.parseDouble(txtTarifa.getText().trim()),
                        comboEstado.getValue(),
                        LocalDate.now() // Puede ser sobreescrito o ignorado por el DAO respetando la fecha original
                );

                new EquipoDAO().actualizar(eq);
                new Alert(Alert.AlertType.INFORMATION, "Estatus y metadatos del equipo sincronizados en el servidor.").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Fallo de actualización: Verifique la consistencia de los datos ingresados.").show();
            }
        });

        // Dar de Baja (Eliminar)
        btnEliminar.setOnAction(e -> {
            try {
                if (txtId.getText().trim().isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Defina el código ID del componente a retirar.").show();
                    return;
                }

                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea dar de baja permanentemente este hardware? Esta acción afectará los reportes históricos si el equipo cuenta con rentas previas.", ButtonType.YES, ButtonType.NO);
                confirmacion.setHeaderText("Alerta de Desincorporación de Hardware");
                confirmacion.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.YES) {
                        //new EquipoDAO().eliminar(Integer.parseInt(txtId.getText().trim()));//---------------------------------
                        new Alert(Alert.AlertType.INFORMATION, "El equipo tecnológico ha sido removido del sistema.").show();

                        // Limpieza post-baja
                        txtId.clear();
                        comboTipo.setValue(null);
                        txtSpecs.clear();
                        txtTarifa.clear();
                        comboEstado.setValue("Disponible");
                    }
                });
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Conflicto en la remoción: No se puede eliminar hardware que se encuentre actualmente en renta activa o referenciado en un Detalle de Venta.").show();
            }
        });

        // --- ESCENA Y DESPLIEGUE ---
        Scene scene = new Scene(mainLayout, 450, 480);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
