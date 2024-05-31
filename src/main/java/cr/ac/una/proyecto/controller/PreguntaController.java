package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.JugadorDto;
import cr.ac.una.proyecto.model.PreguntaDto;
import cr.ac.una.proyecto.model.Respuesta;
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
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class PreguntaController extends Controller implements Initializable {

    private Animacion animacion;

    @FXML
    private VBox VboxRespuestas;

    @FXML
    private MFXButton btnBomb;

    @FXML
    private MFXButton btnExtraTry;

    @FXML
    private MFXButton btnPass;

    @FXML
    private MFXButton btnSecondOportunity;

    @FXML
    private TextArea txaEnunciado;

    private String preguntaCategoria;
    private JugadorDto jugador;
    private PreguntaDto preguntaDto;
    private ArrayList<PreguntaDto> preguntas;
    private ArrayList<RespuestaDto> respuestas;
    private Boolean resultado;

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
    void onActionBtnBomb(ActionEvent event) {

    }

    @FXML
    void onActionBtnPass(ActionEvent event) {

    }

    @FXML
    void onActionBtnSecondOportunity(ActionEvent event) {

    }

    @FXML
    void onActionExtraTry(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize() {
        animacion = new Animacion();
        preguntas = new ArrayList<>();
        respuestas = new ArrayList<>();
        txaEnunciado.setTextFormatter(Formato.getInstance().anyCharacterFormatWithMaxLength(200));
        cargarBotonesToList();
        cargarDatosDesdeAppContext();
        obtenerPreguntasCategoria();
        animacion.simpleFadeIn(acpRootPane);
        cargarEnunciadoPregunta();
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
        jugador = ((JugadorDto) AppContext.getInstance().get("preguntaJugador"));

        if (jugador == null)
        {
            System.out.println("Jugador nulo");
        }
        System.out.println("Pregunta Jugador : " + jugador.toString() + ", [cargarPreguntaCategoriaYJugadorTurno][PreguntaController]");
    }

    private void obtenerPreguntasCategoria() {
        PreguntaService preService = new PreguntaService();
        RespuestaUtil respuesta = preService.getPreguntasActivasPorCategoria(preguntaCategoria);
        if (respuesta.getEstado())
        {
            preguntas.clear();
            preguntas.addAll((List<PreguntaDto>) respuesta.getResultado("Preguntas"));
            for (PreguntaDto pre : preguntas)
            {

                System.out.println("Enunciado: " + pre.getEnunciado());
            }
        } else
        {
            System.err.println("Error al obtener las preguntas: " + respuesta.getMensajeInterno());
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
                // unbindRespuestas();
                respuestas.addAll((List<RespuestaDto>) respuesta.getResultado("Respuestas"));
                System.out.println("VARAS DE GEI");
                for (RespuestaDto res : respuestas)
                {
                    System.out.println("EnunRes: " + res.getEnunciado());
                }
                // bindRespuestas(false);
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

    public void cargarEnunciadoPregunta() {
        preguntaDto = cargarPreguntasPorCategoria();
        cargarRespuestas(preguntaDto.getId());
        txaEnunciado.setText(preguntaDto.getEnunciado());
    }

    private PreguntaDto cargarPreguntasPorCategoria() {

        Random random = new Random();
        int numeroAleatorioInt = random.nextInt(preguntas.size());

        PreguntaDto preguntaDto = preguntas.get(numeroAleatorioInt);
        System.out.println("Número aleatorio: " + numeroAleatorioInt);
        System.out.println("ID: " + preguntaDto.getId());
        System.out.println("Enunciado de pregunta: " + preguntaDto.getEnunciado());
        preguntas.remove(numeroAleatorioInt);
        return preguntaDto;
    }

    private void cargarRespuestasPorPregunta(PreguntaDto preguntaSeleccionada) {

    }

    @FXML
    private void onActionBtnRespuesta1(ActionEvent event) {

    }

    @FXML
    private void onActionBtnRespuesta2(ActionEvent event) {

    }

    @FXML
    private void onActionBtnRespuesta3(ActionEvent event) {

    }

    @FXML
    private void onActionBtnRespuesta4(ActionEvent event) {

    }

    public boolean getResultado() {
        return this.resultado;

    }

}
