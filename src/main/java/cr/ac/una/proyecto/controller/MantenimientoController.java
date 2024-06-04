package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.CategoriaDto;
import cr.ac.una.proyecto.model.PreguntaDto;
import cr.ac.una.proyecto.model.RespuestaDto;
import cr.ac.una.proyecto.service.CategoriaService;
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
    private MFXComboBox<String> cmbCategorias;
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
    private CategoriaDto categoriaDto;
    private RespuestaDto respuesta1;
    private RespuestaDto respuesta2;
    private RespuestaDto respuesta3;
    private RespuestaDto respuesta4;
    private boolean preguntaNueva;

    private List<RespuestaDto> respuestasDto = new ArrayList<>();

    List<Node> requeridos = new ArrayList<>();
    private ArrayList<CheckBox> checkboxes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void initialize() {
        initValues();
        cargarCategorias();
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

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
        if (new Mensaje().showConfirmation("Limpiar Pregunta", getStage(),
                "¿Esta seguro que desea limpiar el registro?"))
        {
            nuevaPregunta();
        }
    }

    @FXML
    private void onActionBtnLimpiar(ActionEvent event) {
        if (new Mensaje().showConfirmation("Limpiar Pregunta", getStage(),
                "¿Esta seguro que desea limipiar el registro?"))
        {
            nuevaPregunta();
        }
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
        eliminarRespuestas();
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
        BuscarPreguntaController busquedaController = (BuscarPreguntaController) FlowController.getInstance().getController("BuscarPreguntaView");
        FlowController.getInstance().goViewInWindowModal("BuscarPreguntaView",
                ((Stage) btnBuscar.getScene().getWindow()), true);
        preguntaDto = (PreguntaDto) busquedaController.getResultado();

        if (preguntaDto != null)
        {
            cargarPregunta(preguntaDto.getId());
            cargarRespuestasDtoSingular();
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
        txfPreguntaId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
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
        this.preguntaNueva = true;
        nuevasRespuestas();
    }

    private void nuevasRespuestas() {
        respuesta1 = new RespuestaDto();
        respuesta2 = new RespuestaDto();
        respuesta3 = new RespuestaDto();
        respuesta4 = new RespuestaDto();

        unbindRespuestas();
        txfPreguntaRespuesta1.clear();
        txfPreguntaRespuesta2.clear();
        txfPreguntaRespuesta3.clear();
        txfPreguntaRespuesta4.clear();
        bindRespuestas();
        respuestasDto = new ArrayList<>();
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

    private Boolean guardarPregunta() {
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
                    bindPregunta(false);
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Pregunta", getStage(),
                            "Pregunta guardada correctamente.");
                    return true;

                } else
                {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Pregunta", getStage(),
                            respuesta.getMensaje());
                }
            }
        } catch (Exception ex)
        {
            Logger.getLogger(MantenimientoController.class.getName()).log(Level.SEVERE, "Error al guardar la pregunta.",
                    ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Pregunta", getStage(),
                    "Ocurrió un error al guardar la pregunta.");
        }
        return false;
    }

    private void guardarRespuestas() {
        try
        {
            cargarRespuestasDtoList();
            RespuestaService respuestaService = new RespuestaService();
            RespuestaUtil respuesta = respuestaService.guardarRespuestasPregunta(respuestasDto);
            if (respuesta.getEstado())
            {
                unbindRespuestas();
                this.respuestasDto = (ArrayList<RespuestaDto>) respuesta.getResultado("GRespuestas");
                bindRespuestas();
                nuevaPregunta();
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Respuestas", getStage(),
                        "Respuestas guardadas correctamente.");
            } else
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Respuestas", getStage(),
                        respuesta.getMensaje());
            }

        } catch (Exception ex)
        {
            Logger.getLogger(MantenimientoController.class.getName()).log(Level.SEVERE,
                    "Error al guardar las respuestas.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Respuestas", getStage(),
                    "Ocurrió un error al guardar las respuestas.");
        }
    }

    private void cargarRespuestasDtoList() {

        if (preguntaNueva)
        {
            this.respuestasDto.clear();
            respuesta1.setPreguntaId(preguntaDto.getId());
            respuesta2.setPreguntaId(preguntaDto.getId());
            respuesta3.setPreguntaId(preguntaDto.getId());
            respuesta4.setPreguntaId(preguntaDto.getId());
            System.out.println("ID PREGUNTA DTO: " + preguntaDto.getId());
            respuestasDto.add(respuesta1);
            respuestasDto.add(respuesta2);
            respuestasDto.add(respuesta3);
            respuestasDto.add(respuesta4);
        }
    }

    private void cargarRespuestasDtoSingular() {
        this.preguntaNueva = false;

        this.respuesta1 = respuestasDto.get(0);
        this.respuesta2 = respuestasDto.get(1);
        this.respuesta3 = respuestasDto.get(2);
        this.respuesta4 = respuestasDto.get(3);
    }

    private void IndicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(
                Arrays.asList(txaPreguntaEnunciado, cmbCategorias, txfPreguntaRespuesta1, txfPreguntaRespuesta2, txfPreguntaRespuesta3,
                        txfPreguntaRespuesta4));
    }

    private void bindPregunta(Boolean nuevo) {
        if (!nuevo)
        {
            txfPreguntaId.textProperty().bind(preguntaDto.id);
        }
        cmbCategorias.valueProperty().bindBidirectional(preguntaDto.nombreCat());
        txaPreguntaEnunciado.textProperty().bindBidirectional(preguntaDto.enunciado);
        chkPreguntaEstado.selectedProperty().bindBidirectional(preguntaDto.estado);
    }

    private void unbindPregunta() {
        txfPreguntaId.textProperty().unbind();
        cmbCategorias.valueProperty().unbindBidirectional(preguntaDto.nombreCat());
        txaPreguntaEnunciado.textProperty().unbindBidirectional(preguntaDto.enunciado);
        chkPreguntaEstado.selectedProperty().unbindBidirectional(preguntaDto.estado);

    }

    private void bindRespuestas() {

        txfPreguntaRespuesta1.textProperty().bindBidirectional(respuesta1.enunciado);
        txfPreguntaRespuesta2.textProperty().bindBidirectional(respuesta2.enunciado);
        txfPreguntaRespuesta3.textProperty().bindBidirectional(respuesta3.enunciado);
        txfPreguntaRespuesta4.textProperty().bindBidirectional(respuesta4.enunciado);

        chkValidarRespuesta1.selectedProperty().bindBidirectional(respuesta1.isCorrect);
        chkValidarRespuesta2.selectedProperty().bindBidirectional(respuesta2.isCorrect);
        chkValidarRespuesta3.selectedProperty().bindBidirectional(respuesta3.isCorrect);
        chkValidarRespuesta4.selectedProperty().bindBidirectional(respuesta4.isCorrect);

    }

    private void unbindRespuestas() {

        txfPreguntaRespuesta1.textProperty().unbindBidirectional(respuesta1.enunciado);
        txfPreguntaRespuesta2.textProperty().unbindBidirectional(respuesta2.enunciado);
        txfPreguntaRespuesta3.textProperty().unbindBidirectional(respuesta3.enunciado);
        txfPreguntaRespuesta4.textProperty().unbindBidirectional(respuesta4.enunciado);

        chkValidarRespuesta1.selectedProperty().unbindBidirectional(respuesta1.isCorrect);
        chkValidarRespuesta2.selectedProperty().unbindBidirectional(respuesta2.isCorrect);
        chkValidarRespuesta3.selectedProperty().unbindBidirectional(respuesta3.isCorrect);
        chkValidarRespuesta4.selectedProperty().unbindBidirectional(respuesta4.isCorrect);

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

    private boolean validarComboBoxes() {

        for (CheckBox checkbox : checkboxes)
        {
            if (checkbox.isSelected())
            {
                return true;
            }
        }
        new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Respuestas", getStage(),
                "Debes Seleccionar una respuesta como correcta");
        return false;
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos)
        {
            if (node instanceof MFXTextField
                    && (((MFXTextField) node).getText() == null || ((MFXTextField) node).getText().isBlank()))
            {
                if (validos)
                {
                    invalidos += ((MFXTextField) node).getFloatingText();
                } else
                {
                    invalidos += "," + ((MFXTextField) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXPasswordField
                    && (((MFXPasswordField) node).getText() == null || ((MFXPasswordField) node).getText().isBlank()))
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

    public void cargarCategorias() {
        try
        {
            CategoriaService categoriaService = new CategoriaService();
            RespuestaUtil respuesta = categoriaService.getAll();

            if (respuesta.getEstado())
            {
                cmbCategorias.getItems().clear();
                List<CategoriaDto> categorias = (List<CategoriaDto>) respuesta.getResultado("Categorias");
                for (CategoriaDto categoria : categorias)
                {

                    cmbCategorias.getItems().add(categoria.getNombre());

                }

            } else
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Categorias", getStage(),
                        respuesta.getMensaje());
            }
        } catch (Exception ex)
        {
            Logger.getLogger(MantenimientoController.class.getName()).log(Level.SEVERE,
                    "Error al cargar las categorias.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Categorias", getStage(),
                    "Ocurrió un error al cargar las categorias.");
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
                //validarRequeridos();
                cargarRespuestas(preId);

            } else
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Pregunta", getStage(), respuesta.getMensaje());

            }
        } catch (Exception ex)
        {
            Logger.getLogger(MantenimientoController.class
                    .getName()).log(Level.SEVERE, "Error consultando la pregunta.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Pregunta", getStage(),
                    "Ocurrio un error consultando la pregunta.");
        }
    }

    private void cargarRespuestas(Long preguntaId) {
        respuestasDto.clear();
        try
        {
            RespuestaService respuestaService = new RespuestaService();
            RespuestaUtil respuesta = respuestaService.getRespuestasPreguntas(preguntaId);

            if (respuesta.getEstado())
            {
                unbindRespuestas();
                this.respuestasDto.addAll((List<RespuestaDto>) respuesta.getResultado("Respuestas"));
                cargarRespuestasDtoSingular();
                bindRespuestas();
            } else
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "cargarRespuestas", getStage(), respuesta.getMensaje());

            }
        } catch (Exception ex)
        {
            Logger.getLogger(MantenimientoController.class
                    .getName()).log(Level.SEVERE, "Error consultando las respuestas.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "cargarRespuestas", getStage(),
                    "Ocurrio un error consultando las repuestas.");
        }

    }

    private void eliminarPregunta() {
        try
        {
            System.out.println("EstadoPregunta: " + preguntaDto.getId());
            if (this.preguntaDto.getId() == null)
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Pregunta ", getStage(),
                        "Favor consultar la pregunta a eliminar.");
            } else
            {

                PreguntaService preService = new PreguntaService();
                RespuestaUtil respuesta = preService.eliminarPregunta(this.preguntaDto.getId());
                if (respuesta.getEstado())
                {
                    nuevaPregunta();
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar Pregunta", getStage(),
                            "La Pregunta se elimino correctamente");
                } else
                {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Pregunta", getStage(),
                            respuesta.getMensaje());
                }
            }
        } catch (Exception ex)
        {
            Logger.getLogger(MantenimientoController.class.getName()).log(Level.SEVERE, "Error al la pregunta.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Pregunta", getStage(),
                    "Ocurrió un error al eliminar la pregunta.");
        }
    }

    private void eliminarRespuestas() {
        try
        {

            if (this.preguntaDto.getId() == null)
            {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Respuestas ", getStage(),
                        "Favor consultar las Respuestas a eliminar.");
            } else
            {

                RespuestaService resService = new RespuestaService();
                RespuestaUtil respuesta = resService.eliminarRespuestas(respuestasDto);
                if (respuesta.getEstado())
                {
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar Respuestas", getStage(),
                            "Las Respuestas se eliminaron correctamente");
                    eliminarPregunta();
                } else
                {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Respuestas", getStage(),
                            respuesta.getMensaje());
                }
            }
        } catch (Exception ex)
        {
            Logger.getLogger(MantenimientoController.class.getName()).log(Level.SEVERE,
                    "Error al eliminar las respuestas.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Respuestas", getStage(),
                    "Ocurrió un error al eliminar las respuestas.");
        }
    }

}
