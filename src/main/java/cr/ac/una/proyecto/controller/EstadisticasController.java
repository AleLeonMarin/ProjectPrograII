package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import cr.ac.una.proyecto.model.PreguntaDto;
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
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

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

        // Agrega un listener de la tabla para cargar las estadísticas de la pregunta
        // seleccionada
        tbvPreguntas.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends PreguntaDto> observable,
                        PreguntaDto oldValue, PreguntaDto newValue) -> {

                    if (newValue != null) {
                        // con el valor seleccionado de la tabla se carga el barChart
                        this.preguntaDto = newValue;
                        populateBarChart();
                    }

                });

        // Ajusta la leyenda del gráfico
        bchartStatistics.setLegendVisible(true);
        bchartStatistics.setLegendSide(javafx.geometry.Side.BOTTOM);
        preguntaList.sort(new PreguntaIdComparator());

    }

    // Metodo que da los valores a las columnas de la tabla y se le agrega un boton
    // a la ultima columna
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

    // Metodo que carga las estadisticas de las preguntas y respuestas dentro del
    // barchart
    private void populateBarChart() {
        loadRespuestas();
        // Obtiene la pregunta seleccionada de la tabla
        PreguntaDto preguntaDto = tbvPreguntas.getSelectionModel().getSelectedItem();
        if (preguntaDto != null) {
            // Query que obtiene todas las preguntas y respuestas
            RespuestaUtil respuesta = preguntaService.getAll();
            if (respuesta.getEstado()) {
                bchartStatistics.getData().clear();
                // Se crean dos series, una para las estadísticas de la pregunta y otra para las
                // estadísticas de las respuestas
                XYChart.Series<String, Integer> series1 = new XYChart.Series();
                XYChart.Series<String, Integer> series2 = new XYChart.Series();
                series1.setName("Estadísticas de Pregunta");
                series2.setName("Estadísticas de Respuestas");
                series1.getData().add(new XYChart.Data("Veces Respondida", preguntaDto.getAparicion()));
                series1.getData().add(new XYChart.Data("Veces Acertada", preguntaDto.getAciertos()));
                // Se obtienen las respuestas de la pregunta seleccionada
                cargarPregunta(preguntaDto.getId());
                // se recorren las respuestas de la lista de respuestas
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

    // Metodo que carga la pregunta seleccionada
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
        tbvPreguntas.getSelectionModel().clearSelection();
    }

    // Clase que agrega un boton a la ultima columna de la tabla
    private class ButtonCell extends TableCell<PreguntaDto, Boolean> {

        final Button cellButton = new Button();

        ButtonCell() {
            cellButton.setPrefWidth(500);
            cellButton.getStyleClass().add("jfx-btnimg-tbvSearch");

            cellButton.setOnAction(t -> {
                PreguntaDto preguntaDto = tbvPreguntas.getSelectionModel().getSelectedItem();
                if (preguntaDto != null) {
                    // Se carga la pregunta seleccionada dentro de la vista que permite cargar la
                    // pregunta seleccionada
                    // para ver el detalle de la pregunta
                    FlowController.getInstance().goViewInWindowModal("PreguntaStatistics",
                            ((Stage) cellButton.getScene().getWindow()), true);
                    tbvPreguntas.getSelectionModel().clearSelection();
                } else {
                    new Mensaje().showModal(AlertType.ERROR, "Cargar Pregunta", getStage(),
                            "Debe seleccionar una pregunta");
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

    public PreguntaDto getPregunta() {
        return preguntaDto;
    }

    public void setPreguntaValue() {
        preguntaDto = tbvPreguntas.getSelectionModel().getSelectedItem();
    }

    // carga las preguntas
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

    // en el caso de que los datos vengan desordenados de la base de datos, compara las preguntas por id 
    // y las ordena de menor a mayor
    private class PreguntaIdComparator implements Comparator<PreguntaDto> {

        @Override
        public int compare(PreguntaDto pregunta1, PreguntaDto pregunta2) {
            return Long.compare(pregunta1.getId(), pregunta2.getId());
        }
    }

}
