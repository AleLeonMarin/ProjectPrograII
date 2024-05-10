package cr.ac.una.proyecto.controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class PawnSectorSelectionController extends Controller implements Initializable {

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

    @Override
    public void initialize() {
        Image imagenPeonRosa = new Image(getClass().getResourceAsStream(rutaPeonRosa));
        imgRosa.setImage(imagenPeonRosa);

        Image imagenPeonRojo = new Image(getClass().getResourceAsStream(rutaPeonRojo));
        imgRojo.setImage(imagenPeonRojo);

        Image imagenPeonAzul = new Image(getClass().getResourceAsStream(rutaPeonAzul));
        imgAzul.setImage(imagenPeonAzul);

        Image imagenPeonVerde = new Image(getClass().getResourceAsStream(rutaPeonVerde));
        imgVerde.setImage(imagenPeonVerde);

        Image imagenPeonMorado = new Image(getClass().getResourceAsStream(rutaPeonMorado));
        imgMorado.setImage(imagenPeonMorado);

        Image imagenPeonAmarrilo = new Image(getClass().getResourceAsStream(rutaPeonAmarillo));
        imgAmarrillo.setImage(imagenPeonAmarrilo);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    // Método genérico para iniciar el evento de arrastre
    private void startDrag(MouseEvent event, ImageView imageView) {
        Dragboard dragboard = imageView.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putImage(imageView.getImage());
        dragboard.setContent(content);
        event.consume();
    }

    private void dragOver(DragEvent event) {
        if (event.getGestureSource() != event.getTarget() && event.getDragboard().hasImage()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    private void dragDropped(DragEvent event, ImageView imageView) {
        Dragboard dragboard = event.getDragboard();
        boolean dropCompleted = false;
        if (dragboard.hasImage()) {
            if (imageView.getImage() == null) {
                dropCompleted = handleEmptyImageView(imageView, dragboard.getImage(), event);
            } else {
                dropCompleted = handleNonEmptyImageView(imageView, dragboard.getImage(), event);
            }
        }

        event.setDropCompleted(dropCompleted);
        event.consume();
    }

    private boolean handleEmptyImageView(ImageView imageView, Image image, DragEvent event) {
        imageView.setImage(image);
        clearSourceImageView(event);
        return true;
    }

    private boolean handleNonEmptyImageView(ImageView imageView, Image image, DragEvent event) {
        Image currentImage = imageView.getImage();
        devolverFotoPeon(currentImage);
        clearSourceImageView(event);
        imageView.setImage(image);
        return true;
    }

    private void clearSourceImageView(DragEvent event) {
        ImageView sourceImageView = (ImageView) event.getGestureSource();
        sourceImageView.setImage(null);
    }

    private void devolverFotoPeon(Image image) {

        if (imgAmarrillo.getImage() == null) {
            imgAmarrillo.setImage(image);
        } else if (imgAzul.getImage() == null) {
            imgAzul.setImage(image);
        } else if (imgMorado.getImage() == null) {
            imgMorado.setImage(image);
        } else if (imgRojo.getImage() == null) {
            imgRojo.setImage(image);
        } else if (imgRosa.getImage() == null) {
            imgRosa.setImage(image);
        } else if (imgVerde.getImage() == null) {
            imgVerde.setImage(image);
        }

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
