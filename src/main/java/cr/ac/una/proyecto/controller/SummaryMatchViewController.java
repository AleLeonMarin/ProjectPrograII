package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Flow;

import cr.ac.una.proyecto.model.Jugador;
import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import javafx.stage.Stage;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SummaryMatchViewController extends Controller implements Initializable {

    @FXML
    private MFXButton btnEdit;

    @FXML
    private MFXButton btnPlay;

    @FXML
    private ImageView imgFicha1;

    @FXML
    private ImageView imgFicha2;

    @FXML
    private ImageView imgFicha3;

    @FXML
    private ImageView imgFicha4;

    @FXML
    private ImageView imgFicha5;

    @FXML
    private ImageView imgFicha6;

    @FXML
    private Label lblDifficulty;

    @FXML
    private Label lblJuagdor1;

    @FXML
    private Label lblJugador2;

    @FXML
    private Label lblJugador3;

    @FXML
    private Label lblJugador4;

    @FXML
    private Label lblJugador5;

    @FXML
    private Label lblJugador6;

    private int cantJugadores;
    private ObservableList<String> nombresJugadores;
    private ObservableList<Jugador> jugadores;
    private ArrayList<Sector> sectores;

    @FXML
    void onActionBtnEdit(ActionEvent event) {

        FlowController.getInstance().goViewInWindow("RegistryNewGame");
        ((Stage) btnEdit.getScene().getWindow()).close();

    }

    @FXML
    void onActionBtnPlay(ActionEvent event) {

        FlowController.getInstance().goMain("tableroView");
        ((Stage) btnPlay.getScene().getWindow()).close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        desactivarJugadores();
        cantJugadores = 0;
        nombresJugadores = FXCollections.observableArrayList();
        jugadores = FXCollections.observableArrayList();
        getCantidaDeJugadoresFromAppContext();
        getJugadoresFromAppContext();
        getSectoresFromAppContext();
        validarJugadores();

    }

    private void getCantidaDeJugadoresFromAppContext() {
        cantJugadores = ((int) AppContext.getInstance().get("cantJugadoresSlider"));
        System.out.println("Cantida de jugadores en SectorSelection: " + cantJugadores);
        // TODO Auto-generated method stub

    }

    private void getJugadoresFromAppContext() {
        // TODO Auto-generated method stub
        jugadores = (ObservableList<Jugador>) AppContext.getInstance().get("jugadores");

        for (Jugador jugador : jugadores) {
            System.out.println("Jugador:" + jugador.getNombre());
            nombresJugadores.add(jugador.getNombre());
        }

    }

    private Jugador buscJugador(String nombreJugador) {
        for (int index = 0; index < cantJugadores; index++) {
            if (jugadores.get(index).getNombre() == nombreJugador) {
                return jugadores.get(index);
            }
        }
        return null;
    }

    private void validarJugadores() {

        String rutaImagenJug1 = sectores.get(0).getRutaImagenJugador();
        String rutaImagenJug2 = sectores.get(1).getRutaImagenJugador();
        
        lblJuagdor1.setText(jugadores.get(0).getNombre());
        imgFicha1.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug1)));
        lblJugador2.setText(jugadores.get(1).getNombre());
        imgFicha2.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug2)));
        
        if (cantJugadores >= 3) {
            lblJugador3.setVisible(true);
            imgFicha3.setVisible(true);
            String rutaImagenJug3 = sectores.get(2).getRutaImagenJugador();
            lblJuagdor1.setText(jugadores.get(0).getNombre());
            imgFicha1.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug1)));
            lblJugador2.setText(jugadores.get(1).getNombre());
            imgFicha2.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug2)));
            lblJugador3.setText(jugadores.get(2).getNombre());
            imgFicha3.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug3)));
        }
        if (cantJugadores >= 4) {
            lblJugador3.setVisible(true);
            imgFicha3.setVisible(true);
            lblJugador4.setVisible(true);
            imgFicha4.setVisible(true);
            String rutaImagenJug3 = sectores.get(2).getRutaImagenJugador();
            String rutaImagenJug4 = sectores.get(3).getRutaImagenJugador();
            lblJuagdor1.setText(jugadores.get(0).getNombre());
            imgFicha1.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug1)));
            lblJugador2.setText(jugadores.get(1).getNombre());
            imgFicha2.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug2)));
            lblJugador3.setText(jugadores.get(2).getNombre());
            imgFicha3.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug3)));
            lblJugador4.setText(jugadores.get(3).getNombre());
            imgFicha4.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug4)));
        }
        if (cantJugadores >= 5) {
            lblJugador3.setVisible(true);
            imgFicha3.setVisible(true);
            lblJugador4.setVisible(true);
            imgFicha4.setVisible(true);
            lblJugador5.setVisible(true);
            imgFicha5.setVisible(true);
            String rutaImagenJug3 = sectores.get(2).getRutaImagenJugador();
            String rutaImagenJug4 = sectores.get(3).getRutaImagenJugador();
            String rutaImagenJug5 = sectores.get(4).getRutaImagenJugador();
            lblJuagdor1.setText(jugadores.get(0).getNombre());
            imgFicha1.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug1)));
            lblJugador2.setText(jugadores.get(1).getNombre());
            imgFicha2.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug2)));
            lblJugador3.setText(jugadores.get(2).getNombre());
            imgFicha3.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug3)));
            lblJugador4.setText(jugadores.get(3).getNombre());
            imgFicha4.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug4)));
            lblJugador5.setText(jugadores.get(4).getNombre());
            imgFicha5.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug5)));
        }
        if (cantJugadores == 6) {
            lblJugador3.setVisible(true);
            imgFicha3.setVisible(true);
            lblJugador4.setVisible(true);
            imgFicha4.setVisible(true);
            lblJugador5.setVisible(true);
            imgFicha5.setVisible(true);
            lblJugador6.setVisible(true);
            imgFicha6.setVisible(true);
            String rutaImagenJug3 = sectores.get(2).getRutaImagenJugador();
            String rutaImagenJug4 = sectores.get(3).getRutaImagenJugador();
            String rutaImagenJug5 = sectores.get(4).getRutaImagenJugador();
            String rutaImagenJug6 = sectores.get(5).getRutaImagenJugador();
            lblJuagdor1.setText(jugadores.get(0).getNombre());
            imgFicha1.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug1)));
            lblJugador2.setText(jugadores.get(1).getNombre());
            imgFicha2.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug2)));
            lblJugador3.setText(jugadores.get(2).getNombre());
            imgFicha3.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug3)));
            lblJugador4.setText(jugadores.get(3).getNombre());
            imgFicha4.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug4)));
            lblJugador5.setText(jugadores.get(4).getNombre());
            imgFicha5.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug5)));
            lblJugador6.setText(jugadores.get(5).getNombre());
            imgFicha6.setImage(new Image(getClass().getResourceAsStream(rutaImagenJug6)));
        }

    }

    private void desactivarJugadores() {
        // Jugador 1
        lblJuagdor1.setVisible(true);
        imgFicha1.setVisible(true);
        // Jugador 2
        lblJugador2.setVisible(true);
        imgFicha2.setVisible(true);
        // Jugador 3
        lblJugador3.setVisible(false);
        imgFicha3.setVisible(false);
        // Jugador 4
        lblJugador4.setVisible(false);
        imgFicha4.setVisible(false);
        // Jugador 5
        lblJugador5.setVisible(false);
        imgFicha5.setVisible(false);
        // Jugador 6
        lblJugador6.setVisible(false);
        imgFicha6.setVisible(false);

    }

    private void getSectoresFromAppContext() {
        // Obtén el ArrayList del AppContext
        sectores = (ArrayList<Sector>) AppContext.getInstance().get("sectores");

        // Imprime para verificar (opcional)
        for (Sector sector : sectores) {
            System.out.println("Sector: " + sector.getRutaImagenJugador());
        }
    }

}