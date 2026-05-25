package com.rednova.controller;

import com.rednova.model.*;
import javafx.beans.property.SimpleDoubleProperty; //comentario Necesario para columnas de tipo Double
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;
import java.util.function.Consumer;

public class Consultas {

    //comentario Paleta base para el fondo y bordes de la tabla
    private static final String STYLE_TABLE = 
        "-fx-background-color: #1A1A1E; " +
        "-fx-control-inner-background: #1A1A1E; " +
        "-fx-control-inner-background-alt: #26262B; " +
        "-fx-base: #1A1A1E; " +
        "-fx-table-cell-border-color: #3F3F46; ";

    //comentario Metodo auxiliar que fuerza el color blanco y el centrado en todas las columnas
    private static void aplicarEstiloColumnas(TableView<?> tabla) {
        for (TableColumn<?, ?> col : tabla.getColumns()) {
            col.setStyle("-fx-text-fill: white; -fx-alignment: CENTER;");
        }
    }

    //comentario Metodo para mostrar todos los Usuarios
    public static void verUsuarios(List<Usuario> lista, Consumer<Usuario> alSeleccionar) {
        Stage stage = new Stage();
        stage.setTitle("Catálogo General de Usuarios - RedNova");

        TableView<Usuario> tabla = new TableView<>();
        tabla.setStyle(STYLE_TABLE);

        TableColumn<Usuario, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cd -> new SimpleObjectProperty<>(cd.getValue().getId()));

        TableColumn<Usuario, String> colNum = new TableColumn<>("No. Control");
        colNum.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getNumeroControl()));

        TableColumn<Usuario, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getNombre()));

        TableColumn<Usuario, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getTipoUsuario()));

        TableColumn<Usuario, Integer> colPuntos = new TableColumn<>("Puntos");
        colPuntos.setCellValueFactory(cd -> new SimpleObjectProperty<>(cd.getValue().getPuntosLealtad()));

        tabla.getColumns().addAll(colId, colNum, colNombre, colTipo, colPuntos);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        aplicarEstiloColumnas(tabla);

        tabla.setOnMouseClicked(ev -> {
            if (ev.getClickCount() == 2 && tabla.getSelectionModel().getSelectedItem() != null) {
                alSeleccionar.accept(tabla.getSelectionModel().getSelectedItem());
                stage.close();
            }
        });

        tabla.setItems(FXCollections.observableArrayList(lista));
        mostrarEscena(stage, tabla);
    }

    //comentario Metodo para mostrar todos los Productos
    public static void verProductos(List<Producto> lista, Consumer<Producto> alSeleccionar) {
        Stage stage = new Stage();
        stage.setTitle("Inventario General de Productos - RedNova");

        TableView<Producto> tabla = new TableView<>();
        tabla.setStyle(STYLE_TABLE);

        TableColumn<Producto, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cd -> new SimpleObjectProperty<>(cd.getValue().getIdProducto()));

        TableColumn<Producto, String> colNombre = new TableColumn<>("Producto");
        colNombre.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getNombreProducto()));

        TableColumn<Producto, String> colCat = new TableColumn<>("Categoría");
        colCat.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getCategoria()));

        TableColumn<Producto, Double> colPrecio = new TableColumn<>("Precio ($)");
        colPrecio.setCellValueFactory(cd -> new SimpleObjectProperty<>(cd.getValue().getPrecioUnitario()));

        TableColumn<Producto, Integer> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(cd -> new SimpleObjectProperty<>(cd.getValue().getStockActual()));

        tabla.getColumns().addAll(colId, colNombre, colCat, colPrecio, colStock);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        aplicarEstiloColumnas(tabla);

        tabla.setOnMouseClicked(ev -> {
            if (ev.getClickCount() == 2 && tabla.getSelectionModel().getSelectedItem() != null) {
                alSeleccionar.accept(tabla.getSelectionModel().getSelectedItem());
                stage.close();
            }
        });

        tabla.setItems(FXCollections.observableArrayList(lista));
        mostrarEscena(stage, tabla);
    }

    //comentario Metodo para mostrar Equipos y su tarifa por hora (Modificado)
    public static void verEquipos(List<EquipoTecnologico> lista, Consumer<EquipoTecnologico> alSeleccionar) {
        Stage stage = new Stage();
        stage.setTitle("Control de equipos - RedNova");

        TableView<EquipoTecnologico> tabla = new TableView<>();
        tabla.setStyle(STYLE_TABLE);

        TableColumn<EquipoTecnologico, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cd -> new SimpleObjectProperty<>(cd.getValue().getIdEquipo()));

        TableColumn<EquipoTecnologico, String> colTipo = new TableColumn<>("Equipo");
        colTipo.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getTipoEquipo()));

        TableColumn<EquipoTecnologico, String> colSpecs = new TableColumn<>("Especificaciones");
        colSpecs.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getEspecificaciones()));

        //comentario Nueva columna de precio por hora añadida
        TableColumn<EquipoTecnologico, Double> colTarifa = new TableColumn<>("Precio/Hora");
        colTarifa.setCellValueFactory(cd -> new SimpleDoubleProperty(cd.getValue().getTarifaPorHora()).asObject());

        TableColumn<EquipoTecnologico, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getEstado()));

        tabla.getColumns().addAll(colId, colTipo, colSpecs, colTarifa, colEstado);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        aplicarEstiloColumnas(tabla);

        tabla.setOnMouseClicked(ev -> {
            if (ev.getClickCount() == 2 && tabla.getSelectionModel().getSelectedItem() != null) {
                alSeleccionar.accept(tabla.getSelectionModel().getSelectedItem());
                stage.close();
            }
        });

        tabla.setItems(FXCollections.observableArrayList(lista));
        mostrarEscena(stage, tabla);
    }

    //comentario Metodo para mostrar Espacios
    public static void verEspacios(List<Espacio> lista, Consumer<Espacio> alSeleccionar) {
        Stage stage = new Stage();
        stage.setTitle("Control de espacios - RedNova");

        TableView<Espacio> tabla = new TableView<>();
        tabla.setStyle(STYLE_TABLE);

        TableColumn<Espacio, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cd -> new SimpleObjectProperty<>(cd.getValue().getIdEspacio()));

        TableColumn<Espacio, String> colTipo = new TableColumn<>("Área");
        colTipo.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getTipoEspacio()));

        TableColumn<Espacio, Integer> colCap = new TableColumn<>("Aforo Máximo");
        colCap.setCellValueFactory(cd -> new SimpleObjectProperty<>(cd.getValue().getCapacidadPersonas()));

        TableColumn<Espacio, String> colEstado = new TableColumn<>("Estado actual");
        colEstado.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getEstado()));

        tabla.getColumns().addAll(colId, colTipo, colCap, colEstado);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        aplicarEstiloColumnas(tabla);

        tabla.setOnMouseClicked(ev -> {
            if (ev.getClickCount() == 2 && tabla.getSelectionModel().getSelectedItem() != null) {
                alSeleccionar.accept(tabla.getSelectionModel().getSelectedItem());
                stage.close();
            }
        });

        tabla.setItems(FXCollections.observableArrayList(lista));
        mostrarEscena(stage, tabla);
    }

    //comentario Escena configurada con dimensiones para soportar la nueva columna
    private static void mostrarEscena(Stage stage, TableView<?> tabla) {
        VBox root = new VBox(tabla);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #121214;");
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}