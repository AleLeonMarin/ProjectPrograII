package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.util.Animacion;
import java.net.URL;
import java.util.ResourceBundle;
import cr.ac.una.proyecto.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CrownSelectionController extends Controller implements Initializable {

    @FXML
    private MFXButton bntCorona;

    @FXML
    private MFXButton btnDuelo;

    private Animacion animacion;
    @FXML
    private AnchorPane acpRootPane;

    @FXML
    void onActionBtnCorona(ActionEvent event) {
        Runnable onFinishOut = () ->
        {
            ((Stage) acpRootPane.getScene().getWindow()).close();
        };

        animacion.animarFadeOut(btnDuelo, onFinishOut);
        FlowController.getInstance().goViewInWindow("FrontalCardCrownView");

    }

    @FXML
    void onActionBtnDuelo(ActionEvent event) {
        Runnable onFinishOut = () ->
        {
            ((Stage) acpRootPane.getScene().getWindow()).close();
        };

        animacion.animarFadeOut(btnDuelo, onFinishOut);
        FlowController.getInstance().goViewInWindow("FrontalCardnDuel");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize() {
        initValues();
    }

    private void initValues() {
        animacion = new Animacion();
        animacion.simpleFadeIn(acpRootPane);
    }

}
