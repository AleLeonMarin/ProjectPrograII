package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Corona;
import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.util.Animacion;
import cr.ac.una.proyecto.util.AppContext;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author justi
 */
public class SelectCrownDecisionController extends Controller implements Initializable {

    private ArrayList<String> categoriasRuleta;
    private String categoriaRuleta;
    private Animacion animacion;
    private Sector sectorActual;
    @FXML
    private AnchorPane acpRootPane;
    @FXML
    private MFXButton btnHistoria;
    @FXML
    private MFXButton btnArte;
    @FXML
    private MFXButton btnGeografia;
    @FXML
    private MFXButton btnDeporte;
    @FXML
    private MFXButton btnEntretenimiento;
    @FXML
    private MFXButton btnCiencia;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {
        animacion = new Animacion();
        sectorActual = new Sector();
        cargarCategoriasRuleta();
        cargarSectorActual();
        habilitarBotonesCoronas();
        desabilitarBotonesCoronas();
        animacion.simpleFadeIn(acpRootPane);

    }

    public String getResultado() {
        return categoriaRuleta;
    }

    private void cargarCategoriasRuleta() {
        categoriasRuleta = (ArrayList<String>) AppContext.getInstance().get("categoriasRuleta");
    }

    @FXML
    private void OnActionBtnDeporte(ActionEvent event) {
        categoriaRuleta = categoriasRuleta.get(0);
        animacion.animarFadeOut(acpRootPane, getRunnableOnFinishOut());
    }

    @FXML
    private void OnActionBtnArte(ActionEvent event) {
        categoriaRuleta = categoriasRuleta.get(1);
        animacion.animarFadeOut(acpRootPane, getRunnableOnFinishOut());
    }

    @FXML
    private void OnActionBtnGeografia(ActionEvent event) {
        categoriaRuleta = categoriasRuleta.get(2);
        animacion.animarFadeOut(acpRootPane, getRunnableOnFinishOut());
    }

    @FXML
    private void OnActionBtnCiencia(ActionEvent event) {
        categoriaRuleta = categoriasRuleta.get(3);
        animacion.animarFadeOut(acpRootPane, getRunnableOnFinishOut());
    }

    @FXML
    private void OnActionBtnEntretenimiento(ActionEvent event) {
        categoriaRuleta = categoriasRuleta.get(5);
        animacion.animarFadeOut(acpRootPane, getRunnableOnFinishOut());
    }

    @FXML
    private void OnActionBtnHistoria(ActionEvent event) {
        categoriaRuleta = categoriasRuleta.get(6);
        animacion.animarFadeOut(acpRootPane, getRunnableOnFinishOut());
    }

    private void cargarSectorActual() {

        sectorActual = (Sector) AppContext.getInstance().get("preguntaSector");
    }

    private void habilitarBotonesCoronas() {
        this.btnArte.setDisable(false);
        this.btnCiencia.setDisable(false);
        this.btnDeporte.setDisable(false);
        this.btnEntretenimiento.setDisable(false);
        this.btnGeografia.setDisable(false);
        this.btnHistoria.setDisable(false);
    }

    private void desabilitarBotonesCoronas() {
        if (sectorActual != null)
        {
            ArrayList<Corona> coronas = sectorActual.getCoronas();
            for (Corona corona : coronas)
            {
                if (corona.getEstado())
                {
                    deshabilitarBoton(corona.getNombre());
                }
            }
        }
    }

    private void deshabilitarBoton(String categoria) {
        switch (categoria)
        {
            case "Historia":
                btnHistoria.setDisable(true);
                break;
            case "Arte":
                btnArte.setDisable(true);
                break;
            case "Geografia":
                btnGeografia.setDisable(true);
                break;
            case "Deportes":
                btnDeporte.setDisable(true);
                break;
            case "Entretenimiento":
                btnEntretenimiento.setDisable(true);
                break;
            case "Ciencia":
                btnCiencia.setDisable(true);
                break;
        }
    }

    private Runnable getRunnableOnFinishOut() {

        Runnable onFinishOut = () ->
        {
            ((Stage) acpRootPane.getScene().getWindow()).close();
        };

        return onFinishOut;
    }

    private void comparteCrownsForDuel(){
        
    }

}
