package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.Juego;
import cr.ac.una.proyecto.model.Sector;
import cr.ac.una.proyecto.util.Animacion;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class TablerosController extends Controller implements Initializable {
    
    @FXML
    private ImageView imvRuleta;
    @FXML
    private GridPane grdpTablero;
    @FXML
    private ImageView imvPicker;
    
    private Animacion animacion;
    private Juego juego;
    ArrayList<Sector> sectores;
    private TextField txfRuletaPrueba;
    @FXML
    private AnchorPane acpRootPane;
    private ArrayList<String> categoriasRuleta;
    private String categoria;
    
    @Override
    public void initialize() {
        iniciarClases();
        cargarSectores();
        juego.cargarDatosImagenes(grdpTablero);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @FXML
    private void OnMouseClickedPicker(MouseEvent event) {
        this.imvPicker.setDisable(true);
        moverRuleta();
    }
    
    private void cargarSectores() {
        sectores = new ArrayList<>();
        sectores = (ArrayList<Sector>) AppContext.getInstance().get("sectores");
        
        for (Sector sector : sectores)
        {
            juego.agregarSector(sector);
        }
    }
    
    private void cargarCategoriasRuleta() {
        categoriasRuleta = (ArrayList<String>) AppContext.getInstance().get("categoriasRuleta");
    }
    
    private void iniciarClases() {
        categoriasRuleta = new ArrayList<>();
        juego = new Juego();
        animacion = new Animacion();
        cargarCategoriasRuleta();
    }
    
    private void moverRuleta() {
        
        this.categoria = juego.obtenerPosicionRuleta();
        double anguloDetenido = juego.getRuletaAngulo();
        
        Runnable onFinish = () ->
        {
            System.out.println("La animación de la ruleta ha terminado en esta categoria: " + categoria + ", Angulo: " + anguloDetenido);
            Platform.runLater(() -> mostrarTarjetas());
            this.imvPicker.setDisable(false);
            
        };
        
        animacion.animacionRuleta(imvRuleta, anguloDetenido, onFinish);
        AppContext.getInstance().set("preguntaCategoria", categoria);
        AppContext.getInstance().set("preguntaJugador", juego.getJugadorPregunta());
    }
    
    private void llamarPreguntaView() {
        
        FlowController.getInstance().goViewInWindowModal("preguntaView", ((Stage) imvRuleta.getScene().getWindow()), true);
    }
    
    private void mostrarTarjetas() {
        if (categoria == categoriasRuleta.get(0))
        {
            FlowController.getInstance().goViewInWindowModal("FrontalCardSports", ((Stage) imvRuleta.getScene().getWindow()), true);
        } else if (categoria == categoriasRuleta.get(1))
        {
            FlowController.getInstance().goViewInWindowModal("FrontalCardArt", ((Stage) imvRuleta.getScene().getWindow()), true);
        } else if (categoria == categoriasRuleta.get(2))
        {
            FlowController.getInstance().goViewInWindowModal("FrontalCardGeografy", ((Stage) imvRuleta.getScene().getWindow()), true);
        } else if (categoria == categoriasRuleta.get(3))
        {
            FlowController.getInstance().goViewInWindowModal("FrontalCardScience", ((Stage) imvRuleta.getScene().getWindow()), true);
        } else if (categoria == categoriasRuleta.get(4))
        {
            FlowController.getInstance().goViewInWindowModal("FrontalCardCrownView", ((Stage) imvRuleta.getScene().getWindow()), true);
            SelectCrownDecisionController controladorCoronaSelection = (SelectCrownDecisionController) FlowController.getInstance().getController("SelectCrownDecisionView");
            FlowController.getInstance().goViewInWindowModal("SelectCrownDecisionView", ((Stage) imvRuleta.getScene().getWindow()), true);
            categoria = controladorCoronaSelection.getResultado();
            AppContext.getInstance().set("preguntaCategoria", categoria);
            mostrarTarjetas();
            
        } else if (categoria == categoriasRuleta.get(5))
        {
            FlowController.getInstance().goViewInWindowModal("FrontalCardEntertamient", ((Stage) imvRuleta.getScene().getWindow()), true);
        } else
        {
            FlowController.getInstance().goViewInWindowModal("FrontalCardHistory", ((Stage) imvRuleta.getScene().getWindow()), true);
        }
        llamarPreguntaView();
        juego.jugar(grdpTablero);
    }
    
    public Juego getJuego() {
        return this.juego;
    }
    
}
