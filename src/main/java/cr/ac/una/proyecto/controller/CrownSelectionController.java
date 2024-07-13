package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.App;
import cr.ac.una.proyecto.model.Corona;
import cr.ac.una.proyecto.model.Juego;
import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.util.Animacion;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CrownSelectionController extends Controller implements Initializable {
    @FXML
    private AnchorPane acpRootPane;
    @FXML
    private MFXButton btnCorona;

    @FXML
    private MFXButton btnDuel;

    private ArrayList<Sector> sectores;
    private Animacion animacion;
    private Boolean resultado;
    Juego juego;
    Sector sector;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize() {

        juego = new Juego();
        sector = new Sector();
        cargarSectores();
        cargarJuego();
        initValues();
    }

    private void initValues() {
        animacion = new Animacion();
        animacion.simpleFadeIn(acpRootPane);
        this.resultado = true;
        this.btnDuel.setDisable(true);
        this.btnDuel.setVisible(true);
        habilitarDuelo();
    }

    @FXML
    void onActionBtnCorona(ActionEvent event) {// Llama la carta frontal de corona
        this.resultado = true;
        playAnimation();
        FlowController.getInstance().goViewInWindow("FrontalCardCrownView");
    }

    @FXML
    private void onActionBtnDuel(ActionEvent event) {// Llama la carta frontal de duelo
        resultado = false;
        playAnimation();
        FlowController.getInstance().goViewInWindow("FrontalCardDuel");
    }

    private void playAnimation() {// Realiza la animacion para cerrar la vista
        Runnable onFinishOut = () -> {
            ((Stage) acpRootPane.getScene().getWindow()).close();
        };

        animacion.animarFadeOut(acpRootPane, onFinishOut);
    }

    public boolean getResultado() {
        return this.resultado;
    }

    private void cargarSectores() {
        sectores = (ArrayList<Sector>) AppContext.getInstance().get("sectores");
    }

    private void cargarJuego() {

        juego = (Juego) AppContext.getInstance().get("juego");

    }

    private void habilitarDuelo() {
        boolean crown = false;

        if (juego != null && juego.getSectorActual() != null) {
            int turno = juego.getTurnoActual();
            Sector sector = sectores.get(turno);

            // Obtener las coronas del sector actual
            List<Corona> coronas = sector.getCoronas();
            System.out.println("Coronas: " + coronas);
            AppContext.getInstance().set("coronasRetador", coronas);

            // Verificar si hay coronas activas
            if (coronas != null && !coronas.isEmpty()) {
                for (Corona corona : coronas) {
                    if (corona != null && corona.getEstado() == true) { // Cambia "activada" al estado que corresponda
                                                                        // en tu lógica
                        crown = true;
                        break;
                    } else {
                        crown = false;

                    }
                }
            }

            // Habilitar o deshabilitar el botón de duelo según la verificación de coronas
            if (crown) {
                btnDuel.setDisable(false);
                btnDuel.setVisible(true);
            } else {
                btnDuel.setDisable(true);
                btnDuel.setVisible(false);
            }
        } else {
            btnDuel.setDisable(true);
            btnDuel.setVisible(false);
        }
    }

}
