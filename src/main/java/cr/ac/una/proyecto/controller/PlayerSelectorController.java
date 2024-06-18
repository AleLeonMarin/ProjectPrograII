package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;

import java.util.Objects;
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
   private boolean dueloResult;
    @FXML
    void onActionBtnJugador1(ActionEvent event) {
        verifyCrowns(btnJugador1);
        btnJugador1.setDisable(true);
        botonesSeleccionados++;

    }

    @FXML
    void onActionBtnJugador2(ActionEvent event) {
        verifyCrowns(btnJugador2);

        btnJugador2.setDisable(true);

        botonesSeleccionados++;

    }

    @FXML
    void onActionBtnJugador5(ActionEvent event) {
        verifyCrowns(btnJugador5);
        btnJugador5.setDisable(true);
        botonesSeleccionados++;

    }

    @FXML
    void onActionBtnJugador6(ActionEvent event) {
        verifyCrowns(btnJugador6);

        btnJugador6.setDisable(true);

        botonesSeleccionados++;

    }

    @FXML
    void onActionJugador4(ActionEvent event) {
        verifyCrowns(btnJugador4);

        btnJugador4.setDisable(true);

        botonesSeleccionados++;

    }

    @FXML
    void onActionbtnJugador3(ActionEvent event) {
        verifyCrowns(bntJugador3);

        bntJugador3.setDisable(true);

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

//    private void populateButtons() {
//
//        if (juego.getTurnoActual() == 0) {
//            btnJugador1.setDisable(true);
//            btnJugador1.setVisible(false);
//        }
//        if (juego.getTurnoActual() == 1) {
//            btnJugador2.setDisable(true);
//            btnJugador2.setVisible(false);
//        }
//
//        String ruta = sectores.get(0).getRutaImagenJugador();
//        String ruta2 = sectores.get(1).getRutaImagenJugador();
//        Jugador1.setImage(new Image(getClass().getResourceAsStream(ruta)));
//        Jugador2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(ruta2))));
//
//        btnJugador1.setText(jugadoresEnAppContext.get(0));
//        btnJugador2.setText(jugadoresEnAppContext.get(1));
//        if (cantJugadores >= 3) {
//            if (juego.getTurnoActual() == 0) {
//                btnJugador1.setDisable(true);
//                btnJugador1.setVisible(false);
//            }
//            if (juego.getTurnoActual() == 1) {
//                btnJugador2.setDisable(true);
//                btnJugador2.setVisible(false);
//            }
//            if (juego.getTurnoActual() == 2) {
//                bntJugador3.setDisable(true);
//                bntJugador3.setVisible(false);
//            }
//
//            bntJugador3.setVisible(true);
//            bntJugador3.setDisable(false);
//
//            String ruta3 = sectores.get(2).getRutaImagenJugador();
//            Jugador3.setImage(new Image(ruta3));
//            bntJugador3.setText(jugadoresEnAppContext.get(2));
//
//            Jugador1.setImage(new Image(ruta));
//            Jugador2.setImage(new Image(ruta2));
//
//            btnJugador1.setText(jugadoresEnAppContext.get(0));
//            btnJugador2.setText(jugadoresEnAppContext.get(1));
//        }
//        if (cantJugadores >= 4) {
//            if (juego.getTurnoActual() == 0) {
//                btnJugador1.setDisable(true);
//                btnJugador1.setVisible(false);
//            }
//            if (juego.getTurnoActual() == 1) {
//                btnJugador2.setDisable(true);
//                btnJugador2.setVisible(false);
//            }
//            if (juego.getTurnoActual() == 2) {
//                bntJugador3.setDisable(true);
//                bntJugador3.setVisible(false);
//            }
//            if (juego.getTurnoActual() == 3) {
//                btnJugador4.setDisable(true);
//                btnJugador4.setVisible(false);
//            }
//            bntJugador3.setVisible(true);
//            bntJugador3.setDisable(false);
//            btnJugador4.setVisible(true);
//            btnJugador4.setDisable(false);
//            String ruta4 = sectores.get(3).getRutaImagenJugador();
//            Jugador4.setImage(new Image(ruta4));
//            btnJugador4.setText(jugadoresEnAppContext.get(3));
//            String ruta3 = sectores.get(2).getRutaImagenJugador();
//            Jugador3.setImage(new Image(ruta3));
//            bntJugador3.setText(jugadoresEnAppContext.get(2));
//            Jugador1.setImage(new Image(ruta));
//            Jugador2.setImage(new Image(ruta2));
//            btnJugador1.setText(jugadoresEnAppContext.get(0));
//            btnJugador2.setText(jugadoresEnAppContext.get(1));
//        }
//        if (cantJugadores >= 5) {
//            if (juego.getTurnoActual() == 0) {
//                btnJugador1.setDisable(true);
//                btnJugador1.setVisible(false);
//            }
//            if (juego.getTurnoActual() == 1) {
//                btnJugador2.setDisable(true);
//                btnJugador2.setVisible(false);
//            }
//            if (juego.getTurnoActual() == 2) {
//                bntJugador3.setDisable(true);
//                bntJugador3.setVisible(false);
//            }
//            if (juego.getTurnoActual() == 3) {
//                btnJugador4.setDisable(true);
//                btnJugador4.setVisible(false);
//            }
//            if (juego.getTurnoActual() == 4) {
//                btnJugador5.setDisable(true);
//                btnJugador5.setVisible(false);
//            }
//            bntJugador3.setVisible(true);
//            bntJugador3.setDisable(false);
//            btnJugador4.setVisible(true);
//            btnJugador4.setDisable(false);
//            btnJugador5.setVisible(true);
//            btnJugador5.setDisable(false);
//            String ruta5 = sectores.get(4).getRutaImagenJugador();
//            Jugador5.setImage(new Image(ruta5));
//            btnJugador5.setText(jugadoresEnAppContext.get(4));
//            String ruta4 = sectores.get(3).getRutaImagenJugador();
//            Jugador4.setImage(new Image(ruta4));
//            btnJugador4.setText(jugadoresEnAppContext.get(3));
//            String ruta3 = sectores.get(2).getRutaImagenJugador();
//            Jugador3.setImage(new Image(ruta3));
//            bntJugador3.setText(jugadoresEnAppContext.get(2));
//            Jugador1.setImage(new Image(ruta));
//            Jugador2.setImage(new Image(ruta2));
//            btnJugador1.setText(jugadoresEnAppContext.get(0));
//            btnJugador2.setText(jugadoresEnAppContext.get(1));
//        }
//        if (cantJugadores >= 6) {
//            if (juego.getTurnoActual() == 0) {
//                btnJugador1.setDisable(true);
//                btnJugador1.setVisible(false);
//            }
//            if (juego.getTurnoActual() == 1) {
//                btnJugador2.setDisable(true);
//                btnJugador2.setVisible(false);
//            }
//            if (juego.getTurnoActual() == 2) {
//                bntJugador3.setDisable(true);
//                bntJugador3.setVisible(false);
//            }
//            if (juego.getTurnoActual() == 3) {
//                btnJugador4.setDisable(true);
//                btnJugador4.setVisible(false);
//            }
//            if (juego.getTurnoActual() == 4) {
//                btnJugador5.setDisable(true);
//                btnJugador5.setVisible(false);
//            }
//            if (juego.getTurnoActual() == 5) {
//                btnJugador6.setDisable(true);
//                btnJugador6.setVisible(false);
//            }
//            bntJugador3.setVisible(true);
//            bntJugador3.setDisable(false);
//            btnJugador4.setVisible(true);
//            btnJugador4.setDisable(false);
//            btnJugador5.setVisible(true);
//            btnJugador5.setDisable(false);
//            btnJugador6.setVisible(true);
//            btnJugador6.setDisable(false);
//            String ruta6 = sectores.get(5).getRutaImagenJugador();
//            Jugador6.setImage(new Image(ruta6));
//            btnJugador6.setText(jugadoresEnAppContext.get(5));
//            String ruta5 = sectores.get(4).getRutaImagenJugador();
//            Jugador5.setImage(new Image(ruta5));
//            btnJugador5.setText(jugadoresEnAppContext.get(4));
//            String ruta4 = sectores.get(3).getRutaImagenJugador();
//            Jugador4.setImage(new Image(ruta4));
//            btnJugador4.setText(jugadoresEnAppContext.get(3));
//            String ruta3 = sectores.get(2).getRutaImagenJugador();
//            Jugador3.setImage(new Image(ruta3));
//            bntJugador3.setText(jugadoresEnAppContext.get(2));
//            Jugador1.setImage(new Image(ruta));
//            Jugador2.setImage(new Image(ruta2));
//            btnJugador1.setText(jugadoresEnAppContext.get(0));
//            btnJugador2.setText(jugadoresEnAppContext.get(1));
//        }
//    }

    private void populateButtons() {
        // Desactivar y ocultar el botón del jugador cuyo turno es el actual
        for (int i = 0; i < cantJugadores-1; i++) {
            if (juego.getTurnoActual() == i) {
                getButton(i).setDisable(true);
                getButton(i).setVisible(false);
            } else {
                getButton(i).setDisable(false);
                getButton(i).setVisible(true);
            }
        }

        // Actualizar las imágenes y los textos de los botones
        for (int i = 0; i < cantJugadores-1; i++) {
            String ruta = sectores.get(i).getRutaImagenJugador();
            getJugador(i).setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(ruta))));
            getButton(i).setText(jugadoresEnAppContext.get(i));
        }
    }

    // Métodos auxiliares para obtener los botones y las imágenes de los jugadores
    private MFXButton getButton(int index) {
        switch (index) {
            case 0: return btnJugador1;
            case 1: return btnJugador2;
            case 2: return bntJugador3;
            case 3: return btnJugador4;
            case 4: return btnJugador5;
            case 5: return btnJugador6;
            default: throw new IllegalArgumentException("Índice de jugador no válido: " + index);
        }
    }

    private ImageView getJugador(int index) {
        switch (index) {
            case 0: return Jugador1;
            case 1: return Jugador2;
            case 2: return Jugador3;
            case 3: return Jugador4;
            case 4: return Jugador5;
            case 5: return Jugador6;
            default: throw new IllegalArgumentException("Índice de jugador no válido: " + index);
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

    private void getCrownTypeToDuel() {

        SelectCrownDecisionController controladorCoronaSelection = (SelectCrownDecisionController) FlowController
                .getInstance().getController("SelectCrownDecisionView");
        // sound
        sound.playSound("Card.mp3");
        // flow
        FlowController.getInstance().goViewInWindowModal("SelectCrownDecisionView",
                ((Stage) root.getScene().getWindow()), true);
        categoria = controladorCoronaSelection.getResultado();
        System.out.println(categoria);

    }

    public String getCategoria() {
        return categoria;

    }

    private void verifyCrowns(MFXButton btn) {
        List<Corona> coronasRetador = (List<Corona>) AppContext.getInstance().get("coronasRetador");

        int i = botonesJugadores.indexOf(btn);
        System.out.println(i);
        boolean jugadorTieneCorona = false;

        if (btn.getText().equals(nombreJugadores.get(i))) {
            sector = sectores.get(i);
            List<Corona> coronas = sector.getCoronas();

            if (coronas != null && !coronas.isEmpty()) {

                jugadorTieneCorona = sector.hasOneCrown();

                if (sector.hasOneCrown()) {
                    if (coronasRetador.equals(coronas)) {
                        botonesJugadores.get(i).setDisable(true);
                    }
                }

                if (!jugadorTieneCorona) {

                    new Mensaje().showModal(Alert.AlertType.ERROR, "Obtención de coronas", getStage(),
                            "Este jugador no tiene coronas");

                    // Si este es el último botón seleccionado y ningún jugador tiene coronas
                    if (botonesSeleccionados >= cantJugadores - 1) {
                        juego.cambiarTurno();
                        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Obtención de coronas", getStage(),
                                "Pierdes el turno, ya que no hay duelo de coronas");
                        dueloResult = false;
                        ((Stage) root.getScene().getWindow()).close();
                    }
                } else {
                    dueloResult = true;
                    AppContext.getInstance().set("coronaJugadorDuel", coronas);
                    AppContext.getInstance().set("indexSectorRetado", i);

                    getCrownTypeToDuel();
                    ((Stage) root.getScene().getWindow()).close();
                }
            }

        }
    }

    private void compareCrowns() {

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

    private void habilitarJugadores() {
        for (MFXButton btn : botonesJugadores) {
            btn.setDisable(false);
            btn.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        juego = new Juego();
        sector = new Sector();
        sound = new Sound();
        cantJugadores = 0;
        nombreJugadores = FXCollections.observableArrayList();
        sectores = new ArrayList<>();
    }

    @Override
    public void initialize() {
        this.botonesSeleccionados = 1;
        this.dueloResult = false;
        buttons();
        getCantidadJugadores();
        getJugadoresFromAppContext();
        getSectores();
        habilitarJugadores();
        desactivarJugadores();
        getJuego();
        populateButtons();
    }

    public boolean getDueloResultado() {
        return dueloResult;
    }

}
