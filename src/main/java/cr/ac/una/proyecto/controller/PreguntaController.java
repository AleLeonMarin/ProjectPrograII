package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Ayuda;
import cr.ac.una.proyecto.model.JugadorDto;
import cr.ac.una.proyecto.model.PreguntaDto;
import cr.ac.una.proyecto.model.RespuestaDto;
import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.service.JugadorService;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    private RespuestaDto respuetaDtoAux;
    private ArrayList<PreguntaDto> preguntasDto;
    private ArrayList<RespuestaDto> respuestasDto;
    private Boolean resultadoValorRespuesta;
    private String dificultad;
    private int intentos;
    private ArrayList<MFXButton> botones;
    private Integer contadorHistoria = 0;
    private Integer contadorCiencia = 0;
    private Integer contadorDeportes = 0;
    private Integer contadorGeografia = 0;
    private Integer contadorEntretenimiento = 0;
    private Integer contadorArte = 0;
    private Integer correctaHistoria = 0;
    private Integer correctaCiencia = 0;
    private Integer correctaDeportes = 0;
    private Integer correctaGeografia = 0;
    private Integer correctaEntretenimiento = 0;
    private Integer correctaArte = 0;
    private Integer generalPregunta = 0;
    private Integer generalCorrecta = 0;

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
        jugadorDto = new JugadorDto();
        this.intentos = 1;
        cargarBotones();
        cargarDatosDesdeAppContext();
        obtenerPreguntasCategoria();
        cargarEnunciadoPregunta();
        unbindRespuestas();
        cargarDificultadFromAppContext();
        habilitarBotonesRespuesta(false);
        cargarAyudasDisponibles(sectorDto);
        consultarJugadores();
        animacion.simpleFadeIn(acpRootPane);
    }

    private void cargarDatosDesdeAppContext() {
        cargarCategoriaAppContext();
        cargarSectorJugadorDtoAppContext();
    }

    private void cargarCategoriaAppContext() {

        this.preguntaCategoria = ((String) AppContext.getInstance().get("preguntaCategoria"));
    }

    private void setSectorDtoAppContext() {

        AppContext.getInstance().set("preguntaSector", sectorDto);
    }

    private void cargarSectorJugadorDtoAppContext() {
        sectorDto = ((Sector) AppContext.getInstance().get("preguntaSector"));
        jugadorDto = sectorDto.getJugador();
    }

    private void obtenerPreguntasCategoria() {
        PreguntaService preService = new PreguntaService();
        RespuestaUtil respuesta = preService.getPreguntasActivasPorCategoria(preguntaCategoria);
        if (respuesta.getEstado()) {
            preguntasDto.clear();
            preguntasDto.addAll((List<PreguntaDto>) respuesta.getResultado("Preguntas"));
            for (PreguntaDto pre : preguntasDto) {

                System.out.println("Enunciado: " + pre.getEnunciado());
            }
        } else {
            System.err.println("Error al obtener las preguntas: " + respuesta.getMensajeInterno());
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
                Collections.shuffle(respuestasDto);
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

    public void cargarEnunciadoPregunta() {
        unbindRespuestas();
        preguntaDto = cargarPreguntasPorCategoria();
        cargarRespuestas(preguntaDto.getId());
        txaEnunciado.setText(preguntaDto.getEnunciado());
        bindRespuestas();
    }

    private PreguntaDto cargarPreguntasPorCategoria() {

        Random random = new Random();
        int numeroAleatorioInt = random.nextInt(preguntasDto.size() + 1);

        PreguntaDto preguntaDto = preguntasDto.get(numeroAleatorioInt);
        preguntasDto.remove(numeroAleatorioInt);
        return preguntaDto;
    }

    @FXML
    private void onActionBtnRespuesta1(ActionEvent event) {
        validarRespuestaCorrecta(0);
        actualizarJugador(this.jugadorDto.getId());
    }

    @FXML
    private void onActionBtnRespuesta2(ActionEvent event) {
        validarRespuestaCorrecta(1);
        actualizarJugador(this.jugadorDto.getId());
    }

    @FXML
    private void onActionBtnRespuesta3(ActionEvent event) {
        validarRespuestaCorrecta(2);
        actualizarJugador(this.jugadorDto.getId());
    }

    @FXML
    private void onActionBtnRespuesta4(ActionEvent event) {
        validarRespuestaCorrecta(3);
        actualizarJugador(this.jugadorDto.getId());
    }

    private void validarRespuestaCorrecta(int btnIndice) {
        RespuestaDto respuestaDto = new RespuestaDto();
        respuestaDto = respuestasDto.get(btnIndice);

        if (respuestaDto.getIsCorrect().equals("C")) {
            intentos--;
            this.resultadoValorRespuesta = true;
            validarIntentos(true);

            if (preguntaCategoria.equals("Historia")) {
                contadorHistoria++;
                correctaHistoria++;
            } else if (preguntaCategoria.equals("Ciencia")) {
                contadorCiencia++;
                correctaCiencia++;
            } else if (preguntaCategoria.equals("Deportes")) {
                contadorDeportes++;
                correctaDeportes++;
            } else if (preguntaCategoria.equals("Geografia")) {
                contadorGeografia++;
                correctaGeografia++;
            } else if (preguntaCategoria.equals("Entretenimiento")) {
                contadorEntretenimiento++;
                correctaEntretenimiento++;
            } else if (preguntaCategoria.equals("Arte")) {
                contadorArte++;
                correctaArte++;
            }
            generalCorrecta++;

        } else {

            intentos--;
            this.resultadoValorRespuesta = false;
            botones.get(btnIndice).setDisable(true);
            validarIntentos(false);
            if (preguntaCategoria.equals("Historia")) {
                contadorHistoria++;
            } else if (preguntaCategoria.equals("Ciencia")) {
                contadorCiencia++;
            } else if (preguntaCategoria.equals("Deportes")) {
                contadorDeportes++;
            } else if (preguntaCategoria.equals("Geografia")) {
                contadorGeografia++;
            } else if (preguntaCategoria.equals("Entretenimiento")) {
                contadorEntretenimiento++;
            } else if (preguntaCategoria.equals("Arte")) {
                contadorArte++;
            }

            generalPregunta++;
        }

    }

    private Runnable getRunnableOnFinishOut() {

        Runnable onFinishOut = () -> {
            ((Stage) acpRootPane.getScene().getWindow()).close();
        };

        return onFinishOut;
    }

    private void validarIntentos(boolean value) {
        sectorDto.printAyudasInfo();
        if (value == true) {
            // sonido de correcta
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Respuesta Correcta",
                    acpRootPane.getScene().getWindow(), "Has respondido Correctamente");
            animacion.animarFadeOut(acpRootPane, getRunnableOnFinishOut());

        } else if (intentos <= 0) {
            // sonido de error
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Respuesta Incorrecta",
                    acpRootPane.getScene().getWindow(), "Has respondido Incorrectamente");
            animacion.animarFadeOut(acpRootPane, getRunnableOnFinishOut());
        } else {
            // sonido de error
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Respuesta Incorrecta",
                    acpRootPane.getScene().getWindow(),
                    "Has respondido Incorrectamente, te quedan: " + intentos + " intentos mas;");
        }
    }

    @FXML
    private void onMouseClickedBomba(MouseEvent event) {
        habilitarAyudaImagen(false, imvBomba);
        String ayuda = "Bomba";
        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Ayuda Activada", getStage(),
                "Has seleccionado la ayuda de bomba, esta ayuda elimina dos de las respuestas incorrectas");

        bombaAction();// sounds or animation
        sectorDto.removerAyuda(ayuda);
    }

    @FXML
    private void onMouseClickedNext(MouseEvent event) {
        habilitarAyudaImagen(false, imvNext);
        habilitarBotonesRespuesta(true);
        String ayuda = "Pasar";
        sectorDto.removerAyuda(ayuda);
        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Ayuda Activada", getStage(),
                "Has seleccionado la ayuda de pasar preguntas, esta ayuda te permite cambiar una pregunta por otra de la misma categoria");

        cargarEnunciadoPregunta();

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
        habilitarBotonesRespuesta(true);
        String ayuda = "TirarRuleta";
        sectorDto.removerAyuda(ayuda);
        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Ayuda Activada", getStage(),
                "Has seleccionado la ayuda de tirar ruleta, esta ayuda te deja girar la ruleta para seleccionar otra pregunta");

        TirarRuletaController tirarRuletaBusquedaController = (TirarRuletaController) FlowController.getInstance()
                .getController("TirarRuletaView");
        FlowController.getInstance().goViewInWindowModal("TirarRuletaView",
                ((Stage) txaEnunciado.getScene().getWindow()), true);
        preguntaCategoria = (String) tirarRuletaBusquedaController.getResultado();
        obtenerPreguntasCategoria();
        cargarEnunciadoPregunta();
        animacion.simpleFadeIn(acpRootPane);

    }

    private void cargarDificultadFromAppContext() {
        dificultad = ((String) AppContext.getInstance().get("dificultad"));

        if (dificultad.equals("Dificil")) {
            habilitarBotonesRespuesta(false);
        } else {
            cargarAyudasDisponibles(sectorDto);
        }
    }

    private void habilitarBotonesRespuesta(boolean valor) {

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

    private void actualizarJugador(Long id) {
        try {
            JugadorService jugadorService = new JugadorService();
            RespuestaUtil respuestaUtil = jugadorService.actualizarJugador(this.jugadorDto);
            if (respuestaUtil.getEstado()) {
                this.jugadorDto = (JugadorDto) respuestaUtil.getResultado("Jugador");
    
                jugadorDto.setContadorArte(contadorArte);
                jugadorDto.setContadorCiencia(contadorCiencia);
                jugadorDto.setContadorDeportes(contadorDeportes);
                jugadorDto.setContadorEntretenimiento(contadorEntretenimiento);
                jugadorDto.setContadorGeografia(contadorGeografia);
                jugadorDto.setContadorHistoria(contadorHistoria);
                jugadorDto.setContadorCorrectasArte(contadorArte);
                jugadorDto.setContadorCorrectasCiencia(contadorCiencia);
                jugadorDto.setContadorCorrectasDeportes(contadorDeportes);
                jugadorDto.setContadorCorrectasEntretenimiento(contadorEntretenimiento);
                jugadorDto.setContadorCorrectasGeografia(contadorGeografia);
                jugadorDto.setContadorCorrectaHistoria(contadorHistoria);
                jugadorDto.setPRespondidasCorrectamente(correctaArte);
                jugadorDto.setPreguntasRespondidas(generalPregunta);
    
                new Mensaje().showModal(AlertType.INFORMATION, "Actualizar Jugador", getStage(), "Jugador Actualizado");
            } else {
                new Mensaje().showModal(AlertType.ERROR, "Actualizar Jugador", getStage(), "Error al actualizar el jugador");
            }
        } catch (Exception ex) {
            Logger.getLogger(PreguntaController.class.getName()).log(Level.SEVERE, "Error actualizando el jugador.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Actualizar jugador", getStage(), "Ocurrio un error actualizando el jugador.");
        }
    }
    

    private void consultarJugadores() {
        JugadorService jugadorService = new JugadorService();
        RespuestaUtil respuesta = jugadorService.getAll();
        if (respuesta.getEstado()) {
            List<JugadorDto> jugadores = (List<JugadorDto>) respuesta.getResultado("Jugadores");
            for (JugadorDto jugador : jugadores) {
                System.out.println("Jugador: " + jugador.getId());
            }
        } else {
            System.err.println("Error al obtener los jugadores: " + respuesta.getMensajeInterno());
        }
    }

    public boolean getResultadoRespuestaPregunta() {
        return resultadoValorRespuesta;
    }

    public Sector getSectorDto(){
        return sectorDto;
    }


}


