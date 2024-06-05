package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.proyecto.model.PreguntaDto;
import cr.ac.una.proyecto.util.FlowController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EstadisticasController extends Controller implements Initializable{

    @FXML
    private BarChart<Number, String> bchartStatistics;

    @FXML
    private TableView<PreguntaDto> tbvPreguntas;

    @FXML
    private TableColumn<PreguntaDto, String> tbcEnun;

    @FXML
    private TableColumn<PreguntaDto, String> tbcID;

    @FXML
    private TableColumn<PreguntaDto, Boolean> tbcLook;

    PreguntaDto preguntaDto;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub


    }

    private void populateTableView(){

        tbcID.setCellValueFactory(cd -> cd.getValue().id);
        tbcEnun.setCellValueFactory(cd -> cd.getValue().enunciado);
        tbcLook.setCellValueFactory((TableColumn.CellDataFeatures<PreguntaDto, Boolean> p) -> new SimpleBooleanProperty(p.getValue() != null));
       /*  tbcLook.setCellFactory((TableColumn<PreguntaDto, Boolean> p) -> new ButtonCell());

        tbvPreguntas.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends PreguntaDto> observable, PreguntaDto oldValue, PreguntaDto newValue) -> {
                    unbindEmpleado();
                    if (newValue != null) {
                        this.preguntaDto = newValue;
                        bindEmpleado(false);
                    }
                });*/


    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        //FlowController.getInstance().goViewInWindow("PreguntaStatistics");
    }

}
