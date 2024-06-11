package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Sound;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GameLogInViewController extends Controller implements Initializable {

    @FXML
    private MFXButton btnExit;

    @FXML
    private MFXButton btnLog;

    @FXML
    private Label lblAcerca;

    Sound sound = new Sound();

    PauseTransition pause = new PauseTransition(javafx.util.Duration.millis(600));

    @FXML
    void onActionBtnExit(ActionEvent event) {
        sound.playSound(("quitExited.mp3"));
        pause.setOnFinished(events -> {
            ((Stage) btnExit.getScene().getWindow()).close();
        });
        pause.play();
    }

    @FXML
    void onActionBtnLog(ActionEvent event) {

        sound.playSound("clickedStart.mp3");
        pause.setOnFinished(events -> {
            FlowController.getInstance().goViewInWindow("SelectingMode");
            ((Stage) btnLog.getScene().getWindow()).close();
        });
        pause.play();

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        btnExit.setDisable(true);
        btnLog.setDisable(true);
        lblAcerca.setDisable(true);
        btnExit.setVisible(true);
        btnLog.setVisible(true);
        lblAcerca.setVisible(true);
        btnExit.setOpacity(1);
        btnLog.setOpacity(1);
        lblAcerca.setOpacity(1);
    
        sound.disableForButtons(btnLog, btnExit, lblAcerca, "MainSound.mp3");
    }

    @Override
    public void initialize() {


    }

    @FXML
    void onMousePressed(MouseEvent event) {
        sound.playSound("windMovement1.mp3");
        FlowController.getInstance().goViewInWindow("acercadeView");
    }

    

}
