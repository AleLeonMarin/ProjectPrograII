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
        int index = 0;
        for (String cat : categoriasRuleta)
        {
            System.out.println("CategoriaSelectCrown: " + cat + " Indice: " + index);
            index++;
        }
    }

    @FXML
    private void OnActionBtnHistoria(ActionEvent event) {
        categoriaRuleta = categoriasRuleta.get(6);
        ((Stage) acpRootPane.getScene().getWindow()).close();
    }

    @FXML
    private void OnActionBtnArte(ActionEvent event) {
        categoriaRuleta = categoriasRuleta.get(1);
        ((Stage) acpRootPane.getScene().getWindow()).close();
    }

    @FXML
    private void OnActionBtnGeografia(ActionEvent event) {
        categoriaRuleta = categoriasRuleta.get(2);
        ((Stage) acpRootPane.getScene().getWindow()).close();
    }

    @FXML
    private void OnActionBtnDeporte(ActionEvent event) {
        categoriaRuleta = categoriasRuleta.get(0);
        ((Stage) acpRootPane.getScene().getWindow()).close();
    }

    @FXML
    private void OnActionBtnEntretenimiento(ActionEvent event) {
        categoriaRuleta = categoriasRuleta.get(5);
        ((Stage) acpRootPane.getScene().getWindow()).close();
    }

    @FXML
    private void OnActionBtnCiencia(ActionEvent event) {
        categoriaRuleta = categoriasRuleta.get(3);
        System.out.println("HOLA" + categoriaRuleta);
        ((Stage) acpRootPane.getScene().getWindow()).close();
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
                { // Si el sector ya tiene esta corona, deshabilita el botón correspondiente
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
            case "Deporte":
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

}
