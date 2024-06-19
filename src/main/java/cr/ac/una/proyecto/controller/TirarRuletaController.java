package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.util.Animacion;
import cr.ac.una.proyecto.util.Ruleta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TirarRuletaController extends Controller implements Initializable {

    @FXML
    private AnchorPane acpRootPane;
    @FXML
    private ImageView imvRuleta;
    @FXML
    private ImageView imvPicker;

    private Animacion animacion;
    private Ruleta ruleta;
    private String resultado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void OnMouseClickedPicker(MouseEvent event) {
        moverRuleta();
    }

    @Override
    public void initialize() {

        animacion = new Animacion();
        ruleta = new Ruleta();
        resultado = "";

    }

    public String getResultado() {
        return resultado;
    }

    public double getRuletaAngulo() {
        return this.ruleta.getAnguloDetenido();
    }

    public String obtenerPosicionRuleta() {
        return this.ruleta.determinarPosicionRuleta();
    }

    private void moverRuleta() {//mueve la ruleta, si la categoria sale diferente de corona sera la nueva categoria de la pregunta a responder.

        this.resultado = obtenerPosicionRuleta();
        double anguloDetenido = getRuletaAngulo();

        Runnable onFinishOut = () ->
        {
            ((Stage) acpRootPane.getScene().getWindow()).close();
        };

        Runnable onFinish = () ->
        {
            if (!(resultado.equals("Corona"))) {
                this.imvPicker.setDisable(false);
                animacion.animarFadeOut(acpRootPane, onFinishOut);
            }
        };

        animacion.animacionRuleta(imvRuleta, anguloDetenido, onFinish);
    }

}
