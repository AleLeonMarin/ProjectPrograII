package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.util.Animacion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FrontalSpecialCards extends Controller implements Initializable {

    @FXML
    private AnchorPane acpRootPane;
    private Animacion animacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {
        initValues();
    }

    private void initValues() {
        animacion = new Animacion();
        Runnable onFinishOut = () ->
        {
            ((Stage) acpRootPane.getScene().getWindow()).close();
        };
        Runnable onFinishIn = () ->
        {
            Platform.runLater(() -> animacion.animarFadeOut(acpRootPane, onFinishOut));
        };

        animacion.animarFadeIn(acpRootPane, onFinishIn);
    }
}
