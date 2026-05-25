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

    // CSS Estructural Reutilizable
    private final String STYLE_INPUT = String.format(
        "-fx-background-color: %s; -fx-text-fill: %s; -fx-border-color: #3F3F46; " +
        "-fx-border-radius: 4; -fx-background-radius: 4; -fx-padding: 6 10; -fx-font-size: 13px;",
        COLOR_INPUT, COLOR_TEXT_PRIMARY
    );

    private final String STYLE_LABEL = String.format(
        "-fx-text-fill: %s; -fx-font-size: 13px; -fx-font-weight: bold;", COLOR_TEXT_MUTED
    );

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("RedNova OS - Padrón de Usuarios");

        // --- CONTENEDOR PRINCIPAL ---
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + COLOR_BG + ";");
        mainLayout.setPadding(new Insets(24));

        // --- ENCABEZADO: TÍTULO Y BARRA DE BÚSQUEDA ---
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

        // Barra horizontal de búsqueda por ID
        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setPadding(new Insets(8, 12, 8, 12));
        searchBar.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 4; -fx-border-color: #27272A; -fx-border-radius: 4;", COLOR_CARD));

        Label lblSearch = new Label("Buscar ID:");
        lblSearch.setStyle(STYLE_LABEL);
        
        TextField txtId = new TextField();
        txtId.setPromptText("Num. ID");
        txtId.setPrefWidth(80);
        txtId.setStyle(STYLE_INPUT);

        Button btnBuscar = new Button("Consultar");
        btnBuscar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 6 14;", COLOR_INPUT));
        btnBuscar.setOnMouseEntered(e -> btnBuscar.setStyle("-fx-background-color: #3F3F46; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 6 14;"));
        btnBuscar.setOnMouseExited(e -> btnBuscar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 6 14;", COLOR_INPUT)));

        searchBar.getChildren().addAll(lblSearch, txtId, btnBuscar);
        headerBox.getChildren().addAll(titleBox, searchBar);
        mainLayout.setTop(headerBox);

        // --- CUERPO: FORMULARIO DE CAPTURA (GridPane) ---
        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(12);
        formGrid.setPadding(new Insets(20));
        formGrid.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 6; -fx-border-color: #27272A; -fx-border-radius: 6;", COLOR_CARD));

        // Campos de entrada estandarizados
        Label lblNumControl = new Label("Número de Control:");
        lblNumControl.setStyle(STYLE_LABEL);
        TextField txtNumControl = new TextField();
        txtNumControl.setPromptText("Ej. 22130245");
        txtNumControl.setStyle(STYLE_INPUT);

        Label lblCorreo = new Label("Correo Institucional:");
        lblCorreo.setStyle(STYLE_LABEL);
        TextField txtCorreo = new TextField();
        txtCorreo.setPromptText("Ej. l22130245@chetumal.tecnm.mx");
        txtCorreo.setStyle(STYLE_INPUT);

        Label lblNombre = new Label("Nombre Completo:");
        lblNombre.setStyle(STYLE_LABEL);
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre(s) y Apellidos");
        txtNombre.setStyle(STYLE_INPUT);

        Label lblTipo = new Label("Tipo de Miembro:");
        lblTipo.setStyle(STYLE_LABEL);
        ComboBox<String> comboTipo = new ComboBox<>(FXCollections.observableArrayList(
            "Estudiante", "Docente", "Administrativo", "Externo"
        ));
        comboTipo.setPromptText("Seleccionar...");
        comboTipo.setMaxWidth(Double.MAX_VALUE);
        comboTipo.setStyle(STYLE_INPUT + " -fx-background-color: " + COLOR_INPUT + ";");

        // Elementos visuales dinámicos de fidelidad (Estatus de Puntos acumulados)
        Label lblInfoLealtad = new Label("Métricas de Lealtad:");
        lblInfoLealtad.setStyle(STYLE_LABEL);
        
        HBox statusLealtadBox = new HBox(15);
        statusLealtadBox.setAlignment(Pos.CENTER_LEFT);
        
        Label lblPuntos = new Label("0 pts");
        lblPuntos.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        lblPuntos.setStyle("-fx-text-fill: #10B981; -fx-background-color: #064E3B; -fx-padding: 4 8; -fx-background-radius: 4;");
        
        Label lblNivel = new Label("BRONCE");
        lblNivel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        lblNivel.setStyle("-fx-text-fill: #F59E0B; -fx-background-color: #78350F; -fx-padding: 4 8; -fx-background-radius: 4;");
        statusLealtadBox.getChildren().addAll(lblPuntos, lblNivel);

        // Distribución en las coordenadas de la rejilla
        formGrid.add(lblNumControl, 0, 0);   formGrid.add(txtNumControl, 1, 0);
        formGrid.add(lblCorreo, 0, 1);       formGrid.add(txtCorreo, 1, 1);
        formGrid.add(lblNombre, 0, 2);       formGrid.add(txtNombre, 1, 2);
        formGrid.add(lblTipo, 0, 3);         formGrid.add(comboTipo, 1, 3);
        formGrid.add(lblInfoLealtad, 0, 4);  formGrid.add(statusLealtadBox, 1, 4);

        ColumnConstraints col1 = new ColumnConstraints(140);
        ColumnConstraints col2 = new ColumnConstraints(230);
        formGrid.getColumnConstraints().addAll(col1, col2);
        mainLayout.setCenter(formGrid);

        // --- BOTONERA DE CONTROL (Footer) ---
        HBox footerBox = new HBox(10);
        footerBox.setAlignment(Pos.CENTER_RIGHT);
        footerBox.setPadding(new Insets(16, 0, 0, 0));

        Button btnEliminar = new Button("Dar de Baja");
        btnEliminar.setStyle(String.format("-fx-background-color: transparent; -fx-text-fill: %s; -fx-border-color: %s; -fx-border-radius: 4; -fx-padding: 8 14; -fx-cursor: hand;", COLOR_DANGER, COLOR_DANGER));
        btnEliminar.setOnMouseEntered(e -> btnEliminar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-border-color: %s; -fx-border-radius: 4; -fx-padding: 8 14; -fx-cursor: hand;", COLOR_DANGER, COLOR_DANGER)));
        btnEliminar.setOnMouseExited(e -> btnEliminar.setStyle(String.format("-fx-background-color: transparent; -fx-text-fill: %s; -fx-border-color: %s; -fx-border-radius: 4; -fx-padding: 8 14; -fx-cursor: hand;", COLOR_DANGER, COLOR_DANGER)));

        Button btnActualizar = new Button("Actualizar");
        btnActualizar.setStyle("-fx-background-color: #27272A; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 8 18; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-color: #3F3F46; -fx-border-radius: 4;");
        btnActualizar.setOnMouseEntered(e -> btnActualizar.setStyle("-fx-background-color: #3F3F46; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 8 18; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-color: #52525B; -fx-border-radius: 4;"));
        btnActualizar.setOnMouseExited(e -> btnActualizar.setStyle("-fx-background-color: #27272A; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 8 18; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-color: #3F3F46; -fx-border-radius: 4;"));

        Button btnRegistrar = new Button("Registrar Nuevo");
        btnRegistrar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 9 20; -fx-font-weight: bold; -fx-cursor: hand;", COLOR_ACCENT));
        btnRegistrar.setOnMouseEntered(e -> btnRegistrar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 9 20; -fx-font-weight: bold; -fx-cursor: hand;", COLOR_ACCENT_HOVER)));
        btnRegistrar.setOnMouseExited(e -> btnRegistrar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 9 20; -fx-font-weight: bold; -fx-cursor: hand;", COLOR_ACCENT)));

        footerBox.getChildren().addAll(btnEliminar, btnActualizar, btnRegistrar);
        mainLayout.setBottom(footerBox);

        // --- MANEJO DE LÓGICA DE BASE DE DATOS ---

        // Buscar por ID
        btnBuscar.setOnAction(e -> {
            try {
                if(txtId.getText().trim().isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Defina un ID numérico de usuario para consultar.").show();
                    return;
                }
                // Conexión al DAO para leer la base de datos
                Usuario u = new UsuarioDAO().buscarPorId(Integer.parseInt(txtId.getText().trim()));
                if(u != null) {
                    txtNumControl.setText(u.getNumeroControl());
                    txtCorreo.setText(u.getCorreoInstitucional());
                    txtNombre.setText(u.getNombre());
                    comboTipo.setValue(u.getTipoUsuario());
                    
                    // Modificar etiquetas de lealtad recuperadas de la base de datos
                    lblPuntos.setText(u.getPuntosLealtad() + " pts");
                    lblNivel.setText(u.getNivelMembresia().toUpperCase());
                } else {
                    new Alert(Alert.AlertType.WARNING, "El usuario consultado no se encuentra registrado.").show();
                }
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error de formato: El ID de búsqueda debe ser estrictamente entero.").show();
            }
        });

        // Registrar Nuevo
        btnRegistrar.setOnAction(e -> {
            try {
                if(txtNumControl.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty() || comboTipo.getValue() == null) {
                    new Alert(Alert.AlertType.WARNING, "Por favor complete los campos obligatorios del registro.").show();
                    return;
                }
                // Instancia con parámetros de inicialización por defecto
                Usuario u = new Usuario(0, txtNumControl.getText().trim(), txtCorreo.getText().trim(), 
                                        txtNombre.getText().trim(), comboTipo.getValue(), LocalDate.now(), 0, "Bronce");
                
                // Conexión al DAO para insertar en la base de datos
                new UsuarioDAO().registrar(u);
                new Alert(Alert.AlertType.INFORMATION, "Usuario dado de alta exitosamente en el sistema RedNova.").show();
                
                // Limpieza post operación
                txtNumControl.clear(); txtCorreo.clear(); txtNombre.clear(); comboTipo.setValue(null);
            } catch (Exception ex) { 
                new Alert(Alert.AlertType.ERROR, "Fallo en la persistencia: " + ex.getMessage()).show(); 
            }
        });

        // Actualizar
        btnActualizar.setOnAction(e -> {
            try {
                if(txtId.getText().trim().isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Se requiere el ID del usuario en la sección superior para procesar modificaciones.").show();
                    return;
                }
                // Mapear los datos modificados manteniendo los puntos y nivel vigentes
                Usuario u = new Usuario(
                    Integer.parseInt(txtId.getText().trim()), 
                    txtNumControl.getText().trim(), 
                    txtCorreo.getText().trim(), 
                    txtNombre.getText().trim(), 
                    comboTipo.getValue(), 
                    LocalDate.now(), // La capa DAO puede ignorar o actualizar esta según la estructura
                    Integer.parseInt(lblPuntos.getText().replace(" pts", "").trim()), 
                    lblNivel.getText()
                );
                
                // Conexión al DAO para hacer un UPDATE en la base de datos
                new UsuarioDAO().actualizar(u);
                new Alert(Alert.AlertType.INFORMATION, "Perfil de usuario actualizado correctamente.").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error en la actualización de datos: " + ex.getMessage()).show();
            }
        });

        // Eliminar / Dar de Baja
        btnEliminar.setOnAction(e -> {
            try {
                if(txtId.getText().trim().isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Establezca el ID del usuario que se dará de baja.").show();
                    return;
                }
                
                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea revocar el acceso y eliminar de manera permanente a este usuario de la base de datos?", ButtonType.YES, ButtonType.NO);
                confirmacion.setHeaderText("Advertencia de Eliminación");
                confirmacion.showAndWait().ifPresent(res -> {
                    if (res == ButtonType.YES) {
                        try {
                            // --- LÍNEA DESCOMENTADA Y CORREGIDA: Llamada real a la BD ---
                            new UsuarioDAO().eliminar(Integer.parseInt(txtId.getText().trim()));
                            
                            new Alert(Alert.AlertType.INFORMATION, "El usuario ha sido removido del ecosistema.").show();
                            
                            // Reseteo visual completo
                            txtId.clear(); txtNumControl.clear(); txtCorreo.clear(); txtNombre.clear(); 
                            comboTipo.setValue(null); lblPuntos.setText("0 pts"); lblNivel.setText("BRONCE");
                        } catch (Exception daoEx) {
                            // Este Catch interno atrapa problemas específicos al intentar borrar en la base de datos
                            new Alert(Alert.AlertType.ERROR, "Imposible eliminar: El usuario cuenta con transacciones activas o registros de renta asociados.\nDetalle: " + daoEx.getMessage()).show();
                        }
                    }
                });
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error al procesar el ID ingresado. Verifique que sea un número.").show();
            }
        });

        // --- CONFIGURACIÓN DE ESCENA ---
        Scene scene = new Scene(mainLayout, 450, 490);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}