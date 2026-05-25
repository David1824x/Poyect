package com.rednova.controller;

import com.rednova.model.*;
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

    //comentario Metodo para mostrar todos los Usuarios y permitir seleccionar uno con doble clic
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

        //comentario Evento de doble clic para retornar el elemento seleccionado a la ventana del formulario
        tabla.setOnMouseClicked(ev -> {
            if (ev.getClickCount() == 2 && tabla.getSelectionModel().getSelectedItem() != null) {
                Usuario seleccionado = tabla.getSelectionModel().getSelectedItem();
                if (alSeleccionar != null) {
                    alSeleccionar.accept(seleccionado);
                }
                stage.close();
            }
        });

        ObservableList<Usuario> datos = FXCollections.observableArrayList(lista);
        tabla.setItems(datos);

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
                Producto seleccionado = tabla.getSelectionModel().getSelectedItem();
                if (alSeleccionar != null) {
                    alSeleccionar.accept(seleccionado);
                }
                stage.close();
            }
        });

        ObservableList<Producto> datos = FXCollections.observableArrayList(lista);
        tabla.setItems(datos);

        mostrarEscena(stage, tabla);
    }

    //comentario Metodo para mostrar todos los Equipos Tecnologicos
    public static void verEquipos(List<EquipoTecnologico> lista, Consumer<EquipoTecnologico> alSeleccionar) {
        Stage stage = new Stage();
        stage.setTitle("Control de Hardware - RedNova");

        TableView<EquipoTecnologico> tabla = new TableView<>();
        tabla.setStyle(STYLE_TABLE);

        TableColumn<EquipoTecnologico, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cd -> new SimpleObjectProperty<>(cd.getValue().getIdEquipo()));

        TableColumn<EquipoTecnologico, String> colTipo = new TableColumn<>("Equipo");
        colTipo.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getTipoEquipo()));

        TableColumn<EquipoTecnologico, String> colSpecs = new TableColumn<>("Especificaciones");
        colSpecs.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getEspecificaciones()));

        TableColumn<EquipoTecnologico, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getEstado()));

        tabla.getColumns().addAll(colId, colTipo, colSpecs, colEstado);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        aplicarEstiloColumnas(tabla);

        tabla.setOnMouseClicked(ev -> {
            if (ev.getClickCount() == 2 && tabla.getSelectionModel().getSelectedItem() != null) {
                EquipoTecnologico seleccionado = tabla.getSelectionModel().getSelectedItem();
                if (alSeleccionar != null) {
                    alSeleccionar.accept(seleccionado);
                }
                stage.close();
            }
        });

        ObservableList<EquipoTecnologico> datos = FXCollections.observableArrayList(lista);
        tabla.setItems(datos);

        mostrarEscena(stage, tabla);
    }

    //comentario Metodo para mostrar todos los Espacios de Coworking
    public static void verEspacios(List<Espacio> lista, Consumer<Espacio> alSeleccionar) {
        Stage stage = new Stage();
        stage.setTitle("Infraestructura Coworking - RedNova");

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
                Espacio seleccionado = tabla.getSelectionModel().getSelectedItem();
                if (alSeleccionar != null) {
                    alSeleccionar.accept(seleccionado);
                }
                stage.close();
            }
        });

        ObservableList<Espacio> datos = FXCollections.observableArrayList(lista);
        tabla.setItems(datos);

        mostrarEscena(stage, tabla);
    }

    //comentario Metodo con dimensiones aumentadas para dar soporte a multiples filas en pantalla
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