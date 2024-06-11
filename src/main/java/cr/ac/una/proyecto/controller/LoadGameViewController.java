package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import cr.ac.una.proyecto.model.PartidaDto;
import cr.ac.una.proyecto.service.PartidaService;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Mensaje;
import cr.ac.una.proyecto.util.RespuestaUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.media.Media;
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

    private ObservableList<PartidaDto> partidas;

    @FXML
    void onActionBtnChargeMatch(ActionEvent event) {
        FlowController.getInstance().goMain("tableroView");
        ((Stage) btnChargeMatch.getScene().getWindow()).close();

    }

    @FXML
    void onActionBtnSalir(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("SelectingMode");
        ((Stage) btnSalir.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        loadMatchesToTable();
        
    }

    private void loadMatchesToTable() {
        
        RespuestaUtil respuesta = partidaService.getAll();

        if(respuesta.getEstado()){
            partidas.clear();
            partidas.addAll((List<PartidaDto>) respuesta.getResultado("Partidas"));
        }else{
            new Mensaje().showModal(AlertType.ERROR, "Cargar Partidas", getStage(), respuesta.getMensaje());
        }
    }

}
