package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.util.AppContext;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PawnSelectionController extends Controller implements Initializable {

    private String rutaPeonRosa = "/cr/ac/una/proyecto/resources/PeonRosa.png";
    private String rutaPeonRojo = "/cr/ac/una/proyecto/resources/PeonRojo.png";
    private String rutaPeonVerde = "/cr/ac/una/proyecto/resources/PeonVerde.png";
    private String rutaPeonAzul = "/cr/ac/una/proyecto/resources/PeonAzul.png";
    private String rutaPeonAmarillo = "/cr/ac/una/proyecto/resources/PeonAmarrillo.png";
    private String rutaPeonMorado = "/cr/ac/una/proyecto/resources/PeonMorado.png";
    @FXML
    private ImageView imgAmarrillo;
    @FXML
    private ImageView imgAzul;
    @FXML
    private ImageView imgRojo;
    @FXML
    private ImageView imgRosa;
    @FXML
    private ImageView imgVerde;
    @FXML
    private ImageView imgPeon1;
    @FXML
    private ImageView imgPeon2;
    @FXML
    private ImageView imgPeon3;
    @FXML
    private ImageView imgPeon4;
    @FXML
    private ImageView imgPeon5;
    @FXML
    private ImageView imgPeon6;
    @FXML
    private ImageView imgMorado;
    @FXML
    private VBox vboxPlayer1;
    @FXML
    private VBox vboxPlayer2;
    @FXML
    private VBox vboxPlayer3;
    @FXML
    private VBox vboxPlayer4;
    @FXML
    private VBox vboxPlayer5;
    @FXML
    private VBox vboxPlayer6;
    @FXML
    private Button btnSiguiente;

    private int cantJugadores;

    private Map<ImageView, String> imageViewMap;

    private ArrayList<ImageView> jugadoresImagenes;
    @FXML
    private Label hjkhkh;

    @Override
    public void initialize() {
        jugadoresImagenes = new ArrayList<>();
        imageViewMap = new HashMap<>();
        cargarDatos();

        Image imagenPeonRosa = new Image(getClass().getResourceAsStream(rutaPeonRosa));
        imgRosa.setImage(imagenPeonRosa);
        imageViewMap.put(imgRosa, rutaPeonRosa);

        Image imagenPeonRojo = new Image(getClass().getResourceAsStream(rutaPeonRojo));
        imgRojo.setImage(imagenPeonRojo);
        imageViewMap.put(imgRojo, rutaPeonRojo);

        Image imagenPeonAzul = new Image(getClass().getResourceAsStream(rutaPeonAzul));
        imgAzul.setImage(imagenPeonAzul);
        imageViewMap.put(imgAzul, rutaPeonAzul);

        Image imagenPeonVerde = new Image(getClass().getResourceAsStream(rutaPeonVerde));
        imgVerde.setImage(imagenPeonVerde);
        imageViewMap.put(imgVerde, rutaPeonVerde);

        Image imagenPeonMorado = new Image(getClass().getResourceAsStream(rutaPeonMorado));
        imgMorado.setImage(imagenPeonMorado);
        imageViewMap.put(imgMorado, rutaPeonMorado);

        Image imagenPeonAmarrilo = new Image(getClass().getResourceAsStream(rutaPeonAmarillo));
        imgAmarrillo.setImage(imagenPeonAmarrilo);
        imageViewMap.put(imgAmarrillo, rutaPeonAmarillo);

        habilitarEspacios(false);
        cargarSliderCantJug();
        mostrarEspacioJugador(cantJugadores);
    }

    private void cargarSliderCantJug() {
        cantJugadores = ((int) AppContext.getInstance().get("cantJugadoresSlider"));
        System.out.println("Cantida de jugadoresEnPawnSelecion: " + cantJugadores);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void mostrarEspacioJugador(int cantJug) {
        if (cantJug > 2)
        {
            if (cantJug == 3)
            {
                this.vboxPlayer3.setDisable(false);
                this.vboxPlayer3.setVisible(true);
            } else if (cantJug == 4)
            {
                this.vboxPlayer3.setDisable(false);
                this.vboxPlayer3.setVisible(true);
                this.vboxPlayer4.setDisable(false);
                this.vboxPlayer4.setVisible(true);
            } else if (cantJug == 5)
            {
                habilitarEspacios(true);
                this.vboxPlayer6.setDisable(false);
                this.vboxPlayer6.setVisible(true);

            } else
            {
                habilitarEspacios(true);
            }
        }

    }

    private void habilitarEspacios(Boolean estado) {
        this.vboxPlayer3.setDisable(!estado);
        this.vboxPlayer3.setVisible(estado);
        this.vboxPlayer4.setDisable(!estado);
        this.vboxPlayer4.setVisible(estado);
        this.vboxPlayer5.setDisable(!estado);
        this.vboxPlayer5.setVisible(estado);
        this.vboxPlayer6.setDisable(!estado);
        this.vboxPlayer6.setVisible(estado);
    }

    private void startDrag(MouseEvent event, ImageView imageView) {
        Dragboard dragboard = imageView.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putImage(imageView.getImage());
        dragboard.setContent(content);
        event.consume();
    }

    private void dragOver(DragEvent event) {
        if (event.getGestureSource() != event.getTarget() && event.getDragboard().hasImage())
        {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    private void dragDropped(DragEvent event, ImageView imageView) {
        Dragboard dragboard = event.getDragboard();
        boolean dropCompleted = false;
        if (dragboard.hasImage())
        {
            if (imageView.getImage() == null)
            {
                dropCompleted = handleEmptyImageView(imageView, dragboard.getImage(), event);
            } else
            {
                dropCompleted = handleNonEmptyImageView(imageView, dragboard.getImage(), event);
            }
        }

        event.setDropCompleted(dropCompleted);
        event.consume();
    }

    private boolean handleEmptyImageView(ImageView imageView, Image image, DragEvent event) {
        imageView.setImage(image);
        clearSourceImageView(event);
        updateImageViewMap(imageView, image); // Actualiza el mapa
        return true;
    }

    private boolean handleNonEmptyImageView(ImageView imageView, Image image, DragEvent event) {
        Image currentImage = imageView.getImage();
        devolverFotoPeon(currentImage);
        clearSourceImageView(event);
        imageView.setImage(image);
        updateImageViewMap(imageView, image); // Actualiza el mapa
        return true;
    }

    private void updateImageViewMap(ImageView imageView, Image image) {
        // Encuentra la ruta correspondiente a la imagen y actualiza el mapa
        String imagePath = getImagePath(image);
        if (imagePath != null)
        {
            imageViewMap.put(imageView, imagePath);
        }
    }

    private String getImagePath(Image image) {
        if (image == null)
        {
            return null;
        }

        if (image.equals(new Image(getClass().getResourceAsStream(rutaPeonRosa))))
        {
            return rutaPeonRosa;
        } else if (image.equals(new Image(getClass().getResourceAsStream(rutaPeonRojo))))
        {
            return rutaPeonRojo;
        } else if (image.equals(new Image(getClass().getResourceAsStream(rutaPeonAzul))))
        {
            return rutaPeonAzul;
        } else if (image.equals(new Image(getClass().getResourceAsStream(rutaPeonVerde))))
        {
            return rutaPeonVerde;
        } else if (image.equals(new Image(getClass().getResourceAsStream(rutaPeonMorado))))
        {
            return rutaPeonMorado;
        } else if (image.equals(new Image(getClass().getResourceAsStream(rutaPeonAmarillo))))
        {
            return rutaPeonAmarillo;
        }

        return null;
    }

    private void clearSourceImageView(DragEvent event) {
        ImageView sourceImageView = (ImageView) event.getGestureSource();
        sourceImageView.setImage(null);
    }

    private void devolverFotoPeon(Image image) {

        if (imgAmarrillo.getImage() == null)
        {
            imgAmarrillo.setImage(image);
        } else if (imgAzul.getImage() == null)
        {
            imgAzul.setImage(image);
        } else if (imgMorado.getImage() == null)
        {
            imgMorado.setImage(image);
        } else if (imgRojo.getImage() == null)
        {
            imgRojo.setImage(image);
        } else if (imgRosa.getImage() == null)
        {
            imgRosa.setImage(image);
        } else if (imgVerde.getImage() == null)
        {
            imgVerde.setImage(image);
        }

    }

    public String getImagePathForImageView(ImageView imageView) {
        return imageViewMap.get(imageView);
    }

    private void cargarDatos() {

        jugadoresImagenes.add(imgPeon1);
        jugadoresImagenes.add(imgPeon2);

        if (cantJugadores >= 3)
        {
            jugadoresImagenes.add(imgPeon3);
        }
        if (cantJugadores >= 4)
        {
            jugadoresImagenes.add(imgPeon4);
        }
        if (cantJugadores >= 5)
        {
            jugadoresImagenes.add(imgPeon5);
        }

        if (cantJugadores >= 6)
        {
            jugadoresImagenes.add(imgPeon6);
        }
    }

    @FXML
    private void Siguiente(ActionEvent event) {
        int i = 0;
        for (ImageView imagen : jugadoresImagenes)
        {
            i++;
            System.out.println("Ruta de la imagen del jugador: " + i + getImagePathForImageView(imagen));
        }
        //FlowController.getInstance().goViewInWindow("DifficultySelectionView");
        // ((Stage) btnSiguiente.getScene().getWindow()).close();
    }

    @FXML
    private void imgAmarrilloOnDragDetected(MouseEvent event) {
        startDrag(event, imgAmarrillo);
    }

    @FXML
    private void imgAzulOnDragDetected(MouseEvent event) {
        startDrag(event, imgAzul);
    }

    @FXML
    private void imgMoradoOnDragDetected(MouseEvent event) {
        startDrag(event, imgMorado);
    }

    @FXML
    private void imgRojoOnDragDetected(MouseEvent event) {
        startDrag(event, imgRojo);
    }

    @FXML
    private void imgRosaOnDragDetected(MouseEvent event) {
        startDrag(event, imgRosa);
    }

    @FXML
    private void imgVerdeOnDragDetected(MouseEvent event) {
        startDrag(event, imgVerde);
    }

    @FXML
    private void imgPeon1OnDragDetected(MouseEvent event) {
        startDrag(event, imgPeon1);
    }

    @FXML
    private void imgPeon1OnDragOver(DragEvent event) {
        dragOver(event);
    }

    @FXML
    private void imgPeon1OnDragDropped(DragEvent event) {
        dragDropped(event, imgPeon1);
    }

    @FXML
    private void imgPeon2OnDragDetected(MouseEvent event) {
        startDrag(event, imgPeon2);
    }

    @FXML
    private void imgPeon2DragOver(DragEvent event) {
        dragOver(event);
    }

    @FXML
    private void imgPeon2OnDragDropped(DragEvent event) {
        dragDropped(event, imgPeon2);
    }

    @FXML
    private void imgPeon3OnDragDetected(MouseEvent event) {
        startDrag(event, imgPeon3);
    }

    @FXML
    private void imgPeon3DragOver(DragEvent event) {
        dragOver(event);
    }

    @FXML
    private void imgPeon3OnDragDropped(DragEvent event) {
        dragDropped(event, imgPeon3);
    }

    @FXML
    private void imgPeon4OnDragDetected(MouseEvent event) {
        startDrag(event, imgPeon4);
    }

    @FXML
    private void imgPeon4OnDragOver(DragEvent event) {
        dragOver(event);
    }

    @FXML
    private void imgPeon4OnDragDropped(DragEvent event) {
        dragDropped(event, imgPeon4);
    }

    @FXML
    private void imgPeon5OnDragDetected(MouseEvent event) {
        startDrag(event, imgPeon5);
    }

    @FXML
    private void imgPeon5OnDragOver(DragEvent event) {
        dragOver(event);
    }

    @FXML
    private void imgPeon5OnDragDropped(DragEvent event) {
        dragDropped(event, imgPeon5);
    }

    @FXML
    private void imgPeon6VerdeOnDragDetected(MouseEvent event) {
        startDrag(event, imgPeon6);
    }

    @FXML
    private void imgPeon6DragOver(DragEvent event) {
        dragOver(event);
    }

    @FXML
    private void imgPeon6OnDragDropped(DragEvent event) {
        dragDropped(event, imgPeon6);
    }

}
