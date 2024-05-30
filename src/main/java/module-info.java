module cr.ac.una.proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires MaterialFX;
    requires java.base;
    requires javafx.graphics;
    requires javafx.media;
    requires com.oracle.database.jdbc;
    //requires eclipselink;
    requires jakarta.persistence;
     requires jakarta.xml.bind;
    requires java.sql;
    
    opens cr.ac.una.proyecto to javafx.fxml, javafx.graphics;
    exports cr.ac.una.proyecto;
    exports cr.ac.una.proyecto.controller;
    opens cr.ac.una.proyecto.controller to javafx.fxml , javafx.graphics , MaterialFX;
    exports cr.ac.una.proyecto.model;  // Descomentar si se necesita
    opens cr.ac.una.proyecto.model to eclipselink, com.oracle.database.jdbc,jakarta.persistence; 
    exports cr.ac.una.proyecto.service;  // Descomentar si se necesita
    exports cr.ac.una.proyecto.util;
}
