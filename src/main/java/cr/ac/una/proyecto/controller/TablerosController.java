package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Juego;
import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.util.Animacion;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
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

public class TablerosController extends Controller implements Initializable {

    @FXML
    private ImageView imvRuleta;
    @FXML
    private GridPane grdpTablero;
    @FXML
    private ImageView imvPicker;

    private Animacion animacion;
    private Juego juego;
    ArrayList<Sector> sectores;
    @FXML
    private ImageView imvCarta;

    @Override
    public void initialize() {
        iniciarClases();
        cargarSectores();

        juego.cargarDatosImagenes(grdpTablero);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void OnMouseClickedPicker(MouseEvent event) {
        moverRuleta();
    }

    private void cargarSectores() {
        sectores = new ArrayList<>();
        sectores = (ArrayList<Sector>) AppContext.getInstance().get("sectores");

        for (Sector sector : sectores)
        {
            juego.agregarSector(sector);
        }
    }

    private void iniciarClases() {
        juego = new Juego();
        animacion = new Animacion();
    }

    private void moverRuleta() {

        String categoria = juego.obtenerPosicionRuleta();
        double anguloDetenido = juego.getRuletaAngulo();

        Runnable onFinish = () ->
        {
            System.out.println("La animación de la ruleta ha terminado en esta categoria: " + categoria + ", Angulo: " + anguloDetenido);
            Platform.runLater(() -> llamarPreguntaView());
        };

        animacion.animacionRuleta(imvRuleta, anguloDetenido, onFinish);
        AppContext.getInstance().set("preguntaCategoria", categoria);
        AppContext.getInstance().set("preguntaJugador", juego.getJugadorPregunta());
    }

    private void llamarPreguntaView() {
        FlowController.getInstance().goViewInWindowModal("preguntaView", ((Stage) imvRuleta.getScene().getWindow()), true);
    }

    @FXML
    private void moverTarjeta(MouseEvent event) {
    }

}
