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
 * @author Justin Mendez & Alejandro Leon
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
        tbvTablaPreguntas.getItems().clear();
        preService = new PreguntaService();
        txfPreguntaId.delegateSetTextFormatter(Formato.getInstance().integerFormatWithMaxLength(4));
        txfCategoria.delegateSetTextFormatter(Formato.getInstance().anyCharacterFormatWithMaxLength(15));
        txfEnunciadoPregunta.delegateSetTextFormatter(Formato.getInstance().anyCharacterFormatWithMaxLength(50));

        clmPreguntaId.setCellValueFactory(cd -> cd.getValue().id);
        clmPreguntaEnunciado.setCellValueFactory(cd -> cd.getValue().enunciado);
        clmPreguntaCategoria.setCellValueFactory(cd -> cd.getValue().nombreCategoria);
    }

    @FXML
    private void onActionBtnFiltrar(ActionEvent event) {//Filtra las preguntas en al tabla
        tbvTablaPreguntas.getItems().clear();
        preguntas.sort(new PreguntaIdComparator());
        filtrarDatos();
    }

    @FXML
    private void onActionBtnAceptar(ActionEvent event) {//Carga una preguntaDto a la varibale ´resultado´
        resultado = tbvTablaPreguntas.getSelectionModel().getSelectedItem();
        ((Stage) tbvTablaPreguntas.getScene().getWindow()).close();
    }

    private void filtrarDatos() {//Filtra una pregunta segun datos escritos en la vista, y obtiene resultados de la base de datops con coicidencias a los mismos
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

    @Override
    public void initialize() {
        tbvTablaPreguntas.getItems().clear();
    }

    @FXML
    private void OnMousePressedTbvTablaPreguntas(MouseEvent event) {//Carga una preguta a la variable mediante el evento del boton de aceptar

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
