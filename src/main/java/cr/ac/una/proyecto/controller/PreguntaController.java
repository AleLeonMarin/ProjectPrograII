package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Ayuda;
import cr.ac.una.proyecto.model.JugadorDto;
import cr.ac.una.proyecto.model.PreguntaDto;
import cr.ac.una.proyecto.model.RespuestaDto;
import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.service.PreguntaService;
import cr.ac.una.proyecto.service.RespuestaService;
import cr.ac.una.proyecto.util.Animacion;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
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
    private Sector sectorDto;
    private PreguntaDto preguntaDto;
    private RespuestaDto respuetaDtoAux;
    private ArrayList<PreguntaDto> preguntasDto;
    private ArrayList<RespuestaDto> respuestasDto;
    private Boolean resultadoValorRespuesta;
    private String dificultad;
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
        this.sectorDto = new Sector();
        animacion = new Animacion();
        preguntasDto = new ArrayList<>();
        respuestasDto = new ArrayList<>();
        respuetaDtoAux = new RespuestaDto();
        preguntaDto = new PreguntaDto();
        this.intentos = 1;
        cargarBotones();
        cargarDatosDesdeAppContext();
        obtenerPreguntasCategoria();
        cargarEnunciadoPregunta();
        unbindRespuestas();
        cargarDificultadFromAppContext();
        disableAll();
        cargarAyudasDisponibles(sectorDto);

        animacion.simpleFadeIn(acpRootPane);

    }

    private void cargarDatosDesdeAppContext() {
        cargarCategoriaAppContext();
        cargarSectorJugadorDtoAppContext();
    }

    private void cargarCategoriaAppContext() {

        this.preguntaCategoria = ((String) AppContext.getInstance().get("preguntaCategoria"));
    }

    private void cargarSectorJugadorDtoAppContext() {
        sectorDto = ((Sector) AppContext.getInstance().get("preguntaSector"));
        jugadorDto = sectorDto.getJugador();
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
            RespuestaUtil respuesta = respuestaService.getRespuestasPreguntas(preguntaId);

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
        unbindRespuestas();
        preguntaDto = cargarPreguntasPorCategoria();
        cargarRespuestas(preguntaDto.getId());
        txaEnunciado.setText(preguntaDto.getEnunciado());
        bindRespuestas();
    }

    private PreguntaDto cargarPreguntasPorCategoria() {

        Random random = new Random();
        int numeroAleatorioInt = random.nextInt(preguntasDto.size());

        PreguntaDto preguntaDto = preguntasDto.get(numeroAleatorioInt);
        preguntasDto.remove(numeroAleatorioInt);
        return preguntaDto;
    }

    @FXML
    private void onActionBtnRespuesta1(ActionEvent event) {
        validarRespuestaCorrecta(0);
    }

    @FXML
    private void onActionBtnRespuesta2(ActionEvent event) {
        validarRespuestaCorrecta(1);
    }

    @FXML
    private void onActionBtnRespuesta3(ActionEvent event) {
        validarRespuestaCorrecta(2);
    }

    @FXML
    private void onActionBtnRespuesta4(ActionEvent event) {
        validarRespuestaCorrecta(3);
    }

    private void validarRespuestaCorrecta(int btnIndice) {
        RespuestaDto respuestaDto = new RespuestaDto();
        respuestaDto = respuestasDto.get(btnIndice);
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
            botones.get(btnIndice).setDisable(true);
            validarIntentos(false);
        }

    }

    private Runnable getRunnableOnFinishOut() {

        Runnable onFinishOut = () ->
        {
            ((Stage) acpRootPane.getScene().getWindow()).close();
        };

        return onFinishOut;
    }

    private void validarIntentos(boolean value) {

        if (value == true)
        {
            //sonido de correcta
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Respuesta Correcta", acpRootPane.getScene().getWindow(), "Has respondido Correctamente");
            animacion.animarFadeOut(acpRootPane, getRunnableOnFinishOut());

        } else if (intentos <= 0)
        {
            //sonido de error
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Respuesta Incorrecta", acpRootPane.getScene().getWindow(), "Has respondido Incorrectamente");
            animacion.animarFadeOut(acpRootPane, getRunnableOnFinishOut());
        } else
        {
            //sonido de error
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Respuesta Incorrecta", acpRootPane.getScene().getWindow(), "Has respondido Incorrectamente, te quedan: " + intentos + " intentos mas;");
        }

        AppContext.getInstance().set("valorRespuesta", resultadoValorRespuesta);
    }

    @FXML
    private void onMouseClickedBomba(MouseEvent event) {
        String ayuda = "Bomba";
        new Mensaje().show(Alert.AlertType.INFORMATION, "Ayuda Activada",
                "Has seleccionado la ayuda de bomba, esta ayuda elimina dos de las respuestas incorrectas");
        bombaAction();//sounds or animation
        sectorDto.removerAyuda(ayuda);

    }

    @FXML
    private void onMouseClickedNext(MouseEvent event) {
        String ayuda = "Pasar";
        new Mensaje().show(Alert.AlertType.INFORMATION, "Ayuda Activada",
                "Has seleccionado la ayuda de pasar preguntas, esta ayuda te permite cambiar una pregunta por otra de la misma categoria");
        cargarEnunciadoPregunta();
        habilitarAyudaImagen(false, imvNext);
        sectorDto.removerAyuda(ayuda);

    }

    @FXML
    private void onMouseOportunidadDoble(MouseEvent event) {
        String ayuda = "DobleOportunidad";
        new Mensaje().show(Alert.AlertType.INFORMATION, "Ayuda Activada",
                "Has seleccionado la ayuda de doble oportunidad, esta ayuda te da un intento mas si te llegarasa a equivocar");
        this.intentos += 1;
        habilitarAyudaImagen(false, imvSecondOportunity);
        sectorDto.removerAyuda(ayuda);

    }

    @FXML
    private void onMouseTirarRuleta(MouseEvent event) {
        String ayuda = "TirarRuleta";
        new Mensaje().show(Alert.AlertType.INFORMATION, "Ayuda Activada",
                "Has seleccionado la ayuda de tirar ruleta, esta ayuda te deja girar la ruleta para seleccionar otra pregunta");

        TirarRuletaController tirarRuletaBusquedaController = (TirarRuletaController) FlowController.getInstance().getController("TirarRuletaView");
        FlowController.getInstance().goViewInWindowModal("TirarRuletaView", ((Stage) txaEnunciado.getScene().getWindow()), true);
        preguntaCategoria = (String) tirarRuletaBusquedaController.getResultado();
        obtenerPreguntasCategoria();
        cargarEnunciadoPregunta();
        habilitarAyudaImagen(false, imvTirarRuleta);
        animacion.simpleFadeIn(acpRootPane);
        sectorDto.removerAyuda(ayuda);
    }

    private void cargarDificultadFromAppContext() {
        dificultad = ((String) AppContext.getInstance().get("dificultad"));

        if (dificultad.equals("Dificil"))
        {
            disableAll();
        } else
        {
            cargarAyudasDisponibles(sectorDto);
        }
    }

    private void disableAll() {

        habilitarAyudaImagen(false, imvNext);
        habilitarAyudaImagen(false, imvBomba);
        habilitarAyudaImagen(false, imvSecondOportunity);
        habilitarAyudaImagen(false, imvTirarRuleta);

    }

    private void habilitarPorAyuda(Ayuda ayuda) {

        System.out.println("Ayuda nombre: " + ayuda.getNombre());

        if (ayuda.getNombre().equals("Bomba") && ayuda.getEstado())
        {
            habilitarAyudaImagen(true, imvBomba);
        } else if (ayuda.getNombre().equals("Pasar") && ayuda.getEstado())
        {
            habilitarAyudaImagen(true, imvNext);
        } else if (ayuda.getNombre().equals("DobleOportunidad") && ayuda.getEstado())
        {
            habilitarAyudaImagen(true, imvSecondOportunity);
        } else if (ayuda.getNombre().equals("TirarRuleta") && ayuda.getEstado())
        {
            habilitarAyudaImagen(true, imvTirarRuleta);
        }

    }

    private void habilitarAyudaImagen(boolean valor, ImageView imagen) {
        imagen.setDisable(!valor);
        imagen.setVisible(valor);

    }

    private void cargarAyudasDisponibles(Sector sector) {

        for (Ayuda ayuda : sector.getAyudas())
        {
            habilitarPorAyuda(ayuda);
        }
    }

    private void bombaAction() {

        int cantidadRes = 2;
        int index = 0;
        int contadorBtonoes = 0;

        while (contadorBtonoes < cantidadRes)
        {
            RespuestaDto respuestaDto = new RespuestaDto();
            respuestaDto = respuestasDto.get(index);
            System.out.println("PreguntaEnun: " + respuestaDto.getEnunciado());
            if (respuestaDto.getIsCorrect().equals("X"))
            {
                botones.get(index).setDisable(true);
                contadorBtonoes++;
            }
            index++;
        }

        habilitarAyudaImagen(false, imvBomba);
    }

    private void cargarBotones() {
        this.botones = new ArrayList<>();
        this.botones.clear();
        this.botones.add(btnRespuesta1);
        this.botones.add(btnRespuesta2);
        this.botones.add(btnRespuesta3);
        this.botones.add(btnRespuesta4);

        for (MFXButton boton : botones)
        {
            boton.setDisable(false);
        }
    }

}
