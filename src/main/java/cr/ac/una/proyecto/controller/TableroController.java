package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Juego;
import cr.ac.una.proyecto.model.JugadorDto;
import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.model.Corona;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Mensaje;
import cr.ac.una.proyecto.util.Sound;
import io.github.palexdev.materialfx.controls.MFXButton;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Justin Mendez y Alejandro Leon
 */
public class TableroController extends Controller implements Initializable {

    @FXML
    private MFXButton btnIniciar, btnSalir, btnGuardar;
    @FXML
    private ImageView imgvArteJug1, imgvJug1Ciencia, imgvJug1Deporte, imgvJug1Entretenimiento, imgvJug1Geografia, imgvJug1Historia;
    @FXML
    private ImageView imgvJug2Arte, imgvJug2Ciencia, imgvJug2Deporte, imgvJug2Entretenimiento, imgvJug2Geografia, imgvJug2Historia;
    @FXML
    private ImageView imgvJug3Arte, imgvJug3Ciencia, imgvJug3Deporte, imgvJug3Entretenimiento, imgvJug3Geografia, imgvJug3Historia;
    @FXML
    private ImageView imgvJug4Arte, imgvJug4Ciencia, imgvJug4Deporte, imgvJug4Entretenimiento, imgvJug4Geografia, imgvJug4Historia;
    @FXML
    private ImageView imgvJug5Arte, imgvJug5Ciencia, imgvJug5Deporte, imgvJug5Entretenimiento, imgvJug5Geografia, imgvJug5Historia;
    @FXML
    private ImageView imgvJug6Arte, imgvJug6Ciencia, imgvJug6Deporte, imgvJug6Entretenimiento, imgvJug6Geografia, imgvJug6Historia;
    @FXML
    private Label lblJugador1, lblJugador2, lblJugador3, lblJugador4, lblJugador5, lblJugador6, lblTiempo;

    private Sound sound = new Sound();
    private int segundos = 0;
    private ObservableList<String> nombresJugadores;
    private List<JugadorDto> jugadores;
    private Juego juego;
    private ArrayList<String> loadToJson;
    private ArrayList<Sector> sectores;

    private ArrayList<ArrayList<ImageView>> jugadoresListas = new ArrayList<>();
    private ArrayList<ImageView> imgvJug1List = new ArrayList<>();
    private ArrayList<ImageView> imgvJug2List = new ArrayList<>();
    private ArrayList<ImageView> imgvJug3List = new ArrayList<>();
    private ArrayList<ImageView> imgvJug4List = new ArrayList<>();
    private ArrayList<ImageView> imgvJug5List = new ArrayList<>();
    private ArrayList<ImageView> imgvJug6List = new ArrayList<>();

    TablerosController busquedaController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sectores = new ArrayList<>();
        lblTiempo.setVisible(false);
        nombresJugadores = FXCollections.observableArrayList();
        jugadores = new ArrayList<>();
        juego = new Juego();
        loadToJson = new ArrayList<>();
        getJugadoresFromAppContext();
        disablePlayer();
        showPlayer();
    }

    @Override
    public void initialize() {

    }

    private void timer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                validarCantidadJugadores();
                timer.cancel();
            }
        };
        timer.schedule(task, 5000);
    }

    private void validarCantidadJugadores() {

        try
        {
            int contextSlider = (int) AppContext.getInstance().get("cantJugadoresSlider");
            System.out.println("AppContextInfoSlider: " + contextSlider);

            if (contextSlider == 6)
            {
                busquedaController = (TablerosController) FlowController.getInstance().getController("Tablero6jugadores");
                FlowController.getInstance().goView("Tablero6jugadores");
            } else if (contextSlider == 5)
            {
                busquedaController = (TablerosController) FlowController.getInstance().getController("Tablero5jugadores");
                FlowController.getInstance().goView("Tablero5jugadores");
            } else if (contextSlider == 4)
            {
                busquedaController = (TablerosController) FlowController.getInstance().getController("Tablero4jugadores");
                FlowController.getInstance().goView("Tablero4jugadores");
            } else if (contextSlider == 3)
            {
                busquedaController = (TablerosController) FlowController.getInstance().getController("Tablero3jugadores");
                FlowController.getInstance().goView("Tablero3jugadores");
            } else
            {
                busquedaController = (TablerosController) FlowController.getInstance().getController("Tablero2jugadores");
                FlowController.getInstance().goView("Tablero2jugadores");

            }

        } catch (Exception ex)
        {

            Logger.getLogger(TableroController.class
                    .getName()).log(Level.SEVERE, "Error loading tablerosController", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Subtablero", getStage(), "Error al cargar el subtablero");

        }

    }

    @FXML
    private void onMouseMoved(MouseEvent event) {
        if (busquedaController != null && (busquedaController.pickerStatus()))
        {
            cargarSectoresCoronas();
            if (sectores != null)
            {
                actualizarOpacidadSegunCorona();
            }

        }
    }

    @FXML
    private void onActionBtnIniciar(ActionEvent event) {
        sound.playSound("src/main/resources/cr/ac/una/proyecto/resources/audio/clickedStart.mp3");
        startTimer();
        validarCantidadJugadores();
        btnIniciar.setVisible(false);
        btnIniciar.setDisable(true);

    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        sound.playSound("src/main/resources/cr/ac/una/proyecto/resources/audio/quitExited.mp3");
        PauseTransition pause = new PauseTransition(Duration.millis(600));
        pause.setOnFinished(e ->
        {
            ((Stage) btnSalir.getScene().getWindow()).close();
        });
        pause.play();
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        sound.playSound("src/main/resources/cr/ac/una/proyecto/resources/audio/Chance_audio.mp3");
        cargarJuegoClass();
        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar", getStage(), "Partida Guardada");

    }

    public void showPlayer() {

        int contextSlider = (int) AppContext.getInstance().get("cantJugadoresSlider");
        System.out.println("AppContextInfoSlider: " + contextSlider);

        if (contextSlider == 2)
        {
            enablePlayerOne();
            enablePlayerTwo();
            disablePlayerThree();
            disablePlayerFour();
            disablePlayerFive();
            disablePlayerSix();

        }
        if (contextSlider == 3)
        {
            enablePlayerOne();
            enablePlayerTwo();
            enablePlayerThree();
            disablePlayerFour();
            disablePlayerFive();
            disablePlayerSix();
        }
        if (contextSlider == 4)
        {
            enablePlayerOne();
            enablePlayerTwo();
            enablePlayerThree();
            enablePlayerFour();
            disablePlayerFive();
            disablePlayerSix();

        }
        if (contextSlider == 5)
        {
            enablePlayerOne();
            enablePlayerTwo();
            enablePlayerThree();
            enablePlayerFour();
            enablePlayerFive();
            disablePlayerSix();
        }
        if (contextSlider == 6)
        {
            enablePlayerOne();
            enablePlayerTwo();
            enablePlayerThree();
            enablePlayerFour();
            enablePlayerFive();
            enablePlayerSix();
        }

    }

    public void disablePlayer() {

        disablePlayerThree();
        disablePlayerFour();
        disablePlayerFive();
        disablePlayerSix();
    }

    public void enablePlayerOne() {
        lblJugador1.setText(jugadores.get(0).getNombre());
        imgvArteJug1.setOpacity(0.5);
        imgvJug1Ciencia.setOpacity(0.5);
        imgvJug1Deporte.setOpacity(0.5);
        imgvJug1Entretenimiento.setOpacity(0.5);
        imgvJug1Geografia.setOpacity(0.5);
        imgvJug1Historia.setOpacity(0.5);
        agregarImageViews(imgvJug1List, imgvArteJug1, imgvJug1Ciencia, imgvJug1Deporte, imgvJug1Entretenimiento, imgvJug1Geografia, imgvJug1Historia);
        jugadoresListas.add(imgvJug1List);
    }

    public void enablePlayerTwo() {
        lblJugador2.setText(jugadores.get(1).getNombre());
        imgvJug2Arte.setOpacity(0.5);
        imgvJug2Ciencia.setOpacity(0.5);
        imgvJug2Deporte.setOpacity(0.5);
        imgvJug2Entretenimiento.setOpacity(0.5);
        imgvJug2Geografia.setOpacity(0.5);
        imgvJug2Historia.setOpacity(0.5);
        agregarImageViews(imgvJug2List, imgvJug2Arte, imgvJug2Ciencia, imgvJug2Deporte, imgvJug2Entretenimiento, imgvJug2Geografia, imgvJug2Historia);
        jugadoresListas.add(imgvJug2List);
    }

    public void enablePlayerThree() {

        // Get Player Name
        lblJugador3.setVisible(true);
        lblJugador3.setText(jugadores.get(2).getNombre());
        // Set Visibility
        imgvJug3Arte.setVisible(true);
        imgvJug3Ciencia.setVisible(true);
        imgvJug3Deporte.setVisible(true);
        imgvJug3Entretenimiento.setVisible(true);
        imgvJug3Geografia.setVisible(true);
        imgvJug3Historia.setVisible(true);
        // Set Opacity
        imgvJug3Arte.setOpacity(0.5);
        imgvJug3Ciencia.setOpacity(0.5);
        imgvJug3Deporte.setOpacity(0.5);
        imgvJug3Entretenimiento.setOpacity(0.5);
        imgvJug3Geografia.setOpacity(0.5);
        imgvJug3Historia.setOpacity(0.5);
        agregarImageViews(imgvJug3List, imgvJug3Arte, imgvJug3Ciencia, imgvJug3Deporte, imgvJug3Entretenimiento, imgvJug3Geografia, imgvJug3Historia);
        jugadoresListas.add(imgvJug3List);
    }

    public void enablePlayerFour() {

        lblJugador4.setVisible(true);
        lblJugador4.setText(jugadores.get(3).getNombre());

        imgvJug4Arte.setVisible(true);
        imgvJug4Ciencia.setVisible(true);
        imgvJug4Deporte.setVisible(true);
        imgvJug4Entretenimiento.setVisible(true);
        imgvJug4Geografia.setVisible(true);
        imgvJug4Historia.setVisible(true);

        imgvJug4Arte.setOpacity(0.5);
        imgvJug4Ciencia.setOpacity(0.5);
        imgvJug4Deporte.setOpacity(0.5);
        imgvJug4Entretenimiento.setOpacity(0.5);
        imgvJug4Geografia.setOpacity(0.5);
        imgvJug4Historia.setOpacity(0.5);
        agregarImageViews(imgvJug4List, imgvJug4Arte, imgvJug4Ciencia, imgvJug4Deporte, imgvJug4Entretenimiento, imgvJug4Geografia, imgvJug4Historia);
        jugadoresListas.add(imgvJug4List);
    }

    public void enablePlayerFive() {
        lblJugador5.setVisible(true);
        lblJugador5.setText(jugadores.get(4).getNombre());

        imgvJug5Arte.setVisible(true);
        imgvJug5Ciencia.setVisible(true);
        imgvJug5Deporte.setVisible(true);
        imgvJug5Entretenimiento.setVisible(true);
        imgvJug5Geografia.setVisible(true);
        imgvJug5Historia.setVisible(true);

        imgvJug5Arte.setOpacity(0.5);
        imgvJug5Ciencia.setOpacity(0.5);
        imgvJug5Deporte.setOpacity(0.5);
        imgvJug5Entretenimiento.setOpacity(0.5);
        imgvJug5Geografia.setOpacity(0.5);
        imgvJug5Historia.setOpacity(0.5);
        agregarImageViews(imgvJug5List, imgvJug5Arte, imgvJug5Ciencia, imgvJug5Deporte, imgvJug5Entretenimiento, imgvJug5Geografia, imgvJug5Historia);
        jugadoresListas.add(imgvJug5List);
    }

    public void enablePlayerSix() {

        lblJugador6.setVisible(true);
        lblJugador6.setText(jugadores.get(5).getNombre());

        imgvJug6Arte.setVisible(true);
        imgvJug6Ciencia.setVisible(true);
        imgvJug6Deporte.setVisible(true);
        imgvJug6Entretenimiento.setVisible(true);
        imgvJug6Geografia.setVisible(true);
        imgvJug6Historia.setVisible(true);

        imgvJug6Arte.setOpacity(0.5);
        imgvJug6Ciencia.setOpacity(0.5);
        imgvJug6Deporte.setOpacity(0.5);
        imgvJug6Entretenimiento.setOpacity(0.5);
        imgvJug6Geografia.setOpacity(0.5);
        imgvJug6Historia.setOpacity(0.5);
        agregarImageViews(imgvJug6List, imgvJug6Arte, imgvJug6Ciencia, imgvJug6Deporte, imgvJug6Entretenimiento, imgvJug6Geografia, imgvJug6Historia);
        jugadoresListas.add(imgvJug6List);
    }

    public void disablePlayerThree() {

        lblJugador3.setVisible(false);
        imgvJug3Arte.setVisible(false);
        imgvJug3Arte.setDisable(true);
        imgvJug3Ciencia.setVisible(false);
        imgvJug3Ciencia.setDisable(true);
        imgvJug3Deporte.setVisible(false);
        imgvJug3Deporte.setDisable(true);
        imgvJug3Entretenimiento.setVisible(false);
        imgvJug3Entretenimiento.setDisable(true);
        imgvJug3Geografia.setVisible(false);
        imgvJug3Geografia.setDisable(true);
        imgvJug3Historia.setVisible(false);
        imgvJug3Historia.setDisable(true);

    }

    public void disablePlayerFour() {

        lblJugador4.setVisible(false);
        imgvJug4Arte.setVisible(false);
        imgvJug4Arte.setDisable(true);
        imgvJug4Ciencia.setVisible(false);
        imgvJug4Ciencia.setDisable(true);
        imgvJug4Deporte.setVisible(false);
        imgvJug4Deporte.setDisable(true);
        imgvJug4Entretenimiento.setVisible(false);
        imgvJug4Entretenimiento.setDisable(true);
        imgvJug4Geografia.setVisible(false);
        imgvJug4Geografia.setDisable(true);
        imgvJug4Historia.setVisible(false);
        imgvJug4Historia.setDisable(true);
    }

    public void disablePlayerFive() {

        lblJugador5.setVisible(false);
        imgvJug5Arte.setVisible(false);
        imgvJug5Arte.setDisable(true);
        imgvJug5Ciencia.setVisible(false);
        imgvJug5Ciencia.setDisable(true);
        imgvJug5Deporte.setVisible(false);
        imgvJug5Deporte.setDisable(true);
        imgvJug5Entretenimiento.setVisible(false);
        imgvJug5Entretenimiento.setDisable(true);
        imgvJug5Geografia.setVisible(false);
        imgvJug5Geografia.setDisable(true);
        imgvJug5Historia.setVisible(false);
        imgvJug5Historia.setDisable(true);
    }

    public void disablePlayerSix() {

        lblJugador6.setVisible(false);
        imgvJug6Arte.setVisible(false);
        imgvJug6Arte.setDisable(true);
        imgvJug6Ciencia.setVisible(false);
        imgvJug6Ciencia.setDisable(true);
        imgvJug6Deporte.setVisible(false);
        imgvJug6Deporte.setDisable(true);
        imgvJug6Entretenimiento.setVisible(false);
        imgvJug6Entretenimiento.setDisable(true);
        imgvJug6Geografia.setVisible(false);
        imgvJug6Geografia.setDisable(true);
        imgvJug6Historia.setVisible(false);
        imgvJug6Historia.setDisable(true);
    }

    private void getJugadoresFromAppContext() {

        jugadores = (List<JugadorDto>) AppContext.getInstance().get("jugadores");
        for (JugadorDto jugador : jugadores)
        {
            nombresJugadores.add(jugador.getNombre());
        }

    }

    public void startTimer() {

        lblTiempo.setVisible(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                segundos++;
                int minutos = segundos / 60;
                int seg = segundos % 60;
                lblTiempo.setText(String.format("Tiempo: %02d:%02d", minutos, seg));
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    public void loadToJsonData() {
        loadToJson.add(juego.toString());
    }

    private void createJson() {
        loadToJsonData();
        System.out.println(loadToJson.toString());
        Gson gson = new Gson();
        String json = gson.toJson(loadToJson.toString());
        try
        {
            FileWriter file = new FileWriter("Partida " + lblJugador1.getText() + ".json");
            file.write(json);
            file.close();
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private void cargarJuegoClass() {

        juego = busquedaController.getJuego();

        if (juego != null)
        {
            createJson();
        }
    }

    private void cargarSectoresCoronas() {
        this.sectores = new ArrayList<>();
        this.sectores = (ArrayList<Sector>) AppContext.getInstance().get("JuegoSectores");;
    }

    private void actualizarOpacidadSegunCorona() {
        int index = 0;
        for (Sector sector : sectores)
        {
            ArrayList<Corona> coronas = sector.getCoronas();
            for (Corona corona : coronas)
            {
                String nombreCorona = corona.getNombre();
                boolean coronaActiva = corona.getEstado();
                switch (nombreCorona)
                {
                    case "Arte":
                        actualizarOpacidadImagenes(jugadoresListas.get(index).get(0), coronaActiva);
                        break;
                    case "Ciencia":
                        actualizarOpacidadImagenes(jugadoresListas.get(index).get(1), coronaActiva);
                        break;
                    case "Deporte":
                        actualizarOpacidadImagenes(jugadoresListas.get(index).get(2), coronaActiva);
                        break;
                    case "Entretenimiento":
                        actualizarOpacidadImagenes(jugadoresListas.get(index).get(3), coronaActiva);
                        break;
                    case "Geografia":
                        actualizarOpacidadImagenes(jugadoresListas.get(index).get(4), coronaActiva);
                        break;
                    case "Historia":
                        actualizarOpacidadImagenes(jugadoresListas.get(index).get(5), coronaActiva);
                        break;
                }
            }
            index++;
        }
    }

    private void actualizarOpacidadImagenes(ImageView imageView, boolean coronaActiva) {
        double opacidad = coronaActiva ? 1.0 : 0.5;
        imageView.setOpacity(opacidad);

    }

    private void agregarImageViews(ArrayList<ImageView> list, ImageView... imageViews) {
        for (ImageView imageView : imageViews)
        {
            list.add(imageView);
        }
    }

}
