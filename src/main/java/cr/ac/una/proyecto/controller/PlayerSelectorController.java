package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import java.util.ResourceBundle;
import cr.ac.una.proyecto.model.Juego;
import cr.ac.una.proyecto.model.JugadorDto;
import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.model.Corona;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Mensaje;
import cr.ac.una.proyecto.util.Sound;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PlayerSelectorController extends Controller implements Initializable {

    @FXML
    private ImageView Jugador1;

    @FXML
    private ImageView Jugador2;

    @FXML
    private ImageView Jugador3;

    @FXML
    private ImageView Jugador4;

    @FXML
    private ImageView Jugador5;

    @FXML
    private ImageView Jugador6;

    @FXML
    private MFXButton bntJugador3;

    @FXML
    private MFXButton btnJugador1;

    @FXML
    private MFXButton btnJugador2;

    @FXML
    private MFXButton btnJugador4;

    @FXML
    private MFXButton btnJugador5;

    @FXML
    private MFXButton btnJugador6;

    @FXML
    private AnchorPane root;

    private ObservableList<String> nombreJugadores;
    private ArrayList<Sector> sectores;
    private List<String> jugadoresEnAppContext;
    private List<JugadorDto> jugadores;
    private ArrayList<MFXButton> botonesJugadores;
    private int cantJugadores;
    Juego juego;
    Sound sound;
    private String categoria;
    Sector sector;
    private ArrayList<Corona> coronas;
    int botonesSeleccionados = 1;

    @FXML
    void onActionBtnJugador1(ActionEvent event) {
        verifyCrowns(btnJugador1);
        if (btnJugador1.isPressed()) {

            btnJugador1.setDisable(true);
        }
        botonesSeleccionados++;

    }

    @FXML
    void onActionBtnJugador2(ActionEvent event) {
        verifyCrowns(btnJugador2);
        if (btnJugador2.isPressed()) {
            btnJugador2.setDisable(true);
        }
        botonesSeleccionados++;

    }

    @FXML
    void onActionBtnJugador5(ActionEvent event) {
        verifyCrowns(btnJugador5);

        if (btnJugador5.isPressed()) {
            btnJugador5.setDisable(true);

        }
        botonesSeleccionados++;

    }

    @FXML
    void onActionBtnJugador6(ActionEvent event) {
        verifyCrowns(btnJugador6);

        if (btnJugador6.isPressed()) {
            btnJugador6.setDisable(true);
        }
        botonesSeleccionados++;

    }

    @FXML
    void onActionJugador4(ActionEvent event) {
        verifyCrowns(btnJugador4);

        if (btnJugador4.isPressed()) {
            btnJugador4.setDisable(true);
        }
        botonesSeleccionados++;

    }

    @FXML
    void onActionbtnJugador3(ActionEvent event) {
        verifyCrowns(bntJugador3);

        if (bntJugador3.isPressed()) {
            bntJugador3.setDisable(true);
        }
        botonesSeleccionados++;

    }

    private void getJugadoresFromAppContext() {
        jugadores = (List<JugadorDto>) AppContext.getInstance().get("jugadores");

        for (JugadorDto jugador : jugadores) {
            nombreJugadores.add(jugador.getNombre());
        }

        jugadoresEnAppContext = nombreJugadores;

    }

    private void getCantidadJugadores() {

        cantJugadores = ((int) AppContext.getInstance().get("cantJugadoresSlider"));
    }

    private void getSectores() {
        sectores = (ArrayList<Sector>) AppContext.getInstance().get("sectores");
    }

    private void getJuego() {
        juego = (Juego) AppContext.getInstance().get("juego");
    }

    private void populateButtons() {

        if (juego.getTurnoActual() == 0) {
            btnJugador1.setDisable(true);
            btnJugador1.setVisible(false);
        }
        if (juego.getTurnoActual() == 1) {
            btnJugador2.setDisable(true);
            btnJugador2.setVisible(false);
        }

        String ruta = sectores.get(0).getRutaImagenJugador();
        String ruta2 = sectores.get(1).getRutaImagenJugador();
        Jugador1.setImage(new Image(ruta));
        Jugador2.setImage(new Image(ruta2));
        btnJugador1.setText(jugadoresEnAppContext.get(0));
        btnJugador2.setText(jugadoresEnAppContext.get(1));
        if (cantJugadores >= 3) {
            if (juego.getTurnoActual() == 0) {
                btnJugador1.setDisable(true);
                btnJugador1.setVisible(false);
            }
            if (juego.getTurnoActual() == 1) {
                btnJugador2.setDisable(true);
                btnJugador2.setVisible(false);
            }
            if (juego.getTurnoActual() == 2) {
                bntJugador3.setDisable(true);
                bntJugador3.setVisible(false);
            }

            bntJugador3.setVisible(true);
            bntJugador3.setDisable(false);

            String ruta3 = sectores.get(2).getRutaImagenJugador();
            Jugador3.setImage(new Image(ruta3));
            bntJugador3.setText(jugadoresEnAppContext.get(2));

            Jugador1.setImage(new Image(ruta));
            Jugador2.setImage(new Image(ruta2));

            btnJugador1.setText(jugadoresEnAppContext.get(0));
            btnJugador2.setText(jugadoresEnAppContext.get(1));
        }
        if (cantJugadores >= 4) {
            if (juego.getTurnoActual() == 0) {
                btnJugador1.setDisable(true);
                btnJugador1.setVisible(false);
            }
            if (juego.getTurnoActual() == 1) {
                btnJugador2.setDisable(true);
                btnJugador2.setVisible(false);
            }
            if (juego.getTurnoActual() == 2) {
                bntJugador3.setDisable(true);
                bntJugador3.setVisible(false);
            }
            if (juego.getTurnoActual() == 3) {
                btnJugador4.setDisable(true);
                btnJugador4.setVisible(false);
            }
            bntJugador3.setVisible(true);
            bntJugador3.setDisable(false);
            btnJugador4.setVisible(true);
            btnJugador4.setDisable(false);
            String ruta4 = sectores.get(3).getRutaImagenJugador();
            Jugador4.setImage(new Image(ruta4));
            btnJugador4.setText(jugadoresEnAppContext.get(3));
            String ruta3 = sectores.get(2).getRutaImagenJugador();
            Jugador3.setImage(new Image(ruta3));
            bntJugador3.setText(jugadoresEnAppContext.get(2));
            Jugador1.setImage(new Image(ruta));
            Jugador2.setImage(new Image(ruta2));
            btnJugador1.setText(jugadoresEnAppContext.get(0));
            btnJugador2.setText(jugadoresEnAppContext.get(1));
        }
        if (cantJugadores >= 5) {
            if (juego.getTurnoActual() == 0) {
                btnJugador1.setDisable(true);
                btnJugador1.setVisible(false);
            }
            if (juego.getTurnoActual() == 1) {
                btnJugador2.setDisable(true);
                btnJugador2.setVisible(false);
            }
            if (juego.getTurnoActual() == 2) {
                bntJugador3.setDisable(true);
                bntJugador3.setVisible(false);
            }
            if (juego.getTurnoActual() == 3) {
                btnJugador4.setDisable(true);
                btnJugador4.setVisible(false);
            }
            if (juego.getTurnoActual() == 4) {
                btnJugador5.setDisable(true);
                btnJugador5.setVisible(false);
            }
            bntJugador3.setVisible(true);
            bntJugador3.setDisable(false);
            btnJugador4.setVisible(true);
            btnJugador4.setDisable(false);
            btnJugador5.setVisible(true);
            btnJugador5.setDisable(false);
            String ruta5 = sectores.get(4).getRutaImagenJugador();
            Jugador5.setImage(new Image(ruta5));
            btnJugador5.setText(jugadoresEnAppContext.get(4));
            String ruta4 = sectores.get(3).getRutaImagenJugador();
            Jugador4.setImage(new Image(ruta4));
            btnJugador4.setText(jugadoresEnAppContext.get(3));
            String ruta3 = sectores.get(2).getRutaImagenJugador();
            Jugador3.setImage(new Image(ruta3));
            bntJugador3.setText(jugadoresEnAppContext.get(2));
            Jugador1.setImage(new Image(ruta));
            Jugador2.setImage(new Image(ruta2));
            btnJugador1.setText(jugadoresEnAppContext.get(0));
            btnJugador2.setText(jugadoresEnAppContext.get(1));
        }
        if (cantJugadores >= 6) {
            if (juego.getTurnoActual() == 0) {
                btnJugador1.setDisable(true);
                btnJugador1.setVisible(false);
            }
            if (juego.getTurnoActual() == 1) {
                btnJugador2.setDisable(true);
                btnJugador2.setVisible(false);
            }
            if (juego.getTurnoActual() == 2) {
                bntJugador3.setDisable(true);
                bntJugador3.setVisible(false);
            }
            if (juego.getTurnoActual() == 3) {
                btnJugador4.setDisable(true);
                btnJugador4.setVisible(false);
            }
            if (juego.getTurnoActual() == 4) {
                btnJugador5.setDisable(true);
                btnJugador5.setVisible(false);
            }
            if (juego.getTurnoActual() == 5) {
                btnJugador6.setDisable(true);
                btnJugador6.setVisible(false);
            }
            bntJugador3.setVisible(true);
            bntJugador3.setDisable(false);
            btnJugador4.setVisible(true);
            btnJugador4.setDisable(false);
            btnJugador5.setVisible(true);
            btnJugador5.setDisable(false);
            btnJugador6.setVisible(true);
            btnJugador6.setDisable(false);
            String ruta6 = sectores.get(5).getRutaImagenJugador();
            Jugador6.setImage(new Image(ruta6));
            btnJugador6.setText(jugadoresEnAppContext.get(5));
            String ruta5 = sectores.get(4).getRutaImagenJugador();
            Jugador5.setImage(new Image(ruta5));
            btnJugador5.setText(jugadoresEnAppContext.get(4));
            String ruta4 = sectores.get(3).getRutaImagenJugador();
            Jugador4.setImage(new Image(ruta4));
            btnJugador4.setText(jugadoresEnAppContext.get(3));
            String ruta3 = sectores.get(2).getRutaImagenJugador();
            Jugador3.setImage(new Image(ruta3));
            bntJugador3.setText(jugadoresEnAppContext.get(2));
            Jugador1.setImage(new Image(ruta));
            Jugador2.setImage(new Image(ruta2));
            btnJugador1.setText(jugadoresEnAppContext.get(0));
            btnJugador2.setText(jugadoresEnAppContext.get(1));
        }
    }

    public void desactivarJugadores() {

        btnJugador4.setDisable(true);
        btnJugador5.setDisable(true);
        btnJugador6.setDisable(true);
        bntJugador3.setDisable(true);

        btnJugador4.setVisible(false);
        btnJugador5.setVisible(false);
        btnJugador6.setVisible(false);
        bntJugador3.setVisible(false);

    }

    private void getCrownType() {

        SelectCrownDecisionController controladorCoronaSelection = (SelectCrownDecisionController) FlowController
                .getInstance().getController("SelectCrownDecisionView");
        // sound
        sound.playSound("Card.mp3");
        // flow
        FlowController.getInstance().goViewInWindowModal("SelectCrownDecisionView",
                ((Stage) root.getScene().getWindow()), true);
        categoria = controladorCoronaSelection.getResultado();
        AppContext.getInstance().set("categorias", categoria);

    }

    public String getCategoria() {
        return categoria;

    }

    private void verifyCrowns(MFXButton btn) {
        int i = botonesJugadores.indexOf(btn);
        System.out.println(i);

        if (btn.getText().equals(nombreJugadores.get(i))) {
            sector = sectores.get(i);
            List<Corona> coronas = sector.getCoronas();

            if (coronas != null && !coronas.isEmpty()) {
                boolean jugadorTieneCorona = false;

                for (Corona corona : coronas) {
                    if (corona != null && corona.getEstado()) {
                        jugadorTieneCorona = true;
                        getCrownType();
                    }
                }

                if (!jugadorTieneCorona) {

                    new Mensaje().showModal(Alert.AlertType.ERROR, "Obtención de coronas", getStage(),
                            "Este jugador no tiene coronas");

                    // Si este es el último botón seleccionado y ningún jugador tiene coronas
                    if (botonesSeleccionados == cantJugadores - 1) {
                        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Obtención de coronas", getStage(),
                                "Pierdes el turno, ya que no hay duelo de coronas");
                        ((Stage) root.getScene().getWindow()).close();
                        juego.cambiarTurno();
                    }
                }
            }
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error", getStage(), "Error inesperado");
        }
    }

    private void buttons() {

        botonesJugadores = new ArrayList<>();
        botonesJugadores.add(btnJugador1);
        botonesJugadores.add(btnJugador2);
        botonesJugadores.add(bntJugador3);
        botonesJugadores.add(btnJugador4);
        botonesJugadores.add(btnJugador5);
        botonesJugadores.add(btnJugador6);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        juego = new Juego();
        sector = new Sector();
        sound = new Sound();
        cantJugadores = 0;
        nombreJugadores = FXCollections.observableArrayList();
        sectores = new ArrayList<>();
        desactivarJugadores();
        buttons();
        getCantidadJugadores();
        getJugadoresFromAppContext();
        getSectores();
        getJuego();
        populateButtons();

    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub

    }

}
