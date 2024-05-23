package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import cr.ac.una.proyecto.model.Juego;
import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.util.Animacion;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.Ruleta;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class TableroTresJugadores extends Controller implements Initializable {

    @FXML
    private MFXButton btnPicker;

    @FXML
    private ImageView ruleta;

    @FXML
    private GridPane grdpTablero;

    private Ruleta ruletaa;

    private Animacion animacion;

    private Juego juego;

    ArrayList<Sector> sectores;


    @Override
    public void initialize() {
        juego = new Juego();
        cargarDatos();
        cargarSectores();

        juego.cargarDatosImagenes(grdpTablero);

    }

    @FXML
    void onActionBtnPicker(ActionEvent event) {

        moverRuleta();

    }

    public void cargarSectores() {

        sectores = new ArrayList<>();
        sectores = ((ArrayList<Sector>) AppContext.getInstance().get("sectores"));

        for (Sector sector : sectores) {
            juego.agregarSector(sector);
        }

    }

    public void cargarDatos() {
        ruletaa = new Ruleta();
        animacion = new Animacion();
    }

    private void moverRuleta() {
        String categoria = ruletaa.determinarPosicionRuleta();
        double anguloDetenido = ruletaa.getAnguloDetenido();

        Runnable onFinish = () -> {
            System.out
                    .println("La animación de la ruleta ha terminado en: " + categoria + ", Angulo: " + anguloDetenido);
            juego.jugar(grdpTablero);
            // Aquí puedes realizar cualquier acción adicional que desees
        };

        // Llamar al método animacionRuleta para iniciar la animación de la ruleta
        animacion.animacionRuleta(ruleta, anguloDetenido, onFinish);

        // Aquí puedes actualizar la UI o realizar cualquier acción necesaria con la
        // categoría resultante
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }

}
