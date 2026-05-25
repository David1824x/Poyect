module com.rednova.rednovaapp {
    requires javafx.controls;
    requires javafx.graphics; // <--- ESTO ES LO QUE TE FALTABA PARA EL ERROR DE APPLICATION
    requires java.sql;
    
    // Exportamos el paquete principal para que JavaFX pueda iniciarlo
    exports com.rednova.rednovaapp;
}