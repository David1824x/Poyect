package com.rednova.controller;

import com.rednova.dao.VentaDAO;
import com.rednova.dao.ProductoDAO; // DAO de productos actualizado
import com.rednova.dao.UsuarioDAO;
import com.rednova.model.Producto;
import com.rednova.model.Usuario;
import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.StringConverter; // IMPORTANTE para limpiar el ComboBox
import java.util.List;

public class VentanaVentas {

    // Paleta de colores formal (Estilo Dark Premium con el acento Carmesí de RedNova)
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
    private final String STYLE_LABEL = String.format(
        "-fx-text-fill: %s; -fx-font-size: 13px; -fx-font-weight: bold;", COLOR_TEXT_MUTED
    );

    private final int[] puntosDisponiblesCliente = {0};

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("RedNova OS - Terminal Punto de Venta Inteligente");

        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + COLOR_BG + ";");
        mainLayout.setPadding(new Insets(24));

        // --- HEADER ---
        VBox headerBox = new VBox(4);
        headerBox.setPadding(new Insets(0, 0, 15, 0));
        Label lblTitle = new Label("NUEVA TRANSACCIÓN");
        lblTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        lblTitle.setStyle("-fx-text-fill: " + COLOR_TEXT_PRIMARY + ";");
        Label lblSubtitle = new Label("Terminal de Punto de Venta Automatizada");
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

        Label lblUsuarioId = new Label("ID Cliente / Usuario:");
        lblUsuarioId.setStyle(STYLE_LABEL);
        TextField txtUsuarioId = new TextField();
        txtUsuarioId.setStyle(STYLE_INPUT);

        Label lblNombreClienteTexto = new Label("Nombre Cliente:");
        lblNombreClienteTexto.setStyle(STYLE_LABEL);
        Label lblNombreClienteValor = new Label("---");
        lblNombreClienteValor.setStyle("-fx-text-fill: #E4E4E7; -fx-font-size: 13px; -fx-font-style: italic;");

        Label lblProducto = new Label("Seleccionar Producto:");
        lblProducto.setStyle(STYLE_LABEL);
        ComboBox<Producto> comboProducto = new ComboBox<>();
        comboProducto.setStyle(STYLE_COMBO);
        comboProducto.setMaxWidth(Double.MAX_VALUE);
        comboProducto.setPromptText("Seleccione un ítem...");

        // SOLUCIÓN AL NOMBRE RARO: Forzar al ComboBox a renderizar solo el Nombre del Producto
        comboProducto.setConverter(new StringConverter<Producto>() {
            @Override
            public String toString(Producto prod) {
                // Si el objeto no es nulo, muestra el nombre y su stock actual como ayuda visual
                return (prod == null) ? "" : prod.getNombreProducto()+ " (Stock: " + prod.getStockActual()+ ")";
            }
            @Override
            public Producto fromString(String string) {
                return null; 
            }
        });

        // Carga de catálogo de productos
        try {
            List<Producto> listaProductos = new ProductoDAO().buscarTodos();
            comboProducto.setItems(FXCollections.observableArrayList(listaProductos));
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Error al cargar catálogo: " + ex.getMessage()).show();
        }

        Label lblCantidad = new Label("Cantidad:");
        lblCantidad.setStyle(STYLE_LABEL);
        TextField txtCantidad = new TextField();
        txtCantidad.setStyle(STYLE_INPUT);

        Label lblMetodo = new Label("Método de Pago:");
        lblMetodo.setStyle(STYLE_LABEL);
        ComboBox<String> comboMetodo = new ComboBox<>(FXCollections.observableArrayList("Efectivo", "Tarjeta", "Puntos Lealtad"));
        comboMetodo.setValue("Efectivo");
        comboMetodo.setStyle(STYLE_COMBO);
        comboMetodo.setMaxWidth(Double.MAX_VALUE);

        Label lblPuntosTexto = new Label("Canjear Puntos:");
        lblPuntosTexto.setStyle(STYLE_LABEL);
        HBox puntosContainer = new HBox(10);
        puntosContainer.setAlignment(Pos.CENTER_LEFT);
        TextField txtPuntosCanjear = new TextField("0");
        txtPuntosCanjear.setStyle(STYLE_INPUT);
        txtPuntosCanjear.setPrefWidth(80);
        Label lblPuntosDisponiblesValor = new Label("(Disponibles: 0 pts)");
        lblPuntosDisponiblesValor.setStyle("-fx-text-fill: " + COLOR_TEXT_MUTED + "; -fx-font-size: 12px;");
        puntosContainer.getChildren().addAll(txtPuntosCanjear, lblPuntosDisponiblesValor);

        Label lblTotalTexto = new Label("Monto Total:");
        lblTotalTexto.setStyle(STYLE_LABEL);
        Label lblTotalDinero = new Label("$0.00");
        lblTotalDinero.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        lblTotalDinero.setStyle("-fx-text-fill: #10B981;");

        formGrid.add(lblUsuarioId, 0, 0);           formGrid.add(txtUsuarioId, 1, 0);
        formGrid.add(lblNombreClienteTexto, 0, 1);  formGrid.add(lblNombreClienteValor, 1, 1);
        formGrid.add(lblProducto, 0, 2);            formGrid.add(comboProducto, 1, 2);
        formGrid.add(lblCantidad, 0, 3);            formGrid.add(txtCantidad, 1, 3);
        formGrid.add(lblMetodo, 0, 4);              formGrid.add(comboMetodo, 1, 4);
        formGrid.add(lblPuntosTexto, 0, 5);         formGrid.add(puntosContainer, 1, 5);
        formGrid.add(lblTotalTexto, 0, 6);          formGrid.add(lblTotalDinero, 1, 6);

        formGrid.getColumnConstraints().addAll(new ColumnConstraints(140), new ColumnConstraints(260));
        mainLayout.setCenter(formGrid);

        // --- LÓGICA DE CÁLCULO EN TIEMPO REAL ---
        Runnable calcularTotalDinamico = () -> {
            try {
                Producto prodSeleccionado = comboProducto.getValue();
                int cant = txtCantidad.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtCantidad.getText().trim());
                int ptsACanjear = txtPuntosCanjear.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtPuntosCanjear.getText().trim());
                
                if (prodSeleccionado != null && cant > 0) {
                    double precioUnitario = prodSeleccionado.getPrecioUnitario();
                    double subtotal = cant * precioUnitario;

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

        txtCantidad.textProperty().addListener((o, old, n) -> calcularTotalDinamico.run());
        comboProducto.valueProperty().addListener((o, old, n) -> calcularTotalDinamico.run());
        txtPuntosCanjear.textProperty().addListener((o, old, n) -> calcularTotalDinamico.run());

        // --- BUSQUEDA AUTOMÁTICA DE USUARIO ---
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
            } catch (Exception ex) { lblNombreClienteValor.setText("❌ ID Inválido"); }
            calcularTotalDinamico.run();
        });

        // --- FOOTER ---
        HBox footerBox = new HBox(12); footerBox.setAlignment(Pos.CENTER_RIGHT); footerBox.setPadding(new Insets(18, 0, 0, 0));
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: transparent; -fx-text-fill: " + COLOR_TEXT_MUTED + "; -fx-border-color: #3F3F46; -fx-border-radius: 4; -fx-padding: 9 18; -fx-cursor: hand;");
        btnCancelar.setOnAction(e -> stage.close());

        Button btnRegistrar = new Button("Confirmar Transacción");
        btnRegistrar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 10 22; -fx-font-weight: bold; -fx-cursor: hand;", COLOR_ACCENT));
        footerBox.getChildren().addAll(btnCancelar, btnRegistrar);
        mainLayout.setBottom(footerBox);

        // --- LÓGICA DE REGISTRO INTEGRADA CON DESCUENTO DE INVENTARIO ---
        btnRegistrar.setOnAction(e -> {
            try {
                Producto prodSeleccionado = comboProducto.getValue();
                
                if(txtUsuarioId.getText().isEmpty() || prodSeleccionado == null || txtCantidad.getText().isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Por favor complete todos los campos.").show();
                    return;
                }

                int cant = Integer.parseInt(txtCantidad.getText().trim());
                int stockDisponible = prodSeleccionado.getStockActual(); // Obtiene las existencias actuales

                // VALIDACIÓN DE STOCK: Evita vender lo que no hay
                if (cant > stockDisponible) {
                    new Alert(Alert.AlertType.WARNING, "Stock insuficiente. Unidades disponibles: " + stockDisponible).show();
                    return;
                }
                if (cant <= 0) {
                    new Alert(Alert.AlertType.ERROR, "La cantidad debe ser mayor a cero.").show();
                    return;
                }

                int uId = Integer.parseInt(txtUsuarioId.getText().trim());
                int pId = prodSeleccionado.getIdProducto();
                double precio = prodSeleccionado.getPrecioUnitario();
                int ptsUtilizados = Integer.parseInt(txtPuntosCanjear.getText().trim());
                double totalFinal = (cant * precio) - ptsUtilizados;

                // 1. Guarda la venta en la base de datos
                new VentaDAO().registrarVentaCompleta(uId, totalFinal, totalFinal, comboMetodo.getValue(), pId, cant, precio);
                
                // 2. ACTUALIZA EL INVENTARIO EN LA BASE DE DATOS
                int nuevoStock = stockDisponible - cant;
                new ProductoDAO().actualizarStock(pId, nuevoStock);
                
                new Alert(Alert.AlertType.INFORMATION, "Transacción completada e Inventario actualizado.").show();
                stage.close();
            } catch (Exception ex) { 
                new Alert(Alert.AlertType.ERROR, "Error transaccional: " + ex.getMessage()).show(); 
            }
        });

        Scene scene = new Scene(mainLayout, 620, 580);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}