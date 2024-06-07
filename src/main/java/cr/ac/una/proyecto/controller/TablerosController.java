package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Juego;
import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.util.Animacion;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Mensaje;
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

    private ArrayList<String> categoriasRuleta;
    private String categoria;
    private String dificultad;
    private Boolean turnoDecidido;
    private Boolean valorPreguntaRespuesta;
    private Boolean isOnCrown;
    @FXML
    private Label lblRonda;
    @FXML
    private MFXButton btnCederTurno;

    @Override
    public void initialize() {
        iniciarClases();
        cargarSectores();
        juego.cargarAyudasFacil();
        juego.cargarDatosImagenes(grdpTablero);
        this.turnoDecidido = false;
        this.valorPreguntaRespuesta = false;
        cargarLabelsPartidaInfo();
        dificultad = juego.getDificultad();
        this.btnCederTurno.setDisable(true);
        isOnCrown = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void OnMouseClickedPicker(MouseEvent event) {
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

    private void cargarLabelsPartidaInfo() {
        String nombreJugador = sectores.get(juego.getTurnoActual()).getJugador().getNombre();
        Integer rondasJuego = juego.getRondas();
        if (nombreJugador != null) {
            lblJugadorActual.setText(nombreJugador);
        }
        if (rondasJuego > 0 && rondasJuego != null) {
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
        cargarCategoriasRuleta();
    }

    private void calcularTurnos() {

        this.categoria = juego.obtenerPosicionRuleta();
        double anguloDetenido = juego.getRuletaAngulo();

        Runnable onFinish = () ->
        {
            this.imvPicker.setDisable(false);

            if (categoria == categoriasRuleta.get(4)) {
                turnoDecidido = true;
            } else {
                juego.cambiarPrimerTurno();
            }
            if (turnoDecidido) {
                Platform.runLater(() ->
                {
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Escoger turno", getStage(), "Cayo corona, el jugador: "
                            + sectores.get(juego.getTurnoActual()).getJugador().getNombre() + ", inicia la partida");
                });
            }
        };

        animacion.animacionRuleta(imvRuleta, juego.getRuletaAngulo(), onFinish);
    }

    private void moverRuleta() {

        this.categoria = juego.obtenerPosicionRuleta();
        double anguloDetenido = juego.getRuletaAngulo();

        Runnable onFinish = () ->
        {
            juego.setSectorActualAppContext();
            juego.setSectoresAppContext();
            Platform.runLater(() -> mostrarTarjetas());
            this.imvPicker.setDisable(false);
        };

        animacion.animacionRuleta(imvRuleta, anguloDetenido, onFinish);
        AppContext.getInstance().set("preguntaCategoria", categoria);
        AppContext.getInstance().set("preguntaJugador", juego.getJugadorPregunta());
    }

    private void llamarPreguntaView() {
        juego.setSectorActualAppContext();
        FlowController.getInstance().goViewInWindowModal("preguntaView", ((Stage) imvRuleta.getScene().getWindow()), true);
        PreguntaController controladorPreguntaView = (PreguntaController) FlowController.getInstance().getController("preguntaView");
        valorPreguntaRespuesta = controladorPreguntaView.getResultadoRespuestaPregunta();

    }

    private void mostrarTarjetas() {
        if (categoria == categoriasRuleta.get(0)) {
            FlowController.getInstance().goViewInWindowModal("FrontalCardSports", ((Stage) imvRuleta.getScene().getWindow()), true);
        } else if (categoria == categoriasRuleta.get(1)) {
            FlowController.getInstance().goViewInWindowModal("FrontalCardArt", ((Stage) imvRuleta.getScene().getWindow()), true);
        } else if (categoria == categoriasRuleta.get(2)) {
            FlowController.getInstance().goViewInWindowModal("FrontalCardGeografy", ((Stage) imvRuleta.getScene().getWindow()), true);
        } else if (categoria == categoriasRuleta.get(3)) {
            FlowController.getInstance().goViewInWindowModal("FrontalCardScience", ((Stage) imvRuleta.getScene().getWindow()), true);
        } else if (categoria == categoriasRuleta.get(4)) {
            goCoronaDuelView();
            return;

        } else if (categoria == categoriasRuleta.get(5)) {
            FlowController.getInstance().goViewInWindowModal("FrontalCardEntertamient", ((Stage) imvRuleta.getScene().getWindow()), true);
        } else {
            FlowController.getInstance().goViewInWindowModal("FrontalCardHistory", ((Stage) imvRuleta.getScene().getWindow()), true);
        }
        llamarPreguntaView();
        juego.jugar(grdpTablero, valorPreguntaRespuesta);
        setCorona();
        validarJugadorGanador();
        isJugadorInCoronaPos();
        cargarLabelsPartidaInfo();
        this.btnCederTurno.setDisable(false);
    }

    private void goCoronaDuelView() {

        if (getCrowDuelResult()) {
            isOnCrown = true;
            SelectCrownDecisionController controladorCoronaSelection = (SelectCrownDecisionController) FlowController.getInstance().getController("SelectCrownDecisionView");
            FlowController.getInstance().goViewInWindowModal("SelectCrownDecisionView", ((Stage) acpRootPane.getScene().getWindow()), true);
            categoria = controladorCoronaSelection.getResultado();
            AppContext.getInstance().set("preguntaCategoria", categoria);
            mostrarTarjetas();
        } else {
            System.out.println("DUElO");
        }
    }

    private void setCorona() {
        if (isOnCrown) {
            if (valorPreguntaRespuesta) {
                juego.getSectorActual().setEstadoCorona(this.categoria, true);
                juego.getSectorActual().setActualPosInFirst();
                juego.setSectorActualAppContext();
                validarCoronasPrimerTurno();
                this.valorPreguntaRespuesta = false;

            }
            isOnCrown = false;
        }
    }


    private boolean getCrowDuelResult() {

        CrownSelectionController controladorDueloCorona = (CrownSelectionController) FlowController.getInstance().getController("CrownDuelSelector");
        FlowController.getInstance().goViewInWindowModal("CrownDuelSelector", ((Stage) acpRootPane.getScene().getWindow()), true);
        boolean resultado = controladorDueloCorona.getResultado();
        return resultado;
    }

    private void isJugadorInCoronaPos() {
        Sector sectorActual = juego.getSectorActual();

        if (sectorActual != null) {
            if (sectorActual.getIsOnCoronaPos()) {
                juego.getSectorActual().setIsOnCoronaPos(false);
                goCoronaDuelView();
                return;
            }
        }
    }

    private void validarCoronasPrimerTurno() {
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

    private void validarJugadorGanador() {
        juego.valdidarCoronasGanador();
    }

    @FXML
    private void OnActionBtnCederTurno(ActionEvent event) {
        if (dificultad.equals("Facil")) {
            System.out.println("Dificultad Seleccionada: " + dificultad);
            juego.getSectorActual().setAyudaRandom(2);
            juego.getSectorActual().printAyudasInfo();
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
