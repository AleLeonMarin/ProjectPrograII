package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.PreguntaDto;
import cr.ac.una.proyecto.service.PreguntaService;
import cr.ac.una.proyecto.util.Formato;
import cr.ac.una.proyecto.util.RespuestaUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author justi
 */
public class BuscarPreguntaController extends Controller implements Initializable {

    ObservableList<PreguntaDto> preguntas = FXCollections.observableArrayList();
    private PreguntaDto resultado;
    private PreguntaService preService;

    @FXML
    private MFXTextField txfPreguntaId;
    @FXML
    private MFXTextField txfEnunciadoPregunta;
    @FXML
    private MFXButton btnFiltrar;
    @FXML
    private TableColumn<PreguntaDto, String> clmPreguntaId;
    @FXML
    private TableColumn<PreguntaDto, String> clmPreguntaCategoria;
    @FXML
    private TableColumn<PreguntaDto, String> clmPreguntaEnunciado;
    @FXML
    private MFXButton btnAceptar;
    @FXML
    private TableView<PreguntaDto> tbvTablaPreguntas;
    @FXML
    private MFXTextField txfCategoria;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        preService = new PreguntaService();
        txfPreguntaId.delegateSetTextFormatter(Formato.getInstance().integerFormatWithMaxLength(4));
        txfCategoria.delegateSetTextFormatter(Formato.getInstance().anyCharacterFormatWithMaxLength(15));
        txfEnunciadoPregunta.delegateSetTextFormatter(Formato.getInstance().anyCharacterFormatWithMaxLength(50));

        clmPreguntaId.setCellValueFactory(cd -> cd.getValue().id);
        clmPreguntaEnunciado.setCellValueFactory(cd -> cd.getValue().enunciado);
        clmPreguntaCategoria.setCellValueFactory(cd -> cd.getValue().nombreCategoria);

        obtenerTodasLasPreguntas();

    }
    
    @FXML
    private void onActionBtnFiltrar(ActionEvent event) {
        tbvTablaPreguntas.getItems().clear();
        preguntas.sort(new PreguntaIdComparator());
        filtrarDatos();
    }
    
    @FXML
    private void onActionBtnAceptar(ActionEvent event) {
        resultado = tbvTablaPreguntas.getSelectionModel().getSelectedItem();
        ((Stage) tbvTablaPreguntas.getScene().getWindow()).close();
        System.out.println("Hola");
    }
    
    private void filtrarDatos() {
        String idPregunta = txfPreguntaId.getText();
        String categoria = txfCategoria.getText().toUpperCase();
        String enunciado = txfEnunciadoPregunta.getText().toUpperCase();
        
        RespuestaUtil pregunta = preService.getPreguntasByFiltros(idPregunta, categoria, enunciado);
        if (pregunta.getEstado()) {
            preguntas.clear();
            preguntas.addAll((List<PreguntaDto>) pregunta.getResultado("Preguntas"));
            preguntas.sort(new PreguntaIdComparator());
            tbvTablaPreguntas.setItems(preguntas);
            tbvTablaPreguntas.refresh();

        } else {
            System.err.println("Error al obtener las tipoPlanillas: " + pregunta.getMensajeInterno());
        }
    }
    
    private void obtenerTodasLasPreguntas() {
        RespuestaUtil respuesta = preService.getAll();
        if (respuesta.getEstado()) {
            preguntas.clear();
            preguntas.addAll((List<PreguntaDto>) respuesta.getResultado("Preguntas"));
            tbvTablaPreguntas.setItems(preguntas);
            tbvTablaPreguntas.refresh();
            
        } else {
            System.err.println("Error al obtener las preguntas: " + respuesta.getMensajeInterno());
        }

    }

    @Override
    public void initialize() {
        tbvTablaPreguntas.getItems().clear();
    }

    @FXML
    private void OnMousePressedTbvTablaPreguntas(MouseEvent event) {

        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            onActionBtnAceptar(null);
        }
    }

    public PreguntaDto getResultado() {
        return resultado;
    }

    private class PreguntaIdComparator implements Comparator<PreguntaDto> {

        @Override
        public int compare(PreguntaDto pregunta1, PreguntaDto pregunta2) {
            return Long.compare(pregunta1.getId(), pregunta2.getId());
        }
    }

}
