module cr.ac.una.proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires MaterialFX;
    requires java.base;
    requires javafx.graphics;
    requires javafx.media;
    requires com.oracle.database.jdbc;
    requires eclipselink;
    requires jakarta.persistence;
    requires jakarta.xml.bind;
    requires java.sql;
    requires com.google.gson;
    requires java.desktop;
    requires javafx.swing;
    requires org.apache.pdfbox;


    opens cr.ac.una.proyecto to javafx.fxml, javafx.graphics;
    exports cr.ac.una.proyecto;
    exports cr.ac.una.proyecto.controller;
    opens cr.ac.una.proyecto.controller to javafx.fxml, javafx.graphics, MaterialFX,org.apache.pdfbox,java.desktop,javafx.swing;
    exports cr.ac.una.proyecto.model;
    opens cr.ac.una.proyecto.model to eclipselink, com.oracle.database.jdbc, jakarta.persistence;
    exports cr.ac.una.proyecto.service;
    exports cr.ac.una.proyecto.util;
}