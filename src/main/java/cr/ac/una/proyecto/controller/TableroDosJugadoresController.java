package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Juego;
import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.util.Animacion;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.Ruleta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TableroDosJugadoresController extends Controller implements Initializable {

    @FXML
    private ImageView imvRuleta;

    private int playerOnePositionY = 0;
    private int playerOnePositionX = 0;
    private int playerOneCurrentPosition = 0;

    private int playerTwoPositionY = 3;
    private int playerTwoPositionX = 3;
    private int playerTwoCurrentPosition = 3;

    private String resultadoRuleta;
    private Ruleta ruleta;
    private Animacion animacion;
    private String rutaImagen = "/cr/ac/una/proyecto/resources/HistoriaC.png";

    @FXML
    private GridPane grdpTablero;

    @FXML
    private ImageView imvCarta;
    @FXML
    private ImageView imvPicker;

    private Juego juego;
    ObservableList<Sector> sectores;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void cargarSectores() {
        sectores = FXCollections.observableArrayList();
        sectores = (ObservableList<Sector>) AppContext.getInstance().get("sectores");

        for (Sector sector : sectores)
        {
            juego.agregarSector(sector);
        }
    }

    private void cargarDatos() {
        ruleta = new Ruleta();
        animacion = new Animacion();
    }

    private void cargarDatosImagenes() {
        imvCarta.setImage(new Image(getClass().getResourceAsStream(rutaImagen)));
    }

    @Override
    public void initialize() {
        juego = new Juego();
        cargarDatos();
        cargarDatosImagenes();
        cargarSectores();

        juego.cargarDatosImagenes(grdpTablero);
    }

    @FXML
    private void moverTarjeta(MouseEvent event) {
        Animacion animacion = new Animacion();
        Stage stage = (Stage) imvCarta.getScene().getWindow();
        animacion.saltoTarjeta(imvCarta, stage);
    }

    @FXML
    private void OnMouseClickedPicker(MouseEvent event) {
        moverRuleta();
    }

    private void moverRuleta() {
        String categoria = ruleta.determinarPosicionRuleta();
        double anguloDetenido = ruleta.getAnguloDetenido();

        Runnable onFinish = () ->
        {
            System.out.println("La animación de la ruleta ha terminado en: " + categoria + ", Angulo: " + anguloDetenido);
            juego.jugar(grdpTablero);
            // Aquí puedes realizar cualquier acción adicional que desees
        };

        // Llamar al método animacionRuleta para iniciar la animación de la ruleta
        animacion.animacionRuleta(imvRuleta, anguloDetenido, onFinish);

        // Aquí puedes actualizar la UI o realizar cualquier acción necesaria con la categoría resultante
    }
}
