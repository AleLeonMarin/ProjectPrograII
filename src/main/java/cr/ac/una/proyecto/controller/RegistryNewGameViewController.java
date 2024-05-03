package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class RegistryNewGameViewController extends Controller implements Initializable{

    @FXML
    private MFXButton btnNext;

    @FXML
    private MFXSlider sldQty;

    @FXML
    private MFXTextField txfJug1;

    @FXML
    private MFXTextField txfJug2;

    @FXML
    private MFXTextField txfJug3;

    @FXML
    private MFXTextField txfJug4;

    @FXML
    private MFXTextField txfJug5;

    @FXML
    private MFXTextField txfJug6;

    @FXML
    void onActionBtnNext(ActionEvent event) {

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
