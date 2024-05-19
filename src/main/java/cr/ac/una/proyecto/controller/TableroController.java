package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;

/**
 * FXML Controller class
 *
 * @author Justin Mendez y Alejandro Leon
 */
public class TableroController extends Controller implements Initializable {

    @FXML
    private Label lblTiempo;
    private int segundos = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validarCantidadJugadores();
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

    private void validarCantidadJugadores() {
        int contextSlider = (int) AppContext.getInstance().get("cantJugadoresSlider");
        System.out.println("AppContextInfoSlider: " + contextSlider);

        if (contextSlider == 6)
        {
            System.out.println("6 personas");

            // FlowController.getInstance().goView("Tablero6jugadores");
        } else if (contextSlider == 5)
        {
            //FlowController.getInstance().goView("Tablero5jugadores");
        } else if (contextSlider == 4)
        {
            //FlowController.getInstance().goView("Tablero4jugadores");
        } else if (contextSlider == 3)
        {
            // FlowController.getInstance().goView("Tablero3jugadores");
        } else
        {
            System.out.println("2 personas");
            FlowController.getInstance().goViewInWindow("Tablero2jugadores");
        }
    }

}
