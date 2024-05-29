module cr.ac.una.proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires MaterialFX;
    requires java.base;
    requires javafx.graphics;
    requires javafx.media;
    
    opens cr.ac.una.proyecto to javafx.fxml, javafx.graphics;
    exports cr.ac.una.proyecto;
    exports cr.ac.una.proyecto.controller;
    opens cr.ac.una.proyecto.controller to javafx.fxml , javafx.graphics , MaterialFX;
    exports cr.ac.una.proyecto.model;  // Descomentar si se necesita
    exports cr.ac.una.proyecto.service;  // Descomentar si se necesita
    exports cr.ac.una.proyecto.util;
}