package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import cr.ac.una.proyecto.model.Pregunta;
import cr.ac.una.proyecto.model.PreguntaDto;
import cr.ac.una.proyecto.model.Respuesta;
import cr.ac.una.proyecto.model.RespuestaDto;
import cr.ac.una.proyecto.service.PreguntaService;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Mensaje;
import cr.ac.una.proyecto.util.RespuestaUtil;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EstadisticasController extends Controller implements Initializable {

    @FXML
    private BarChart<String, Integer> bchartStatistics;

    @FXML
    private TableView<PreguntaDto> tbvPreguntas;

    @FXML
    private TableColumn<PreguntaDto, String> tbcEnun;

    @FXML
    private TableColumn<PreguntaDto, String> tbcID;

    @FXML
    private TableColumn<PreguntaDto, Boolean> tbcLook;

    PreguntaDto preguntaDto;
    ObservableList<PreguntaDto> preguntaList;
    PreguntaService preguntaService;
    private List<RespuestaDto> respuestasList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preguntaList = FXCollections.observableArrayList();
        respuestasList = new ArrayList<>();
        tbvPreguntas.setItems(preguntaList);
        preguntaService = new PreguntaService();
        populateTableView();
        bchartStatistics.setTitle("Estadísticas de Preguntas y Respuestas");
        
        tbvPreguntas.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends PreguntaDto> observable,
                        PreguntaDto oldValue, PreguntaDto newValue) -> {

                    if (newValue != null) {
                        this.preguntaDto = newValue;
                        populateBarChart();
                    }

                });

        // Ajusta la leyenda del gráfico
        bchartStatistics.setLegendVisible(true);
        bchartStatistics.setLegendSide(javafx.geometry.Side.BOTTOM);

    }

    private void populateTableView() {

        tbcID.setCellValueFactory(cd -> cd.getValue().id);
        tbcEnun.setCellValueFactory(cd -> cd.getValue().enunciado);
        tbcLook.setCellValueFactory((TableColumn.CellDataFeatures<PreguntaDto, Boolean> p) -> new SimpleBooleanProperty(
                p.getValue() != null));
        tbcLook.setCellFactory((TableColumn<PreguntaDto, Boolean> p) -> new ButtonCell());
        chargePregunta();

    }

    private void loadRespuestas() {
        respuestasList.clear();

        respuestasList = this.preguntaDto.getRespuestas();
    }

    private void populateBarChart() {
        loadRespuestas();
        PreguntaDto preguntaDto = tbvPreguntas.getSelectionModel().getSelectedItem();
        if (preguntaDto != null) {
            RespuestaUtil respuesta = preguntaService.getAll();
            if (respuesta.getEstado()) {
                bchartStatistics.getData().clear();
                XYChart.Series<String, Integer> series1 = new XYChart.Series();
                XYChart.Series<String, Integer> series2 = new XYChart.Series();
                series1.setName("Estadísticas de Pregunta");
                series2.setName("Estadísticas de Respuestas");
                series1.getData().add(new XYChart.Data("Veces Respondida", preguntaDto.getAparicion()));
                series1.getData().add(new XYChart.Data("Veces Acertada", preguntaDto.getAciertos()));
                cargarPregunta(preguntaDto.getId());
                for (RespuestaDto respuestaDto : respuestasList) {
                    series2.getData()
                            .add(new XYChart.Data("Respuesta " + respuestaDto.getId(), respuestaDto.getContador()));
                }
                bchartStatistics.getData().addAll(series1, series2);
            } else {
                new Mensaje().showModal(AlertType.ERROR, "Cargar Estadísticas", getStage(), respuesta.getMensaje());
            }
        }
    }

    private void cargarPregunta(Long preId) {
        try {
            PreguntaService preguntaService = new PreguntaService();
            RespuestaUtil respuesta = preguntaService.getPregunta(preId);
            if (respuesta.getEstado()) {
                this.preguntaDto = (PreguntaDto) respuesta.getResultado("Pregunta");
                loadRespuestas();

            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Pregunta", getStage(), respuesta.getMensaje());

            }
        } catch (Exception ex) {
            Logger.getLogger(MantenimientoController.class
                    .getName()).log(Level.SEVERE, "Error consultando la pregunta.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Pregunta", getStage(),
                    "Ocurrio un error consultando la pregunta.");
        }
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        // FlowController.getInstance().goViewInWindow("PreguntaStatistics");
    }

    private class ButtonCell extends TableCell<PreguntaDto, Boolean> {

        final Button cellButton = new Button();

        ButtonCell() {
            cellButton.setPrefWidth(500);
            cellButton.getStyleClass().add("jfx-btnimg-tbvSearch");

            cellButton.setOnAction(t -> {
                FlowController.getInstance().goViewInWindow("PreguntaStatistics");
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

    private void chargePregunta() {

        PreguntaService preguntaService = new PreguntaService();
        RespuestaUtil respuesta = preguntaService.getAll();

        if (respuesta.getEstado()) {
            preguntaList.clear();
            preguntaList.addAll((List<PreguntaDto>) respuesta.getResultado("Preguntas"));

        } else {
            new Mensaje().showModal(AlertType.ERROR, "Cargar Pregunta", getStage(), respuesta.getMensaje());
        }
    }

}
