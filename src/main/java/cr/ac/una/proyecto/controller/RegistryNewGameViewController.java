package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.JugadorDto;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Formato;
import cr.ac.una.proyecto.util.Mensaje;
import cr.ac.una.proyecto.util.Sound;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXSlider;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
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
    private List<JugadorDto> jugadores;
    private ArrayList<MFXTextField> textFields;

    private JugadorDto jugadorDto;
    List<Node> requeridos = new ArrayList<>();

    Sound sound = new Sound();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txfJug1.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(30));
        txfJug2.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(30));
        txfJug3.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(30));
        txfJug4.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(30));
        txfJug5.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(30));
        txfJug6.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(30));
        jugadorDto = new JugadorDto();
        nuevoJugador();
        validarRequeridos(); // replace with your actual list variable

    }

    @Override
    public void initialize() {
        initializeComponents();
        setupSliderListener();
    }

    private void initializeComponents() {
        jugadores = new ArrayList<>();
        sldQty.setMin(2);
        sldQty.setMax(6);
        cargarSliderCantidad();
        sldQty.setValue(cantJugadores);

        textFields = new ArrayList<>(List.of(txfJug1, txfJug2, txfJug3, txfJug4, txfJug5, txfJug6));
        for (int i = 2; i < textFields.size(); i++) {
            textFields.get(i).setDisable(true);
            textFields.get(i).setVisible(false);
        }
    }

    private void cargarSliderCantidad() {
        cantJugadores = (Integer) AppContext.getInstance().get("cantJugadoresSlider");
        if (cantJugadores == null || cantJugadores > 6) {
            cantJugadores = 2;
        }

    }

    private void setupSliderListener() {
        sldQty.valueProperty().addListener((obs, oldValue, newValue) ->
        {
            int value = newValue.intValue();
            for (int index = 2; index < textFields.size(); index++) {
                textFields.get(index).setDisable(index >= value);
                textFields.get(index).setVisible(index < value);
            }
            cantJugadores = value;
        });
    }

    @FXML
    void onActionBtnNext(ActionEvent event) {
        sound.playSound("src/main/resources/cr/ac/una/proyecto/resources/audio/clickedNext.mp3");

        if (validateFields() && validateUniqueNames()) {
            savePlayerNames();
            proceedToNextView();
        }
    }

    @FXML
    void onDragDetectedSldQty(MouseEvent event) {
        sound.playSound("src/main/resources/cr/ac/una/proyecto/resources/audio/SelectNumberOfPlayers.mp3");
    }

    private boolean validateFields() {
        for (MFXTextField textField : textFields) {
            if (textField.isVisible() && textField.getText().isEmpty()) {
                showAlert("Error", "Existe uno o más espacios en blanco.");
                return false;
            }
        }
        return true;
    }

    private boolean validateUniqueNames() {
        Set<String> namesSet = new HashSet<>();
        for (MFXTextField textField : textFields) {
            if (textField.isVisible()) {
                String name = textField.getText().trim().toUpperCase(); // Convertir a mayúsculas
                if (!name.isEmpty() && !namesSet.add(name)) {
                    showAlert("Error", "El nombre de cada jugador debe ser único");
                    return false;
                }
            }
        }
        return true;
    }


    private void savePlayerNames() {
        for (MFXTextField textField : textFields) {
            if (textField.isVisible() && !textField.getText().isBlank()) {
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

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof MFXTextField
                    && (((MFXTextField) node).getText() == null || ((MFXTextField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((MFXTextField) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXTextField) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXPasswordField
                    && (((MFXPasswordField) node).getText() == null || ((MFXPasswordField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((MFXPasswordField) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXPasswordField) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXDatePicker && ((MFXDatePicker) node).getValue() == null) {
                if (validos) {
                    invalidos += ((MFXDatePicker) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXDatePicker) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXComboBox && ((MFXComboBox) node).getSelectionModel().getSelectedIndex() < 0) {
                if (validos) {
                    invalidos += ((MFXComboBox) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXComboBox) node).getFloatingText();
                }
                validos = false;
            }
        }
        if (validos) {
            return "";
        } else {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }
    }

    private void bindJugador(Boolean nuevo) {
        if (!nuevo) {
            txfJug1.textProperty().bind(jugadorDto.nombre);
            txfJug2.textProperty().bind(jugadorDto.nombre);
            txfJug3.textProperty().bind(jugadorDto.nombre);
            txfJug4.textProperty().bind(jugadorDto.nombre);
            txfJug5.textProperty().bind(jugadorDto.nombre);
            txfJug6.textProperty().bind(jugadorDto.nombre);
        }
    }

    private void unbindJugador() {
        txfJug1.textProperty().unbind();
        txfJug2.textProperty().unbind();
        txfJug3.textProperty().unbind();
        txfJug4.textProperty().unbind();
        txfJug5.textProperty().unbind();
        txfJug6.textProperty().unbind();
    }

    private void nuevoJugador() {
        unbindJugador();
        jugadorDto = new JugadorDto();
        bindJugador(true);
        txfJug1.requestFocus();
        txfJug2.requestFocus();
        txfJug3.requestFocus();
        txfJug4.requestFocus();
        txfJug5.requestFocus();
        txfJug6.requestFocus();
    }

}
