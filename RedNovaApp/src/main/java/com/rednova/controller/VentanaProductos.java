package com.rednova.controller;

import com.rednova.dao.ProductoDAO;
import com.rednova.model.Producto;
import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;

public class VentanaProductos {

    //Paleta de colores institucional (RedNova Dark Premium)
    private final String COLOR_BG = "#121214";
    private final String COLOR_CARD = "#1A1A1E";
    private final String COLOR_INPUT = "#26262B";
    private final String COLOR_ACCENT = "#C3073F";
    private final String COLOR_ACCENT_HOVER = "#950530";
    private final String COLOR_DANGER = "#EF4444";
    private final String COLOR_TEXT_PRIMARY = "#FFFFFF";
    private final String COLOR_TEXT_MUTED = "#A0A0A5";

    //Variable interna para recordar que ID de producto se encuentra en edicion o baja activa
    private int idProductoSeleccionado = 0;

    //Reglas CSS para componentes homogéneos
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
        stage.setTitle("RedNova OS - Gestión de Inventario");

        // --- CONTENEDOR PRINCIPAL ---
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + COLOR_BG + ";");
        mainLayout.setPadding(new Insets(24));

        // --- ENCABEZADO: TÍTULO Y CONTROL DE BÚSQUEDA ---
        VBox headerBox = new VBox(12);
        headerBox.setPadding(new Insets(0, 0, 16, 0));

        VBox titleTitleBox = new VBox(2);
        Label lblTitle = new Label("CONTROL DE INVENTARIO");
        lblTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        lblTitle.setStyle("-fx-text-fill: " + COLOR_TEXT_PRIMARY + ";");
        
        Label lblSubtitle = new Label("Catálogo de Productos y Almacén");
        lblSubtitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        lblSubtitle.setStyle("-fx-text-fill: " + COLOR_ACCENT + ";");
        titleTitleBox.getChildren().addAll(lblTitle, lblSubtitle);

        // Barra de búsqueda rápida optimizada sin caja de texto ID
        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setPadding(new Insets(8, 12, 8, 12));
        searchBar.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 4; -fx-border-color: #27272A; -fx-border-radius: 4;", COLOR_CARD));

        Button btnBuscar = new Button("Buscar Productos");
        btnBuscar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 6 14;", COLOR_INPUT));
        btnBuscar.setOnMouseEntered(e -> btnBuscar.setStyle("-fx-background-color: #3F3F46; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 6 14;"));
        btnBuscar.setOnMouseExited(e -> btnBuscar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 6 14;", COLOR_INPUT)));

        searchBar.getChildren().addAll(btnBuscar);
        headerBox.getChildren().addAll(titleTitleBox, searchBar);
        mainLayout.setTop(headerBox);

        // --- CUERPO: FORMULARIO DETALLADO (GridPane) ---
        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(12);
        formGrid.setPadding(new Insets(20));
        formGrid.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 6; -fx-border-color: #27272A; -fx-border-radius: 6;", COLOR_CARD));

        //Componentes del Formulario
        Label lblNombre = new Label("Nombre del Producto:");
        lblNombre.setStyle(STYLE_LABEL);
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Ej. Café Americano 16oz");
        txtNombre.setStyle(STYLE_INPUT);

        Label lblCategoria = new Label("Categoría:");
        lblCategoria.setStyle(STYLE_LABEL);
        ComboBox<String> comboCategoria = new ComboBox<>(FXCollections.observableArrayList(
            "Bebida", "Alimento", "Especialidad"
        ));
        comboCategoria.setPromptText("Seleccionar...");
        comboCategoria.setMaxWidth(Double.MAX_VALUE);
        
        //Se inyecta -fx-base y -fx-control-inner-background para forzar que la lista desplegable interna sea oscura y el texto blanco
        comboCategoria.setStyle(STYLE_INPUT + " -fx-background-color: " + COLOR_INPUT + "; -fx-base: " + COLOR_INPUT + "; -fx-control-inner-background: " + COLOR_INPUT + ";");

        Label lblPrecio = new Label("Precio Público ($):");
        lblPrecio.setStyle(STYLE_LABEL);
        TextField txtPrecio = new TextField();
        txtPrecio.setPromptText("0.00");
        txtPrecio.setStyle(STYLE_INPUT);

        Label lblCosto = new Label("Costo Proveedor ($):");
        lblCosto.setStyle(STYLE_LABEL);
        TextField txtCosto = new TextField();
        txtCosto.setPromptText("0.00");
        txtCosto.setStyle(STYLE_INPUT);

        Label lblStock = new Label("Existencia Actual:");
        lblStock.setStyle(STYLE_LABEL);
        TextField txtStock = new TextField();
        txtStock.setPromptText("Cantidad");
        txtStock.setStyle(STYLE_INPUT);

        Label lblMinimo = new Label("Stock Mínimo:");
        lblMinimo.setStyle(STYLE_LABEL);
        TextField txtMinimo = new TextField();
        txtMinimo.setPromptText("Alerta de reabastecimiento");
        txtMinimo.setStyle(STYLE_INPUT);

        //Registro en la cuadrícula (Columna, Fila)
        formGrid.add(lblNombre, 0, 0);     formGrid.add(txtNombre, 1, 0);
        formGrid.add(lblCategoria, 0, 1);  formGrid.add(comboCategoria, 1, 1);
        formGrid.add(lblPrecio, 0, 2);     formGrid.add(txtPrecio, 1, 2);
        formGrid.add(lblCosto, 0, 3);      formGrid.add(txtCosto, 1, 3);
        formGrid.add(lblStock, 0, 4);      formGrid.add(txtStock, 1, 4);
        formGrid.add(lblMinimo, 0, 5);     formGrid.add(txtMinimo, 1, 5);

        ColumnConstraints col1 = new ColumnConstraints(140);
        ColumnConstraints col2 = new ColumnConstraints(230);
        formGrid.getColumnConstraints().addAll(col1, col2);
        mainLayout.setCenter(formGrid);

        // --- CONTROLADORES E INTERACCIÓN DE BOTONES (Footer) ---
        HBox footerBox = new HBox(10);
        footerBox.setAlignment(Pos.CENTER_RIGHT);
        footerBox.setPadding(new Insets(16, 0, 0, 0));

        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setStyle(String.format("-fx-background-color: transparent; -fx-text-fill: %s; -fx-border-color: %s; -fx-border-radius: 4; -fx-padding: 8 16; -fx-cursor: hand;", COLOR_DANGER, COLOR_DANGER));
        btnEliminar.setOnMouseEntered(e -> btnEliminar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-border-color: %s; -fx-border-radius: 4; -fx-padding: 8 16; -fx-cursor: hand;", COLOR_DANGER, COLOR_DANGER)));
        btnEliminar.setOnMouseExited(e -> btnEliminar.setStyle(String.format("-fx-background-color: transparent; -fx-text-fill: %s; -fx-border-color: %s; -fx-border-radius: 4; -fx-padding: 8 16; -fx-cursor: hand;", COLOR_DANGER, COLOR_DANGER)));

        Button btnActualizar = new Button("Actualizar");
        btnActualizar.setStyle("-fx-background-color: #27272A; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 8 18; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-color: #3F3F46; -fx-border-radius: 4;");
        btnActualizar.setOnMouseEntered(e -> btnActualizar.setStyle("-fx-background-color: #3F3F46; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 8 18; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-color: #52525B; -fx-border-radius: 4;"));
        btnActualizar.setOnMouseExited(e -> btnActualizar.setStyle("-fx-background-color: #27272A; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 8 18; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-color: #3F3F46; -fx-border-radius: 4;"));

        Button btnGuardar = new Button("Guardar Nuevo");
        btnGuardar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 9 20; -fx-font-weight: bold; -fx-cursor: hand;", COLOR_ACCENT));
        btnGuardar.setOnMouseEntered(e -> btnGuardar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 9 20; -fx-font-weight: bold; -fx-cursor: hand;", COLOR_ACCENT_HOVER)));
        btnGuardar.setOnMouseExited(e -> btnGuardar.setStyle(String.format("-fx-background-color: %s; -fx-text-fill: white; -fx-background-radius: 4; -fx-padding: 9 20; -fx-font-weight: bold; -fx-cursor: hand;", COLOR_ACCENT)));

        footerBox.getChildren().addAll(btnEliminar, btnActualizar, btnGuardar);
        mainLayout.setBottom(footerBox);

        // --- ACCIONES DE LÓGICA ---

        //Buscar todos
        btnBuscar.setOnAction(e -> {
            try {
                //Trae la lista completa de productos desde la base de datos MySQL
                List<Producto> lista = new ProductoDAO().buscarTodos();
                if (lista.isEmpty()) {
                    new Alert(Alert.AlertType.INFORMATION, "No existen productos registrados en el inventario.").show();
                    return;
                }
                
                //Invoca el catalogo general y mapea la fila elegida directamente al formulario
                Consultas.verProductos(lista, p -> {
                    idProductoSeleccionado = p.getIdProducto();
                    txtNombre.setText(p.getNombreProducto());
                    comboCategoria.setValue(p.getCategoria());
                    txtPrecio.setText(String.valueOf(p.getPrecioUnitario()));
                    txtCosto.setText(String.valueOf(p.getCostoUnitario()));
                    txtStock.setText(String.valueOf(p.getStockActual()));
                    txtMinimo.setText(String.valueOf(p.getStockMinimo()));
                });
            } catch (Exception ex) { 
                new Alert(Alert.AlertType.ERROR, "Error de interconexión al buscar productos: " + ex.getMessage()).show(); 
            }
        });

        //Guardar Nuevo
        btnGuardar.setOnAction(e -> {
            try {
                Producto p = new Producto(
                    0, 
                    txtNombre.getText().trim(), 
                    comboCategoria.getValue(), 
                    Double.parseDouble(txtPrecio.getText().trim()), 
                    Double.parseDouble(txtCosto.getText().trim()), 
                    Integer.parseInt(txtStock.getText().trim()), 
                    Integer.parseInt(txtMinimo.getText().trim())
                );
                
                //Inserta el objeto Producto en la base de datos
                new ProductoDAO().insertar(p);
                new Alert(Alert.AlertType.INFORMATION, "Registro consolidado con éxito.").show();
                
                //Limpia los campos post-registro para un nuevo ingreso
                txtNombre.clear(); comboCategoria.setValue(null);
                txtPrecio.clear(); txtCosto.clear(); txtStock.clear(); txtMinimo.clear();
                idProductoSeleccionado = 0;
            } catch (Exception ex) { 
                new Alert(Alert.AlertType.ERROR, "Inconsistencia de datos: Verifique los campos numéricos y que la categoría esté seleccionada.").show(); 
            }
        });

        //Actualizar
        btnActualizar.setOnAction(e -> {
            try {
                //Valida que primero se haya buscado y seleccionado un producto de la tabla
                if(idProductoSeleccionado == 0) {
                    new Alert(Alert.AlertType.WARNING, "Primero presione 'Buscar Productos' y elija un registro con doble clic.").show();
                    return;
                }
                Producto p = new Producto(
                    idProductoSeleccionado, 
                    txtNombre.getText().trim(), 
                    comboCategoria.getValue(), 
                    Double.parseDouble(txtPrecio.getText().trim()), 
                    Double.parseDouble(txtCosto.getText().trim()), 
                    Integer.parseInt(txtStock.getText().trim()), 
                    Integer.parseInt(txtMinimo.getText().trim())
                );
                
                //Actualiza los datos en la base de datos
                new ProductoDAO().actualizar(p);
                new Alert(Alert.AlertType.INFORMATION, "Información del producto actualizada correctamente.").show();
            } catch (Exception ex) { 
                new Alert(Alert.AlertType.ERROR, "Error al actualizar: Verifique que las modificaciones respeten los tipos de datos.").show(); 
            }
        });

        //Eliminar
        btnEliminar.setOnAction(e -> {
            try {
                //Valida que exista una seleccion valida para proceder a borrar en la base de datos
                if(idProductoSeleccionado == 0) {
                    new Alert(Alert.AlertType.WARNING, "Primero presione 'Buscar Productos' y seleccione el ítem que dará de baja.").show();
                    return;
                }
                
                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de eliminar de forma permanente este producto del inventario?", ButtonType.YES, ButtonType.NO);
                confirmacion.setHeaderText("Confirmación de Baja");
                confirmacion.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        try {
                            //Ejecuta la instrucción DELETE en la base de datos usando el ID seleccionado
                            new ProductoDAO().eliminar(idProductoSeleccionado);
                            new Alert(Alert.AlertType.INFORMATION, "El ítem ha sido removido del almacén.").show();
                            
                            //Limpieza de campos post-eliminación
                            txtNombre.clear(); comboCategoria.setValue(null);
                            txtPrecio.clear(); txtCosto.clear(); txtStock.clear(); txtMinimo.clear();
                            idProductoSeleccionado = 0;
                        } catch (Exception daoEx) {
                            //Atrapa el error de la base de datos si el producto ya fue vendido y tiene conexion con DetalleVenta
                            new Alert(Alert.AlertType.ERROR, "Error en el proceso de baja. El producto tiene un registro en las ventas y no puede ser eliminado.\nDetalle: " + daoEx.getMessage()).show();
                        }
                    }
                });
            } catch (Exception ex) { 
                new Alert(Alert.AlertType.ERROR, "Error al procesar la solicitud de baja.").show(); 
            }
        });

        // --- ESCENA Y CONFIGURACIÓN ---
        Scene scene = new Scene(mainLayout, 600, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}