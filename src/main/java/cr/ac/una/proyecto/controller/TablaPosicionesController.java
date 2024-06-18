package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import cr.ac.una.proyecto.model.JugadorDto;
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
        // Agrega listener para saber que jugador se selecciona
        tbvPosiciones.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends JugadorDto> observable,
                        JugadorDto oldValue, JugadorDto newValue) -> {

                    if (newValue != null) {
                        // Obtiene el jugador seleccionado en la tabla
                        this.jugadorDto = newValue;
                    }

                });
        // ordena la tabla por id
        jugadorList.sort(new JugadorIdComparator());

    }

    // Asigna que tipo de dato va a tener cada columna de la tabla
    private void populateTable() {

        tbcID.setCellValueFactory(cd -> cd.getValue().id);
        tbcName.setCellValueFactory(cd -> cd.getValue().nombre);
        // asigna el boton a la columna de estadisticas
        tbcEstadisticas
                .setCellValueFactory((TableColumn.CellDataFeatures<JugadorDto, Boolean> p) -> new SimpleBooleanProperty(
                        p.getValue() != null));
        tbcEstadisticas.setCellFactory((TableColumn<JugadorDto, Boolean> p) -> new ButtonCell());
        chargeJugadores();

    }

    // Metodo que se ejecuta al presionar el boton de volver
    @FXML
    void onActionBtnVolver(ActionEvent event) {

        PauseTransition pause = new PauseTransition(javafx.util.Duration.millis(600));
        Sound sound = new Sound();
        sound.playSound("quitExited.mp3");

        pause.setOnFinished(events -> {
            FlowController.getInstance().goViewInWindow("SelectingMode");
            ((Stage) btnVolver.getScene().getWindow()).close();
        });
        pause.play();

    }

    // Obtiene el jugador seleccionado
    public JugadorDto getJugador() {
        return jugadorDto;
    }

    public void setJugadorValue() {
        jugadorDto = tbvPosiciones.getSelectionModel().getSelectedItem();
    }

    // Carga todos los jugadores de la base de datos
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

    // Clase que agrega un boton a la ultima columna de la tabla
    private class ButtonCell extends TableCell<JugadorDto, Boolean> {

        final Button cellButton = new Button();
        Sound sound = new Sound();

        ButtonCell() {
            cellButton.setPrefWidth(500);
            cellButton.getStyleClass().add("jfx-btnimg-tbvSearch");

            cellButton.setOnAction(t -> {
                JugadorDto jugadorDto = tbvPosiciones.getSelectionModel().getSelectedItem();
                if (jugadorDto != null) {
                    sound.playSound("clickedNext.mp3");
                    // Carga la vista de estadisticas del jugador seleccionado
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

    // Clase que compara los id de los jugadores para ordenarlos
    // en la tabla de menor a mayor
    private class JugadorIdComparator implements Comparator<JugadorDto> {

        @Override
        public int compare(JugadorDto jugador1, JugadorDto jugador2) {
            return Long.compare(jugador1.getId(), jugador2.getId());
        }
    }

}
