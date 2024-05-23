package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Jugador;
import cr.ac.una.proyecto.model.Pregunta;
import cr.ac.una.proyecto.model.Respuesta;
import cr.ac.una.proyecto.util.AppContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.base.MFXCombo;
import static io.github.palexdev.materialfx.utils.RandomUtils.random;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class PreguntaController extends Controller implements Initializable {

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
        VboxRespuestas.getChildren().clear();
        btnsAnswers();
        cargarPreguntaCategoria();
        cargarJugadorPregunta();
        cargarPreguntasRespuestas();
        cargarEnunciadoPregunta();
    }

    public void btnsAnswers() {

        List<MFXButton> buttons = new ArrayList<>();
        buttons.add(new MFXButton());
        buttons.add(new MFXButton());
        buttons.add(new MFXButton());
        buttons.add(new MFXButton());

        randomButtonsAnswers(buttons);
    }

    public void randomButtonsAnswers(List<MFXButton> buttons) {
        for (int i = 0; i < buttons.size(); i++)
        {
            int random = (int) (Math.random() * buttons.size());
            MFXButton temp = buttons.get(i);
            buttons.set(i, buttons.get(random));
            buttons.set(random, temp);
        }

        VboxRespuestas.getChildren().addAll(buttons);
    }

    private void cargarPreguntaCategoria() {
        preguntaCategoria = ((String) AppContext.getInstance().get("preguntaCategoria"));

        if (preguntaCategoria == null)
        {
            preguntaCategoria = "";
        }
        System.out.println("Pregunta Categoria : " + preguntaCategoria + ", [cargarPreguntaCategoria][PreguntaController]");
    }

    private void cargarJugadorPregunta() {
        jugador = ((Jugador) AppContext.getInstance().get("preguntaJugador"));

        if (jugador == null)
        {
            System.out.println("Jugador nulo");
        }
        System.out.println("Pregunta Jugador : " + jugador.toString() + ", [cargarJugadorPregunta][PreguntaController]");
    }

    private void cargarPreguntasRespuestas() {
        preguntas = (ArrayList<Pregunta>) AppContext.getInstance().get("preguntas");
        respuestas = (ArrayList<Respuesta>) AppContext.getInstance().get("respuestas");
    }

    private void cargarEnunciadoPregunta() {
        ArrayList<Pregunta> preguntasPorCategoria = new ArrayList<>();
        ArrayList<Respuesta> respuestasPorPregunta = new ArrayList<>();

        System.out.println("Categoria: " + preguntaCategoria);
        for (Pregunta pregunta : preguntas)
        {
            if (pregunta.getCategoria().equals(preguntaCategoria))
            {
                preguntasPorCategoria.add(pregunta);
                System.out.println(pregunta.toString());

            }
        }

        Random random = new Random();
        int numeroAleatorioInt = random.nextInt(preguntasPorCategoria.size());

        Pregunta preguntaTexto = preguntasPorCategoria.get(numeroAleatorioInt);
        System.out.println("Numer");
        int id = preguntaTexto.getId();
        System.out.println("ID: " + id);
        for (Respuesta respuesta : respuestas)
        {
            if (id == respuesta.getIdPadre())
            {
                respuestasPorPregunta.add(respuesta);
                System.out.println(respuesta.toString());
                if (respuesta.getIsCorrect())
                {
                    respuestaCorrecta = respuesta.getEnunciado();
                    System.out.println("Respuesta Correcta es: " + respuestaCorrecta);
                }
            }
        }

        txaEnunciado.setText(preguntaTexto.getEnunciado());

    }

    private void cargarBotones(ArrayList<Respuesta> respuestas) {

        for (Respuesta respuesta : respuestas)
        {
          
        }

    }

    private void validarRespuesta(MFXButton button) {
        if (button.getText().equals(respuestaCorrecta))
        {
            System.out.println("Respuesta Correcta");
        } else
        {
            System.out.println("Respuesta Incorrecta");
        }
    }

}
