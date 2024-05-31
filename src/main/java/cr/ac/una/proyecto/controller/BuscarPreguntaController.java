package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.model.PreguntaDto;
import cr.ac.una.proyecto.service.PreguntaService;
import cr.ac.una.proyecto.util.Formato;
import cr.ac.una.proyecto.util.RespuestaUtil;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
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

    ObservableList<PreguntaDto> categorias = FXCollections.observableArrayList();
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
        txfPreguntaId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txfEnunciadoPregunta.delegateSetTextFormatter(Formato.getInstance().cedulaFormat(50));

        clmPreguntaId.setCellValueFactory(cd -> cd.getValue().id);
        clmPreguntaEnunciado.setCellValueFactory(cd -> cd.getValue().enunciado);
        clmPreguntaCategoria.setCellValueFactory(cd -> cd.getValue().nombreCategoria);//

        //  obtenterTodo();
    }

    @FXML
    private void onActionBtnFiltrar(ActionEvent event) {
        obtenerTodasLasPreguntas();
    }

    @FXML
    private void onActionBtnAceptar(ActionEvent event) {
        resultado = tbvTablaPreguntas.getSelectionModel().getSelectedItem();
        ((Stage) tbvTablaPreguntas.getScene().getWindow()).close();
        System.out.println("Hola");
    }

    private void filtrarDatos() {
//        String codigo = txfCodigo.getText();
//        String descripcion = txfDescripcion.getText();
//        String planillasXMes = txfPlanillasXMes.getText();
//
//        Respuesta respuesta = tplaService.getTiposPlanillaFiltros(codigo, descripcion, planillasXMes);
//        if (respuesta.getEstado())
//        {
//            tiposPlanilla.clear();
//            tiposPlanilla.addAll((List<TipoPlanillaDto>) respuesta.getResultado("TiposPlanilla"));
//            tbvTablaEmpleados.setItems(tiposPlanilla);
//            tbvTablaEmpleados.refresh();
//
//        } else
//        {
//            System.err.println("Error al obtener las tipoPlanillas: " + respuesta.getMensajeInterno());
//        }
    }

    private void obtenerTodasLasPreguntas() {
        RespuestaUtil respuesta = preService.getAll();
        if (respuesta.getEstado())
        {
            categorias.clear();
            categorias.addAll((List<PreguntaDto>) respuesta.getResultado("Preguntas"));
            tbvTablaPreguntas.setItems(categorias);
            tbvTablaPreguntas.refresh();

        } else
        {
            System.err.println("Error al obtener las preguntas: " + respuesta.getMensajeInterno());
        }

    }

    @Override
    public void initialize() {
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @FXML
    private void OnMousePressedTbvTablaPreguntas(MouseEvent event) {

        if (event.isPrimaryButtonDown() && event.getClickCount() == 2)
        {
            onActionBtnAceptar(null);
        }
    }

    public PreguntaDto getResultado() {
        return resultado;
    }

}
