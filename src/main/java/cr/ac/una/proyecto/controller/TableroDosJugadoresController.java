package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Animacion;
import cr.ac.una.proyecto.model.Juego;
import cr.ac.una.proyecto.model.Jugador;
import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.util.AppContext;
import java.net.URL;
import java.util.ResourceBundle;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TableroDosJugadoresController extends Controller implements Initializable {

    @FXML
    private ImageView ruleta;

    private int playerOnePositionY = 0;
    private int playerOnePositionX = 0;
    private int playerOneCurrentPosition = 0;

    private int playerTwoPositionY = 3;
    private int playerTwoPositionX = 3;
    private int playerTwoCurrentPosition = 3;

    private Juego juego;
    private Sector sector1;
    private Sector sector2;

    private String rutaPeonRojo = "/cr/ac/una/proyecto/resources/PeonRojo.png";
    private String rutaPeonVerde = "/cr/ac/una/proyecto/resources/PeonVerde.png";

    @FXML
    private GridPane grdpTablero;

    private ImageView imageViewJugador1;
    private ImageView imageViewJugador2;
    @FXML
    private ImageView imvCarta;
    @FXML
    private ImageView imvPicker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void initialize() {

        sector1 = new Sector(new Jugador("Jugador1Andres"), playerOnePositionX, playerOnePositionY, playerOneCurrentPosition, 1, rutaPeonRojo);
        sector2 = new Sector(new Jugador("Jugador2Justin"), playerTwoPositionX, playerTwoPositionY, playerTwoCurrentPosition, 2, rutaPeonVerde);

        imvCarta.setImage(new Image("file:///C:/Users/justi/Desktop/Netbeans/pruebasAnimacionImagen/src/main/resources/cr/ac/una/pruebasanimacionimagen/cara.png"));
        // Crear los ImageViews para las imágenes de los peones
        imageViewJugador1 = new ImageView();
        imageViewJugador2 = new ImageView();

        // Cargar las imágenes desde las rutas
        Image imagenPeonRojo = new Image(getClass().getResourceAsStream(rutaPeonRojo));
        Image imagenPeonVerde = new Image(getClass().getResourceAsStream(rutaPeonVerde));

        // Establecer el tamaño de las imágenes a 100px
        imageViewJugador1.setFitWidth(100);
        imageViewJugador1.setFitHeight(100);
        imageViewJugador2.setFitWidth(100);
        imageViewJugador2.setFitHeight(100);

        // Establecer las imágenes en los ImageViews
        imageViewJugador1.setImage(imagenPeonRojo);
        imageViewJugador2.setImage(imagenPeonVerde);

        // Agregar los ImageViews al GridPane en las posiciones especificadas y centrarlos
        grdpTablero.add(imageViewJugador1, playerOnePositionY, playerOnePositionX);
        grdpTablero.add(imageViewJugador2, playerTwoPositionY, playerTwoPositionX);

        // Centrar los elementos dentro de las celdas del GridPane
        GridPane.setHalignment(imageViewJugador1, HPos.CENTER);
        GridPane.setValignment(imageViewJugador1, VPos.CENTER);
        GridPane.setHalignment(imageViewJugador2, HPos.CENTER);
        GridPane.setValignment(imageViewJugador2, VPos.CENTER);
        juego = new Juego();
        cargarJuegoJugadores();
    }

    @FXML
    private void moverPeonPrimeroOnAction(ActionEvent event) {
        System.out.println("Jug1");
        System.out.println("PosicionX  = " + sector1.getPosicionFija());
        System.out.println("PosicionActual  = " + sector1.getPosActual());

        sector1.setPosActual(sector1.mover(imageViewJugador1, grdpTablero));
       // juego.iniciarJuego();
    }

    @FXML
    private void moverPeonSegundoOnAction(ActionEvent event) {
        System.out.println("Jug2");
        System.out.println("PosicionX  = " + sector2.getPosicionFija());
        System.out.println("PosicionActual  = " + sector2.getPosActual());
        sector2.setPosActual(sector2.mover(imageViewJugador2, grdpTablero));
    }

    @FXML
    private void moverTarjeta(MouseEvent event) {
        Animacion animacion = new Animacion();
        Stage stage = (Stage) imvCarta.getScene().getWindow();
        animacion.saltoTarjeta(imvCarta, stage);
    }

    @FXML
    private void OnMouseClickedPicker(MouseEvent event) {
        Animacion animacion = new Animacion();
        animacion.animacionRuleta(ruleta); // Donde 'ruleta' es la instancia de la ImageView de la ruleta en tu controlador

    }

    private void cargarJuegoJugadores() {
//        ArrayList<Jugador> jugadores = (ArrayList<Jugador>) AppContext.getInstance().get("jugadores");
//
//        for (Jugador jugadorAux : jugadores)
//        {
//            juego.agregarJugador(jugadorAux);
//        }

        juego.agregarJugador(new Jugador("Juanchis"));
        juego.agregarJugador(new Jugador("Carlos"));

        juego.obtenerInfoJugadores();

    }

}
