package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Juego;
import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.util.Animacion;

import java.net.URL;
import java.util.ArrayList;
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
    private Animacion animacion;
    private Juego juegoDatos;
    private Boolean resultado;
    @FXML
    private AnchorPane acpRootPane;
    @FXML
    private MFXButton btnCorona;

    @FXML
    private MFXButton btnDuel;

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
        this.resultado = true;
        this.btnDuel.setDisable(true);
        this.btnDuel.setVisible(true);
        cargarJuego();
    }

    @FXML
    void onActionBtnCorona(ActionEvent event) {
        this.resultado = true;
        playAnimation();
        FlowController.getInstance().goViewInWindow("FrontalCardCrownView");
    }

    @FXML
    private void onActionBtnDuel(ActionEvent event) {
        resultado = false;
        playAnimation();
        FlowController.getInstance().goViewInWindow("FrontalCardDuel");
    }


    private void playAnimation() {
        Runnable onFinishOut = () ->
        {
            ((Stage) acpRootPane.getScene().getWindow()).close();
        };

        animacion.animarFadeOut(acpRootPane, onFinishOut);
    }


    public boolean getResultado() {
        return this.resultado;
    }

    private void cargarJuego() {
        this.juegoDatos = (Juego) AppContext.getInstance().get("juego");
        boolean sectorActualHasCrown = false;
        boolean rivalActualHasCrown = false;

        if (juegoDatos != null && juegoDatos.getSectores().size() >= 2) {
            ArrayList<Sector> sectoresJuego = juegoDatos.getSectores();
            int turnoActual = juegoDatos.getTurnoActual();

            for (int index = 0; index < sectoresJuego.size(); index++) {
                Sector sector = sectoresJuego.get(index);

                if (index == turnoActual) {
                    if (sector.hasOneHint()) {
                        sectorActualHasCrown = true;
                    }
                } else {
                    if (sector.hasOneHint()) {
                        rivalActualHasCrown = true;
                    }
                }
                if (sectorActualHasCrown && rivalActualHasCrown) {
                    this.btnDuel.setDisable(false);
                    break;
                }
            }
        }
    }


}