package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.ImageStorage;
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
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PawnSelectionController extends Controller implements Initializable {

    private String rutaPeonRosa = "/cr/ac/una/proyecto/resources/PeonRosa.png";
    private String rutaPeonRojo = "/cr/ac/una/proyecto/resources/PeonRojo.png";
    private String rutaPeonVerde = "/cr/ac/una/proyecto/resources/PeonVerde.png";
    private String rutaPeonAzul = "/cr/ac/una/proyecto/resources/PeonAzul.png";
    private String rutaPeonAmarillo = "/cr/ac/una/proyecto/resources/PeonAmarrillo.png";
    private String rutaPeonMorado = "/cr/ac/una/proyecto/resources/PeonMorado.png";

    @FXML
    private ImageView imvAmarillo;
    @FXML
    private ImageView imvAzul;
    @FXML
    private ImageView imvRojo;
    @FXML
    private ImageView imvRosa;
    @FXML
    private ImageView imvVerde;
    @FXML
    private ImageView imvMorado;

    @FXML
    private Button btnSiguiente;

    @FXML
    private MFXComboBox<String> cmbJugadorSector1;
    @FXML
    private MFXComboBox<String> cmbJugadorSector2;
    @FXML
    private MFXComboBox<String> cmbJugadorSector3;
    @FXML
    private MFXComboBox<String> cmbJugadorSector4;
    @FXML
    private MFXComboBox<String> cmbJugadorSector5;
    @FXML
    private MFXComboBox<String> cmbJugadorSector6;

    @FXML
    private ImageView imvJugadorSector1;
    @FXML
    private ImageView imvJugadorSector2;
    @FXML
    private ImageView imvJugadorSector3;
    @FXML
    private ImageView imvJugadorSector4;
    @FXML
    private ImageView imvJugadorSector5;
    @FXML
    private ImageView imvJugadorSector6;

    private ImageStorage imageViewMap;
    private ObservableList<String> nombresPeones;
    private ArrayList<MFXComboBox<String>> botonesLista;
    private int cantJugadores;
    private List<String> personajesSeleccionados;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void initialize() {
        nombresPeones = FXCollections.observableArrayList();
        personajesSeleccionados = new ArrayList<>();
        imageViewMap = new ImageStorage();
        botonesLista = new ArrayList<>();
        cargarPeones();
        cargarDatosImagenes();

        habilitarEspacios(false);
        cargarSliderCantJug();
        mostrarCmbJugadores(cantJugadores);
    }

    private void cargarDatosImagenes() {
        Image imagenPeonRosa = new Image(getClass().getResourceAsStream(rutaPeonRosa));
        imvRosa.setImage(imagenPeonRosa);
        imageViewMap.addImage(nombresPeones.get(0), rutaPeonRosa);

        Image imagenPeonAmarillo = new Image(getClass().getResourceAsStream(rutaPeonAmarillo));
        imvAmarillo.setImage(imagenPeonAmarillo);
        imageViewMap.addImage(nombresPeones.get(1), rutaPeonAmarillo);

        Image imagenPeonVerde = new Image(getClass().getResourceAsStream(rutaPeonVerde));
        imvVerde.setImage(imagenPeonVerde);
        imageViewMap.addImage(nombresPeones.get(2), rutaPeonVerde);

        Image imagenPeonAzul = new Image(getClass().getResourceAsStream(rutaPeonAzul));
        imvAzul.setImage(imagenPeonAzul);
        imageViewMap.addImage(nombresPeones.get(3), rutaPeonAzul);

        Image imagenPeonRojo = new Image(getClass().getResourceAsStream(rutaPeonRojo));
        imvRojo.setImage(imagenPeonRojo);
        imageViewMap.addImage(nombresPeones.get(4), rutaPeonRojo);

        Image imagenPeonMorado = new Image(getClass().getResourceAsStream(rutaPeonMorado));
        imvMorado.setImage(imagenPeonMorado);
        imageViewMap.addImage(nombresPeones.get(5), rutaPeonMorado);

    }

    private void cargarPeones() {
        String nameBase = "Peon ";
        nombresPeones.add(nameBase + "Rosa");
        nombresPeones.add(nameBase + "Amarrilo");
        nombresPeones.add(nameBase + "Verde");
        nombresPeones.add(nameBase + "Azul");
        nombresPeones.add(nameBase + "Rojo");
        nombresPeones.add(nameBase + "Morado");
    }

    @FXML
    private void Siguiente(ActionEvent event) {
        if (validarSeleccion())
        {
            System.out.println("Hay errores");
        } else
        {
            System.out.println("No hay errores");
            cargarSectores();
            FlowController.getInstance().goViewInWindow("DifficultySelectionView");
            ((Stage) btnSiguiente.getScene().getWindow()).close();
        }
    }

    private void cargarSliderCantJug() {
        cantJugadores = ((int) AppContext.getInstance().get("cantJugadoresSlider"));
        System.out.println("Cantida de jugadoresEnPawnSelecion: " + cantJugadores);
    }

    private void mostrarCmbJugadores(int cantJug) {

        cmbJugadorSector1.setItems(nombresPeones);
        cmbJugadorSector2.setItems(nombresPeones);

        botonesLista.add(cmbJugadorSector1);
        botonesLista.add(cmbJugadorSector2);

        if (cantJug >= 6)
        {
            habilitarEspacios(true);
        } else
        {
            if (cantJug >= 3)
            {
                this.cmbJugadorSector3.setDisable(false);
                this.cmbJugadorSector3.setVisible(true);
                cmbJugadorSector3.setItems(nombresPeones);
                botonesLista.add(cmbJugadorSector3);

            }
            if (cantJug >= 4)
            {
                this.cmbJugadorSector4.setDisable(false);
                this.cmbJugadorSector4.setVisible(true);
                cmbJugadorSector4.setItems(nombresPeones);
                botonesLista.add(cmbJugadorSector4);
            }
            if (cantJug >= 5)
            {
                this.cmbJugadorSector5.setDisable(false);
                this.cmbJugadorSector5.setVisible(true);
                cmbJugadorSector5.setItems(nombresPeones);
                botonesLista.add(cmbJugadorSector5);
            }
        }
    }

    private void cargarSelecionados(int cantJug) {

        personajesSeleccionados.add(cmbJugadorSector1.getSelectedItem());
        personajesSeleccionados.add(cmbJugadorSector2.getSelectedItem());

        if (cantJug >= 6)
        {
            personajesSeleccionados.add(cmbJugadorSector3.getSelectedItem());
            personajesSeleccionados.add(cmbJugadorSector4.getSelectedItem());
            personajesSeleccionados.add(cmbJugadorSector5.getSelectedItem());
            personajesSeleccionados.add(cmbJugadorSector6.getSelectedItem());
        } else
        {
            if (cantJug >= 3)
            {

                personajesSeleccionados.add(cmbJugadorSector3.getSelectedItem());
            }
            if (cantJug >= 4)
            {

                personajesSeleccionados.add(cmbJugadorSector4.getSelectedItem());
            }
            if (cantJug >= 5)
            {

                personajesSeleccionados.add(cmbJugadorSector5.getSelectedItem());

            }
        }
    }

    private void habilitarEspacios(Boolean estado) {
        this.cmbJugadorSector3.setDisable(!estado);
        this.cmbJugadorSector3.setVisible(estado);
        this.cmbJugadorSector4.setDisable(!estado);
        this.cmbJugadorSector4.setVisible(estado);
        this.cmbJugadorSector5.setDisable(!estado);
        this.cmbJugadorSector5.setVisible(estado);
        this.cmbJugadorSector6.setDisable(!estado);
        this.cmbJugadorSector6.setVisible(estado);

        if (estado)
        {
            cmbJugadorSector3.setItems(nombresPeones);
            cmbJugadorSector4.setItems(nombresPeones);
            cmbJugadorSector5.setItems(nombresPeones);
            cmbJugadorSector6.setItems(nombresPeones);
            botonesLista.add(cmbJugadorSector3);
            botonesLista.add(cmbJugadorSector4);
            botonesLista.add(cmbJugadorSector5);
            botonesLista.add(cmbJugadorSector6);
        }
    }

    public String getImagePathForImageView(String MFXBOXContent) {
        return imageViewMap.getPathByName(MFXBOXContent);
    }

    private void setImagenMfxButton(ActionEvent event, MFXComboBox<String> comboBox, ImageView imageView) {
        if (!comboBox.getValue().isBlank() || comboBox.getValue() != null)
        {
            String rutaImagen = imageViewMap.getPathByName(comboBox.getSelectedItem());
            System.out.println("ruta imagen: " + rutaImagen);
            Image image = new Image(getClass().getResourceAsStream(rutaImagen));
            if (image != null)
            {
                imageView.setImage(image);
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
        for (int i = 0; i < personajesSeleccionados.size(); i++)
        {
            String personaje = personajesSeleccionados.get(i);
            System.out.println("Personaje: " + i + personaje);
            // Verificar si la cadena es nula, vacía o si es igual a otro elemento en la lista
            if (personaje == null || personaje.isEmpty() || personajesSeleccionados.indexOf(personaje) != i)
            {
                return true; // Hay personajes repetidos o espacios en blanco
            }
        }
        return false; // No hay personajes repetidos ni espacios en blanco
    }

    private void cargarSectores() {
        ArrayList<Sector> sectores;
        sectores = new ArrayList<>();
        sectores = (ArrayList<Sector>) AppContext.getInstance().get("sectores");

        for (int index = 0; index < cantJugadores; index++)
        {
            String rutaButon = getImagePathForImageView(botonesLista.get(index).getSelectedItem());
            sectores.get(index).setRutaImagenJugador(rutaButon);
            sectores.toString();
        }
    }

}