package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import cr.ac.una.proyecto.model.JugadorDto;
import cr.ac.una.proyecto.model.PartidaDto;
import cr.ac.una.proyecto.service.JugadorService;
import cr.ac.una.proyecto.service.PartidaService;
import cr.ac.una.proyecto.util.*;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WinnerViewController extends Controller implements Initializable {

    @FXML
    private ImageView imgScale;

    @FXML
    private Label lblGanador;

    @FXML
    private AnchorPane root;

    PauseTransition pause = new PauseTransition(Duration.seconds(15));
    Sound sound = new Sound();
    Animacion animacion = new Animacion();

    private PartidaDto partidaDto;
    private JugadorDto jugadorGanador;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.partidaDto = new PartidaDto();
        this.jugadorGanador = new JugadorDto();
        animacion.zoomInOut(imgScale);
        sound.playSound("AwardSoundEffect.mp3");
        pause.setOnFinished(e -> {
            FlowController.getInstance().goViewInWindow("GameLogIn");
            ((Stage) root.getScene().getWindow()).close();
        });
        pause.play();
    }

    @Override
    public void initialize() {
        cargarPartidaDto();
        cargarJugadorGanadorDto();
        setGanador();
        actualizarJugador();
        eliminarPartida();
    }

    public void setGanador() {
        lblGanador.setText(jugadorGanador.getNombre());
    }


    private void cargarPartidaDto() {
        this.partidaDto = (PartidaDto) AppContext.getInstance().get("partidaCargada");
    }

    private void cargarJugadorGanadorDto() {
        jugadorGanador = (JugadorDto) AppContext.getInstance().get("jugadorGanador");

    }

    private void actualizarJugador() {//Actualiza al jugador en la base de datos
        try {
            this.jugadorGanador.incrementarPartidasGanadas();
            JugadorService jugadorService = new JugadorService();
            RespuestaUtil respuestaUtil = jugadorService.actualizarJugador(this.jugadorGanador);
            if (!(respuestaUtil.getEstado())) {

                new Mensaje().showModal(Alert.AlertType.ERROR, "Actualizar Jugador", getStage(),
                        "Error al actualizar el jugador");
            }
        } catch (Exception ex) {
            Logger.getLogger(PreguntaController.class.getName()).log(Level.SEVERE, "Error actualizando el jugador.",
                    ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Actualizar jugador", getStage(),
                    "Ocurrio un error actualizando el jugador.");
        }
    }


    private void eliminarPartida() {
        if (partidaDto != null) {
            try {
                if (this.partidaDto.getParId() == null) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Partida ", getStage(),
                            "Partida Id es nulo.");
                } else {

                    PartidaService partidaService = new PartidaService();
                    RespuestaUtil respuesta = partidaService.eliminarPartida(this.partidaDto.getParId());
                    if (!(respuesta.getEstado())) {

                        new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Partida", getStage(),
                                respuesta.getMensaje());
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(WinnerViewController.class.getName()).log(Level.SEVERE, "Error al eliminar la partida.", ex);
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Partida", getStage(),
                        "Ocurrió un error al eliminar la partida.");
            }
        }
    }
}
