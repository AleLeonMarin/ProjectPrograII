package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.concurrent.Flow;

import cr.ac.una.proyecto.model.JugadorDto;
import cr.ac.una.proyecto.model.PreguntaDto;
import cr.ac.una.proyecto.service.JugadorService;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Mensaje;
import cr.ac.una.proyecto.util.RespuestaUtil;
import cr.ac.una.proyecto.util.Sound;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.util.List;

public class TablaPosicionesController extends Controller implements Initializable {

    @FXML
    private MFXButton btnVolver;

    @FXML
    private TableColumn<JugadorDto, Boolean> tbcEstadisticas;

    @FXML
    private TableColumn<JugadorDto, String> tbcID;

    @FXML
    private TableColumn<JugadorDto, String> tbcName;

    @FXML
    private TableView<JugadorDto> tbvPosiciones;

    JugadorDto jugadorDto;
    ObservableList<JugadorDto> jugadorList;
    JugadorService jugadorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        jugadorList = FXCollections.observableArrayList();
        jugadorService = new JugadorService();
        jugadorDto = new JugadorDto();
        tbvPosiciones.setItems(jugadorList);
        populateTable();

        tbvPosiciones.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends JugadorDto> observable,
                        JugadorDto oldValue, JugadorDto newValue) -> {

                    if (newValue != null) {
                        this.jugadorDto = newValue;
                    }

                });
        jugadorList.sort(new JugadorIdComparator());

    }

    private void populateTable() {

        tbcID.setCellValueFactory(cd -> cd.getValue().id);
        tbcName.setCellValueFactory(cd -> cd.getValue().nombre);
        tbcEstadisticas
                .setCellValueFactory((TableColumn.CellDataFeatures<JugadorDto, Boolean> p) -> new SimpleBooleanProperty(
                        p.getValue() != null));
        tbcEstadisticas.setCellFactory((TableColumn<JugadorDto, Boolean> p) -> new ButtonCell());
        chargeJugadores();

    }

    @FXML
    void onActionBtnVolver(ActionEvent event) {

        PauseTransition pause = new PauseTransition(javafx.util.Duration.millis(600));
        Sound sound = new Sound();
        sound.playSound("src/main/resources/cr/ac/una/proyecto/resources/audio/quitExited.mp3");

        pause.setOnFinished(events -> {
            FlowController.getInstance().goViewInWindow("SelectingMode");
            ((Stage) btnVolver.getScene().getWindow()).close();
        });
        pause.play();

    }

    public JugadorDto getJugador() {
        return jugadorDto;
    }

    public void setJugadorValue() {
        jugadorDto = tbvPosiciones.getSelectionModel().getSelectedItem();
    }

    private void chargeJugadores() {

        RespuestaUtil respuestaUtil = jugadorService.getAll();

        if (respuestaUtil.getEstado()) {
            jugadorList.clear();
            jugadorList.addAll((List<JugadorDto>) respuestaUtil.getResultado("Jugadores"));
        } else {
            new Mensaje().showModal(AlertType.ERROR, "Cargar Jugadores", getStage(), respuestaUtil.getMensaje());
        }
    }

    public void initialize() {
        tbvPosiciones.getSelectionModel().clearSelection();

    }

    private class ButtonCell extends TableCell<JugadorDto, Boolean> {

        final Button cellButton = new Button();
        Sound sound = new Sound();

        ButtonCell() {
            cellButton.setPrefWidth(500);
            cellButton.getStyleClass().add("jfx-btnimg-tbvSearch");

            cellButton.setOnAction(t -> {
                JugadorDto jugadorDto = tbvPosiciones.getSelectionModel().getSelectedItem();
                if (jugadorDto != null) {
                    sound.playSound("src/main/resources/cr/ac/una/proyecto/resources/audio/clickedNext.mp3");
                    FlowController.getInstance().goViewInWindowModal("EstadisticasJugador",
                            ((Stage) cellButton.getScene().getWindow()), true);
                    tbvPosiciones.getSelectionModel().clearSelection();
                } else {
                    new Mensaje().showModal(AlertType.ERROR, "Cargar Jugador", getStage(),
                            "Debe seleccionar un jugador");
                }

            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }

    }

    private class JugadorIdComparator implements Comparator<JugadorDto> {

        @Override
        public int compare(JugadorDto jugador1, JugadorDto jugador2) {
            return Long.compare(jugador1.getId(), jugador2.getId());
        }
    }

}
