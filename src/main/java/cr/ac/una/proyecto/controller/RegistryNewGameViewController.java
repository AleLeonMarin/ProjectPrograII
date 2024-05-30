package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Jugador;
import cr.ac.una.proyecto.model.JugadorDto;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Mensaje;
import cr.ac.una.proyecto.util.Sound;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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

    private Integer cantJugadores;
    private ObservableList<JugadorDto> jugadores;
    private ArrayList<MFXTextField> textFields;

    Sound sound = new Sound();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void initialize() {
        initializeComponents();
        setupSliderListener();
    }

    private void initializeComponents() {

        jugadores = FXCollections.observableArrayList();
        sldQty.setMin(2);
        sldQty.setMax(6);
        cargarSliderCantidad();
        sldQty.setValue(cantJugadores);

        textFields = new ArrayList<>(List.of(txfJug1, txfJug2, txfJug3, txfJug4, txfJug5, txfJug6));
        for (int i = 2; i < textFields.size(); i++)
        {
            textFields.get(i).setDisable(true);
            textFields.get(i).setVisible(false);
        }
    }

    private void cargarSliderCantidad() {
        cantJugadores = (Integer) AppContext.getInstance().get("cantJugadoresSlider");
        if (cantJugadores == null || cantJugadores > 6)
        {
            cantJugadores = 2;
        }

    }

    private void setupSliderListener() {
        sldQty.valueProperty().addListener((obs, oldValue, newValue) ->
        {
            int value = newValue.intValue();
            for (int index = 2; index < textFields.size(); index++)
            {
                textFields.get(index).setDisable(index >= value);
                textFields.get(index).setVisible(index < value);
            }
            cantJugadores = value;
        });
    }

    @FXML
    void onActionBtnNext(ActionEvent event) {
        sound.playSound("src/main/resources/cr/ac/una/proyecto/resources/audio/clickedNext.mp3");

        if (validateFields() && validateUniqueNames())
        {
            savePlayerNames();
            proceedToNextView();
        }
    }

    @FXML
    void onDragDetectedSldQty(MouseEvent event) {
        sound.playSound("src/main/resources/cr/ac/una/proyecto/resources/audio/SelectNumberOfPlayers.mp3");
    }

    private boolean validateFields() {
        for (MFXTextField textField : textFields)
        {
            if (textField.isVisible() && textField.getText().isEmpty())
            {
                showAlert("Error", "Existe uno o más espacios en blanco.");
                return false;
            }
        }
        return true;
    }

    private boolean validateUniqueNames() {
        Set<String> namesSet = new HashSet<>();
        for (MFXTextField textField : textFields)
        {
            if (textField.isVisible())
            {
                String name = textField.getText().trim();
                if (!name.isEmpty() && !namesSet.add(name))
                {
                    showAlert("Error", "El nombre de cada jugador debe ser único");
                    return false;
                }
            }
        }
        return true;
    }

    private void savePlayerNames() {
        for (MFXTextField textField : textFields)
        {
            if (textField.isVisible() && !textField.getText().isBlank())
            {
                JugadorDto jugador = new JugadorDto(textField.getText());
                System.out.println("Jugador nombre" + jugador.getNombre());
                jugadores.add(jugador);
            }
        }
        AppContext.getInstance().set("jugadores", jugadores);
    }

    private void proceedToNextView() {
        AppContext.getInstance().set("cantJugadoresSlider", cantJugadores);
        FlowController.getInstance().goViewInWindow("SectorSelectionView");
        ((Stage) btnNext.getScene().getWindow()).close();
    }

    private void showAlert(String title, String message) {
        new Mensaje().showModal(Alert.AlertType.ERROR, title, getStage(), message);
    }

}
