package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Juego;
import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.util.Animacion;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Mensaje;
import cr.ac.una.proyecto.util.Sound;
import io.github.palexdev.materialfx.controls.MFXButton;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class TablerosController extends Controller implements Initializable {

    @FXML
    private ImageView imvRuleta;
    @FXML
    private GridPane grdpTablero;
    @FXML
    private ImageView imvPicker;
    @FXML
    private AnchorPane acpRootPane;
    @FXML
    private Label lblJugadorActual;

    private Animacion animacion;
    private Juego juego;
    ArrayList<Sector> sectores;
    private TextField txfRuletaPrueba;
    Sound sound = new Sound();

    private ArrayList<String> categoriasRuleta;
    private String categoria;
    private String dificultad;
    private Boolean turnoDecidido;
    private Boolean valorPreguntaRespuesta;
    private Boolean isOnCrown;
    private Boolean isOnDuel;
    @FXML
    private Label lblRonda;
    @FXML
    private MFXButton btnCederTurno;

    @Override
    public void initialize() {
        iniciarClases();
        isOnCargarPartida();
        juego.cargarDatosImagenes(grdpTablero);
        cargarLabelsPartidaInfo();
        dificultad = juego.getDificultad();
        validarTurno();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void OnMouseClickedPicker(MouseEvent event) {//Gira la ruleta en la vista
        sound.playSound("Roulette.mp3");
        cargarLabelsPartidaInfo();
        this.imvPicker.setDisable(true);
        if (!turnoDecidido) {
            calcularTurnos();
            this.btnCederTurno.setDisable(true);
        } else {
            this.btnCederTurno.setDisable(true);
            moverRuleta();
        }
    }

    private void cargarLabelsPartidaInfo() {//Carga y setea la informacion de ronda y jugador actual en los label de la vista
        String nombreJugador = juego.getSectorActual().getJugador().getNombre();
        Integer rondasJuego = juego.getRondas();
        if (nombreJugador != null) {
            lblJugadorActual.setText(nombreJugador);
        }
        if (rondasJuego >= 0 && rondasJuego != null) {
            this.lblRonda.setText(rondasJuego.toString());
        }

    }

    private void cargarSectores() {
        sectores = new ArrayList<>();
        sectores = (ArrayList<Sector>) AppContext.getInstance().get("sectores");

        for (Sector sector : sectores) {
            juego.agregarSector(sector);
        }
    }

    private void
    isOnCargarPartida() {//Identifica si se quiere cargar una partida o si se creo una nueva
        boolean cargarPartida = (Boolean) AppContext.getInstance().get("cargarPartida");

        if (cargarPartida) {
            this.juego = new Juego();
            this.juego = (Juego) AppContext.getInstance().get("juego");
            if (juego.getRondas() > 0) {
                this.turnoDecidido = true;
                this.btnCederTurno.setDisable(false);
            }
        } else {
            cargarSectores();
            juego.cargarAyudasSegunDificultad();
        }
    }

    public boolean pickerStatus() {
        if (imvPicker.isDisable()) {
            return true;
        }
        return false;
    }

    private void cargarCategoriasRuleta() {
        categoriasRuleta = (ArrayList<String>) AppContext.getInstance().get("categoriasRuleta");
    }

    private void iniciarClases() {
        categoriasRuleta = new ArrayList<>();
        juego = new Juego();
        animacion = new Animacion();
        this.turnoDecidido = false;
        this.valorPreguntaRespuesta = false;
        isOnCrown = false;
        isOnDuel = false;
        this.btnCederTurno.setDisable(true);
        cargarCategoriasRuleta();
    }

    private void calcularTurnos() {//Calcula el primer jugador en responder la primera pregunta

        this.categoria = juego.obtenerPosicionRuleta();
        double anguloDetenido = juego.getRuletaAngulo();

        Runnable onFinish = () -> {
            this.imvPicker.setDisable(false);

            if (categoria.equals(categoriasRuleta.get(4))) {
                turnoDecidido = true;
                this.btnCederTurno.setDisable(false);
                this.juego.setRondas(1);
                Platform.runLater(() -> {
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Escoger turno", getStage(),
                            "Cayo corona, el jugador: "
                                    + juego.getSectorActual().getJugador().getNombre()
                                    + ", inicia la partida");
                });
            } else {
                juego.cambiarPrimerTurno();
            }
        };

        animacion.animacionRuleta(imvRuleta, juego.getRuletaAngulo(), onFinish);
    }

    private void moverRuleta() {//Gira la ruleta en la vista durante el juego

        this.categoria = juego.obtenerPosicionRuleta();
        double anguloDetenido = juego.getRuletaAngulo();

        Runnable onFinish = () -> {
            juego.setSectorActualAppContext();
            juego.setSectoresAppContext();
            Platform.runLater(() -> mostrarTarjetas());
            this.imvPicker.setDisable(false);
        };

        animacion.animacionRuleta(imvRuleta, anguloDetenido, onFinish);
        AppContext.getInstance().set("preguntaCategoria", categoria);
    }

    private void llamarPreguntaView() {//Llama a la vista de responder pregunta
        FlowController.getInstance().goViewInWindowModal("preguntaView", ((Stage) acpRootPane.getScene().getWindow()),
                true);
        PreguntaController controladorPreguntaView = (PreguntaController) FlowController.getInstance()
                .getController("preguntaView");
        valorPreguntaRespuesta = controladorPreguntaView.getResultadoRespuestaPregunta();
        juego.cargarSectorActualFromAppContext();
    }

    private void mostrarTarjetas() {//muestra las tarjetas frontales dependiendo de la categoria
        sound.playSound("Card.mp3");
        if (categoria.equals(categoriasRuleta.get(0))) {
            FlowController.getInstance().goViewInWindowModal("FrontalCardSports",
                    ((Stage) acpRootPane.getScene().getWindow()), true);
        } else if (categoria.equals(categoriasRuleta.get(1))) {
            FlowController.getInstance().goViewInWindowModal("FrontalCardArt",
                    ((Stage) acpRootPane.getScene().getWindow()), true);
        } else if (categoria.equals(categoriasRuleta.get(2))) {
            FlowController.getInstance().goViewInWindowModal("FrontalCardGeografy",
                    ((Stage) acpRootPane.getScene().getWindow()), true);
        } else if (categoria.equals(categoriasRuleta.get(3))) {
            FlowController.getInstance().goViewInWindowModal("FrontalCardScience",
                    ((Stage) acpRootPane.getScene().getWindow()), true);
        } else if (categoria.equals(categoriasRuleta.get(4))) {
            goCoronaDuelView();
            return;
        } else if (categoria.equals(categoriasRuleta.get(5))) {
            FlowController.getInstance().goViewInWindowModal("FrontalCardEntertamient",
                    ((Stage) acpRootPane.getScene().getWindow()), true);
        } else {
            FlowController.getInstance().goViewInWindowModal("FrontalCardHistory",
                    ((Stage) imvRuleta.getScene().getWindow()), true);
        }
        isOnDuel();
    }

    private void isOnDuel() {//Identifica si escogimos duelo en la vista de CrowSelectionController
        if (isOnDuel) {
            //accion
        } else {
            sound.playSound("windMovement1.mp3");
            llamarPreguntaView();
            juego.jugar(grdpTablero, valorPreguntaRespuesta, isOnCrown);
            setCorona();
            validarJugadorGanador(acpRootPane);
            isJugadorInCoronaPos();
            cargarLabelsPartidaInfo();
            this.btnCederTurno.setDisable(false);
        }
    }

    private void goCoronaDuelView() {//Llama a la vistade SelectCrownDecisionView

        if (getCrowDuelResult()) {
            isOnCrown = true;
            SelectCrownDecisionController controladorCoronaSelection = (SelectCrownDecisionController) FlowController
                    .getInstance().getController("SelectCrownDecisionView");
            // sound
            sound.playSound("Card.mp3");
            // flow
            FlowController.getInstance().goViewInWindowModal("SelectCrownDecisionView",
                    ((Stage) acpRootPane.getScene().getWindow()), true);

            categoria = controladorCoronaSelection.getResultado();
            AppContext.getInstance().set("preguntaCategoria", categoria);
            mostrarTarjetas();
        } else {
            isOnDuel = true;
        }
    }

    private void setCorona() {//Setea una corona de una categoria  en especifico si el jugador en corona respondio correctamente
        if (isOnCrown) {
            if (valorPreguntaRespuesta) {
                juego.getSectorActual().setEstadoCorona(this.categoria, true);
                dificultadMediaDarAyuda();
                validarCoronasPrimerTurno();
            }
            juego.setSectorActualAppContext();
            isOnCrown = false;
        }
    }

    private void dificultadMediaDarAyuda() {
        if (dificultad.equals("Media")) {
            juego.getSectorActual().setAyudaRandom(1);
        }
    }

    private boolean getCrowDuelResult() {
        boolean resultado = true;
        AppContext.getInstance().set("juego", juego);
        CrownSelectionController controladorDueloCorona = (CrownSelectionController) FlowController.getInstance().getController("CrownDuelSelector");
        FlowController.getInstance().goViewInWindowModal("CrownDuelSelector", ((Stage) acpRootPane.getScene().getWindow()), false);
        resultado = controladorDueloCorona.getResultado();
        return resultado;
    }

    private void isJugadorInCoronaPos() {//Identifica si un jugador esta en la posicion de corona
        Sector sectorActual = juego.getSectorActual();

        if (sectorActual != null) {
            if (sectorActual.getIsOnCoronaPos()) {
                juego.getSectorActual().setIsOnCoronaPos(false);
                goCoronaDuelView();
                return;
            }
        }
    }

    private void validarCoronasPrimerTurno() {//Valida si un jugador cumple con la regla de ganar 3 coronas en el primer turno
        Sector sectorActual = juego.getSectorActual();
        if ((juego.validarPrimerTurnoObtencionDeCoronas(sectorActual))) {
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Max Coronas alcanzadas", getStage(),
                    "Has ganado 3 coronas en tu primer turno, es momento de pasar el turno.");
        }
    }

    private void cargarValorRespuestaPregunta() {
        this.valorPreguntaRespuesta = (Boolean) AppContext.getInstance().get("valorRespuesta");
    }

    public Juego getJuego() {
        return this.juego;
    }

    private void validarJugadorGanador(AnchorPane anchorPane) {//Valida si un jugador en el juego ha ganado
        juego.valdidarCoronasGanador(anchorPane);
    }

    private void validarTurno() {
        if (this.juego.getRondas() > 1) {
            this.turnoDecidido = true;
        }
    }

    @FXML
    private void OnActionBtnCederTurno(ActionEvent event) {//Cede el turno hacia el siguiente jugador en el juego
        if (dificultad.equals("Facil")) {
            System.out.println("Dificultad Seleccionada: " + dificultad);
            juego.getSectorActual().setAyudaRandom(2);
            juego.cambiarTurno();
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Ojo por ojo", getStage(),
                    "Has cedido el turno, si no tenias ayudas has ganado algunas, ya las veras luego");
        } else {
            juego.cambiarTurno();
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Has decidido cambiar el turno", getStage(),
                    "Has decicido cambiar el turno");
        }
        cargarLabelsPartidaInfo();

    }

}
