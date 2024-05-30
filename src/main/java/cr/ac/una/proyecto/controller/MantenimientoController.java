package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.PreguntaDto;
import cr.ac.una.proyecto.service.PreguntaService;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Formato;
import cr.ac.una.proyecto.util.Mensaje;
import cr.ac.una.proyecto.util.Respuesta;
import java.net.URL;
import java.util.ResourceBundle;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class MantenimientoController extends Controller implements Initializable {

    @FXML
    private MFXButton btnEliminar;
    @FXML
    private MFXButton btnGuardar;
    @FXML
    private MFXButton btnLimpiar;
    @FXML
    private MFXTextField txfPreguntaId;
    @FXML
    private MFXTextField txfPreguntaCategoria;
    @FXML
    private MFXCheckbox chkPreguntaEstado;
    @FXML
    private MFXTextField txfPreguntaRespuesta1;
    @FXML
    private MFXTextField txfPreguntaRespuesta2;
    @FXML
    private MFXTextField txfPreguntaRespuesta3;
    @FXML
    private MFXTextField txfPreguntaRespuesta4;
    @FXML
    private MFXButton btnNuevo;
    @FXML
    private MFXButton btnBuscar;

    private PreguntaDto preguntaDto;
    List<Node> requeridos = new ArrayList<>();
    @FXML
    private TextArea txaPreguntaEnunciado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preguntaDto = new PreguntaDto();
        txfPreguntaId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txfPreguntaCategoria.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txaPreguntaEnunciado.setTextFormatter(Formato.getInstance().cedulaFormat(200));
        chkPreguntaEstado.setSelected(true);
        nuevaPregunta();
        IndicarRequeridos();
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
    }

    @FXML
    private void OnActionChklPreguntaEstado(ActionEvent event) {
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
        if (new Mensaje().showConfirmation("Limpiar Pregunta", getStage(), "¿Esta seguro que desea limipiar el registro?"))
        {
            nuevaPregunta();
        }
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnLimpiar(ActionEvent event) {
        if (new Mensaje().showConfirmation("Limpiar Pregunta", getStage(), "¿Esta seguro que desea limipiar el registro?"))
        {
            nuevaPregunta();
        }
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
        BuscarPreguntaController busquedaController = (BuscarPreguntaController) FlowController.getInstance().getController("BuscarPreguntaView");
        FlowController.getInstance().goViewInWindowModal("BuscarPreguntaView", ((Stage) btnBuscar.getScene().getWindow()), true);
        PreguntaDto preguntaDto = (PreguntaDto) busquedaController.getResultado();

        if (preguntaDto != null)
        {
            cargarPregunta(preguntaDto.getId());
        }

    }

    @FXML
    private void OnKeyPressedPreguntaId(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !txfPreguntaId.getText().isBlank())
        {
            cargarPregunta(Long.valueOf(txfPreguntaId.getText()));
        }
    }

    private void nuevaPregunta() {
        unbindPregunta();
        preguntaDto = new PreguntaDto();
        bindPregunta(true);
        txfPreguntaId.clear();
        txfPreguntaId.requestFocus();

    }

    private void bindPregunta(Boolean nuevo) {
        if (!nuevo)
        {
            txfPreguntaId.textProperty().bind(preguntaDto.id);
        }
        txfPreguntaCategoria.textProperty().bindBidirectional(preguntaDto.nombreCategoria);
        txaPreguntaEnunciado.textProperty().bindBidirectional(preguntaDto.enunciado);
        chkPreguntaEstado.selectedProperty().bindBidirectional(preguntaDto.estado);
    }

    private void unbindPregunta() {
        txfPreguntaId.textProperty().unbind();
        txfPreguntaCategoria.textProperty().unbindBidirectional(preguntaDto.nombreCategoria);
        txaPreguntaEnunciado.textProperty().unbindBidirectional(preguntaDto.enunciado);
        chkPreguntaEstado.selectedProperty().unbindBidirectional(preguntaDto.estado);

    }

    private void IndicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txfPreguntaId, txfPreguntaCategoria, txaPreguntaEnunciado));
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos)
        {
            if (node instanceof MFXTextField && (((MFXTextField) node).getText() == null || ((MFXTextField) node).getText().isBlank()))
            {
                if (validos)
                {
                    invalidos += ((MFXTextField) node).getFloatingText();
                } else
                {
                    invalidos += "," + ((MFXTextField) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXPasswordField && (((MFXPasswordField) node).getText() == null || ((MFXPasswordField) node).getText().isBlank()))
            {
                if (validos)
                {
                    invalidos += ((MFXPasswordField) node).getFloatingText();
                } else
                {
                    invalidos += "," + ((MFXPasswordField) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXDatePicker && ((MFXDatePicker) node).getValue() == null)
            {
                if (validos)
                {
                    invalidos += ((MFXDatePicker) node).getFloatingText();
                } else
                {
                    invalidos += "," + ((MFXDatePicker) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXComboBox && ((MFXComboBox) node).getSelectionModel().getSelectedIndex() < 0)
            {
                if (validos)
                {
                    invalidos += ((MFXComboBox) node).getFloatingText();
                } else
                {
                    invalidos += "," + ((MFXComboBox) node).getFloatingText();
                }
                validos = false;
            }
        }
        if (validos)
        {
            return "";
        } else
        {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }
    }

    private void cargarPregunta(Long id) {
        try
        {
            PreguntaService preguntaService = new PreguntaService();
            Respuesta respuesta = preguntaService.getPregunta(id);

            if (respuesta.getEstado())
            {
                unbindPregunta();
                this.preguntaDto = (PreguntaDto) respuesta.getResultado("Pregunta");
                bindPregunta(false);
                validarRequeridos();
            } else
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Empleado", getStage(), respuesta.getMensaje());

            }
        } catch (Exception ex)
        {
            Logger.getLogger(MantenimientoController.class
                    .getName()).log(Level.SEVERE, "Error consultando la pregunta.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Pregunta", getStage(), "Ocurrio un error consultando la pregunta.");
        }
    }

}
