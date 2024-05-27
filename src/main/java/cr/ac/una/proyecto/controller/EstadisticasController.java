package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.proyecto.util.FlowController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.TableView;

public class EstadisticasController extends Controller implements Initializable{

    @FXML
    private BarChart<?, ?> bchartStatistics;

    @FXML
    private TableView<?> tbvPreguntas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        FlowController.getInstance().goViewInWindow("PreguntaStatistics");
    }

}
