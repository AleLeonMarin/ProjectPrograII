package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Jugador;
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
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class SectorSelectionController extends Controller implements Initializable {

    private int cantJugadores;
    private ObservableList<String> nombresJugadores;
    private ObservableList<Jugador> jugadores;
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
        jugadores = FXCollections.observableArrayList();
        botonesCmbBox = Arrays.asList(cmbSector1, cmbSector2, cmbSector3, cmbSector4, cmbSector5, cmbSector6);

        desactivarBotones();
        obtenerDatosDesdeAppContext();
        cargarNombresJugadores();
        configurarSectores();
    }

    @FXML
    private void onActionBtnNext(ActionEvent event) {

        sound.playSound("src/main/resources/cr/ac/una/proyecto/resources/audio/clickedNext.mp3");
        if (!validarBotones())
        {
            crearSectores(cantJugadores);
            FlowController.getInstance().goViewInWindow("PawnSelectionView");
            ((Stage) btnNext.getScene().getWindow()).close();
        }
    }

    private void desactivarBotones() {
        for (int index = 2; index < botonesCmbBox.size(); index++)
        {
            botonesCmbBox.get(index).setDisable(true);
            botonesCmbBox.get(index).setVisible(false);
        }
    }

    private void configurarSectores() {
        for (int index = 0; index < cantJugadores; index++)
        {
            MFXComboBox<String> comboBox = botonesCmbBox.get(index);
            comboBox.setDisable(false);
            comboBox.setVisible(true);
            comboBox.setItems(nombresJugadores);
        }
    }

    private void obtenerDatosDesdeAppContext() {
        cantJugadores = (Integer) AppContext.getInstance().get("cantJugadoresSlider");
        jugadores = (ObservableList<Jugador>) AppContext.getInstance().get("jugadores");
    }

    private void cargarNombresJugadores() {
        jugadores.forEach(jugador -> nombresJugadores.add(jugador.getNombre()));
    }

    private boolean validarBotones() {
        Set<String> selectedNames = new HashSet<>();

        for (MFXComboBox<String> comboBox : botonesCmbBox)
        {
            if (comboBox.isVisible())
            {
                String selected = comboBox.getValue();

                if (selected == null || comboBox.getSelectedItem() == null)
                {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Todos los jugadores deben seleccionar un sector");
                    return true;
                }

                if (!selectedNames.add(selected))
                {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Los jugadores deben ir en sectores separados");
                    return true;
                }
            }
        }

        return false;
    }

    private Jugador buscarJugador(String nombreJugador) {
        return jugadores.stream().filter(jugador -> jugador.getNombre().equals(nombreJugador)).findFirst().orElse(null);
    }

    private void crearSectores(int cantidadJugadores) {// manejarLogicade inicio de sectores en tableros

        ArrayList<Sector> sectores;
        sectores = new ArrayList<>();
        if (cantidadJugadores == 2)
        {

            int playerOnePositionY = 0;
            int playerOnePositionX = 0;

            int playerTwoPositionY = 3;
            int playerTwoPositionX = 3;

            Sector sector1 = new Sector(buscarJugador(cmbSector1.getValue()), playerOnePositionX, playerOnePositionY, 1, "");
            Sector sector2 = new Sector(buscarJugador(cmbSector2.getValue()), playerTwoPositionX, playerTwoPositionY, 2, "");

            sectores.add(sector1);
            sectores.add(sector2);
        }

        if (cantidadJugadores == 3)
        {
            int playerOnePositionY = 3;
            int playerOnePositionX = 4;

            int playerTwoPositionY = 0;
            int playerTwoPositionX = 3;

            int playerThreePositionY = 3;
            int playerThreePositionX = 0;

            Sector sector1 = new Sector(buscarJugador(cmbSector1.getValue()), playerOnePositionX, playerOnePositionY, 2, "");
            Sector sector2 = new Sector(buscarJugador(cmbSector2.getValue()), playerTwoPositionX, playerTwoPositionY, 4, "");
            Sector sector3 = new Sector(buscarJugador(cmbSector3.getValue()), playerThreePositionX, playerThreePositionY, 3, "");

            sectores.addAll(Arrays.asList(sector1, sector2, sector3));
        }
        if (cantidadJugadores == 4)
        {

            int playerOnePositionY = 1;
            int playerOnePositionX = 0;

            int playerTwoPositionY = 1;
            int playerTwoPositionX = 4;

            int playerThreePositionY = 3;
            int playerThreePositionX = 4;

            int playerFourPositionY = 3;
            int playerFourPositionX = 0;

            Sector sector1 = new Sector(buscarJugador(cmbSector1.getValue()), playerOnePositionX, playerOnePositionY, 1, "");
            Sector sector2 = new Sector(buscarJugador(cmbSector2.getValue()), playerTwoPositionY, playerTwoPositionX, 3, "");
            Sector sector3 = new Sector(buscarJugador(cmbSector3.getValue()), playerThreePositionX, playerThreePositionY, 2, "");
            Sector sector4 = new Sector(buscarJugador(cmbSector4.getValue()), playerFourPositionY, playerFourPositionX, 4, "");

            sectores.addAll(Arrays.asList(sector1, sector2, sector3, sector4));

        }
        if (cantidadJugadores == 5)
        {
            int playerOnePositionY = 6;
            int playerOnePositionX = 4;

            int playerTwoPositionY = 2;
            int playerTwoPositionX = 4;

            int playerThreePositionY = 0;
            int playerThreePositionX = 2;

            int playerFourPositionY = 2;
            int playerFourPositionX = 0;

            int playerFivePositionY = 6;
            int playerFivePositionX = 0;

            Sector sector1 = new Sector(buscarJugador(cmbSector1.getValue()), playerOnePositionX, playerOnePositionY, 1,
                    "");
            Sector sector2 = new Sector(buscarJugador(cmbSector2.getValue()), playerTwoPositionX, playerTwoPositionY, 2,
                    "");
            Sector sector3 = new Sector(buscarJugador(cmbSector3.getValue()), playerThreePositionX,
                    playerThreePositionY, 3, "");
            Sector sector4 = new Sector(buscarJugador(cmbSector4.getValue()), playerFourPositionX, playerFourPositionY,
                    4, "");

            Sector sector5 = new Sector(buscarJugador(cmbSector5.getValue()), playerFivePositionX, playerFivePositionY,
                    5, "");
            sectores.addAll(Arrays.asList(sector1, sector2, sector3, sector4, sector5));

        }
        if (cantidadJugadores == 6)
        {

            int playerOnePositionY = 7;
            int playerOnePositionX = 5;

            int playerTwoPositionY = 3;
            int playerTwoPositionX = 5;

            int playerThreePositionY = 0;
            int playerThreePositionX = 4;

            int playerFourPositionY = 0;
            int playerFourPositionX = 0;

            int playerFivePositionY = 4;
            int playerFivePositionX = 0;

            int playerSixPositionY = 7;
            int playerSixPositionX = 1;

            Sector sector1 = new Sector(buscarJugador(cmbSector1.getValue()), playerOnePositionX, playerOnePositionY, 1,
                    "");
            Sector sector2 = new Sector(buscarJugador(cmbSector2.getValue()), playerTwoPositionX, playerTwoPositionY, 2,
                    "");
            Sector sector3 = new Sector(buscarJugador(cmbSector3.getValue()), playerThreePositionX,
                    playerThreePositionY, 3, "");
            Sector sector4 = new Sector(buscarJugador(cmbSector4.getValue()), playerFourPositionX, playerFourPositionY,
                    4, "");
            Sector sector5 = new Sector(buscarJugador(cmbSector5.getValue()), playerFivePositionX, playerFivePositionY,
                    5, "");
            Sector sector6 = new Sector(buscarJugador(cmbSector6.getValue()), playerSixPositionX, playerSixPositionY, 6,
                    "");
            sectores.addAll(Arrays.asList(sector1, sector2, sector3, sector4, sector5, sector6));
        }
        AppContext.getInstance().set("sectores", sectores);
    }

//    private int[][] obtenerPosicionesIniciales(int cantidadJugadores) {
//        switch (cantidadJugadores) {
//            case 2:
//                return new int[][]{{0, 0}, {3, 3}};//sector uno y dos posiciones tablero dos jugadores
//            case 3:
//                return new int[][]{{4, 3}, {3, 0}, {0, 3}};//sector uno ,dos,tres posiciones tablero 3 jugadores
//            case 4:
//                return new int[][]{{4, 3}, {3, 0}, {0, 1}, {1, 4}};
//            case 5:
//                return new int[][]{{4, 6}, {4, 2}, {2, 0}, {0, 2}, {0, 6}};
//            case 6:
//                return new int[][]{{5, 7}, {5, 3}, {4, 0}, {0, 0}, {0, 4}, {1, 7}};//sector uno ,dos,tres,4,5,6 posiciones tablero 6 jugadores
//            default:
//                return new int[0][0];
//        }
//    }
//         private void crearSectores(int cantidadJugadores) {//se ocupa saber la dirrecion de los sectores, reeplanter la idea de como moverse en el tablero
//        List<Sector> sectores = new ArrayList<>();
//        int[][] posiciones = obtenerPosicionesIniciales(cantidadJugadores);
//
//        for (int index = 0; index < cantidadJugadores; index++)
//        {
//            MFXComboBox<String> comboBox = botonesCmbBox.get(index);
//            Jugador jugador = buscarJugador(comboBox.getValue());
//            int x = posiciones[index][0];
//            int y = posiciones[index][1];
//            int posicionActual = index + 1;
//            sectores.add(new Sector(jugador, x, y, posicionActual, posicionActual, ""));
//        }
//
//        AppContext.getInstance().set("sectores", sectores);
//    }
}
