/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

/**
 * FXML Controller class
 *
 * @author justi
 */
public class TableroController extends Controller implements Initializable {

    @FXML
    private Pane tablero;
    private static final int RADIO_TABLERO = 400; // Radio del tablero circular

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {
        int numeroJugadores = 1;
        tablero.setPrefSize(RADIO_TABLERO
                * 2, RADIO_TABLERO * 2);
        double anguloSeparacion = 360.0 / numeroJugadores;

        crearTablero(anguloSeparacion, numeroJugadores);

    }

    private void crearTablero(double anguloSeparacion, int numeroJugadores) {
        for (int i = 0; i < numeroJugadores; i++)
        {
            double anguloInicio = i * anguloSeparacion;
            double anguloFin = anguloInicio + anguloSeparacion;
            Arc celda = new Arc(RADIO_TABLERO, RADIO_TABLERO, RADIO_TABLERO, RADIO_TABLERO, anguloInicio, anguloSeparacion); // Tamaño y ubicación del arco
            celda.setType(ArcType.ROUND); // Tipo de arco (redondeado)
            celda.setFill(Color.WHITE); // Color de fondo del arco
            celda.setStroke(Color.BLACK); // Color del borde del arco
            celda.setStrokeWidth(2); // Grosor del borde del arco
            celda.setId("celda_" + i); // Asignar un ID a cada arco

            // Cargar una imagen
            Image imagen = new Image("file:src/main/resources/cr/ac/una/proyecto/resources/baseT.png");

            ImageView imageView = new ImageView(imagen);

            // Calcular el ancho y alto de la imagen para que coincida con el arco
            double radioX = RADIO_TABLERO * Math.cos(Math.toRadians(anguloSeparacion / 2));
            double radioY = RADIO_TABLERO * Math.sin(Math.toRadians(anguloSeparacion / 2));
            double anchoImagen = 2 * Math.sqrt(Math.pow(radioX, 2) + Math.pow(radioY, 2));
            double altoImagen = imageView.getFitHeight();

            // Establecer el tamaño de la imagen
            imageView.setFitWidth(anchoImagen);
            imageView.setFitHeight(altoImagen);

            // Rotar la imagen para que se alinee con el arco
            imageView.setRotate(anguloInicio + anguloSeparacion / 2);

            // Calcular la posición del ImageView para que se superponga al arco
            double x = RADIO_TABLERO - anchoImagen / 2;
            double y = RADIO_TABLERO - altoImagen / 2;
            imageView.setLayoutX(x);
            imageView.setLayoutY(y);
            tablero.getChildren().addAll(celda, imageView);
        }
    }

}
