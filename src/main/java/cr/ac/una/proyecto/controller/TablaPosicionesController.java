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
import io.github.palexdev.materialfx.controls.MFXButton;
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

        FlowController.getInstance().goViewInWindow("SelectingMode");
        ((Stage) btnVolver.getScene().getWindow()).close();

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
        // TODO Auto-generated method stub

    }

    private class ButtonCell extends TableCell<JugadorDto, Boolean> {

        final Button cellButton = new Button();

        ButtonCell() {
            cellButton.setPrefWidth(500);
            cellButton.getStyleClass().add("jfx-btnimg-tbvSearch");

            cellButton.setOnAction(t -> {
                FlowController.getInstance().goViewInWindowModal("EstadisticasJugador",
                        ((Stage) cellButton.getScene().getWindow()), true);
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

    private class PreguntaIdComparator implements Comparator<PreguntaDto> {

        @Override
        public int compare(PreguntaDto pregunta1, PreguntaDto pregunta2) {
            return Long.compare(pregunta1.getId(), pregunta2.getId());
        }
    }

}
