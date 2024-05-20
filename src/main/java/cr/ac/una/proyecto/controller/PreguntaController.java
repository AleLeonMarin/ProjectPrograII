package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class PreguntaController extends Controller implements Initializable{

    @FXML
    private VBox VboxRespuestas;

    @FXML
    private MFXButton btnBomb;

    @FXML
    private MFXButton btnExtraTry;

    @FXML
    private MFXButton btnPass;

    @FXML
    private MFXButton btnSecondOportunity;

    @FXML
    private TextArea txaEnunciado;

    @FXML
    void onActionBtnBomb(ActionEvent event) {

    }

    @FXML
    void onActionBtnPass(ActionEvent event) {

    }

    @FXML
    void onActionBtnSecondOportunity(ActionEvent event) {

    }

    @FXML
    void onActionExtraTry(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize() {
        btnsAnswers();
    }

    public void btnsAnswers(){

        List<MFXButton> buttons = new ArrayList<>();
        buttons.add(new MFXButton());
        buttons.add(new MFXButton());
        buttons.add(new MFXButton());
        buttons.add(new MFXButton());

        randomButtonsAnswers(buttons);
    }

    public void randomButtonsAnswers(List<MFXButton> buttons){
        for (int i = 0; i < buttons.size(); i++) {
            int random = (int) (Math.random() * buttons.size());
            MFXButton temp = buttons.get(i);
            buttons.set(i, buttons.get(random));
            buttons.set(random, temp);
        }

        VboxRespuestas.getChildren().addAll(buttons);
    }
}
