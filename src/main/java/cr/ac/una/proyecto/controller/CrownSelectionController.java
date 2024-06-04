package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import cr.ac.una.proyecto.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class CrownSelectionController extends Controller implements Initializable {

    @FXML
    private MFXButton bntCorona;

    @FXML
    private MFXButton btnDuelo;

    @FXML
    void onActionBtnCorona(ActionEvent event) {

        FlowController.getInstance().goViewInWindow("FrontalCardCrownView");
        ((Stage) bntCorona.getScene().getWindow()).close();

    }

    @FXML
    void onActionBtnDuelo(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("FrontalCardnDuel");
        ((Stage) bntCorona.getScene().getWindow()).close();


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
