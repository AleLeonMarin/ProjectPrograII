package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import cr.ac.una.proyecto.model.PreguntaDto;
import cr.ac.una.proyecto.model.RespuestaDto;
import cr.ac.una.proyecto.service.PreguntaService;
import cr.ac.una.proyecto.service.RespuestaService;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Mensaje;
import cr.ac.una.proyecto.util.RespuestaUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

public class PreguntaStatisticsController extends Controller implements Initializable {

    @FXML
    private MFXButton btnRespuesta1;

    @FXML
    private MFXButton btnRespuesta2;

    @FXML
    private MFXButton btnRespuesta3;

    @FXML
    private MFXButton btnRespuesta4;

    @FXML
    private TextArea txaPregunta;

    PreguntaDto preguntaDto;
    private List<RespuestaDto> respuestasDto;
    private RespuestaDto respuetaDtoAux;
    private ArrayList<MFXButton> buttons;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void cargarPreguntaFromOtherController() {
        EstadisticasController estadisticasController = (EstadisticasController) FlowController.getInstance()
                .getController("Estadisticas");
        preguntaDto = (PreguntaDto) estadisticasController.getPregunta();

        if (preguntaDto != null) {
            cargarPregunta(preguntaDto.getId());
            txaPregunta.setText(preguntaDto.getEnunciado());
        }
    }

    private void addButtons() {
        this.buttons = new ArrayList<>();
        buttons.add(btnRespuesta1);
        buttons.add(btnRespuesta2);
        buttons.add(btnRespuesta3);
        buttons.add(btnRespuesta4);
    }

    @Override
    public void initialize() {
        addButtons();
        respuestasDto = new ArrayList<>();
        respuetaDtoAux = new RespuestaDto();
        cargarPreguntaFromOtherController();

    }

    public void setPregunta(PreguntaDto preguntaDto) {
        unbindRespuestas();
        this.preguntaDto = preguntaDto;
        cargarPregunta(this.preguntaDto.getId());
        txaPregunta.setText(preguntaDto.getEnunciado());
        bindRespuestas();
        // TODO Auto-generated method stub

    }

    private void bindRespuestas() {
        if (respuestasDto.size() > 0) {
            this.btnRespuesta1.textProperty().bindBidirectional(respuestasDto.get(0).enunciado);
            this.btnRespuesta2.textProperty().bindBidirectional(respuestasDto.get(1).enunciado);
            this.btnRespuesta3.textProperty().bindBidirectional(respuestasDto.get(2).enunciado);
            this.btnRespuesta4.textProperty().bindBidirectional(respuestasDto.get(3).enunciado);
        } else {
            this.btnRespuesta1.textProperty().bindBidirectional(respuetaDtoAux.enunciado);
            this.btnRespuesta2.textProperty().bindBidirectional(respuetaDtoAux.enunciado);
            this.btnRespuesta3.textProperty().bindBidirectional(respuetaDtoAux.enunciado);
            this.btnRespuesta4.textProperty().bindBidirectional(respuetaDtoAux.enunciado);
        }
    }

    private void unbindRespuestas() {

        if (respuestasDto.size() > 0) {
            this.btnRespuesta1.textProperty().unbindBidirectional(respuestasDto.get(0).enunciado);
            this.btnRespuesta2.textProperty().unbindBidirectional(respuestasDto.get(1).enunciado);
            this.btnRespuesta3.textProperty().unbindBidirectional(respuestasDto.get(2).enunciado);
            this.btnRespuesta4.textProperty().unbindBidirectional(respuestasDto.get(3).enunciado);
        } else {
            this.btnRespuesta1.textProperty().unbindBidirectional(respuetaDtoAux.enunciado);
            this.btnRespuesta2.textProperty().unbindBidirectional(respuetaDtoAux.enunciado);
            this.btnRespuesta3.textProperty().unbindBidirectional(respuetaDtoAux.enunciado);
            this.btnRespuesta4.textProperty().unbindBidirectional(respuetaDtoAux.enunciado);
        }
    }

    private void cargarRespuestas(Long preguntaId) {
        respuestasDto.clear();
        try {
            RespuestaService respuestaService = new RespuestaService();
            RespuestaUtil respuesta = respuestaService.getRespuestasPreguntas(preguntaId);

            if (respuesta.getEstado()) {
                unbindRespuestas();
                respuestasDto.addAll((List<RespuestaDto>) respuesta.getResultado("Respuestas"));
                bindRespuestas();
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "cargarRespuestas", getStage(), respuesta.getMensaje());

            }
        } catch (Exception ex) {
            Logger.getLogger(MantenimientoController.class
                    .getName()).log(Level.SEVERE, "Error consultando las respuestas.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "cargarRespuestas", getStage(),
                    "Ocurrio un error consultando las repuestas.");
        }

    }

    private void cargarPregunta(Long preId) {
        try {
            PreguntaService preguntaService = new PreguntaService();
            RespuestaUtil respuesta = preguntaService.getPregunta(preId);
            if (respuesta.getEstado()) {
                txaPregunta.textProperty().unbindBidirectional(preguntaDto.enunciado);
                unbindRespuestas();
                this.preguntaDto = (PreguntaDto) respuesta.getResultado("Pregunta");
                cargarRespuestas(preId);
                txaPregunta.textProperty().bindBidirectional(preguntaDto.enunciado);
                bindRespuestas();

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

}
