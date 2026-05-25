package com.rednova.controller;

import com.rednova.dao.UsuarioDAO;
import com.rednova.model.Usuario;
import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.List;

public class VentanaUsuarios {

    // Paleta de colores unificada de RedNova OS
    private final String COLOR_BG = "#121214";
    private final String COLOR_CARD = "#1A1A1E";
    private final String COLOR_INPUT = "#26262B";
    private final String COLOR_ACCENT = "#C3073F";
    private final String COLOR_ACCENT_HOVER = "#950530";
    private final String COLOR_DANGER = "#EF4444";
    private final String COLOR_TEXT_PRIMARY = "#FFFFFF";
    private final String COLOR_TEXT_MUTED = "#A0A0A5";

    private int idUsuarioSeleccionado = 0;

    // CSS Estructural Reutilizable
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
        stage.setTitle("RedNova OS - Padrón de Usuarios");

        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + COLOR_BG + ";");
        mainLayout.setPadding(new Insets(24));

        VBox headerBox = new VBox(12);
        headerBox.setPadding(new Insets(0, 0, 16, 0));

        VBox titleBox = new VBox(2);
        Label lblTitle = new Label("PADRÓN DE USUARIOS");
        lblTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        lblTitle.setStyle("-fx-text-fill: " + COLOR_TEXT_PRIMARY + ";");
        
        Label lblSubtitle = new Label("Módulo de Afiliación y Membresías Lealtad");
        lblSubtitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        lblSubtitle.setStyle("-fx-text-fill: " + COLOR_ACCENT + ";");
        titleBox.getChildren().addAll(lblTitle, lblSubtitle);

        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setPadding(new Insets(8, 12, 8, 12));
        searchBar.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 4; -fx-border-color: #27272A; -fx-border-radius: 4;", COLOR_CARD));

        Button btnBuscar = new Button("Buscar Usuarios");
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

        Label lblNumControl = new Label("Número de Control:");
        lblNumControl.setStyle(STYLE_LABEL);
        TextField txtNumControl = new TextField();
        txtNumControl.setStyle(STYLE_INPUT);

        Label lblCorreo = new Label("Correo Institucional:");
        lblCorreo.setStyle(STYLE_LABEL);
        TextField txtCorreo = new TextField();
        txtCorreo.setStyle(STYLE_INPUT);

        Label lblNombre = new Label("Nombre Completo:");
        lblNombre.setStyle(STYLE_LABEL);
        TextField txtNombre = new TextField();
        txtNombre.setStyle(STYLE_INPUT);

        Label lblTipo = new Label("Tipo de Miembro:");
        lblTipo.setStyle(STYLE_LABEL);
        ComboBox<String> comboTipo = new ComboBox<>(FXCollections.observableArrayList(
            "Estudiante", "Docente", "Administrativo", "Externo"
        ));
        comboTipo.setMaxWidth(Double.MAX_VALUE);
        comboTipo.setStyle(STYLE_COMBO); //comentario Aplicado estilo corregido

        Label lblInfoLealtad = new Label("Métricas de Lealtad:");
        lblInfoLealtad.setStyle(STYLE_LABEL);
        
        HBox statusLealtadBox = new HBox(15);
        statusLealtadBox.setAlignment(Pos.CENTER_LEFT);
        Label lblPuntos = new Label("0 pts");
        lblPuntos.setStyle("-fx-text-fill: #10B981; -fx-background-color: #064E3B; -fx-padding: 4 8; -fx-background-radius: 4;");
        Label lblNivel = new Label("BRONCE");
        lblNivel.setStyle("-fx-text-fill: #F59E0B; -fx-background-color: #78350F; -fx-padding: 4 8; -fx-background-radius: 4;");
        statusLealtadBox.getChildren().addAll(lblPuntos, lblNivel);

        formGrid.add(lblNumControl, 0, 0);   formGrid.add(txtNumControl, 1, 0);
        formGrid.add(lblCorreo, 0, 1);       formGrid.add(txtCorreo, 1, 1);
        formGrid.add(lblNombre, 0, 2);       formGrid.add(txtNombre, 1, 2);
        formGrid.add(lblTipo, 0, 3);         formGrid.add(comboTipo, 1, 3);
        formGrid.add(lblInfoLealtad, 0, 4);  formGrid.add(statusLealtadBox, 1, 4);

        ColumnConstraints col1 = new ColumnConstraints(140);
        ColumnConstraints col2 = new ColumnConstraints(230);
        formGrid.getColumnConstraints().addAll(col1, col2);
        mainLayout.setCenter(formGrid);

        // --- BOTONERA ---
        HBox footerBox = new HBox(10);
        footerBox.setAlignment(Pos.CENTER_RIGHT);
        footerBox.setPadding(new Insets(16, 0, 0, 0));
        Button btnEliminar = new Button("Dar de Baja");
        btnEliminar.setStyle(String.format("-fx-background-color: transparent; -fx-text-fill: %s; -fx-border-color: %s; -fx-border-radius: 4; -fx-padding: 8 14; -fx-cursor: hand;", COLOR_DANGER, COLOR_DANGER));
        Button btnActualizar = new Button("Actualizar");
        btnActualizar.setStyle("-fx-background-color: #27272A; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 8 18; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-color: #3F3F46; -fx-border-radius: 4;");
        Button btnRegistrar = new Button("Registrar Nuevo");
        btnRegistrar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 9 20; -fx-font-weight: bold; -fx-cursor: hand;", COLOR_ACCENT));

        footerBox.getChildren().addAll(btnEliminar, btnActualizar, btnRegistrar);
        mainLayout.setBottom(footerBox);

        // --- LÓGICA ---
        btnBuscar.setOnAction(e -> {
            try {
                List<Usuario> lista = new UsuarioDAO().buscarTodos();
                if (lista.isEmpty()) { new Alert(Alert.AlertType.INFORMATION, "No existen usuarios registrados.").show(); return; }
                
                Consultas.verUsuarios(lista, u -> {
                    idUsuarioSeleccionado = u.getId();
                    txtNumControl.setText(u.getNumeroControl());
                    txtCorreo.setText(u.getCorreoInstitucional());
                    txtNombre.setText(u.getNombre());
                    comboTipo.setValue(u.getTipoUsuario());
                    lblPuntos.setText(u.getPuntosLealtad() + " pts");
                    lblNivel.setText(u.getNivelMembresia().toUpperCase());
                });
            } catch (Exception ex) { new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage()).show(); }
        });

        // ... (restos de botones igual que antes)
        btnRegistrar.setOnAction(e -> {
            try {
                Usuario u = new Usuario(0, txtNumControl.getText().trim(), txtCorreo.getText().trim(), 
                                        txtNombre.getText().trim(), comboTipo.getValue(), LocalDate.now(), 0, "Bronce");
                new UsuarioDAO().registrar(u);
                new Alert(Alert.AlertType.INFORMATION, "Usuario registrado.").show();
                txtNumControl.clear(); txtCorreo.clear(); txtNombre.clear(); comboTipo.setValue(null); idUsuarioSeleccionado = 0;
            } catch (Exception ex) { new Alert(Alert.AlertType.ERROR, "Fallo: " + ex.getMessage()).show(); }
        });

        btnActualizar.setOnAction(e -> {
            try {
                if(idUsuarioSeleccionado == 0) { new Alert(Alert.AlertType.WARNING, "Seleccione un usuario primero.").show(); return; }
                Usuario u = new Usuario(idUsuarioSeleccionado, txtNumControl.getText().trim(), txtCorreo.getText().trim(), 
                                        txtNombre.getText().trim(), comboTipo.getValue(), LocalDate.now(), 
                                        Integer.parseInt(lblPuntos.getText().replace(" pts", "").trim()), lblNivel.getText());
                new UsuarioDAO().actualizar(u);
                new Alert(Alert.AlertType.INFORMATION, "Actualizado.").show();
            } catch (Exception ex) { new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage()).show(); }
        });

        btnEliminar.setOnAction(e -> {
            try {
                if(idUsuarioSeleccionado == 0) { new Alert(Alert.AlertType.WARNING, "Seleccione un usuario primero.").show(); return; }
                new UsuarioDAO().eliminar(idUsuarioSeleccionado);
                new Alert(Alert.AlertType.INFORMATION, "Removido.").show();
                idUsuarioSeleccionado = 0;
            } catch (Exception ex) { new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage()).show(); }
        });

        Scene scene = new Scene(mainLayout, 450, 490);
        stage.setScene(scene);
        stage.show();
    }
}