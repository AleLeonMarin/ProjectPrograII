package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Mensaje;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Justin Mendez y Alejandro Leon
 */
public class TableroController extends Controller implements Initializable {

    @FXML
    private Label lblTiempo;
    private int segundos = 0;
    @FXML
    private Button btnIniciar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

    private void timer() {
        Timer timer = new Timer();

        // Crear una tarea que será ejecutada después de 5 segundos
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // Este es el código que se ejecutará después de 5 segundos
                validarCantidadJugadores();
                // Cancelar el timer después de la ejecución de la tarea
                timer.cancel();
            }
        };

        // Programar la tarea para que se ejecute después de 5 segundos (5000 milisegundos)
        timer.schedule(task, 5000);
    }

    private void validarCantidadJugadores() {

        try
        {
            int contextSlider = (int) AppContext.getInstance().get("cantJugadoresSlider");
            System.out.println("AppContextInfoSlider: " + contextSlider);

            if (contextSlider == 6)
            {
                System.out.println("6 personas");

                FlowController.getInstance().goView("Tablero6jugadores");
            } else if (contextSlider == 5)
            {
                FlowController.getInstance().goView("Tablero5jugadores");
            } else if (contextSlider == 4)
            {
                FlowController.getInstance().goView("Tablero4jugadores");
            } else if (contextSlider == 3)
            {
                FlowController.getInstance().goView("Tablero3jugadores");
            } else
            {
                System.out.println("2 personas");
                FlowController.getInstance().goView("Tablero2jugadores");

            }

        } catch (Exception ex)
        {

            Logger.getLogger(TableroController.class
                    .getName()).log(Level.SEVERE, "Esta mamando papito", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Subtablero", getStage(), "Esta mamando papito");

        }

    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onMouseMoved(MouseEvent event) {
        //

    }

    @FXML
    private void onActionBtnIniciar(ActionEvent event) {
        validarCantidadJugadores();
        btnIniciar.setVisible(false);
        btnIniciar.setDisable(true);

    }

}
