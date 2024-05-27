package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Sound;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SelectingModeViewController extends Controller implements Initializable {

    @FXML
    private MFXButton btnChargeMatch;

    @FXML
    private MFXButton btnNewMatch;

   Sound sound = new Sound();

    PauseTransition pause = new PauseTransition(javafx.util.Duration.millis(600));

    @FXML
    void onActionBtnChargeMatch(ActionEvent event) {
        
        sound.playSound("src/main/resources/cr/ac/una/proyecto/resources/audio/clickedNext.mp3");
        FlowController.getInstance().goViewInWindow("LoadGame");
        ((Stage) btnChargeMatch.getScene().getWindow()).close();

    }

    @FXML
    void onActionBtnNewMatch(ActionEvent event) {

        sound.playSound("src/main/resources/cr/ac/una/proyecto/resources/audio/clickedNext.mp3");
        FlowController.getInstance().goViewInWindow("RegistryNewGame");
        ((Stage) btnNewMatch.getScene().getWindow()).close();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub

    }

}
