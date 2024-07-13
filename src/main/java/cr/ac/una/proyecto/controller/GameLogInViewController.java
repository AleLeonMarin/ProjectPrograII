package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.proyecto.model.PDFViewer;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Sound;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameLogInViewController extends Controller implements Initializable {

    @FXML
    private MFXButton btnExit;
    @FXML
    private MFXButton btnLog;
    @FXML
    private Label lblAcercaDe;

    private PDFViewer pdfViewer;
    private Sound sound = new Sound();

    PauseTransition pause = new PauseTransition(javafx.util.Duration.millis(600));
    @FXML
    private Label lblIntrucciones;
    @FXML
    private AnchorPane acpPane;


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
        this.pdfViewer = new PDFViewer();
        btnExit.setDisable(true);
        btnLog.setDisable(true);
        lblAcercaDe.setDisable(true);
        btnExit.setVisible(true);
        btnLog.setVisible(true);
        lblAcercaDe.setVisible(true);
        btnExit.setOpacity(1);
        btnLog.setOpacity(1);
        lblAcercaDe.setOpacity(1);

        sound.disableForButtons(btnLog, btnExit, lblAcercaDe, "MainSound.mp3");
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onMousePressedLblAcercaDe(MouseEvent event) {
        sound.playSound("windMovement1.mp3");
        FlowController.getInstance().goViewInWindow("acercadeView");
    }

    @FXML
    private void onMousePressedLblInstrucciones(MouseEvent event) {
        this.pdfViewer.start((Stage) acpPane.getScene().getWindow(), "/cr/ac/una/proyecto/resources/Instrucciones.pdf");
    }


}
