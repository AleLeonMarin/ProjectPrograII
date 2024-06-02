package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.proyecto.util.FlowController;
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
    private MFXButton btnEstadisticas;

    @FXML
    void onActionBntSalir(ActionEvent event) {
       FlowController.getInstance().salir();

    }

    @FXML
    void onActionBtnMantenimiento(ActionEvent event) {

        FlowController.getInstance().goView("MantenimientoPreguntas");

    }

    @FXML
    void onActionBtnEstadisticas(ActionEvent event) {

        FlowController.getInstance().goView("Estadisticas");

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
