package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.PreguntaDto;
import cr.ac.una.proyecto.model.RespuestaDto;
import cr.ac.una.proyecto.service.PreguntaService;
import cr.ac.una.proyecto.service.RespuestaService;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Formato;
import cr.ac.una.proyecto.util.Mensaje;
import cr.ac.una.proyecto.util.RespuestaUtil;
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
import javafx.scene.control.CheckBox;
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
    @FXML
    private TextArea txaPreguntaEnunciado;
    @FXML
    private MFXCheckbox chkValidarRespuesta1;
    @FXML
    private MFXCheckbox chkValidarRespuesta2;
    @FXML
    private MFXCheckbox chkValidarRespuesta3;
    @FXML
    private MFXCheckbox chkValidarRespuesta4;

    private PreguntaDto preguntaDto;
    ArrayList<RespuestaDto> respuestas = new ArrayList<>();
    ArrayList<RespuestaDto> respuestasAuxDto = new ArrayList<>();
    private RespuestaDto respuestaDtoAux;
    List<Node> requeridos = new ArrayList<>();
    private ArrayList<CheckBox> checkboxes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initValues();
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
        if (validarComboBoxes())
        {
            if (guardarPregunta())
            {
                guardarRespuestas();
            }
        }

    }

    private void deseleccionarOtrasCasillas(CheckBox selectedCheckbox) {

        for (CheckBox checkbox : checkboxes)
        {
            if (checkbox != selectedCheckbox)
            {
                checkbox.setSelected(false);
            }
        }
    }

    private void initCheckboxesValues() {
        this.checkboxes = new ArrayList<>();
        checkboxes.add(chkValidarRespuesta1);
        checkboxes.add(chkValidarRespuesta2);
        checkboxes.add(chkValidarRespuesta3);
        checkboxes.add(chkValidarRespuesta4);

        chkValidarRespuesta1.setOnAction(e -> deseleccionarOtrasCasillas(chkValidarRespuesta1));
        chkValidarRespuesta2.setOnAction(e -> deseleccionarOtrasCasillas(chkValidarRespuesta2));
        chkValidarRespuesta3.setOnAction(e -> deseleccionarOtrasCasillas(chkValidarRespuesta3));
        chkValidarRespuesta4.setOnAction(e -> deseleccionarOtrasCasillas(chkValidarRespuesta4));

    }

    private boolean guardarPregunta() {
        try
        {
            String invalidos = validarRequeridos();
            if (!invalidos.isBlank())
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Pregunta", getStage(), invalidos);
            } else
            {
                PreguntaService preService = new PreguntaService();
                RespuestaUtil respuesta = preService.guardarPregunta(this.preguntaDto);
                if (respuesta.getEstado())
                {
                    unbindPregunta();
                    this.preguntaDto = (PreguntaDto) respuesta.getResultado("Pregunta");
                    bindPregunta(true);
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Pregunta", getStage(), "Pregunta guardada correctamente.");
                    return true;
                } else
                {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Pregunta", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex)
        {
            Logger.getLogger(MantenimientoController.class.getName()).log(Level.SEVERE, "Error al guardar la pregunta.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Pregunta", getStage(), "Ocurrió un error al guardar la pregunta.");
        }
        return false;
    }

    private void guardarRespuestas() {
        try
        {
            ArrayList<RespuestaDto> respuestasAux = new ArrayList<>();

            if (respuestas.size() > 0)
            {
                respuestasAux = this.respuestas;
            } else
            {
                respuestasAux = this.respuestasAuxDto;
            }

            RespuestaService respuestaService = new RespuestaService();
            RespuestaUtil respuesta = respuestaService.guardarRespuestasPregunta(respuestasAux);
            if (respuesta.getEstado())
            {
                unbindPregunta();
                this.respuestas = (ArrayList<RespuestaDto>) respuesta.getResultado("GRespuestas");
                bindPregunta(true);
                nuevaPregunta();
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Respuestas", getStage(), "Respuestas guardadas correctamente.");
            } else
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Respuestas", getStage(), respuesta.getMensaje());
            }

        } catch (Exception ex)
        {
            Logger.getLogger(MantenimientoController.class.getName()).log(Level.SEVERE, "Error al guardar las respuestas.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Respuestas", getStage(), "Ocurrió un error al guardar las respuestas.");
        }
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
        eliminarRespuestas();
    }

    private void eliminarPregunta() {
        try
        {
            System.out.println("EstadoPregunta: " + preguntaDto.getId());
            if (this.preguntaDto.getId() == null)
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Pregunta ", getStage(), "Favor consultar la pregunta a eliminar.");
            } else
            {

                PreguntaService preService = new PreguntaService();
                RespuestaUtil respuesta = preService.eliminarPregunta(this.preguntaDto.getId());
                if (respuesta.getEstado())
                {
                    nuevaPregunta();
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar Pregunta", getStage(), "La Pregunta se elimino correctamente");
                } else
                {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Pregunta", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex)
        {
            Logger.getLogger(MantenimientoController.class.getName()).log(Level.SEVERE, "Error al la pregunta.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Pregunta", getStage(), "Ocurrió un error al eliminar la pregunta.");
        }
    }

    private void eliminarRespuestas() {
        try
        {
            if (this.preguntaDto.getId() == null)
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Respuestas ", getStage(), "Favor consultar las Respuestas a eliminar.");
            } else
            {

                RespuestaService resService = new RespuestaService();
                RespuestaUtil respuesta = resService.eliminarRespuestas(respuestas);
                if (respuesta.getEstado())
                {
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar Respuestas", getStage(), "Las Respuestas se eliminaron correctamente");
                    eliminarPregunta();
                } else
                {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Respuestas", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex)
        {
            Logger.getLogger(MantenimientoController.class.getName()).log(Level.SEVERE, "Error al eliminar las respuestas.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Respuestas", getStage(), "Ocurrió un error al eliminar las respuestas.");
        }
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

    private void initValues() {

        preguntaDto = new PreguntaDto();
        respuestaDtoAux = new RespuestaDto();
        txfPreguntaId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txfPreguntaCategoria.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txaPreguntaEnunciado.setTextFormatter(Formato.getInstance().anyCharacterFormatWithMaxLength(200));
        chkPreguntaEstado.setSelected(false);
        txfPreguntaRespuesta1.delegateSetTextFormatter(Formato.getInstance().anyCharacterFormatWithMaxLength(50));
        txfPreguntaRespuesta2.delegateSetTextFormatter(Formato.getInstance().anyCharacterFormatWithMaxLength(50));
        txfPreguntaRespuesta3.delegateSetTextFormatter(Formato.getInstance().anyCharacterFormatWithMaxLength(50));
        txfPreguntaRespuesta4.delegateSetTextFormatter(Formato.getInstance().anyCharacterFormatWithMaxLength(50));

        nuevaPregunta();
        IndicarRequeridos();
        initCheckboxesValues();

    }

    private void nuevaPregunta() {
        preguntaDto = new PreguntaDto();
        unbindPregunta();
        bindPregunta(true);
        txfPreguntaId.clear();
        txfPreguntaId.requestFocus();
        nuevasRespuestas();
    }

    private void nuevasRespuestas() {

        for (int index = 0; index < 4; index++)
        {
            respuestasAuxDto.add(new RespuestaDto());
        }
        unbindRespuestas();
        bindRespuestas(true);
        txfPreguntaRespuesta1.clear();
        txfPreguntaRespuesta2.clear();
        txfPreguntaRespuesta3.clear();
        txfPreguntaRespuesta4.clear();
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
        requeridos.addAll(Arrays.asList(txaPreguntaEnunciado, txfPreguntaRespuesta1, txfPreguntaRespuesta2, txfPreguntaRespuesta3,
                txfPreguntaRespuesta4));
    }

    private boolean validarComboBoxes() {

        for (CheckBox checkbox : checkboxes)
        {
            if (checkbox.isSelected())
            {
                return true;
            }
        }
        new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Respuestas", getStage(), "Debes Seleccionar una respuesta como correcta");
        return false;
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

    private void cargarPregunta(Long preId) {
        try
        {
            PreguntaService preguntaService = new PreguntaService();
            RespuestaUtil respuesta = preguntaService.getPregunta(preId);

            if (respuesta.getEstado())
            {
                unbindPregunta();
                this.preguntaDto = (PreguntaDto) respuesta.getResultado("Pregunta");
                bindPregunta(false);
                validarRequeridos();
                cargarRespuestas(preId);
            } else
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Pregunta", getStage(), respuesta.getMensaje());

            }
        } catch (Exception ex)
        {
            Logger.getLogger(MantenimientoController.class
                    .getName()).log(Level.SEVERE, "Error consultando la pregunta.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Pregunta", getStage(), "Ocurrio un error consultando la pregunta.");
        }
    }

    private void cargarRespuestas(Long preguntaId) {
        respuestas.clear();
        try
        {
            RespuestaService respuestaService = new RespuestaService();
            RespuestaUtil respuesta = respuestaService.getPreguntaRespuestas(preguntaId);

            if (respuesta.getEstado())
            {
                unbindRespuestas();
                respuestas.addAll((List<RespuestaDto>) respuesta.getResultado("Respuestas"));
                bindRespuestas(false);
            } else
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "cargarRespuestas", getStage(), respuesta.getMensaje());

            }
        } catch (Exception ex)
        {
            Logger.getLogger(MantenimientoController.class
                    .getName()).log(Level.SEVERE, "Error consultando las respuestas.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "cargarRespuestas", getStage(), "Ocurrio un error consultando las repuestas.");
        }

    }

    private void bindRespuestas(Boolean nuevo) {
        if (!nuevo)
        {
            //txfPreguntaId.textProperty().bind(preguntaDto.id);
        }

        if (respuestas.size() > 0)
        {
            txfPreguntaRespuesta1.textProperty().bindBidirectional(respuestas.get(0).enunciado);
            txfPreguntaRespuesta2.textProperty().bindBidirectional(respuestas.get(1).enunciado);
            txfPreguntaRespuesta3.textProperty().bindBidirectional(respuestas.get(2).enunciado);
            txfPreguntaRespuesta4.textProperty().bindBidirectional(respuestas.get(3).enunciado);

            chkValidarRespuesta1.selectedProperty().bindBidirectional(respuestas.get(0).isCorrect);
            chkValidarRespuesta2.selectedProperty().bindBidirectional(respuestas.get(1).isCorrect);
            chkValidarRespuesta3.selectedProperty().bindBidirectional(respuestas.get(2).isCorrect);
            chkValidarRespuesta4.selectedProperty().bindBidirectional(respuestas.get(3).isCorrect);
        } else
        {
            txfPreguntaRespuesta1.textProperty().bindBidirectional(respuestasAuxDto.get(0).enunciado);
            txfPreguntaRespuesta2.textProperty().bindBidirectional(respuestasAuxDto.get(1).enunciado);
            txfPreguntaRespuesta3.textProperty().bindBidirectional(respuestasAuxDto.get(2).enunciado);
            txfPreguntaRespuesta4.textProperty().bindBidirectional(respuestasAuxDto.get(3).enunciado);

            chkValidarRespuesta1.selectedProperty().bindBidirectional(respuestasAuxDto.get(0).isCorrect);
            chkValidarRespuesta2.selectedProperty().bindBidirectional(respuestasAuxDto.get(1).isCorrect);
            chkValidarRespuesta3.selectedProperty().bindBidirectional(respuestasAuxDto.get(2).isCorrect);
            chkValidarRespuesta4.selectedProperty().bindBidirectional(respuestasAuxDto.get(3).isCorrect);
        }

    }

    private void unbindRespuestas() {

        if (respuestas.size() > 0)
        {
            txfPreguntaRespuesta1.textProperty().unbindBidirectional(respuestas.get(0).enunciado);
            txfPreguntaRespuesta2.textProperty().unbindBidirectional(respuestas.get(1).enunciado);
            txfPreguntaRespuesta3.textProperty().unbindBidirectional(respuestas.get(2).enunciado);
            txfPreguntaRespuesta4.textProperty().unbindBidirectional(respuestas.get(3).enunciado);

            chkValidarRespuesta1.selectedProperty().unbindBidirectional(respuestas.get(0).isCorrect);
            chkValidarRespuesta2.selectedProperty().unbindBidirectional(respuestas.get(1).isCorrect);
            chkValidarRespuesta3.selectedProperty().unbindBidirectional(respuestas.get(2).isCorrect);
            chkValidarRespuesta4.selectedProperty().unbindBidirectional(respuestas.get(3).isCorrect);

        } else
        {

            txfPreguntaRespuesta1.textProperty().unbindBidirectional(respuestasAuxDto.get(0).enunciado);
            txfPreguntaRespuesta2.textProperty().unbindBidirectional(respuestasAuxDto.get(1).enunciado);
            txfPreguntaRespuesta3.textProperty().unbindBidirectional(respuestasAuxDto.get(2).enunciado);
            txfPreguntaRespuesta4.textProperty().unbindBidirectional(respuestasAuxDto.get(3).enunciado);

            chkValidarRespuesta1.selectedProperty().unbindBidirectional(respuestasAuxDto.get(0).isCorrect);
            chkValidarRespuesta2.selectedProperty().unbindBidirectional(respuestasAuxDto.get(1).isCorrect);
            chkValidarRespuesta3.selectedProperty().unbindBidirectional(respuestasAuxDto.get(2).isCorrect);
            chkValidarRespuesta4.selectedProperty().unbindBidirectional(respuestasAuxDto.get(3).isCorrect);
        }

    }

}
