package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Juego;
import cr.ac.una.proyecto.model.Jugador;
import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.util.Animacion;
import cr.ac.una.proyecto.util.Ruleta;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
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

    private Sector sector1;
    private Sector sector2;
    private String resultadoRuleta;
    private Ruleta ruleta;
    private Animacion animacion;

    private String rutaPeonRojo = "/cr/ac/una/proyecto/resources/PeonRojo.png";
    private String rutaPeonVerde = "/cr/ac/una/proyecto/resources/PeonVerde.png";//rutas peones se deben cargar segun appcontext y ejir pj

    private String rutaImagen = "/cr/ac/una/proyecto/resources/HistoriaC.png";

    @FXML
    private GridPane grdpTablero;

    @FXML
    private ImageView imvCarta;
    @FXML
    private ImageView imvPicker;

    private Juego juego;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void cargarDatos() {

        sector1 = new Sector(new Jugador("Jugador1Andres"), playerOnePositionX, playerOnePositionY, playerOneCurrentPosition, 1, rutaPeonRojo);
        sector2 = new Sector(new Jugador("Jugador2Justin"), playerTwoPositionX, playerTwoPositionY, playerTwoCurrentPosition, 2, rutaPeonVerde);
        ruleta = new Ruleta();
        animacion = new Animacion();
    }

    private void cargarDatosImagenes() {
        imvCarta.setImage(new Image(getClass().getResourceAsStream(rutaPeonVerde)));
    }

    @Override
    public void initialize() {

        cargarDatos();
        cargarDatosImagenes();
        juego = new Juego();
        juego.agregarSector(sector1);
        juego.agregarSector(sector2);
        juego.datosImagenes(grdpTablero);
    }

    @FXML
    private void moverPeonPrimeroOnAction(ActionEvent event) {
        System.out.println("Jug1");
        System.out.println("PosicionX  = " + sector1.getPosicionFija());
        System.out.println("PosicionActual  = " + sector1.getPosActual());
        //  sector1.setPosActual(sector1.mover(imageViewJugador1, grdpTablero));

    }

    @FXML
    private void moverPeonSegundoOnAction(ActionEvent event) {
        System.out.println("Jug2");
        System.out.println("PosicionX  = " + sector2.getPosicionFija());
        System.out.println("PosicionActual  = " + sector2.getPosActual());
        //  sector2.setPosActual(sector2.mover(imageViewJugador2, grdpTablero));
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
