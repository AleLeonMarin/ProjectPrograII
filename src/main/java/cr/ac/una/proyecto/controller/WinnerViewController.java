package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.proyecto.App;
import cr.ac.una.proyecto.model.Juego;
import cr.ac.una.proyecto.util.Animacion;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Sound;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WinnerViewController extends Controller implements Initializable {

    @FXML
    private ImageView imgScale;

    @FXML
    private Label lblGanador;

    @FXML
    private AnchorPane root;

    PauseTransition pause = new PauseTransition(Duration.seconds(15));
    Sound sound = new Sound();
    Animacion animacion = new Animacion();
    Juego juego = new Juego();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        animacion.zoomInOut(imgScale);
        sound.playSound("AwardSoundEffect.mp3");
        pause.setOnFinished(e -> {
            FlowController.getInstance().goViewInWindow("GameLogIn");
            ((Stage) root.getScene().getWindow()).close();
        });
        pause.play();
    }

    @Override
    public void initialize() {

        setGanador();
        
    }

    public void setGanador() {

        //AppContext.getInstance().get("nombreGanador");
        lblGanador.setText(AppContext.getInstance().get("nombreGanador").toString());
    }

}
