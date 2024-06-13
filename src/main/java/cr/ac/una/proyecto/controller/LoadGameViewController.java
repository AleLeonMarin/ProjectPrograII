package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cr.ac.una.proyecto.model.PartidaDto;
import cr.ac.una.proyecto.service.PartidaService;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Mensaje;
import cr.ac.una.proyecto.util.RespuestaUtil;
import io.github.palexdev.materialfx.controls.MFXButton;

import java.time.LocalDate;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoadGameViewController extends Controller implements Initializable {

    @FXML
    private MFXButton btnChargeMatch;

    @FXML
    private MFXButton btnSalir;

    @FXML
    private TableView<PartidaDto> tbvMatches;

    @FXML
    private TableColumn<PartidaDto, String> tbcDuenio;

    @FXML
    private TableColumn<PartidaDto, String> tbcId;

    PartidaDto partidaDto;

    PartidaService partidaService;

    private boolean cargarPartida;

    private ObservableList<PartidaDto> partidas;
    @FXML
    private TableColumn<PartidaDto, String> tbcRondas;
    @FXML
    private TableColumn<PartidaDto, LocalDate> tbcFecha;

    @FXML
    void onActionBtnChargeMatch(ActionEvent event) {
        partidaDto = tbvMatches.getSelectionModel().getSelectedItem();
        if (partidaDto != null) {
            setPartidaToAppContext();
            FlowController.getInstance().goMain("tableroView");
            ((Stage) btnChargeMatch.getScene().getWindow()).close();
        }
    }

    @FXML
    void onActionBtnSalir(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("SelectingMode");
        ((Stage) btnSalir.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.cargarPartida = false;
        partidas = FXCollections.observableArrayList();
        partidaService = new PartidaService();
        partidaDto = new PartidaDto();
        populateTable();
        tbvMatches.setItems(partidas);
    }

    @Override
    public void initialize() {
        tbvMatches.getSelectionModel().clearSelection();
    }

    private void populateTable() {

        tbcId.setCellValueFactory(cd -> cd.getValue().parId);
        tbcDuenio.setCellValueFactory(cd -> cd.getValue().parDuenio);
        tbcRondas.setCellValueFactory(cd -> cd.getValue().ronda);
        tbcFecha.setCellValueFactory(cd -> cd.getValue().fecha);
        loadMatchesToTable();

    }

    private void loadMatchesToTable() {

        RespuestaUtil respuesta = partidaService.getAll();

        if (respuesta.getEstado()) {
            partidas.clear();
            partidas.addAll((List<PartidaDto>) respuesta.getResultado("Partidas"));
        } else {
            new Mensaje().showModal(AlertType.ERROR, "Cargar Partidas", getStage(), respuesta.getMensaje());
        }
    }

    private void setPartidaToAppContext() {
        AppContext.getInstance().set("partidaCargada", partidaDto);
    }

    @FXML
    private void onMousePressedTbvMatches(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            onActionBtnChargeMatch(null);
        }
    }

}
