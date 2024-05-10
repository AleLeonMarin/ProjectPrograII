package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class TableroDosJugadoresController extends Controller implements Initializable {

    @FXML
    private MFXButton btnPicker;

    @FXML
    private ImageView ruleta;

    private int playerOnePositionY = 0;
    private int playerOnePositionX = 0;
    private int playerOneCurrentPosition = 0;

    private int playerTwoPositionY = 0;
    private int playerTwoPositionX = 2;
    private int playerTwoCurrentPosition = 0;

    private String rutaPeonRojo = "/cr/ac/una/proyecto/resources/PeonRojo.png";
    private String rutaPeonVerde = "/cr/ac/una/proyecto/resources/PeonVerde.png";

    @FXML
    private GridPane grdpTablero;

    private ImageView imageViewPeon1;
    private ImageView imageViewPeon2;

    @FXML
    void onActionBtnPicker(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void initialize() {
        // Crear los ImageViews para las imágenes de los peones
        imageViewPeon1 = new ImageView();
        imageViewPeon2 = new ImageView();

        // Cargar las imágenes desde las rutas
        Image imagenPeonRojo = new Image(getClass().getResourceAsStream(rutaPeonRojo));
        Image imagenPeonVerde = new Image(getClass().getResourceAsStream(rutaPeonVerde));

        // Establecer el tamaño de las imágenes a 100px
        imageViewPeon1.setFitWidth(100);
        imageViewPeon1.setFitHeight(100);
        imageViewPeon2.setFitWidth(100);
        imageViewPeon2.setFitHeight(100);

        // Establecer las imágenes en los ImageViews
        imageViewPeon1.setImage(imagenPeonRojo);
        imageViewPeon2.setImage(imagenPeonVerde);

        // Agregar los ImageViews al GridPane en las posiciones especificadas y centrarlos
        grdpTablero.add(imageViewPeon1, playerOnePositionY, playerOnePositionX);
        grdpTablero.add(imageViewPeon2, playerTwoPositionY, playerTwoPositionX);

        // Centrar los elementos dentro de las celdas del GridPane
        GridPane.setHalignment(imageViewPeon1, HPos.CENTER);
        GridPane.setValignment(imageViewPeon1, VPos.CENTER);
        GridPane.setHalignment(imageViewPeon2, HPos.CENTER);
        GridPane.setValignment(imageViewPeon2, VPos.CENTER);

    }

    private int evaluarPos(int posFija, int posInicial, int posActual, ImageView imageView) {

        eliminarNodoEnPosicion(posActual, posFija);

        if (posActual >= posInicial + 3)
        {
            posActual = posInicial;
        } else
        {
            posActual++;
        }

        grdpTablero.add(imageView, posActual, posFija);
        GridPane.setHalignment(imageView, HPos.CENTER);
        return posActual;
    }

    private void eliminarNodoEnPosicion(int columna, int fila) {
        ObservableList<Node> children = grdpTablero.getChildren();
        children.removeIf(node -> GridPane.getRowIndex(node) == fila && GridPane.getColumnIndex(node) == columna);
    }

    @FXML
    private void moverPeonPrimeroOnAction(ActionEvent event) {
        System.out.println("PosicionX  = " + playerOnePositionX);
        System.out.println("PosicionActual  = " + playerOneCurrentPosition);
        playerOneCurrentPosition = evaluarPos(playerOnePositionX, playerOnePositionY, playerOneCurrentPosition, imageViewPeon1);
    }

    @FXML
    private void moverPeonSegundoOnAction(ActionEvent event) {
        playerTwoCurrentPosition = evaluarPos(playerTwoPositionX, playerTwoPositionY, playerTwoCurrentPosition, imageViewPeon2);

    }

}
