package cr.ac.una.proyecto.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PreguntaController extends Controller implements Initializable {

    @FXML
    private MFXButton btnRespuesta1;
    @FXML
    private MFXButton btnPorDos;
    @FXML
    private MFXButton btnRetry;
    @FXML
    private MFXButton btnNext1;
    @FXML
    private VBox vboxTextPregunta;
    @FXML
    private MFXButton btnBomb;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void brnRespuesta1OnAcrtion(ActionEvent event) {
        String texto = "En este ejemplo, la función dividirTexto toma el texto y la longitud máxima deseada por línea como argumentos. Divide el texto en palabras y las organiza en líneas, asegurándose de que cada línea no exceda la longitud máxima especificada. Luego, crea un objeto Text para cada línea y los agrega al contenedor VBox para mostrarlos en la interfaz de usuario.";
        addToVbox(texto);
        System.out.println("H1");
    }

    private void addToVbox(String texto) {
        String[] lineas = dividirTexto(texto, 95);
        vboxTextPregunta.getChildren().clear();

        for (String linea : lineas)
        {
            Text textComponent = new Text(linea);
            textComponent.setStyle("-fx-font-family: 'Arial';"
                    + "-fx-font-size: 12px;"
                    + "-fx-font-weight: bold;"
                    + "-fx-fill: whitesmoke;");
            vboxTextPregunta.getChildren().add(textComponent);
        }

    }

    private String[] dividirTexto(String texto, int longitudMaxima) {
        String[] palabras = texto.split("\\s+");
        StringBuilder lineaActual = new StringBuilder();
        StringBuilder lineas = new StringBuilder();

        for (String palabra : palabras)
        {
            if (lineaActual.length() + palabra.length() <= longitudMaxima)
            {

                lineaActual.append(palabra).append(" ");
            } else
            {
                lineas.append(lineaActual.toString().trim()).append("\n");
                lineaActual = new StringBuilder(palabra + " ");
            }
        }

        lineas.append(lineaActual.toString().trim());

        return lineas.toString().split("\n");
    }

    @FXML
    private void sads(ActionEvent event) {
        System.out.println("H1");
    }

}
