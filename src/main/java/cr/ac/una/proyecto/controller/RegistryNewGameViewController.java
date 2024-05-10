package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.proyecto.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class RegistryNewGameViewController extends Controller implements Initializable {

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

        FlowController.getInstance().goViewInWindow("DifficultySelectionView");
        ((Stage) btnNext.getScene().getWindow()).close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize() {
        sldQty.setMin(2);
        sldQty.setMax(6);
        sldQty.setValue(2);
        txfJug3.setDisable(true);
        txfJug4.setDisable(true);
        txfJug5.setDisable(true);
        txfJug6.setDisable(true);
        txfJug3.setVisible(false);
        txfJug4.setVisible(false);
        txfJug5.setVisible(false);
        txfJug6.setVisible(false);

        sldQty.valueProperty().addListener((obs, oldValue, newValue) -> {
            int value = newValue.intValue();

            // Habilitar y mostrar los campos de texto de jugadores adicionales según el
            // valor del slider
            txfJug3.setDisable(value < 3);
            txfJug4.setDisable(value < 4);
            txfJug5.setDisable(value < 5);
            txfJug6.setDisable(value < 6);
            txfJug3.setVisible(value >= 3);
            txfJug4.setVisible(value >= 4);
            txfJug5.setVisible(value >= 5);
            txfJug6.setVisible(value >= 6);
            int position = value;
            System.out.println("El slider quedó en la posición: " + position);
        });
    }

}
