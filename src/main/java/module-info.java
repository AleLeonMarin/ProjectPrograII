module cr.ac.una.proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires MaterialFX;
    requires java.base;

    opens cr.ac.una.proyecto to javafx.fxml;
    exports cr.ac.una.proyecto;
    exports cr.ac.una.proyecto.controller;
    opens cr.ac.una.proyecto.controller to javafx.fxml;
    //exports cr.ac.una.proyecto.model;
    //exports cr.ac.una.proyecto.service;
    exports cr.ac.una.proyecto.util;
}
