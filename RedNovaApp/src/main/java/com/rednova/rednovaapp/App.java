package com.rednova.rednovaapp;

import com.rednova.controller.*;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class App extends Application {
    
    private final String styleFondo = "-fx-background-color: #121214;"; // Ajustado al fondo Dark Premium
    private final String styleBtnNormal = "-fx-background-color: #1A1A1E; -fx-text-fill: white; -fx-border-color: #3F3F46; -fx-border-radius: 8; -fx-background-radius: 8; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 13px;";
    private final String styleBtnHover = "-fx-background-color: #26262B; -fx-text-fill: white; -fx-border-color: #C3073F; -fx-border-radius: 8; -fx-background-radius: 8; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 13px;";

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(30);
        root.setStyle(styleFondo);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40, 20, 40, 20));

        // --- HEADER PRINCIPAL ---
        VBox headerBox = new VBox(4);
        headerBox.setAlignment(Pos.CENTER);
        Label lblTitle = new Label("REDNOVA OS");
        lblTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        lblTitle.setStyle("-fx-text-fill: #FFFFFF; -fx-letter-spacing: 2px;");
        Label lblSubtitle = new Label("CORE DASHBOARD CONTROL");
        lblSubtitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        lblSubtitle.setStyle("-fx-text-fill: #C3073F;");
        headerBox.getChildren().addAll(lblTitle, lblSubtitle);

        // --- CONTENEDOR DIVISOR (IZQUIERDA | DERECHA) ---
        HBox splitLayout = new HBox(60); // Espaciado amplio entre las dos columnas
        splitLayout.setAlignment(Pos.CENTER);

        // =========================================================================
        // COLUMNA IZQUIERDA: OPERACIONES (COMPRAS Y RENTAS)
        // =========================================================================
        VBox leftColumn = new VBox(15);
        leftColumn.setAlignment(Pos.TOP_CENTER);

        Label lblLeftSection = new Label("TRANSACCIONES Y SERVICIOS");
        lblLeftSection.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        lblLeftSection.setStyle("-fx-text-fill: #A0A0A5; -fx-background-color: #1A1A1E; -fx-padding: 6 12; -fx-background-radius: 4;");

        GridPane leftGrid = new GridPane();
        leftGrid.setHgap(15); leftGrid.setVgap(15); leftGrid.setAlignment(Pos.CENTER);
        
        // Botones de salida/flujo comercial
        leftGrid.add(crearBtn("🛒 Punto de Venta", e -> new VentanaVentas().mostrar()), 0, 0);
        leftGrid.add(crearBtn("💻 Renta de Equipos", e -> new VentanaRentaEquipos().mostrar()), 1, 0);
        leftGrid.add(crearBtn("🏢 Renta de Espacios", e -> new VentanaRentaEspacios().mostrar()), 0, 1);
        leftGrid.add(crearBtn("📈 Reportes Globales", e -> new VentanaReporte().mostrarReporte()), 1, 1);
        
        leftColumn.getChildren().addAll(lblLeftSection, leftGrid);

        // =========================================================================
        // COLUMNA DERECHA: ADMINISTRACIÓN (ALTA DE DATOS Y CATÁLOGOS)
        // =========================================================================
        VBox rightColumn = new VBox(15);
        rightColumn.setAlignment(Pos.TOP_CENTER);

        Label lblRightSection = new Label("GESTIÓN Y ALTA DE CATÁLOGOS");
        lblRightSection.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        lblRightSection.setStyle("-fx-text-fill: #A0A0A5; -fx-background-color: #1A1A1E; -fx-padding: 6 12; -fx-background-radius: 4;");

        GridPane rightGrid = new GridPane();
        rightGrid.setHgap(15); rightGrid.setVgap(15); rightGrid.setAlignment(Pos.CENTER);
        
        // Botones de administración/CRUDs
        rightGrid.add(crearBtn("👥 Control Usuarios", e -> new VentanaUsuarios().mostrar()), 0, 0);
        rightGrid.add(crearBtn("📦 Alta de Productos", e -> new VentanaProductos().mostrar()), 1, 0);
        rightGrid.add(crearBtn("⚙️ Alta Equipos Tech", e -> new VentanaRenta().mostrar()), 0, 1); // Mantiene tu ventana de gestión base
        rightGrid.add(crearBtn("🛠️ Control Coworking", e -> new VentanaCoworking().mostrar()), 1, 1);
        
        rightColumn.getChildren().addAll(lblRightSection, rightGrid);

        // --- INTEGRACIÓN AL DISEÑO ---
        splitLayout.getChildren().addAll(leftColumn, rightColumn);
        root.getChildren().addAll(headerBox, splitLayout);

        // Configuración de la escena principal
        Scene scene = new Scene(root, 1050, 550);
        stage.setScene(scene);
        stage.setTitle("RedNova OS - Panel de Control Integrado");
        stage.setMaximized(true);
        stage.show();
    }

    // Método factoría optimizado con listeners de transición visual (Hover effect)
    private Button crearBtn(String texto, javafx.event.EventHandler<javafx.event.ActionEvent> evento) {
        Button btn = new Button(texto);
        btn.setPrefSize(210, 110); // Ligeramente más amplios para mejor legibilidad de los íconos
        btn.setStyle(styleBtnNormal);
        btn.setOnAction(evento);
        
        // Eventos dinámicos del puntero
        btn.setOnMouseEntered(e -> btn.setStyle(styleBtnHover));
        btn.setOnMouseExited(e -> btn.setStyle(styleBtnNormal));
        
        return btn;
    }

    public static void main(String[] args) { 
        launch(args); 
    }
}