package com.rednova.controller;

import com.rednova.dao.UsuarioDAO;
import com.rednova.dao.LealtadDAO;
import com.rednova.model.Usuario;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class VentanaLealtad {

    public void mostrar() {
        Stage stage = new Stage();
        stage.setTitle("Gestión de Lealtad");
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #1A1A1D;");

        TextField txtIdUsuario = new TextField();
        txtIdUsuario.setPromptText("ID del Usuario");
        
        Label lblInfo = new Label("Ingresa ID para consultar puntos");
        lblInfo.setStyle("-fx-text-fill: white;");

        Button btnConsultar = new Button("Consultar Puntos");
        Button btnCanjear = new Button("Canjear 100 Puntos ($50 desc)");
        
        // Estilos
        btnConsultar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        btnCanjear.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");

        // Acción Consultar
        btnConsultar.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtIdUsuario.getText());
                Usuario u = new UsuarioDAO().buscarPorId(id);
                if(u != null) {
                    lblInfo.setText("Usuario: " + u.getNombre() + " | Puntos: " + u.getPuntosLealtad());
                } else {
                    lblInfo.setText("Usuario no encontrado.");
                }
            } catch(Exception ex) { lblInfo.setText("Error: ID inválido."); }
        });

        // Acción Canjear
        btnCanjear.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtIdUsuario.getText());
                boolean exito = new LealtadDAO().realizarCanje(id, 100, 50.0);
                
                if(exito) {
                    new Alert(Alert.AlertType.INFORMATION, "¡Canje exitoso!").show();
                    lblInfo.setText("Canje realizado. Consulta de nuevo.");
                } else {
                    new Alert(Alert.AlertType.ERROR, "Puntos insuficientes o error.").show();
                }
            } catch(Exception ex) { ex.printStackTrace(); }
        });

        root.getChildren().addAll(new Label("SISTEMA DE LEALTAD"), txtIdUsuario, btnConsultar, lblInfo, btnCanjear);
        
        stage.setScene(new Scene(root, 350, 300));
        stage.show();
    }
}