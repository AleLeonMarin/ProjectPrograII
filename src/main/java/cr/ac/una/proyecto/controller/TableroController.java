package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Justin Mendez y Alejandro Leon
 */
public class TableroController extends Controller implements Initializable {

    @FXML
    private Label lblTiempo;
    private static final int RADIO_TABLERO = 400; // Radio del tablero circular
    @FXML
    private ImageView ruleta;
    @FXML
    private ImageView picker;
    @FXML
    private Button btnGirar;
    @FXML
    private Button btnReset;

    private RotateTransition rotate;
    private ArrayList<String> opciones = new ArrayList<>(Arrays.asList("Deporte", "Arte", "Geografia", "Entretenimiento", "Ciencia", "Historia"));
    private double anguloInicial = 0;
    private int segundos = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                segundos++;
                int minutos = segundos / 60;
                int seg = segundos % 60;
                lblTiempo.setText(String.format("Tiempo: %02d:%02d", minutos, seg));
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }


    @FXML
    private void btnResetOnDragDetected(MouseEvent event) {
    }

}
