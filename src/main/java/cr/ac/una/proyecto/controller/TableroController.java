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
    private void btnGirarOnAction(ActionEvent event) {
        if (rotate != null)
        {
            rotate.stop();
        }
        rotate = new RotateTransition();
        rotate.setNode(ruleta);
        ruleta.setRotate(anguloInicial);

        rotate.setDuration(Duration.seconds(3));
        rotate.setInterpolator(Interpolator.EASE_BOTH);

        double nuevoAnguloDetenido = generarAnguloAleatorio();

        rotate.setByAngle(360 * 6 + nuevoAnguloDetenido);
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setOnFinished(e -> determinarPosicionRuleta(nuevoAnguloDetenido));
        rotate.play();

    }

    private int generarAnguloAleatorio() {//se cambio de double a int por 
        Random random = new Random();
        return random.nextInt(360);
    }

    private void determinarPosicionRuleta(double anguloDetenido) {
        double inicio = 0;

        double cienciaInicio = 29;
        double cienciaFin = 80;

        double geografiaInicio = 81;
        double geografiaFin = 134;

        double coronaInicio = 135;
        double coronaFin = 183;

        double entretenimientoInicio = 184;
        double entretenimientoFin = 233;

        double arteInicio = 234;
        double arteFin = 284;

        double deporteInicio = 285;
        double deporteFin = 336;

        double historiaInicio = 337;
        double historiaFin = 29;

        double finalGrados = 360;

        String categoria = "Angulo no encontrado" + String.valueOf(anguloDetenido);

        if (anguloDetenido >= deporteInicio && anguloDetenido <= deporteFin)
        {
            categoria = "Deporte";
        } else if (anguloDetenido >= arteInicio && anguloDetenido <= arteFin)
        {
            categoria = "Arte";
        } else if (anguloDetenido >= geografiaInicio && anguloDetenido <= geografiaFin)
        {
            categoria = "Geografía";
        } else if (anguloDetenido >= cienciaInicio && anguloDetenido <= cienciaFin)
        {
            categoria = "Ciencia";
        } else if (anguloDetenido >= coronaInicio && anguloDetenido <= coronaFin)
        {
            categoria = "Corona";
        } else if (anguloDetenido >= entretenimientoInicio && anguloDetenido <= entretenimientoFin)
        {
            categoria = "Entretenimiento";
        } else if ((anguloDetenido >= historiaInicio && anguloDetenido <= finalGrados) || (anguloDetenido >= inicio && anguloDetenido <= historiaFin))
        {
            categoria = "Historia";
        }

        System.out.println("La ruleta se detuvo en la categoría de: " + categoria + "Angulo: " + String.valueOf(anguloDetenido));
    }

    @FXML
    private void btnResetOnAction(ActionEvent event) {
        ruleta.setRotate(anguloInicial);
    }

    @FXML
    private void btnResetOnDragDetected(MouseEvent event) {
    }

}
