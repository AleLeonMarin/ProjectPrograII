package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.*;
import cr.ac.una.proyecto.service.JugadorService;
import cr.ac.una.proyecto.service.PreguntaService;
import cr.ac.una.proyecto.util.Animacion;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Mensaje;
import cr.ac.una.proyecto.util.RespuestaUtil;
import cr.ac.una.proyecto.util.Sound;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import io.github.palexdev.materialfx.controls.MFXButton;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oracle.jdbc.proxy.annotation.Pre;

public class PreguntaController extends Controller implements Initializable {

    @FXML
    private VBox VboxRespuestas;

    @FXML
    private TextArea txaEnunciado;

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

    private Animacion animacion;
    private String preguntaCategoria;

    private JugadorDto jugadorDto;
    private Sector sectorDto;
    private PreguntaDto preguntaDto;
    private ArrayList<RespuestaDto> respuestasDto;
    private Boolean resultadoValorRespuesta;
    private String dificultad;
    private int intentos;
    private ArrayList<MFXButton> botones;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize() {
        this.sectorDto = new Sector();
        animacion = new Animacion();
        respuestasDto = new ArrayList<>();
        preguntaDto = new PreguntaDto();
        jugadorDto = new JugadorDto();
        this.intentos = 1;
        cargarBotones();
        cargarDatosDesdeAppContext();
        habilitarAyudas(false);
        cargarAyudasDisponibles(sectorDto);
        nuevasRespuestas();
        unbindRespuestas();
        obtenerPreguntaCategoria();
        animacion.simpleFadeIn(acpRootPane);
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

    @FXML
    private void onMouseClickedBomba(MouseEvent event) {
        habilitarAyudaImagen(false, imvBomba);
        String ayuda = "Bomba";
        sectorDto.removerAyuda(ayuda);
        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Ayuda Activada", getStage(),
                "Has seleccionado la ayuda de bomba, esta ayuda elimina dos de las respuestas incorrectas");
        bombaAction();// sounds or animation
    }

    @FXML
    private void onMouseClickedNext(MouseEvent event) {
        habilitarAyudaImagen(false, imvNext);
        String ayuda = "Pasar";
        sectorDto.removerAyuda(ayuda);
        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Ayuda Activada", getStage(),
                "Has seleccionado la ayuda de pasar preguntas, esta ayuda te permite cambiar una pregunta por otra de la misma categoria");
    }

    @FXML
    private void onMouseOportunidadDoble(MouseEvent event) {
        habilitarAyudaImagen(false, imvSecondOportunity);
        String ayuda = "DobleOportunidad";
        sectorDto.removerAyuda(ayuda);
        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Ayuda Activada", getStage(),
                "Has seleccionado la ayuda de doble oportunidad, esta ayuda te da un intento mas si te llegarasa a equivocar");
        this.intentos += 1;
    }

    @FXML
    private void onMouseTirarRuleta(MouseEvent event) {
        habilitarAyudaImagen(false, imvTirarRuleta);
        String ayuda = "TirarRuleta";
        sectorDto.removerAyuda(ayuda);
        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Ayuda Activada", getStage(),
                "Has seleccionado la ayuda de tirar ruleta, esta ayuda te deja girar la ruleta para seleccionar otra pregunta");

        TirarRuletaController tirarRuletaBusquedaController = (TirarRuletaController) FlowController.getInstance()
                .getController("TirarRuletaView");
        FlowController.getInstance().goViewInWindowModal("TirarRuletaView",
                ((Stage) txaEnunciado.getScene().getWindow()), true);
        preguntaCategoria = (String) tirarRuletaBusquedaController.getResultado();
        obtenerPreguntaCategoria();
        animacion.simpleFadeIn(acpRootPane);
    }

    private void obtenerPreguntaCategoria() {
        PreguntaService preService = new PreguntaService();
        RespuestaUtil respuesta = preService.getPreguntaAleatoriaPorCategoria(preguntaCategoria);
        if (respuesta.getEstado()) {
            unbindRespuestas();
            preguntaDto = new PreguntaDto();
            preguntaDto = (PreguntaDto) respuesta.getResultado("Pregunta");
            preguntaDto.sumarAparicion();
            this.respuestasDto.clear();
            this.respuestasDto.addAll(preguntaDto.getRespuestas());
            bindRespuestas();
        } else {
            System.err.println("Error al obtener las preguntas: " + respuesta.getMensajeInterno());
        }

    }

    private void bindRespuestas() {
        txaEnunciado.setText(preguntaDto.getEnunciado());
        this.btnRespuesta1.textProperty().bindBidirectional(respuestasDto.get(0).enunciado);
        this.btnRespuesta2.textProperty().bindBidirectional(respuestasDto.get(1).enunciado);
        this.btnRespuesta3.textProperty().bindBidirectional(respuestasDto.get(2).enunciado);
        this.btnRespuesta4.textProperty().bindBidirectional(respuestasDto.get(3).enunciado);
    }

    private void unbindRespuestas() {
        this.txaEnunciado.clear();
        this.btnRespuesta1.textProperty().unbindBidirectional(respuestasDto.get(0).enunciado);
        this.btnRespuesta2.textProperty().unbindBidirectional(respuestasDto.get(1).enunciado);
        this.btnRespuesta3.textProperty().unbindBidirectional(respuestasDto.get(2).enunciado);
        this.btnRespuesta4.textProperty().unbindBidirectional(respuestasDto.get(3).enunciado);
        nuevasRespuestas();
    }

    private void nuevasRespuestas() {
        this.respuestasDto.clear();
        for (int index = 0; index < 4; index++) {
            respuestasDto.add(new RespuestaDto());
        }
    }

    private void validarRespuestaCorrecta(int btnIndice) {
        RespuestaDto respuestaDto = new RespuestaDto();
        respuestaDto = respuestasDto.get(btnIndice);
        preguntaDto.getRespuestas().get(btnIndice).incrementarContador();

        if (respuestaDto.getIsCorrect().equals("C")) {
            preguntaDto.sumarAcierto();
            jugadorDto.incrementarPreRespondidasCorrectamente();
            intentos--;
            this.resultadoValorRespuesta = true;
            if (preguntaCategoria.equals("Historia")) {
                jugadorDto.incrementarContHis();
                jugadorDto.incrementarCorHis();
            } else if (preguntaCategoria.equals("Ciencia")) {
                jugadorDto.incrementarContCie();
                jugadorDto.incrementarCorCie();
            } else if (preguntaCategoria.equals("Deportes")) {
                jugadorDto.incrementarContDep();
                jugadorDto.incrementarCorDep();
            } else if (preguntaCategoria.equals("Geografia")) {
                jugadorDto.incrementarContGeo();
                jugadorDto.incrementarCorGeo();
            } else if (preguntaCategoria.equals("Entretenemiento")) {
                jugadorDto.incrementarContEntre();
                jugadorDto.incrementarCorEntre();
            } else if (preguntaCategoria.equals("Arte")) {
                jugadorDto.incrementarContArte();
                jugadorDto.incrementarCorArte();
            }
            validarIntentos(true);
        } else {
            intentos--;
            this.resultadoValorRespuesta = false;
            botones.get(btnIndice).setDisable(true);
            if (preguntaCategoria.equals("Historia")) {
                jugadorDto.incrementarContHis();
            } else if (preguntaCategoria.equals("Ciencia")) {
                jugadorDto.incrementarContCie();
            } else if (preguntaCategoria.equals("Deportes")) {
                jugadorDto.incrementarContDep();
            } else if (preguntaCategoria.equals("Geografia")) {
                jugadorDto.incrementarContGeo();
            } else if (preguntaCategoria.equals("Entretenimiento")) {
                jugadorDto.incrementarContEntre();
            } else if (preguntaCategoria.equals("Arte")) {
                jugadorDto.incrementarContArte();
            }
            validarIntentos(false);
        }

    }

    private void validarIntentos(boolean value) {
        Sound sound = new Sound();
        setSectorDtoToAppContext();
        if (value == true) {
            actualizarPregunta();
            actualizarJugador();
            sound.playSound("src/main/resources/cr/ac/una/proyecto/resources/audio/Correcta.mp3");
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Respuesta Correcta",
                    acpRootPane.getScene().getWindow(), "Has respondido Correctamente");
            animacion.animarFadeOut(acpRootPane, getRunnableOnFinishOut());

        } else if (intentos <= 0) {
            actualizarPregunta();
            actualizarJugador();
            sound.playSound("src/main/resources/cr/ac/una/proyecto/resources/audio/Failed.mp3");
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Respuesta Incorrecta",
                    acpRootPane.getScene().getWindow(), "Has respondido Incorrectamente");
            animacion.animarFadeOut(acpRootPane, getRunnableOnFinishOut());
        } else {
            sound.playSound("src/main/resources/cr/ac/una/proyecto/resources/audio/Chance_audio.mp3");
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Respuesta Incorrecta",
                    acpRootPane.getScene().getWindow(),
                    "Has respondido Incorrectamente, te quedan: " + intentos + " intentos mas;");
        }
    }

    private Runnable getRunnableOnFinishOut() {
        Runnable onFinishOut = () -> {
            ((Stage) acpRootPane.getScene().getWindow()).close();
        };
        return onFinishOut;
    }

    private void habilitarAyudas(boolean valor) {
        habilitarAyudaImagen(valor, imvNext);
        habilitarAyudaImagen(valor, imvBomba);
        habilitarAyudaImagen(valor, imvSecondOportunity);
        habilitarAyudaImagen(valor, imvTirarRuleta);
    }

    private void habilitarPorAyuda(Ayuda ayuda) {
        if (ayuda.getNombre().equals("Bomba") && ayuda.getEstado()) {
            habilitarAyudaImagen(true, imvBomba);
        } else if (ayuda.getNombre().equals("Pasar") && ayuda.getEstado()) {
            habilitarAyudaImagen(true, imvNext);
        } else if (ayuda.getNombre().equals("DobleOportunidad") && ayuda.getEstado()) {
            habilitarAyudaImagen(true, imvSecondOportunity);
        } else if (ayuda.getNombre().equals("TirarRuleta") && ayuda.getEstado()) {
            habilitarAyudaImagen(true, imvTirarRuleta);
        }
    }

    private void habilitarAyudaImagen(boolean valor, ImageView imagen) {
        imagen.setDisable(!valor);
        imagen.setVisible(valor);
    }

    private void cargarAyudasDisponibles(Sector sector) {
        for (Ayuda ayuda : sector.getAyudas()) {
            habilitarPorAyuda(ayuda);
        }
    }

    private void bombaAction() {
        int cantidadRes = 2;
        int index = 0;
        int contadorBtonoes = 0;

        while (contadorBtonoes < cantidadRes) {
            RespuestaDto respuestaDto = new RespuestaDto();
            respuestaDto = respuestasDto.get(index);
            System.out.println("PreguntaEnun: " + respuestaDto.getEnunciado());
            if (respuestaDto.getIsCorrect().equals("X")) {
                botones.get(index).setDisable(true);
                contadorBtonoes++;
            }
            index++;
        }

    }

    private void cargarBotones() {
        this.botones = new ArrayList<>();
        this.botones.clear();
        this.botones.add(btnRespuesta1);
        this.botones.add(btnRespuesta2);
        this.botones.add(btnRespuesta3);
        this.botones.add(btnRespuesta4);

        for (MFXButton boton : botones) {
            boton.setDisable(false);
        }
    }

    private void actualizarJugador() {
        try {
            jugadorDto.incrementarPreguntasRespondidas();
            JugadorService jugadorService = new JugadorService();
            RespuestaUtil respuestaUtil = jugadorService.actualizarJugador(this.jugadorDto);
            if (respuestaUtil.getEstado()) {
                this.jugadorDto = (JugadorDto) respuestaUtil.getResultado("AJugador");
                setSectorDtoToAppContext();
                new Mensaje().showModal(AlertType.INFORMATION, "Actualizar Jugador", getStage(), "Jugador Actualizado");
            } else {
                new Mensaje().showModal(AlertType.ERROR, "Actualizar Jugador", getStage(),
                        "Error al actualizar el jugador");
            }
        } catch (Exception ex) {
            Logger.getLogger(PreguntaController.class.getName()).log(Level.SEVERE, "Error actualizando el jugador.",
                    ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Actualizar jugador", getStage(),
                    "Ocurrio un error actualizando el jugador.");
        }
    }

    private void cargarDatosDesdeAppContext() {
        cargarCategoriaAppContext();
        cargarDificultadFromAppContext();
        cargarSectorJugadorDtoAppContext();
    }

    private void cargarCategoriaAppContext() {
        this.preguntaCategoria = ((String) AppContext.getInstance().get("preguntaCategoria"));
    }

    private void cargarDificultadFromAppContext() {
        dificultad = ((String) AppContext.getInstance().get("dificultad"));
    }

    private void cargarSectorJugadorDtoAppContext() {
        sectorDto = ((Sector) AppContext.getInstance().get("preguntaSector"));
        jugadorDto = sectorDto.getJugador();
    }

    private void setSectorDtoToAppContext() {
        sectorDto.setJugador(jugadorDto);
        AppContext.getInstance().set("preguntaSector", sectorDto);
    }

    public boolean getResultadoRespuestaPregunta() {
        return resultadoValorRespuesta;
    }

    public void actualizarPregunta() {
        try {
            PreguntaService preguntaService = new PreguntaService();
            RespuestaUtil respuestaUtil = preguntaService.actualizarPregunta(this.preguntaDto);
            if (respuestaUtil.getEstado()) {
                this.preguntaDto = (PreguntaDto) respuestaUtil.getResultado("APregunta");
                System.out.println("Pregunta Actualizada" + preguntaDto.getEnunciado() + ", ID" + preguntaDto.getId());
                new Mensaje().showModal(AlertType.INFORMATION, "Actualizar Pregunta", getStage(), "Pregunta Actualizada");
            } else {
                new Mensaje().showModal(AlertType.ERROR, "Actualizar Pregunta", getStage(),
                        "Error al actualizar la pregunta");
            }
        } catch (Exception ex) {
            Logger.getLogger(PreguntaController.class.getName()).log(Level.SEVERE, "Error actualizando la pregunta.",
                    ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Actualizar pregunta", getStage(),
                    "Ocurrio un error actualizando la pregunta.");
        }
    }

}
