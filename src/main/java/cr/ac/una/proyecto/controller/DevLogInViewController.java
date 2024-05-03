package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import cr.ac.una.proyecto.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class DevLogInViewController extends Controller implements Initializable {

    @FXML
    private MFXButton btnAdmin;

    @FXML
    private MFXButton btnClient;

    @FXML
    void onActionBtnAction(ActionEvent event) {

        FlowController.getInstance().goViewInWindow("GameLogIn");
        ((Stage) btnClient.getScene().getWindow()).close();

    }

    @FXML
    void onActionBtnAdmin(ActionEvent event) {

        FlowController.getInstance().goViewInWindow("AdminLogIn");

    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
    }

}
