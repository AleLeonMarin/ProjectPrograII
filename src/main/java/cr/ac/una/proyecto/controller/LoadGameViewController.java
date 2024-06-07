package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.proyecto.model.PartidaDto;
import cr.ac.una.proyecto.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class LoadGameViewController extends Controller implements Initializable{

    @FXML
    private MFXButton btnChargeMatch;

    @FXML
    private MFXButton btnSalir;

    @FXML
    private TableView<PartidaDto> tbvMatches;

    @FXML
    void onActionBtnChargeMatch(ActionEvent event) {
        FlowController.getInstance().goMain("tableroView");
        ((Stage) btnChargeMatch.getScene().getWindow()).close();

    }

    @FXML
    void onActionBtnSalir(ActionEvent event) {
        ((Stage) btnSalir.getScene().getWindow()).close();

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
