package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import cr.ac.una.proyecto.model.JugadorDto;
import cr.ac.una.proyecto.service.JugadorService;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Mensaje;
import cr.ac.una.proyecto.util.RespuestaUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

public class EstadisticasJugadorController extends Controller implements Initializable {

    @FXML
    private BarChart<String, Number> bchartStadistics;

    @FXML
    private MFXButton btnSalir;

    JugadorDto jugadorDto;
    JugadorService jugadorService;

    @FXML
    void onActionBtnSalir(ActionEvent event) {

        ((Stage) btnSalir.getScene().getWindow()).close();

    }

    @Override
    public void initialize() {
        jugadorDto = new JugadorDto();
        jugadorService = new JugadorService();
        populateBarChart();

    }


    private void populateBarChart() {

        TablaPosicionesController tablaPosicionesController = (TablaPosicionesController) FlowController.getInstance()
                .getController("TablaPosiciones");
        jugadorDto = (JugadorDto) tablaPosicionesController.getJugador();

        if (jugadorDto != null) {

            RespuestaUtil respuesta = jugadorService.getJugador(jugadorDto.getId());

            if (respuesta != null) {

                bchartStadistics.getData().clear();

                XYChart.Series<String, Number> series1 = new XYChart.Series();
                XYChart.Series<String, Number> series2 = new XYChart.Series();
                XYChart.Series<String, Number> series3 = new XYChart.Series();
                XYChart.Series<String, Number> series4 = new XYChart.Series();
                XYChart.Series<String, Number> series5 = new XYChart.Series();
                XYChart.Series<String, Number> series6 = new XYChart.Series();
                XYChart.Series<String, Number> series7 = new XYChart.Series();
                XYChart.Series<String, Number> series8 = new XYChart.Series();
                XYChart.Series<String, Number> series9 = new XYChart.Series();
                XYChart.Series<String, Number> series10 = new XYChart.Series();
                XYChart.Series<String, Number> series11 = new XYChart.Series();
                XYChart.Series<String, Number> series12 = new XYChart.Series();
                XYChart.Series<String, Number> series13 = new XYChart.Series();
                XYChart.Series<String, Number> series14 = new XYChart.Series();
                XYChart.Series<String, Number> series15 = new XYChart.Series();

                series1.setName("Partidas Ganadas");
                series2.setName("Preguntas Respondidas");
                series3.setName("Preguntas Acertadas");
                series4.setName("Respondidas Historia");
                series5.setName("Respondidas Geografia");
                series6.setName("Respondidas Ciencia");
                series7.setName("Respondidas Deportes");
                series8.setName("Respondidas Entretenimiento");
                series9.setName("Respondidas Arte");
                series10.setName("Acertas Historia");
                series11.setName("Acertas Geografia");
                series12.setName("Acertas Ciencia");
                series13.setName("Acertas Deportes");
                series14.setName("Acertas Entretenimiento");
                series15.setName("Acertas Arte");

                series1.getData().add(new XYChart.Data("Partidas Ganadas", jugadorDto.getPartidasGanadas()));
                series2.getData().add(new XYChart.Data("Preguntas Respondidas", jugadorDto.getPreguntasRespondidas()));
                series3.getData().add(new XYChart.Data("Preguntas Acertadas", jugadorDto.getPartidasGanadas()));
                series4.getData().add(new XYChart.Data("Respondidas Historia", jugadorDto.getContadorHistoria()));
                series5.getData().add(new XYChart.Data("Respondidas Geografia", jugadorDto.getContadorGeografia()));
                series6.getData().add(new XYChart.Data("Respondidas Ciencia", jugadorDto.getContadorCiencia()));
                series7.getData().add(new XYChart.Data("Respondidas Deportes", jugadorDto.getContadorDeportes()));
                series8.getData()
                        .add(new XYChart.Data("Respondidas Entretenimiento", jugadorDto.getContadorEntretenimiento()));
                series9.getData().add(new XYChart.Data("Respondidas Arte", jugadorDto.getContadorArte()));
                series10.getData().add(new XYChart.Data("Acertas Historia", jugadorDto.getContadorCorrectasHistoria()));
                series11.getData()
                        .add(new XYChart.Data("Acertas Geografia", jugadorDto.getContadorCorrectasGeografia()));
                series12.getData().add(new XYChart.Data("Acertas Ciencia", jugadorDto.getContadorCorrectasCiencia()));
                series13.getData().add(new XYChart.Data("Acertas Deportes", jugadorDto.getContadorCorrectasDeportes()));
                series14.getData().add(
                        new XYChart.Data("Acertas Entretenimiento", jugadorDto.getContadorCorrectasEntretenimiento()));
                series15.getData().add(new XYChart.Data("Acertas Arte", jugadorDto.getContadorCorrectasArte()));

                bchartStadistics.getData().addAll(series1, series2, series3, series4, series5, series6, series7,
                        series8, series9, series10, series11, series12, series13, series14, series15);

            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Estadisticas", getStage(),
                        respuesta.getMensaje());
            }

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }

}
