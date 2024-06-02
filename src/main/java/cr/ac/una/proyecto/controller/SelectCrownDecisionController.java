package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.util.Animacion;
import cr.ac.una.proyecto.util.AppContext;
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
    @FXML
    private AnchorPane acpRootPane;

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
        cargarCategoriasRuleta();
        animacion.simpleFadeIn(acpRootPane);
    }

    public String getResultado() {
        return categoriaRuleta;
    }

    private void cargarCategoriasRuleta() {
        categoriasRuleta = (ArrayList<String>) AppContext.getInstance().get("categoriasRuleta");
        int i = 0;
        for (String cat : categoriasRuleta)
        {
            System.out.println("CategoriaSelectCrown: " + cat + " Indice: " + i);
            i++;
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

}
