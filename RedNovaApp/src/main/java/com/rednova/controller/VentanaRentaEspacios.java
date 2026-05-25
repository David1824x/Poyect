package com.rednova.controller;

import com.rednova.dao.ReservaEspacioDAO;
import com.rednova.model.ReservaEspacio;
import java.sql.Date;
import com.rednova.dao.UsuarioDAO;
import com.rednova.dao.EspacioDAO;
import com.rednova.model.Usuario;
import com.rednova.model.Espacio;
import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.util.ArrayList;
import java.util.List;

public class VentanaRentaEspacios {

    private final String COLOR_BG = "#121214";
    private final String COLOR_CARD = "#1A1A1E";
    private final String COLOR_INPUT = "#26262B";
    private final String COLOR_ACCENT = "#C3073F";
    private final String COLOR_TEXT_PRIMARY = "#FFFFFF";
    private final String COLOR_TEXT_MUTED = "#A0A0A5";

    private final String STYLE_INPUT = String.format(
        "-fx-background-color: %s; -fx-text-fill: %s; -fx-border-color: #3F3F46; " +
        "-fx-border-radius: 4; -fx-background-radius: 4; -fx-padding: 8 12; -fx-font-size: 13px;",
        COLOR_INPUT, COLOR_TEXT_PRIMARY
    );
    private final String STYLE_COMBO = STYLE_INPUT + " -fx-base: " + COLOR_INPUT + "; -fx-control-inner-background: " + COLOR_INPUT + ";";
    private final String STYLE_LABEL = String.format("-fx-text-fill: %s; -fx-font-size: 13px; -fx-font-weight: bold;", COLOR_TEXT_MUTED);

    private final int[] puntosDisponiblesCliente = {0};

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("RedNova OS - Reserva de Espacios Co-working");

        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + COLOR_BG + ";");
        mainLayout.setPadding(new Insets(24));

        // --- HEADER ---
        VBox headerBox = new VBox(4);
        headerBox.setPadding(new Insets(0, 0, 15, 0));
        Label lblTitle = new Label("RESERVA DE ESPACIOS");
        lblTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        lblTitle.setStyle("-fx-text-fill: " + COLOR_TEXT_PRIMARY + ";");
        Label lblSubtitle = new Label("Asignación de Cubículos, Salas de Juntas y Oficinas");
        lblSubtitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        lblSubtitle.setStyle("-fx-text-fill: " + COLOR_ACCENT + ";");
        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #27272A; -fx-padding: 6 0 0 0;");
        headerBox.getChildren().addAll(lblTitle, lblSubtitle, separator);
        mainLayout.setTop(headerBox);

        // --- FORMULARIO ---
        GridPane formGrid = new GridPane();
        formGrid.setHgap(15); formGrid.setVgap(14); formGrid.setPadding(new Insets(22));
        formGrid.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 6; -fx-border-color: #27272A; -fx-border-radius: 6;", COLOR_CARD));

        //ID Usuario y Nombre reactivo
        Label lblUsuarioId = new Label("ID Usuario:");
        lblUsuarioId.setStyle(STYLE_LABEL);
        TextField txtUsuarioId = new TextField();
        txtUsuarioId.setStyle(STYLE_INPUT);

        Label lblNombreClienteTexto = new Label("Nombre Usuario:");
        lblNombreClienteTexto.setStyle(STYLE_LABEL);
        Label lblNombreClienteValor = new Label("---");
        lblNombreClienteValor.setStyle("-fx-text-fill: #E4E4E7; -fx-font-size: 13px; -fx-font-style: italic;");

        //Nuevo campo: ID Espacio
        Label lblIdEspacio = new Label("ID Espacio:");
        lblIdEspacio.setStyle(STYLE_LABEL);
        TextField txtIdEspacio = new TextField();
        txtIdEspacio.setStyle(STYLE_INPUT);

        //ComboBox Espacios
        Label lblEspacio = new Label("Seleccionar Espacio:");
        lblEspacio.setStyle(STYLE_LABEL);
        ComboBox<Espacio> comboEspacio = new ComboBox<>();
        comboEspacio.setStyle(STYLE_COMBO);
        comboEspacio.setMaxWidth(Double.MAX_VALUE);
        comboEspacio.setPromptText("Seleccione área...");
        
        comboEspacio.setConverter(new StringConverter<Espacio>() {
            @Override
            public String toString(Espacio esp) {
                return (esp == null) ? "" : esp.getTipoEspacio() + " ($" + esp.getPrecioHora() + "/hr)";
            }
            @Override public Espacio fromString(String string) { return null; }
        });

        // --- CARGA SEGURA DESDE DB ---
        try {
            EspacioDAO dao = new EspacioDAO();
            List<Espacio> listaEspacios = dao.buscarTodos();
            if (listaEspacios != null) {
                comboEspacio.setItems(FXCollections.observableArrayList(listaEspacios));
            } else {
                comboEspacio.setPromptText("⚠️ Error: Sin conexión");
            }
        } catch (Exception ex) {
            comboEspacio.setPromptText("⚠️ Error de conexión");
        }

        // --- LÓGICA DE SINCRONIZACIÓN ID <-> COMBO ---
        //1. Al escribir ID y dar Enter: busca el espacio
        txtIdEspacio.setOnAction(e -> {
            try {
                int idBuscado = Integer.parseInt(txtIdEspacio.getText().trim());
                for (Espacio esp : comboEspacio.getItems()) {
                    if (esp.getIdEspacio() == idBuscado) {
                        comboEspacio.setValue(esp);
                        break;
                    }
                }
            } catch (NumberFormatException ex) { /* Ignorar entrada inválida */ }
        });

        //2. Al seleccionar en el combo: llena el campo ID
        comboEspacio.setOnAction(e -> {
            if (comboEspacio.getValue() != null) {
                txtIdEspacio.setText(String.valueOf(comboEspacio.getValue().getIdEspacio()));
            }
        });

        //Horas de uso
        Label lblHoras = new Label("Horas de Reserva:");
        lblHoras.setStyle(STYLE_LABEL);
        TextField txtHoras = new TextField();
        txtHoras.setStyle(STYLE_INPUT);

        //Puntos de Lealtad
        Label lblPuntosTexto = new Label("Descuento Puntos:");
        lblPuntosTexto.setStyle(STYLE_LABEL);
        HBox puntosContainer = new HBox(10);
        puntosContainer.setAlignment(Pos.CENTER_LEFT);
        TextField txtPuntosCanjear = new TextField("0");
        txtPuntosCanjear.setStyle(STYLE_INPUT);
        txtPuntosCanjear.setPrefWidth(80);
        Label lblPuntosDisponiblesValor = new Label("(Disponibles: 0 pts)");
        lblPuntosDisponiblesValor.setStyle("-fx-text-fill: " + COLOR_TEXT_MUTED + "; -fx-font-size: 12px;");
        puntosContainer.getChildren().addAll(txtPuntosCanjear, lblPuntosDisponiblesValor);

        //Costo Final
        Label lblTotalTexto = new Label("Monto Asignado:");
        lblTotalTexto.setStyle(STYLE_LABEL);
        Label lblTotalDinero = new Label("$0.00");
        lblTotalDinero.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        lblTotalDinero.setStyle("-fx-text-fill: #10B981;");

        formGrid.add(lblUsuarioId, 0, 0);            formGrid.add(txtUsuarioId, 1, 0);
        formGrid.add(lblNombreClienteTexto, 0, 1);   formGrid.add(lblNombreClienteValor, 1, 1);
        formGrid.add(lblIdEspacio, 0, 2);            formGrid.add(txtIdEspacio, 1, 2);
        formGrid.add(lblEspacio, 0, 3);              formGrid.add(comboEspacio, 1, 3);
        formGrid.add(lblHoras, 0, 4);                formGrid.add(txtHoras, 1, 4);
        formGrid.add(lblPuntosTexto, 0, 5);          formGrid.add(puntosContainer, 1, 5);
        formGrid.add(lblTotalTexto, 0, 6);           formGrid.add(lblTotalDinero, 1, 6);

        formGrid.getColumnConstraints().addAll(new ColumnConstraints(140), new ColumnConstraints(260));
        mainLayout.setCenter(formGrid);

        // --- LÓGICA DE CÁLCULO EN TIEMPO REAL ---
        Runnable calcularTotalDinamico = () -> {
            try {
                Espacio espSeleccionado = comboEspacio.getValue();
                int horas = txtHoras.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtHoras.getText().trim());
                int ptsACanjear = txtPuntosCanjear.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtPuntosCanjear.getText().trim());
                
                if (espSeleccionado != null && horas > 0) {
                    double subtotal = horas * espSeleccionado.getPrecioHora();

                    if (ptsACanjear > puntosDisponiblesCliente[0]) {
                        ptsACanjear = puntosDisponiblesCliente[0];
                        txtPuntosCanjear.setText(String.valueOf(ptsACanjear));
                    }
                    if (ptsACanjear > subtotal) {
                        ptsACanjear = (int) subtotal;
                        txtPuntosCanjear.setText(String.valueOf(ptsACanjear));
                    }

                    lblTotalDinero.setText(String.format("$%.2f", subtotal - ptsACanjear));
                } else {
                    lblTotalDinero.setText("$0.00");
                }
            } catch (NumberFormatException ex) { lblTotalDinero.setText("$ --.--"); }
        };

        txtHoras.textProperty().addListener((o, old, n) -> calcularTotalDinamico.run());
        comboEspacio.valueProperty().addListener((o, old, n) -> calcularTotalDinamico.run());
        txtPuntosCanjear.textProperty().addListener((o, old, n) -> calcularTotalDinamico.run());

        //Escuchador del Usuario
        txtUsuarioId.textProperty().addListener((o, old, nuevoId) -> {
            String idLimpio = nuevoId.trim();
            if (idLimpio.isEmpty()) {
                lblNombreClienteValor.setText("---");
                lblPuntosDisponiblesValor.setText("(Disponibles: 0 pts)");
                puntosDisponiblesCliente[0] = 0;
                txtPuntosCanjear.setText("0");
                return;
            }
            try {
                Usuario usuario = new UsuarioDAO().buscarPorId(Integer.parseInt(idLimpio));
                if (usuario != null) {
                    lblNombreClienteValor.setText(usuario.getNombre());
                    lblPuntosDisponiblesValor.setText("(Disponibles: " + usuario.getPuntosLealtad() + " pts)");
                    puntosDisponiblesCliente[0] = usuario.getPuntosLealtad();
                } else {
                    lblNombreClienteValor.setText("❌ No registrado");
                    puntosDisponiblesCliente[0] = 0;
                }
            } catch (Exception ex) { lblNombreClienteValor.setText("❌ ID Inválido o sin conexión"); }
            calcularTotalDinamico.run();
        });

        // --- FOOTER ---
        HBox footerBox = new HBox(12); footerBox.setAlignment(Pos.CENTER_RIGHT); footerBox.setPadding(new Insets(18, 0, 0, 0));
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: transparent; -fx-text-fill: " + COLOR_TEXT_MUTED + "; -fx-border-color: #3F3F46; -fx-border-radius: 4; -fx-padding: 9 18; -fx-font-weight: bold; -fx-cursor: hand;");
        btnCancelar.setOnAction(e -> stage.close());

        Button btnRegistrar = new Button("Confirmar Espacio");
        btnRegistrar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 10 22; -fx-font-weight: bold; -fx-cursor: hand;", COLOR_ACCENT));
        footerBox.getChildren().addAll(btnCancelar, btnRegistrar);
        mainLayout.setBottom(footerBox);

        //Registro final
        btnRegistrar.setOnAction(e -> {

    try {

        Espacio espSeleccionado = comboEspacio.getValue();

        if(txtUsuarioId.getText().isEmpty()
            || espSeleccionado == null
            || txtHoras.getText().isEmpty()) {

            new Alert(
                Alert.AlertType.WARNING,
                "Por favor complete todos los campos."
            ).show();

            return;
        }

        int horas = Integer.parseInt(
            txtHoras.getText().trim()
        );

        if (horas <= 0) {

            new Alert(
                Alert.AlertType.ERROR,
                "La duración de reserva debe ser mayor a 0 horas."
            ).show();

            return;
        }

        double totalFinal = Double.parseDouble(
            lblTotalDinero.getText().replace("$", "")
        );

        ReservaEspacio reserva = new ReservaEspacio();

        reserva.setIdUsuario(
            Integer.parseInt(txtUsuarioId.getText().trim())
        );

        reserva.setIdEspacio(
            espSeleccionado.getIdEspacio()
        );

        reserva.setCantidadHoras(horas);

        reserva.setPrecioTotal(totalFinal);

        reserva.setFechaReserva(
            new Date(System.currentTimeMillis())
        );

        ReservaEspacioDAO dao = new ReservaEspacioDAO();

        dao.registrar(reserva);

        new Alert(
            Alert.AlertType.INFORMATION,
            "Reserva registrada correctamente."
        ).show();

        stage.close();

    } catch (Exception ex) {

        new Alert(
            Alert.AlertType.ERROR,
            "Error en el sistema: " + ex.getMessage()
        ).show();
    }
});

        Scene scene = new Scene(mainLayout, 620, 560);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}