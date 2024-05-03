package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.proyecto.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class GameLogInViewController extends Controller implements Initializable{

    @FXML
    private MFXButton btnExit;

    @FXML
    private MFXButton btnLog;

    @FXML
    private ImageView imgVLogo;

    @FXML
    void onActionBtnExit(ActionEvent event) {

        ((Stage) btnExit.getScene().getWindow()).close();
    }

    @FXML
    void onActionBtnLog(ActionEvent event) {

        FlowController.getInstance().goViewInWindow("RegistryNewGame");
        ((Stage) btnLog.getScene().getWindow()).close();

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
    }

}
