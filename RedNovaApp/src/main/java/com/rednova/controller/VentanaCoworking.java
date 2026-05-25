package com.rednova.controller;

import com.rednova.dao.EspacioDAO;
import com.rednova.model.Espacio;
import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;

public class VentanaCoworking {

    // Paleta de colores premium de RedNova OS
    private final String COLOR_BG = "#121214";
    private final String COLOR_CARD = "#1A1A1E";
    private final String COLOR_INPUT = "#26262B";
    private final String COLOR_ACCENT = "#C3073F";
    private final String COLOR_ACCENT_HOVER = "#950530";
    private final String COLOR_DANGER = "#EF4444";
    private final String COLOR_TEXT_PRIMARY = "#FFFFFF";
    private final String COLOR_TEXT_MUTED = "#A0A0A5";

    //comentario Variable de control interna
    private int idEspacioSeleccionado = 0;

    // Estilos CSS Reutilizables
    private final String STYLE_INPUT = String.format(
        "-fx-background-color: %s; -fx-text-fill: %s; -fx-border-color: #3F3F46; " +
        "-fx-border-radius: 4; -fx-background-radius: 4; -fx-padding: 6 10; -fx-font-size: 13px;",
        COLOR_INPUT, COLOR_TEXT_PRIMARY
    );

    //comentario Regla CSS extendida para forzar modo oscuro en ComboBoxes
    private final String STYLE_COMBO = STYLE_INPUT + " -fx-base: " + COLOR_INPUT + "; -fx-control-inner-background: " + COLOR_INPUT + ";";

    private final String STYLE_LABEL = String.format(
        "-fx-text-fill: %s; -fx-font-size: 13px; -fx-font-weight: bold;", COLOR_TEXT_MUTED
    );

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("RedNova OS - Espacios Coworking");

        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + COLOR_BG + ";");
        mainLayout.setPadding(new Insets(24));

        VBox headerBox = new VBox(12);
        headerBox.setPadding(new Insets(0, 0, 16, 0));

        VBox titleBox = new VBox(2);
        Label lblTitle = new Label("CONTROL DE COWORKING");
        lblTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        lblTitle.setStyle("-fx-text-fill: " + COLOR_TEXT_PRIMARY + ";");
        
        Label lblSubtitle = new Label("Consola de Gestión de Áreas Físicas y Cubículos");
        lblSubtitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        lblSubtitle.setStyle("-fx-text-fill: " + COLOR_ACCENT + ";");
        titleBox.getChildren().addAll(lblTitle, lblSubtitle);

        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setPadding(new Insets(8, 12, 8, 12));
        searchBar.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 4; -fx-border-color: #27272A; -fx-border-radius: 4;", COLOR_CARD));

        Button btnBuscar = new Button("Buscar Espacios");
        btnBuscar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 6 14;", COLOR_INPUT));
        btnBuscar.setOnMouseEntered(e -> btnBuscar.setStyle("-fx-background-color: #3F3F46; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 6 14;"));
        btnBuscar.setOnMouseExited(e -> btnBuscar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 6 14;", COLOR_INPUT)));

        searchBar.getChildren().addAll(btnBuscar);
        headerBox.getChildren().addAll(titleBox, searchBar);
        mainLayout.setTop(headerBox);

        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(12);
        formGrid.setPadding(new Insets(20));
        formGrid.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 6; -fx-border-color: #27272A; -fx-border-radius: 6;", COLOR_CARD));

        Label lblTipo = new Label("Tipo de Espacio:");
        lblTipo.setStyle(STYLE_LABEL);
        ComboBox<String> comboTipo = new ComboBox<>(FXCollections.observableArrayList(
            "Cubículo Individual", "Sala Grupal", "Área Lounge", "Sala de Proyecciones"
        ));
        comboTipo.setPromptText("Seleccionar...");
        comboTipo.setMaxWidth(Double.MAX_VALUE);
        comboTipo.setStyle(STYLE_COMBO); //comentario Estilo aplicado

        Label lblCapacidad = new Label("Capacidad Máxima:");
        lblCapacidad.setStyle(STYLE_LABEL);
        TextField txtCapacidad = new TextField();
        txtCapacidad.setPromptText("Cantidad de personas");
        txtCapacidad.setStyle(STYLE_INPUT);

        Label lblEstado = new Label("Estado del Área:");
        lblEstado.setStyle(STYLE_LABEL);
        ComboBox<String> comboEstado = new ComboBox<>(FXCollections.observableArrayList(
            "Disponible", "Ocupado", "Mantenimiento"
        ));
        comboEstado.setValue("Disponible");
        comboEstado.setMaxWidth(Double.MAX_VALUE);
        comboEstado.setStyle(STYLE_COMBO); //comentario Estilo aplicado

        Label lblBadgeTexto = new Label("Disponibilidad:");
        lblBadgeTexto.setStyle(STYLE_LABEL);
        Label lblBadgeVisual = new Label("DISPONIBLE");
        lblBadgeVisual.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        lblBadgeVisual.setStyle("-fx-text-fill: #10B981; -fx-background-color: #064E3B; -fx-padding: 4 10; -fx-background-radius: 4;");

        formGrid.add(lblTipo, 0, 0);          formGrid.add(comboTipo, 1, 0);
        formGrid.add(lblCapacidad, 0, 1);     formGrid.add(txtCapacidad, 1, 1);
        formGrid.add(lblEstado, 0, 2);        formGrid.add(comboEstado, 1, 2);
        formGrid.add(lblBadgeTexto, 0, 3);    formGrid.add(lblBadgeVisual, 1, 3);

        ColumnConstraints col1 = new ColumnConstraints(140);
        ColumnConstraints col2 = new ColumnConstraints(230);
        formGrid.getColumnConstraints().addAll(col1, col2);
        mainLayout.setCenter(formGrid);

        // --- Lógica del Listener para color del Badge ---
        comboEstado.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                lblBadgeVisual.setText(newVal.toUpperCase());
                switch (newVal) {
                    case "Disponible": lblBadgeVisual.setStyle("-fx-text-fill: #10B981; -fx-background-color: #064E3B; -fx-padding: 4 10; -fx-background-radius: 4;"); break;
                    case "Ocupado": lblBadgeVisual.setStyle("-fx-text-fill: #EF4444; -fx-background-color: #451A1A; -fx-padding: 4 10; -fx-background-radius: 4;"); break;
                    case "Mantenimiento": lblBadgeVisual.setStyle("-fx-text-fill: #F59E0B; -fx-background-color: #78350F; -fx-padding: 4 10; -fx-background-radius: 4;"); break;
                }
            }
        });

        // --- BOTONERA Y LÓGICA ---
        HBox footerBox = new HBox(10);
        footerBox.setAlignment(Pos.CENTER_RIGHT);
        footerBox.setPadding(new Insets(16, 0, 0, 0));

        Button btnEliminar = new Button("Dar de Baja");
        btnEliminar.setStyle(String.format("-fx-background-color: transparent; -fx-text-fill: %s; -fx-border-color: %s; -fx-border-radius: 4; -fx-padding: 8 14; -fx-cursor: hand;", COLOR_DANGER, COLOR_DANGER));
        Button btnActualizar = new Button("Actualizar Espacio");
        btnActualizar.setStyle("-fx-background-color: #27272A; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 8 18; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-color: #3F3F46; -fx-border-radius: 4;");
        Button btnRegistrar = new Button("Registrar Nuevo");
        btnRegistrar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 9 20; -fx-font-weight: bold; -fx-cursor: hand;", COLOR_ACCENT));

        footerBox.getChildren().addAll(btnEliminar, btnActualizar, btnRegistrar);
        mainLayout.setBottom(footerBox);

        // --- MANEJO DE ACCIONES ---
        btnBuscar.setOnAction(e -> {
            try {
                List<Espacio> lista = new EspacioDAO().buscarTodos();
                if (lista.isEmpty()) { new Alert(Alert.AlertType.INFORMATION, "No hay espacios registrados.").show(); return; }
                Consultas.verEspacios(lista, esp -> {
                    idEspacioSeleccionado = esp.getIdEspacio();
                    comboTipo.setValue(esp.getTipoEspacio());
                    txtCapacidad.setText(String.valueOf(esp.getCapacidadPersonas()));
                    comboEstado.setValue(esp.getEstado());
                });
            } catch (Exception ex) { new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage()).show(); }
        });

        btnRegistrar.setOnAction(e -> {
            try {
                Espacio esp = new Espacio(0, comboTipo.getValue(), Integer.parseInt(txtCapacidad.getText().trim()), comboEstado.getValue());
                new EspacioDAO().registrar(esp);
                new Alert(Alert.AlertType.INFORMATION, "Registrado.").show();
                comboTipo.setValue(null); txtCapacidad.clear(); comboEstado.setValue("Disponible"); idEspacioSeleccionado = 0;
            } catch (Exception ex) { new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage()).show(); }
        });

        btnActualizar.setOnAction(e -> {
            try {
                if(idEspacioSeleccionado == 0) { new Alert(Alert.AlertType.WARNING, "Seleccione un espacio primero.").show(); return; }
                Espacio esp = new Espacio(idEspacioSeleccionado, comboTipo.getValue(), Integer.parseInt(txtCapacidad.getText().trim()), comboEstado.getValue());
                new EspacioDAO().actualizar(esp);
                new Alert(Alert.AlertType.INFORMATION, "Actualizado.").show();
            } catch (Exception ex) { new Alert(Alert.AlertType.ERROR, "Error al actualizar.").show(); }
        });

        btnEliminar.setOnAction(e -> {
            try {
                if(idEspacioSeleccionado == 0) { new Alert(Alert.AlertType.WARNING, "Seleccione un espacio primero.").show(); return; }
                new EspacioDAO().eliminar(idEspacioSeleccionado);
                new Alert(Alert.AlertType.INFORMATION, "Removido.").show();
                idEspacioSeleccionado = 0; comboTipo.setValue(null); txtCapacidad.clear(); comboEstado.setValue("Disponible");
            } catch (Exception ex) { new Alert(Alert.AlertType.ERROR, "Error de integridad: " + ex.getMessage()).show(); }
        });

        Scene scene = new Scene(mainLayout, 600, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}