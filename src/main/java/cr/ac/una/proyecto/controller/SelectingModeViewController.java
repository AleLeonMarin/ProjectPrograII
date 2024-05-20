package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import cr.ac.una.proyecto.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class SelectingModeViewController extends Controller implements Initializable{

    @FXML
    private MFXButton btnChargeMatch;

    @FXML
    private MFXButton btnNewMatch;

    @FXML
    void onActionBtnChargeMatch(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("LoadGame");
        ((Stage) btnChargeMatch.getScene().getWindow()).close();

    }

    @FXML
    void onActionBtnNewMatch(ActionEvent event) {

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
