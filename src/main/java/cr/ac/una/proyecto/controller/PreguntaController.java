package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.JugadorDto;
import cr.ac.una.proyecto.model.PreguntaDto;
import cr.ac.una.proyecto.model.RespuestaDto;
import cr.ac.una.proyecto.service.PreguntaService;
import cr.ac.una.proyecto.service.RespuestaService;
import cr.ac.una.proyecto.util.Animacion;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.Formato;
import cr.ac.una.proyecto.util.Mensaje;
import cr.ac.una.proyecto.util.RespuestaUtil;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PreguntaController extends Controller implements Initializable {

    private Animacion animacion;

    @FXML
    private VBox VboxRespuestas;

    @FXML
    private TextArea txaEnunciado;

    private String preguntaCategoria;
    private JugadorDto jugadorDto;
    private PreguntaDto preguntaDto;
    private RespuestaDto respuetaDtoAux;
    private ArrayList<PreguntaDto> preguntasDto;
    private ArrayList<RespuestaDto> respuestasDto;
    private Boolean resultadoValorRespuesta;
    private int intentos;

    private ArrayList<MFXButton> botones;
    @FXML
    private MFXButton btnRespuesta1;
    @FXML
    private MFXButton btnRespuesta2;
    @FXML
    private MFXButton btnRespuesta3;
    @FXML
    private MFXButton btnRespuesta4;
    @FXML
    private AnchorPane acpRootPane;
    @FXML
    private ImageView imvBomba;
    @FXML
    private ImageView imvNext;
    @FXML
    private ImageView imvSecondOportunity;
    @FXML
    private ImageView imvTirarRuleta;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize() {
        animacion = new Animacion();
        preguntasDto = new ArrayList<>();
        respuestasDto = new ArrayList<>();
        respuetaDtoAux = new RespuestaDto();
        preguntaDto = new PreguntaDto();
        this.intentos = 1;
        txaEnunciado.setTextFormatter(Formato.getInstance().anyCharacterFormatWithMaxLength(200));

        cargarBotonesToList();
        cargarDatosDesdeAppContext();
        obtenerPreguntasCategoria();
        animacion.simpleFadeIn(acpRootPane);
        cargarEnunciadoPregunta();
        unbindRespuestas();
    }

    public void cargarBotonesToList() {
        botones = new ArrayList<>();
        botones.add(btnRespuesta1);
        botones.add(btnRespuesta2);
        botones.add(btnRespuesta3);
        botones.add(btnRespuesta4);

    }

    private void cargarDatosDesdeAppContext() {
        cargarCategoriaAppContext();
        cargarJugadorAppContext();
    }

    private void cargarCategoriaAppContext() {

        this.preguntaCategoria = ((String) AppContext.getInstance().get("preguntaCategoria"));
    }

    private void cargarJugadorAppContext() {
        jugadorDto = ((JugadorDto) AppContext.getInstance().get("preguntaJugador"));

        if (jugadorDto == null)
        {
            System.out.println("Jugador nulo");
        } else
        {
            System.out.println("Pregunta Jugador : " + jugadorDto.getInfoJugador() + ", [cargarPreguntaCategoriaYJugadorTurno][PreguntaController]");
        }
    }

    private void obtenerPreguntasCategoria() {
        PreguntaService preService = new PreguntaService();
        RespuestaUtil respuesta = preService.getPreguntasActivasPorCategoria(preguntaCategoria);
        if (respuesta.getEstado())
        {
            preguntasDto.clear();
            preguntasDto.addAll((List<PreguntaDto>) respuesta.getResultado("Preguntas"));
            for (PreguntaDto pre : preguntasDto)
            {

                System.out.println("Enunciado: " + pre.getEnunciado());
            }
        } else
        {
            System.err.println("Error al obtener las preguntas: " + respuesta.getMensajeInterno());
        }

    }

    private void cargarRespuestas(Long preguntaId) {
        respuestasDto.clear();
        try
        {
            RespuestaService respuestaService = new RespuestaService();
            RespuestaUtil respuesta = respuestaService.getPreguntaRespuestas(preguntaId);

            if (respuesta.getEstado())
            {
                unbindRespuestas();
                respuestasDto.addAll((List<RespuestaDto>) respuesta.getResultado("Respuestas"));
                Collections.shuffle(respuestasDto);
                bindRespuestas();
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

    private void bindRespuestas() {
        if (respuestasDto.size() > 0)
        {
            this.btnRespuesta1.textProperty().bindBidirectional(respuestasDto.get(0).enunciado);
            this.btnRespuesta2.textProperty().bindBidirectional(respuestasDto.get(1).enunciado);
            this.btnRespuesta3.textProperty().bindBidirectional(respuestasDto.get(2).enunciado);
            this.btnRespuesta4.textProperty().bindBidirectional(respuestasDto.get(3).enunciado);
        } else
        {
            this.btnRespuesta1.textProperty().bindBidirectional(respuetaDtoAux.enunciado);
            this.btnRespuesta2.textProperty().bindBidirectional(respuetaDtoAux.enunciado);
            this.btnRespuesta3.textProperty().bindBidirectional(respuetaDtoAux.enunciado);
            this.btnRespuesta4.textProperty().bindBidirectional(respuetaDtoAux.enunciado);
        }
    }

    private void unbindRespuestas() {

        if (respuestasDto.size() > 0)
        {
            this.btnRespuesta1.textProperty().unbindBidirectional(respuestasDto.get(0).enunciado);
            this.btnRespuesta2.textProperty().unbindBidirectional(respuestasDto.get(1).enunciado);
            this.btnRespuesta3.textProperty().unbindBidirectional(respuestasDto.get(2).enunciado);
            this.btnRespuesta4.textProperty().unbindBidirectional(respuestasDto.get(3).enunciado);
        } else
        {
            this.btnRespuesta1.textProperty().unbindBidirectional(respuetaDtoAux.enunciado);
            this.btnRespuesta2.textProperty().unbindBidirectional(respuetaDtoAux.enunciado);
            this.btnRespuesta3.textProperty().unbindBidirectional(respuetaDtoAux.enunciado);
            this.btnRespuesta4.textProperty().unbindBidirectional(respuetaDtoAux.enunciado);
        }
    }

    public void cargarEnunciadoPregunta() {
        preguntaDto = cargarPreguntasPorCategoria();
        cargarRespuestas(preguntaDto.getId());
        txaEnunciado.setText(preguntaDto.getEnunciado());
    }

    private PreguntaDto cargarPreguntasPorCategoria() {

        Random random = new Random();
        int numeroAleatorioInt = random.nextInt(preguntasDto.size());

        PreguntaDto preguntaDto = preguntasDto.get(numeroAleatorioInt);
        System.out.println("Número aleatorio: " + numeroAleatorioInt);
        System.out.println("ID: " + preguntaDto.getId());
        System.out.println("Enunciado de pregunta: " + preguntaDto.getEnunciado());
        preguntasDto.remove(numeroAleatorioInt);
        return preguntaDto;
    }

    private void cargarRespuestasPorPregunta(PreguntaDto preguntaSeleccionada) {

    }

    @FXML
    private void onActionBtnRespuesta1(ActionEvent event) {
        validarRespuetaCorrecta(1);

    }

    @FXML
    private void onActionBtnRespuesta2(ActionEvent event) {
        validarRespuetaCorrecta(2);
    }

    @FXML
    private void onActionBtnRespuesta3(ActionEvent event) {
        validarRespuetaCorrecta(3);
    }

    @FXML
    private void onActionBtnRespuesta4(ActionEvent event) {
        validarRespuetaCorrecta(4);
    }

    private void validarRespuetaCorrecta(int btnIndice) {
        RespuestaDto respuestaDto = new RespuestaDto();
        respuestaDto = respuestasDto.get(btnIndice - 1);
        jugadorDto.setPreguntasRespondidas(jugadorDto.getPreguntasRespondidas() + 1);

        if (respuestaDto.getIsCorrect().equals("C"))
        {
            intentos--;
            this.resultadoValorRespuesta = true;
            validarIntentos(true);

        } else
        {
            intentos--;
            this.resultadoValorRespuesta = false;
            validarIntentos(false);
        }

    }

    private void validarIntentos(boolean value) {

        if (value == true)
        {
            ((Stage) acpRootPane.getScene().getWindow()).close();

        } else if (intentos <= 0)
        {
            ((Stage) acpRootPane.getScene().getWindow()).close();
        }

        AppContext.getInstance().set("valorRespuesta", resultadoValorRespuesta);
    }

    @FXML
    private void onMouseClickedBomba(MouseEvent event) {
        System.out.println("PreguntaController.onMouseClickedBomba()");
    }

    @FXML
    private void onMouseClickedNext(MouseEvent event) {
        System.out.println("PreguntaController.onMouseClickedNext()");
    }

    @FXML
    private void onMouseOportunidadDoble(MouseEvent event) {
        this.intentos += 1;
        this.imvSecondOportunity.setDisable(true);
        this.imvSecondOportunity.setVisible(false);
    }

    @FXML
    private void onMouseTirarRuleta(MouseEvent event) {
        System.out.println("PreguntaController.onMouseTirarRuleta()");
    }

}
