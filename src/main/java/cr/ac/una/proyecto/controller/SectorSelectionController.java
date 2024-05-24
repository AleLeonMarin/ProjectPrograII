package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Jugador;
import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Justin Mendez y Alejandro Leon
 */
public class SectorSelectionController extends Controller implements Initializable {

    private int cantJugadores;
    private ObservableList<String> nombresJugadores;
    private ObservableList<Jugador> jugadores;
    private ArrayList<MFXComboBox> botonesCmbBox;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void initialize() {
        initPrincipalValues();
    }

    private void initPrincipalValues() {
        cantJugadores = 0;
        nombresJugadores = FXCollections.observableArrayList();
        jugadores = FXCollections.observableArrayList();
        botonesCmbBox = new ArrayList<>();

        desactivarBotones();
        getCantidaDeJugadoresFromAppContext();
        getJugadoresFromAppContext();
        validarCantidadSectores();
    }

    @FXML
    private void onActionBtnNext(ActionEvent event) {
        crearSectores(cantJugadores);
        if (!validarBotones())
        {
            FlowController.getInstance().goViewInWindow("PawnSelectionView");
            ((Stage) btnNext.getScene().getWindow()).close();
        }
    }

    private void desactivarBotones() {
        cmbSector3.setDisable(true);
        cmbSector4.setDisable(true);
        cmbSector5.setDisable(true);
        cmbSector6.setDisable(true);

        cmbSector3.setVisible(false);
        cmbSector4.setVisible(false);
        cmbSector5.setVisible(false);
        cmbSector6.setVisible(false);

    }

    private void validarCantidadSectores() {// se puede hacer mas generico

        cmbSector1.setItems(nombresJugadores);
        cmbSector2.setItems(nombresJugadores);
        botonesCmbBox.add(cmbSector1);
        botonesCmbBox.add(cmbSector2);

        if (cantJugadores >= 3)
        {
            cmbSector3.setDisable(false);
            cmbSector3.setVisible(true);
            cmbSector3.setItems(nombresJugadores);
            botonesCmbBox.add(cmbSector3);
        }

        if (cantJugadores >= 4)
        {
            cmbSector4.setDisable(false);
            cmbSector4.setVisible(true);
            cmbSector4.setItems(nombresJugadores);
            botonesCmbBox.add(cmbSector4);
        }

        if (cantJugadores >= 5)
        {
            cmbSector5.setDisable(false);
            cmbSector5.setVisible(true);
            cmbSector5.setItems(nombresJugadores);
            botonesCmbBox.add(cmbSector5);
        }

        if (cantJugadores == 6)
        {
            cmbSector6.setDisable(false);
            cmbSector6.setVisible(true);
            cmbSector6.setItems(nombresJugadores);
            botonesCmbBox.add(cmbSector6);
        }

    }

    private void getCantidaDeJugadoresFromAppContext() {
        cantJugadores = ((int) AppContext.getInstance().get("cantJugadoresSlider"));
    }

    private void getJugadoresFromAppContext() {
        jugadores = (ObservableList<Jugador>) AppContext.getInstance().get("jugadores");

        for (Jugador jugador : jugadores)
        {
            nombresJugadores.add(jugador.getNombre());
        }

    }

    private boolean validarBotones() {
        List<String> selectedCharacters = new ArrayList<>();

        for (MFXComboBox button : botonesCmbBox)
        {
            if (button.getValue() != null)
            {
                selectedCharacters.add((String) button.getValue());
            }
        }

        if (!(selectedCharacters.stream().distinct().count() == cantJugadores))
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Los jugadores deben ir en sectores separados");
            return true;
        } else
        {
            return false;
        }
    }

    private Jugador buscarJugador(String nombreJugador) {

        for (int index = 0; index < cantJugadores; index++)
        {
            if (jugadores.get(index).getNombre() == nombreJugador)
            {
                return jugadores.get(index);
            }
        }
        return null;

    }

    private void crearSectores(int cantidadJugadores) {// manejarLogicade inicio de sectores en tableros

        ArrayList<Sector> sectores;
        sectores = new ArrayList<>();
        if (cantidadJugadores == 2)
        {

            int playerOnePositionY = 0;
            int playerOnePositionX = 0;
            int playerOneCurrentPosition = 0;

            int playerTwoPositionY = 3;
            int playerTwoPositionX = 3;
            int playerTwoCurrentPosition = 3;

            Sector sector1 = new Sector(buscarJugador(cmbSector1.getValue()), playerOnePositionX, playerOnePositionY,
                    playerOneCurrentPosition, 1, "");
            Sector sector2 = new Sector(buscarJugador(cmbSector2.getValue()), playerTwoPositionX, playerTwoPositionY,
                    playerTwoCurrentPosition, 2, "");

            sectores.add(sector1);
            sectores.add(sector2);
        }

        if (cantidadJugadores == 3)// crear los demas sectores con la informacion de posiciones segun tablero
        {
            int playerOnePositionY = 3;
            int playerOnePositionX = 4;

            int playerTwoPositionY = 0;
            int playerTwoPositionX = 3;

            int playerThreePositionY = 3;
            int playerThreePositionX = 0;

            Sector sector1 = new Sector(buscarJugador(cmbSector1.getValue()), playerOnePositionX, playerOnePositionY, 1,
                    "");
            Sector sector2 = new Sector(buscarJugador(cmbSector2.getValue()), playerTwoPositionX, playerTwoPositionY, 2,
                    "");
            Sector sector3 = new Sector(buscarJugador(cmbSector3.getValue()), playerThreePositionX,
                    playerThreePositionY, 3, "");

            sectores.addAll(Arrays.asList(sector1, sector2, sector3));
        }
        if (cantidadJugadores == 4)
        {

            int playerOnePositionY = 3;
            int playerOnePositionX = 4;

            int playerTwoPositionY = 0;
            int playerTwoPositionX = 3;

            int playerThreePositionY = 1;
            int playerThreePositionX = 0;

            int playerFourPositionY = 4;
            int playerFourPositionX = 1;

            Sector sector1 = new Sector(buscarJugador(cmbSector1.getValue()), playerOnePositionX, playerOnePositionY, 1,
                    "");
            Sector sector2 = new Sector(buscarJugador(cmbSector2.getValue()), playerTwoPositionX, playerTwoPositionY, 2,
                    "");
            Sector sector3 = new Sector(buscarJugador(cmbSector3.getValue()), playerThreePositionX,
                    playerThreePositionY, 3, "");
            Sector sector4 = new Sector(buscarJugador(cmbSector4.getValue()), playerFourPositionX, playerFourPositionY,
                    4, "");
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

}
