package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.ImageStorage;
import cr.ac.una.proyecto.util.Mensaje;
import cr.ac.una.proyecto.util.Sound;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class PawnSelectionController extends Controller implements Initializable {

    private static final String[] RUTAS_PEONES =
    {
        "/cr/ac/una/proyecto/resources/PeonRosa.png",
        "/cr/ac/una/proyecto/resources/PeonAmarillo.png",
        "/cr/ac/una/proyecto/resources/PeonVerde.png",
        "/cr/ac/una/proyecto/resources/PeonAzul.png",
        "/cr/ac/una/proyecto/resources/PeonRojo.png",
        "/cr/ac/una/proyecto/resources/PeonMorado.png"
    };

    private static final String[] NOMBRES_PEONES =
    {
        "Peon Rosa", "Peon Amarillo", "Peon Verde", "Peon Azul", "Peon Rojo", "Peon Morado"
    };

    @FXML
    private ImageView imvAmarillo, imvAzul, imvRojo, imvRosa, imvVerde, imvMorado;

    @FXML
    private MFXButton btnSiguiente;
    @FXML
    private MFXComboBox<String> cmbJugadorSector1, cmbJugadorSector2, cmbJugadorSector3, cmbJugadorSector4, cmbJugadorSector5, cmbJugadorSector6;

    @FXML
    private ImageView imvJugadorSector1, imvJugadorSector2, imvJugadorSector3, imvJugadorSector4, imvJugadorSector5, imvJugadorSector6;

    private ImageStorage imageViewMap;
    private ObservableList<String> nombresPeones;
    private List<MFXComboBox<String>> botonesLista;
    private int cantJugadores;
    private List<String> personajesSeleccionados;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void initialize() {
        initPrincipalValues();
    }

    private void initPrincipalValues() {
        nombresPeones = FXCollections.observableArrayList(NOMBRES_PEONES);
        personajesSeleccionados = new ArrayList<>();
        imageViewMap = new ImageStorage();
        botonesLista = List.of(cmbJugadorSector1, cmbJugadorSector2, cmbJugadorSector3, cmbJugadorSector4, cmbJugadorSector5, cmbJugadorSector6);
        cargarDatosImagenesFijasPeon();
        habilitarEspacios(false);
        cargarSliderCantJug();
        mostrarCmbJugadores(cantJugadores);
    }

    private void cargarDatosImagenesFijasPeon() {
        ImageView[] imageViews =
        {
            imvRosa, imvAmarillo, imvVerde, imvAzul, imvRojo, imvMorado
        };

        for (int index = 0; index < RUTAS_PEONES.length; index++)
        {
            try
            {
                Image imagenPeon = new Image(getClass().getResourceAsStream(RUTAS_PEONES[index]));
                imageViews[index].setImage(imagenPeon);
                imageViewMap.addImage(NOMBRES_PEONES[index], RUTAS_PEONES[index]);
            } catch (Exception e)
            {
                System.err.println("Error al cargar la imagen para " + NOMBRES_PEONES[index] + " desde la ruta " + RUTAS_PEONES[index]);
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onActionBtnSiguiente(ActionEvent event) {

        Sound sound = new Sound();
        sound.playSound("clickedNext.mp3");
        if (validarSeleccion())
        {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error de usuario", getStage(), "Cada jugador debe seleccionar un 'Peon' distinto");
        } else
        {
            cargarSectores();
            FlowController.getInstance().goViewInWindow("DifficultySelectionView");
            ((Stage) btnSiguiente.getScene().getWindow()).close();
        }
    }

    private void cargarSliderCantJug() {
        cantJugadores = (Integer) AppContext.getInstance().get("cantJugadoresSlider");
    }

    private void mostrarCmbJugadores(int cantJug) {
        for (int index = 0; index < botonesLista.size(); index++)
        {
            MFXComboBox<String> comboBox = botonesLista.get(index);
            if (index < cantJug)
            {
                comboBox.setDisable(false);
                comboBox.setVisible(true);
                comboBox.setItems(nombresPeones);
            } else
            {
                comboBox.setDisable(true);
                comboBox.setVisible(false);
            }
        }
    }

    private void cargarSelecionados(int cantJug) {
        personajesSeleccionados.clear();
        for (int i = 0; i < cantJug; i++)
        {
            personajesSeleccionados.add(botonesLista.get(i).getSelectedItem());
        }
    }

    private void habilitarEspacios(boolean estado) {
        for (MFXComboBox<String> comboBox : botonesLista)
        {
            comboBox.setDisable(!estado);
            comboBox.setVisible(estado);
            if (estado)
            {
                comboBox.setItems(nombresPeones);
            }
        }
    }

    private void setImagenMfxButton(ActionEvent event, MFXComboBox<String> comboBox, ImageView imageView) {
        String selectedItem = comboBox.getSelectedItem();
        if (selectedItem != null && !selectedItem.isBlank())
        {
            String rutaImagen = imageViewMap.getPathByName(selectedItem);
            try
            {
                Image image = new Image(getClass().getResourceAsStream(rutaImagen));
                imageView.setImage(image);
            } catch (Exception e)
            {
                System.err.println("Error al cargar la imagen desde la ruta: " + rutaImagen);
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void cmbJugadorSector1OnAction(ActionEvent event) {
        setImagenMfxButton(event, cmbJugadorSector1, imvJugadorSector1);
    }

    @FXML
    private void cmbJugadorSector2OnAction(ActionEvent event) {
        setImagenMfxButton(event, cmbJugadorSector2, imvJugadorSector2);
    }

    @FXML
    private void cmbJugadorSector3OnAction(ActionEvent event) {
        setImagenMfxButton(event, cmbJugadorSector3, imvJugadorSector3);
    }

    @FXML
    private void cmbJugadorSector4OnAction(ActionEvent event) {
        setImagenMfxButton(event, cmbJugadorSector4, imvJugadorSector4);
    }

    @FXML
    private void cmbJugadorSector5OnAction(ActionEvent event) {
        setImagenMfxButton(event, cmbJugadorSector5, imvJugadorSector5);
    }

    @FXML
    private void cmbJugadorSector6OnAction(ActionEvent event) {
        setImagenMfxButton(event, cmbJugadorSector6, imvJugadorSector6);
    }

    private boolean validarSeleccion() {
        cargarSelecionados(cantJugadores);
        for (int indexPersonaje = 0; indexPersonaje < personajesSeleccionados.size(); indexPersonaje++)
        {
            String personaje = personajesSeleccionados.get(indexPersonaje);
            if (personaje == null || personaje.isEmpty() || personajesSeleccionados.indexOf(personaje) != indexPersonaje)
            {
                return true;
            }
        }
        return false;
    }

    private void cargarSectores() {
        List<Sector> sectores = (List<Sector>) AppContext.getInstance().get("sectores");
        for (int index = 0; index < cantJugadores; index++)
        {
            String rutaButton = getImagePathForImageView(botonesLista.get(index).getSelectedItem());
            sectores.get(index).setRutaImagenJugador(rutaButton);
        }
        AppContext.getInstance().set("sectores", sectores);
    }

    public String getImagePathForImageView(String mfxButtonContent) {
        return imageViewMap.getPathByName(mfxButtonContent);
    }

}
