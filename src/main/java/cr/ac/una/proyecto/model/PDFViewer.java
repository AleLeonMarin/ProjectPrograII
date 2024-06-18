package cr.ac.una.proyecto.model;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PDFViewer {

    private PDDocument document;
    private PDFRenderer pdfRenderer;
    private List<ImageView> imageViews;
    private int dimensionVentanaImagen = 800;

    public PDFViewer() {}

    public void start(Stage ownerStage, String pdfFilePath) {
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream(pdfFilePath);
            if (resourceAsStream == null) {
                throw new IOException("File not found: " + pdfFilePath);
            }

            document = PDDocument.load(resourceAsStream);
            pdfRenderer = new PDFRenderer(document);

            // Crear la interfaz de usuario
            imageViews = new ArrayList<>();
            VBox vbox = new VBox(10); // Espaciado entre páginas
            for (int i = 0; i < document.getNumberOfPages(); i++) {
                ImageView imageView = new ImageView();
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(dimensionVentanaImagen); // Ajusta esto según el tamaño de tu ventana deseada
                imageViews.add(imageView);
                vbox.getChildren().add(imageView);
                showPage(i); // Cargar cada página
            }

            ScrollPane scrollPane = new ScrollPane(vbox);
            scrollPane.setFitToWidth(true);

            Scene scene = new Scene(scrollPane, dimensionVentanaImagen, dimensionVentanaImagen - 80);

            // Crear un nuevo Stage modal
            Stage modalStage = new Stage();
            modalStage.initOwner(ownerStage);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(scene);
            modalStage.setTitle("App Instrucciones");
            modalStage.setResizable(false);

            // Añadir manejador de cierre de ventana
            modalStage.setOnCloseRequest((WindowEvent event) -> {
                try {
                    stop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            modalStage.showAndWait(); // Esperar hasta que se cierre

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPage(int pageIndex) throws IOException {
        BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(pageIndex, 150); // Ajusta el DPI según sea necesario
        Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
        imageViews.get(pageIndex).setImage(fxImage);
    }

    public void stop() throws IOException {
        if (document != null) {
            document.close();
        }
    }
}
