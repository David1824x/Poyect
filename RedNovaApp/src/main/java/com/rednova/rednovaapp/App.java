package com.rednova.rednovaapp;

import com.rednova.controller.*;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application { // Aquí está el 'extends Application'
    private final String styleFondo = "-fx-background-color: #1A1A1D;";
    private final String styleBtn = "-fx-background-color: #2F3136; -fx-text-fill: white; -fx-border-color: #C3073F; -fx-border-radius: 10; -fx-background-radius: 10; -fx-font-weight: bold; -fx-cursor: hand;";

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(20);
        root.setStyle(styleFondo);
        root.setAlignment(Pos.CENTER);

        Label lbl = new Label("REDNOVA - DASHBOARD");
        lbl.setStyle("-fx-text-fill: #C3073F; -fx-font-size: 30px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setHgap(20); grid.setVgap(20); grid.setAlignment(Pos.CENTER);

        grid.add(crearBtn("🛒 Punto de Venta", e -> new VentanaVentas().mostrar()), 0, 0);
        grid.add(crearBtn("☕ Inventario", e -> new VentanaProductos().mostrar()), 1, 0);
        grid.add(crearBtn("👥 Usuarios/Lealtad", e -> new VentanaUsuarios().mostrar()), 2, 0);
        grid.add(crearBtn("💻 Renta Tech", e -> new VentanaRenta().mostrar()), 0, 1);
        grid.add(crearBtn("📅 Coworking", e -> new VentanaCoworking().mostrar()), 1, 1);
        grid.add(crearBtn("📈 Reportes", e -> new VentanaReporte().mostrarReporte()), 2, 1);

        root.getChildren().addAll(lbl, grid);
        stage.setScene(new Scene(root, 1000, 500));
        stage.setMaximized(true);
        stage.show();
    }

    private Button crearBtn(String t, javafx.event.EventHandler<javafx.event.ActionEvent> ev) {
        Button b = new Button(t);
        b.setPrefSize(200, 100);
        b.setStyle(styleBtn);
        b.setOnAction(ev);
        return b;
    }

    public static void main(String[] args) { launch(args); }
}