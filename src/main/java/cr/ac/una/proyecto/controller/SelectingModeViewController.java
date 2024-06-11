package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Sound;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class SelectingModeViewController extends Controller implements Initializable {

    @FXML
    private MFXButton btnChargeMatch;

    @FXML
    private MFXButton btnNewMatch;

    @FXML
    private Label lblPosiciones;

   Sound sound = new Sound();

    PauseTransition pause = new PauseTransition(javafx.util.Duration.millis(600));

    @FXML
    void onActionBtnChargeMatch(ActionEvent event) {
        
        sound.playSound("clickedNext.mp3");
        FlowController.getInstance().goViewInWindow("LoadGame");
        ((Stage) btnChargeMatch.getScene().getWindow()).close();

    }

    @FXML
    void onActionBtnNewMatch(ActionEvent event) {

        sound.playSound("clickedNext.mp3");
        FlowController.getInstance().goViewInWindow("RegistryNewGame");
        ((Stage) btnNewMatch.getScene().getWindow()).close();

    }

    @FXML
    void onMousePressedPosiciones(MouseEvent event) {

        sound.playSound("clickedNext.mp3");
        FlowController.getInstance().goViewInWindow("TablaPosiciones");
        ((Stage) lblPosiciones.getScene().getWindow()).close();

    

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
