package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.util.Animacion;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXButton;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FrontalCards extends Controller implements Initializable {

    @FXML
    private AnchorPane acpRootPane;
    @FXML
    private ImageView imvTirarRuleta;
    @FXML
    private MFXButton btnResponderPregunta;
    @FXML
    private Label lblAyudaRuleta;

    private Animacion animacion;
    private Sector sectorDto;
    private String preguntaCategoria;
    private final String ayudaRuleta = "TirarRuleta";
    private Runnable onFinishOut;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void initialize() {
        habilitarCosas(false);
        cargarCategoriaAppContext();
        cargarSectorJugadorDtoAppContext();
        initValues();
    }

    @FXML
    private void onMouseTirarRuleta(MouseEvent event) {
        sectorDto.removerAyuda(ayudaRuleta);
        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Ayuda Activada", getStage(),
                "Has seleccionado la ayuda de tirar ruleta, esta ayuda te deja girar la ruleta para seleccionar otra pregunta");

        TirarRuletaController tirarRuletaBusquedaController = (TirarRuletaController) FlowController.getInstance()
                .getController("TirarRuletaView");
        FlowController.getInstance().goViewInWindowModal("TirarRuletaView",
                ((Stage) acpRootPane.getScene().getWindow()), true);
        preguntaCategoria = (String) tirarRuletaBusquedaController.getResultado();
        setCategoriaRuleta();
        animacion.simpleFadeIn(acpRootPane);
        Platform.runLater(() -> animacion.animarFadeOut(acpRootPane, onFinishOut));
    }

    @FXML
    private void OnActionBtnResponderPregunta(ActionEvent event) {
        Platform.runLater(() -> animacion.animarFadeOut(acpRootPane, onFinishOut));
    }

    private void initValues() {
        animacion = new Animacion();
        onFinishOut = () ->
        {
            ((Stage) acpRootPane.getScene().getWindow()).close();
        };

        Runnable onFinishIn = () ->
        {
            if (!(sectorDto.getAyudas().isEmpty()) &&(sectorDto.findAyudaByName(ayudaRuleta))) {
                habilitarCosas(true);
            } else {
                Platform.runLater(() -> animacion.animarFadeOut(acpRootPane, onFinishOut));
            }
        };

        animacion.animarFadeIn(acpRootPane, onFinishIn);
    }


    private void cargarCategoriaAppContext() {
        this.preguntaCategoria = ((String) AppContext.getInstance().get("preguntaCategoria"));
    }

    private void cargarSectorJugadorDtoAppContext() {
        this.sectorDto = new Sector();
        sectorDto = ((Sector) AppContext.getInstance().get("preguntaSector"));
    }

    private void setCategoriaRuleta() {
        AppContext.getInstance().set("preguntaCategoria", preguntaCategoria);
    }


    private void habilitarCosas(boolean valor) {
        this.imvTirarRuleta.setDisable(!valor);
        this.imvTirarRuleta.setVisible(valor);
        this.btnResponderPregunta.setDisable(!valor);
        this.btnResponderPregunta.setVisible(valor);
        this.lblAyudaRuleta.setVisible(valor);
    }

}
