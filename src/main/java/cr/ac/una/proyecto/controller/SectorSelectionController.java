package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Jugador;
import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
    private Button btnConfirmar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void initialize() {
        cantJugadores = 0;
        nombresJugadores = FXCollections.observableArrayList();
        jugadores = FXCollections.observableArrayList();
        botonesCmbBox = new ArrayList<>();
        desactivarBotones();
        getCantidaDeJugadoresFromAppContext();
        getJugadoresFromAppContext();
        validarCantidadSectores();

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

    private void validarCantidadSectores() {

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
        System.out.println("Cantida de jugadores en SectorSelection: " + cantJugadores);
    }

    private void getJugadoresFromAppContext() {
        jugadores = (ObservableList<Jugador>) AppContext.getInstance().get("jugadores");

        for (Jugador jugador : jugadores)
        {
            System.out.println("Jugador:" + jugador.getNombre());
            nombresJugadores.add(jugador.getNombre());
        }

    }

    @FXML
    private void onActionBtnConfirmar(ActionEvent event) {
        validarBotones();

    }

    private void validarBotones() {
        List<String> selectedCharacters = new ArrayList<>();

        for (MFXComboBox button : botonesCmbBox)
        {
            if (button.getValue() != null)
            {
                selectedCharacters.add((String) button.getValue());
            }
        }

        // Verificar que todos los personajes sean únicos
        if (selectedCharacters.stream().distinct().count() == cantJugadores)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Selección Completa");
            alert.setHeaderText(null);
            alert.setContentText("Todos los jugadores han seleccionado personajes únicos.");
            alert.showAndWait();
        } else
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error en la selección");
            alert.setHeaderText(null);
            alert.setContentText("Cada jugador debe seleccionar un sector único.");
            alert.showAndWait();
        }
    }

    private void crearSectores(int cantidadJugadores) {//manejarLogicade inicio de sectores en tableros

        ObservableList<Sector> sectores;
        sectores = FXCollections.observableArrayList();

        int playerOnePositionY = 0;
        int playerOnePositionX = 0;
        int playerOneCurrentPosition = 0;

        int playerTwoPositionY = 3;
        int playerTwoPositionX = 3;
        int playerTwoCurrentPosition = 3;

        String rutaPeonRojo = "/cr/ac/una/proyecto/resources/PeonRojo.png";

        System.out.println("DAtos entrada: " + cantJugadores);

        Sector sector1 = new Sector(buscarJugador(cmbSector1.getValue()), playerOnePositionX, playerOnePositionY, playerOneCurrentPosition, 1, rutaPeonRojo);
        Sector sector2 = new Sector(buscarJugador(cmbSector2.getValue()), playerTwoPositionX, playerTwoPositionY, playerTwoCurrentPosition, 2, rutaPeonRojo);

        System.out.println(sector1.toString());
        System.out.println(sector2.toString());

        sectores.add(sector1);
        sectores.add(sector2);

//        if (cantidadJugadores >= 3)
//        {
//            sectores.add(sector3);
//        }
//        if (cantidadJugadores >= 4)
//        {
//            sectores.add(sector4);
//        }
//        if (cantidadJugadores >= 5)
//        {
//            sectores.add(sector5);
//        }
//        if (cantidadJugadores >= 6)
//        {
//            sectores.add(sector6);
//        }
        AppContext.getInstance().set("sectores", sectores);
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

    @FXML
    private void onActionSiguiente(ActionEvent event) {
        crearSectores(cantJugadores);
        FlowController.getInstance().goMain("tableroView");
        ((Stage) btnConfirmar.getScene().getWindow()).close();
    }

}
