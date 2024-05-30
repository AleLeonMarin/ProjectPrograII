package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Jugador;
import cr.ac.una.proyecto.model.Pregunta;
import cr.ac.una.proyecto.model.Respuesta;
import cr.ac.una.proyecto.util.Animacion;
import cr.ac.una.proyecto.util.AppContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.Collections;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    private String respuestaCorrecta;
    private Jugador jugador;

    private ArrayList<Pregunta> preguntas;
    private ArrayList<Respuesta> respuestas;
    private Pregunta preguntaSeleccionada;
    private Boolean valorRespuesta;

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
        cargarBotonesToList();
        cargarDatosDesdeAppContext();
        cargarEnunciadoPregunta();
        animacion.simpleFadeIn(acpRootPane);
    }

    public void cargarBotonesToList() {
        botones = new ArrayList<>();
        botones.add(btnRespuesta1);
        botones.add(btnRespuesta2);
        botones.add(btnRespuesta3);
        botones.add(btnRespuesta4);

    }

    private void cargarDatosDesdeAppContext() {
        cargarPreguntaCategoriaYJugadorTurno();
        cargarPreguntasRespuestas();
    }

    private void cargarPreguntaCategoriaYJugadorTurno() {
        preguntaCategoria = ((String) AppContext.getInstance().get("preguntaCategoria"));

        if (preguntaCategoria == null)
        {
            preguntaCategoria = "";
        }
        System.out.println("Pregunta Categoria : " + preguntaCategoria + ", [cargarPreguntaCategoriaYJugadorTurno][PreguntaController]");
        jugador = ((Jugador) AppContext.getInstance().get("preguntaJugador"));

        if (jugador == null)
        {
            System.out.println("Jugador nulo");
        }
        System.out.println("Pregunta Jugador : " + jugador.toString() + ", [cargarPreguntaCategoriaYJugadorTurno][PreguntaController]");
    }

    private void cargarPreguntasRespuestas() {
        preguntas = (ArrayList<Pregunta>) AppContext.getInstance().get("preguntas");
        respuestas = (ArrayList<Respuesta>) AppContext.getInstance().get("respuestas");
    }

    public void cargarEnunciadoPregunta() {
        // preguntaSeleccionada = cargarPreguntasPorCategoria();
        cargarRespuestasPorPregunta(preguntaSeleccionada);
    }

//    private Pregunta cargarPreguntasPorCategoria() {
//        ArrayList<Pregunta> preguntasPorCategoria = new ArrayList<>();
//
//        System.out.println("Categoriaa: " + preguntaCategoria);
//        for (Pregunta pregunta : preguntas)
//        {
//            if (pregunta.getCategoria().equals(preguntaCategoria))
//            {
//                preguntasPorCategoria.add(pregunta);
//                System.out.println(pregunta.toString());
//            }
//        }
//
//        Random random = new Random();
//        int numeroAleatorioInt = random.nextInt(preguntasPorCategoria.size());
//
//        Pregunta preguntaSeleccionada = preguntasPorCategoria.get(numeroAleatorioInt);
//        System.out.println("Número aleatorio: " + numeroAleatorioInt);
//        System.out.println("ID: " + preguntaSeleccionada.getId());
//
//        txaEnunciado.setText(preguntaSeleccionada.getEnunciado());
//
//        return preguntaSeleccionada;
//    }
    private void cargarRespuestasPorPregunta(Pregunta preguntaSeleccionada) {
//        ArrayList<Respuesta> respuestasPorPregunta = new ArrayList<>();
//        int id = preguntaSeleccionada.getId();
//
//        for (Respuesta respuesta : respuestas)
//        {
//            if (id == respuesta.getIdPadre())
//            {
//                respuestasPorPregunta.add(respuesta);
//                if (respuesta.getIsCorrect())
//                {
//                    respuestaCorrecta = respuesta.getEnunciado();
//                }
//            }
//        }
//        Collections.shuffle(respuestasPorPregunta);
//        cargarBotones(respuestasPorPregunta);
    }

    private void cargarBotones(ArrayList<Respuesta> respuestas) {

        int index = 0;
        for (MFXButton boton : botones)
        {
            //   boton.setText(respuestas.get(index).getEnunciado());
            index++;
        }

    }

    private void validarRespuesta(MFXButton button) {
        if (button.getText().equals(respuestaCorrecta))
        {
            valorRespuesta = true;
            System.out.println("Respuesta Correcta");

        } else
        {
            valorRespuesta = false;
            System.out.println("Respuesta Incorrecta");
        }

        ((Stage) acpRootPane.getScene().getWindow()).close();
        AppContext.getInstance().set("valorRespuesta", valorRespuesta);

    }

    @FXML
    private void onActionBtnRespuesta1(ActionEvent event) {
        validarRespuesta(btnRespuesta1);
    }

    @FXML
    private void onActionBtnRespuesta2(ActionEvent event) {
        validarRespuesta(btnRespuesta2);
    }

    @FXML
    private void onActionBtnRespuesta3(ActionEvent event) {
        validarRespuesta(btnRespuesta3);
    }

    @FXML
    private void onActionBtnRespuesta4(ActionEvent event) {
        validarRespuesta(btnRespuesta4);
    }

}
