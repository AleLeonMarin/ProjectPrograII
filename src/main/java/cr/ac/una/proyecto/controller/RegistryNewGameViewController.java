package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Jugador;
import cr.ac.una.proyecto.util.AppContext;
import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.HashSet;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistryNewGameViewController extends Controller implements Initializable {

    @FXML
    private MFXButton btnNext;

    @FXML
    private MFXSlider sldQty;

    @FXML
    private MFXTextField txfJug1;

    @FXML
    private MFXTextField txfJug2;

    @FXML
    private MFXTextField txfJug3;

    @FXML
    private MFXTextField txfJug4;

    @FXML
    private MFXTextField txfJug5;

    @FXML
    private MFXTextField txfJug6;
    private int cantJugadores;
    private ObservableList<Jugador> jugadores;

    @FXML
    void onActionBtnNext(ActionEvent event) {

        validarEspacios();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize() {
        jugadores = FXCollections.observableArrayList();
        cantJugadores = 2;
        sldQty.setMin(2);
        sldQty.setMax(6);
        sldQty.setValue(2);
        txfJug3.setDisable(true);
        txfJug4.setDisable(true);
        txfJug5.setDisable(true);
        txfJug6.setDisable(true);
        txfJug3.setVisible(false);
        txfJug4.setVisible(false);
        txfJug5.setVisible(false);
        txfJug6.setVisible(false);

        sldQty.valueProperty().addListener((obs, oldValue, newValue) ->
        {
            int value = newValue.intValue();
            txfJug3.setDisable(value < 3);
            txfJug4.setDisable(value < 4);
            txfJug5.setDisable(value < 5);
            txfJug6.setDisable(value < 6);
            txfJug3.setVisible(value >= 3);
            txfJug4.setVisible(value >= 4);
            txfJug5.setVisible(value >= 5);
            txfJug6.setVisible(value >= 6);
            cantJugadores = value;
            System.out.println("El slider quedó en la posición: " + cantJugadores);
        });
    }

    private boolean validarCampoVacio(String nombreCampo, TextField campoTexto) {
        if (campoTexto.isVisible() && nombreCampo.isEmpty())
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error", getStage(), "Espacio " + nombreCampo + " vacio");
            return false;
        }
        return true;
    }

    private boolean validarNombresUnicos(String... nombres) {
        Set<String> nombresSet = new HashSet<>();
        for (String nombre : nombres)
        {
            if (nombre != null && !nombre.trim().isEmpty())
            {
                if (!nombresSet.add(nombre.trim()))
                {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error", getStage(), "Los nombres deben ser únicos");
                    return false;
                }
            }
        }
        return true;
    }

    private void validarEspacios() {
        if (!validarCampoVacio(txfJug1.getText(), txfJug1)
                || !validarCampoVacio(txfJug2.getText(), txfJug2)
                || !validarCampoVacio(txfJug3.getText(), txfJug3)
                || !validarCampoVacio(txfJug4.getText(), txfJug4)
                || !validarCampoVacio(txfJug5.getText(), txfJug5)
                || !validarCampoVacio(txfJug6.getText(), txfJug6))
        {
            System.out.println("ESPACIO A");
            return;
        }

        if (!validarNombresUnicos(txfJug1.getText(), txfJug2.getText(), txfJug3.getText(), txfJug4.getText(), txfJug5.getText(), txfJug6.getText()))
        {
            System.out.println("ESPACIO B");
            return;
        }

        funcionAppContext();
    }

    private void guardarJugadoresNombres() {

        jugadores.add(new Jugador(txfJug1.getText()));
        jugadores.add(new Jugador(txfJug2.getText()));

        if ((!txfJug3.getText().isBlank()) && (txfJug3.isVisible()))
        {
            jugadores.add(new Jugador(txfJug3.getText()));
        }
        if ((!txfJug4.getText().isBlank()) && (txfJug4.isVisible()))
        {
            jugadores.add(new Jugador(txfJug4.getText()));
        }
        if ((!txfJug5.getText().isBlank()) && (txfJug5.isVisible()))
        {
            jugadores.add(new Jugador(txfJug5.getText()));
        }
        if ((!txfJug6.getText().isBlank()) && (txfJug6.isVisible()))
        {
            jugadores.add(new Jugador(txfJug6.getText()));
        }

        for (Jugador jugadorAux : jugadores)
        {
            System.out.println("Jugador: " + jugadorAux.getNombre());
        }

        AppContext.getInstance().set("jugadores", jugadores);

    }

    private void funcionAppContext() {
        guardarJugadoresNombres();
        AppContext.getInstance().set("cantJugadoresSlider", cantJugadores);
        FlowController.getInstance().goViewInWindow("SectorSelectionView");
        ((Stage) btnNext.getScene().getWindow()).close();
    }

}
