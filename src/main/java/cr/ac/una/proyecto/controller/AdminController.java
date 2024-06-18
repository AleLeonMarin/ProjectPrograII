package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.proyecto.model.PDFViewer;
import cr.ac.una.proyecto.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminController extends Controller implements Initializable {

    @FXML
    private MFXButton bntSalir;

    @FXML
    private MFXButton btnMantenimiento;

    @FXML
    private MFXButton btnEstadisticas;
    @FXML
    private Label lblIntrucciones;

    private PDFViewer pdfViewer;

    @FXML
    void onActionBntSalir(ActionEvent event) {
        FlowController.getInstance().salir();

    }

    @FXML
    void onActionBtnMantenimiento(ActionEvent event) {

        FlowController.getInstance().goView("MantenimientoPreguntas");

    }

    @FXML
    void onActionBtnEstadisticas(ActionEvent event) {

        FlowController.getInstance().goView("Estadisticas");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.pdfViewer = new PDFViewer();
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
    }

    @FXML
    private void onMousePressedLblInstrucciones(MouseEvent event) {
        this.pdfViewer.start((Stage) bntSalir.getScene().getWindow(), "/cr/ac/una/proyecto/resources/Instrucciones.pdf");
    }

}
