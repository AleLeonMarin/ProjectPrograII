package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.util.Animacion;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.Ruleta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
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

    private void moverRuleta() {

        this.resultado = obtenerPosicionRuleta();
        double anguloDetenido = getRuletaAngulo();

        Runnable onFinishOut = () ->
        {
            ((Stage) acpRootPane.getScene().getWindow()).close();
        };

        Runnable onFinish = () ->
        {
            System.out.println("La animación de la ruleta ha terminado en esta categoria: " + resultado + ", Angulo: " + anguloDetenido);
            this.imvPicker.setDisable(false);
            animacion.animarFadeOut(imvRuleta, onFinishOut);

        };

        animacion.animacionRuleta(imvRuleta, anguloDetenido, onFinish);
    }

}
