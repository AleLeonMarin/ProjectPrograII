package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Flow;

import cr.ac.una.proyecto.util.FlowController;
import javafx.stage.Stage;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class SummaryMatchViewController extends Controller implements Initializable{

    @FXML
    private MFXButton btnEdit;

    @FXML
    private MFXButton btnPlay;

    @FXML
    private ImageView imgFicha1;

    @FXML
    private ImageView imgFicha2;

    @FXML
    private ImageView imgFicha3;

    @FXML
    private ImageView imgFicha4;

    @FXML
    private ImageView imgFicha5;

    @FXML
    private ImageView imgFicha6;

    @FXML
    private Label lblDifficulty;

    @FXML
    private Label lblJuagdor1;

    @FXML
    private Label lblJugador2;

    @FXML
    private Label lblJugador3;

    @FXML
    private Label lblJugador4;

    @FXML
    private Label lblJugador5;

    @FXML
    private Label lblJugador6;

    @FXML
    void onActionBtnEdit(ActionEvent event) {

        FlowController.getInstance().goViewInWindow("RegistryNewGame");
        ((Stage) btnEdit.getScene().getWindow()).close();

    }

    @FXML
    void onActionBtnPlay(ActionEvent event) {

        FlowController.getInstance().goViewInWindow("tableroView");
        ((Stage) btnPlay.getScene().getWindow()).close();

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
