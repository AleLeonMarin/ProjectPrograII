package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.proyecto.util.FlowController;
import javafx.stage.Stage;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class AdminController extends Controller implements Initializable{

    @FXML
    private MFXButton bntSalir;

    @FXML
    private MFXButton btnMantenimiento;

    @FXML
    void onActionBntSalir(ActionEvent event) {
        ((Stage) bntSalir.getScene().getWindow()).close();

    }

    @FXML
    void onActionBtnMantenimiento(ActionEvent event) {

        FlowController.getInstance().goView("MantenimientoPreguntas");

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
