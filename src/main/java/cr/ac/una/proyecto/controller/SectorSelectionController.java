package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.JugadorDto;
import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Mensaje;
import cr.ac.una.proyecto.util.Sound;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class SectorSelectionController extends Controller implements Initializable {

    private int cantJugadores;
    private ObservableList<String> nombresJugadores;
    private List<JugadorDto> jugadores;
    private List<MFXComboBox<String>> botonesCmbBox;

    @FXML
    private MFXComboBox<String> cmbSector1;
    @FXML
    private MFXComboBox<String> cmbSector2;
    @FXML
    private MFXComboBox<String> cmbSector3;
    @FXML
    private MFXComboBox<String> cmbSector4;
    @FXML
    private MFXComboBox<String> cmbSector5;
    @FXML
    private MFXComboBox<String> cmbSector6;
    @FXML
    private MFXButton btnNext;

    Sound sound = new Sound();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void initialize() {
        initializeComponents();
    }

    private void initializeComponents() {
        cantJugadores = 0;
        nombresJugadores = FXCollections.observableArrayList();
        jugadores = new ArrayList<>();
        botonesCmbBox = Arrays.asList(cmbSector1, cmbSector2, cmbSector3, cmbSector4, cmbSector5, cmbSector6);

        desactivarBotones();
        obtenerDatosDesdeAppContext();
        cargarNombresJugadores();
        configurarSectores();
    }

    @FXML
    private void onActionBtnNext(ActionEvent event) {

        sound.playSound("clickedNext.mp3");
        if (!validarBotones()) {
            crearSectores(cantJugadores);
            FlowController.getInstance().goViewInWindow("PawnSelectionView");
            ((Stage) btnNext.getScene().getWindow()).close();
        }
    }

    private void desactivarBotones() {
        for (int index = 2; index < botonesCmbBox.size(); index++) {
            botonesCmbBox.get(index).setDisable(true);
            botonesCmbBox.get(index).setVisible(false);
        }
    }

    private void configurarSectores() {
        for (int index = 0; index < cantJugadores; index++) {
            MFXComboBox<String> comboBox = botonesCmbBox.get(index);
            comboBox.setDisable(false);
            comboBox.setVisible(true);
            comboBox.setItems(nombresJugadores);
        }
    }

    private void obtenerDatosDesdeAppContext() {
        cantJugadores = (Integer) AppContext.getInstance().get("cantJugadoresSlider");
        jugadores = (List<JugadorDto>) AppContext.getInstance().get("jugadores");
    }

    private void cargarNombresJugadores() {
        jugadores.forEach(jugador -> nombresJugadores.add(jugador.getNombre()));
    }

    private boolean validarBotones() {//Valida si hay sectores que no se hayan seleccionados y que todos los jugadores vayan en sectores separados.
        Set<String> selectedNames = new HashSet<>();

        for (MFXComboBox<String> comboBox : botonesCmbBox) {
            if (comboBox.isVisible()) {
                String selected = comboBox.getValue();

                if (selected == null || comboBox.getSelectedItem() == null) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Todos los jugadores deben seleccionar un sector");
                    return true;
                }

                if (!selectedNames.add(selected)) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Los jugadores deben ir en sectores separados");
                    return true;
                }
            }
        }

        return false;
    }

    private JugadorDto buscarJugador(String nombreJugador) {//Busca el jugador por nombre dentro de la lista de jugadores y lo retorna.
        return jugadores.stream().filter(jugador -> jugador.getNombre().equals(nombreJugador)).findFirst().orElse(null);
    }

    private void crearSectores(int cantidadJugadores) {// crea sectores,asgina posiciones y dirrecion por defecto segun la cantidad de jugadores.

        ArrayList<Sector> sectores;
        sectores = new ArrayList<>();
        if (cantidadJugadores == 2) {

            int playerOnePositionY = 0;
            int playerOnePositionX = 0;

            int playerTwoPositionY = 3;
            int playerTwoPositionX = 3;

            Sector sector1 = new Sector(buscarJugador(cmbSector1.getValue()), playerOnePositionX, playerOnePositionY, 1, "");
            Sector sector2 = new Sector(buscarJugador(cmbSector2.getValue()), playerTwoPositionX, playerTwoPositionY, 2, "");

            sectores.add(sector1);
            sectores.add(sector2);
        }

        if (cantidadJugadores == 3) {
            int playerOnePositionX = 0;
            int playerOnePositionY = 3;

            int playerTwoPositionX = 4;
            int playerTwoPositionY = 3;

            int playerThreePositionX = 3;
            int playerThreePositionY = 0;

            Sector sector1 = new Sector(buscarJugador(cmbSector1.getValue()), playerOnePositionX, playerOnePositionY, 3, "");
            Sector sector2 = new Sector(buscarJugador(cmbSector2.getValue()), playerTwoPositionX, playerTwoPositionY, 2, "");
            Sector sector3 = new Sector(buscarJugador(cmbSector3.getValue()), playerThreePositionX, playerThreePositionY, 4, "");

            sectores.addAll(Arrays.asList(sector1, sector2, sector3));
        }
        if (cantidadJugadores == 4) {

            int playerOnePositionX = 0;
            int playerOnePositionY = 1;

            int playerTwoPositionX = 1;
            int playerTwoPositionY = 4;

            int playerThreePositionX = 4;
            int playerThreePositionY = 3;

            int playerFourPositionX = 3;
            int playerFourPositionY = 0;

            Sector sector1 = new Sector(buscarJugador(cmbSector1.getValue()), playerOnePositionX, playerOnePositionY, 1, "");
            Sector sector2 = new Sector(buscarJugador(cmbSector2.getValue()), playerTwoPositionX, playerTwoPositionY, 3, "");
            Sector sector3 = new Sector(buscarJugador(cmbSector3.getValue()), playerThreePositionX, playerThreePositionY, 2, "");
            Sector sector4 = new Sector(buscarJugador(cmbSector4.getValue()), playerFourPositionX, playerFourPositionY, 4, "");

            sectores.addAll(Arrays.asList(sector1, sector2, sector3, sector4));

        }
        if (cantidadJugadores == 5) {
            int playerOnePositionY = 7;
            int playerOnePositionX = 5;

            int playerTwoPositionY = 3;
            int playerTwoPositionX = 5;

            int playerThreePositionY = 0;
            int playerThreePositionX = 4;

            int playerFourPositionY = 1;
            int playerFourPositionX = 1; 

            int playerFivePositionY = 7;
            int playerFivePositionX = 1;

            Sector sector1 = new Sector(buscarJugador(cmbSector1.getValue()), playerOnePositionX, playerOnePositionY, 2,
                    "");
            Sector sector2 = new Sector(buscarJugador(cmbSector2.getValue()), playerTwoPositionX, playerTwoPositionY, 2,
                    "");
            Sector sector3 = new Sector(buscarJugador(cmbSector3.getValue()), playerThreePositionX, playerThreePositionY, 4,
                    "");
            Sector sector4 = new Sector(buscarJugador(cmbSector4.getValue()), playerFourPositionX, playerFourPositionY, 1,
                    "");
            Sector sector5 = new Sector(buscarJugador(cmbSector5.getValue()), playerFivePositionX, playerFivePositionY, 3,
                    "");
            sectores.addAll(Arrays.asList(sector1, sector2, sector3, sector4, sector5));
        }
        if (cantidadJugadores == 6) {

            int playerOnePositionY = 0;
            int playerOnePositionX = 0;

            int playerTwoPositionY = 4;
            int playerTwoPositionX = 0;

            int playerThreePositionY = 7;
            int playerThreePositionX = 1;

            int playerFourPositionY = 7;
            int playerFourPositionX = 5;

            int playerFivePositionY = 3;
            int playerFivePositionX = 5;

            int playerSixPositionY = 0;
            int playerSixPositionX = 4;

            Sector sector1 = new Sector(buscarJugador(cmbSector1.getValue()), playerOnePositionX, playerOnePositionY, 1,
                    "");
            Sector sector2 = new Sector(buscarJugador(cmbSector2.getValue()), playerTwoPositionX, playerTwoPositionY, 1,
                    "");
            Sector sector3 = new Sector(buscarJugador(cmbSector3.getValue()), playerThreePositionX, playerThreePositionY, 3,
                    "");
            Sector sector4 = new Sector(buscarJugador(cmbSector4.getValue()), playerFourPositionX, playerFourPositionY, 2,
                    "");
            Sector sector5 = new Sector(buscarJugador(cmbSector5.getValue()), playerFivePositionX, playerFivePositionY, 2,
                    "");
            Sector sector6 = new Sector(buscarJugador(cmbSector6.getValue()), playerSixPositionX, playerSixPositionY, 4,
                    "");
            sectores.addAll(Arrays.asList(sector1, sector2, sector3, sector4, sector5, sector6));
        }
        AppContext.getInstance().set("sectores", sectores);
    }

}
