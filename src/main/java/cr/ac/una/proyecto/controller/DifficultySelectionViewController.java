package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.util.AppContext;
import java.net.URL;
import java.util.ResourceBundle;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.Sound;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DifficultySelectionViewController extends Controller implements Initializable {

    @FXML
    private MFXButton btnPlay;

    @FXML
    private MFXCheckbox ckbEasy;

    @FXML
    private MFXCheckbox ckbHard;

    @FXML
    private MFXCheckbox ckbMedium;

    private Sound sound = new Sound();

    private String dificultad;

    @FXML
    void onActionBtnPlay(ActionEvent event) {
        sound.playSound("src/main/resources/cr/ac/una/proyecto/resources/audio/clickedNext.mp3");
        AppContext.getInstance().set("dificultad", dificultad);
        FlowController.getInstance().goViewInWindow("SumaryMatch");
        ((Stage) btnPlay.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }

    @Override
    public void initialize() {
        initPrincipalValues();
    }

    private void deseleccionarOtrasCasillas(CheckBox selectedCheckbox) {
        CheckBox[] checkboxes =
        {
            ckbEasy, ckbMedium, ckbHard
        };
        for (CheckBox checkbox : checkboxes)
        {
            if (checkbox != selectedCheckbox)
            {
                checkbox.setSelected(false);
            }
        }
    }

    private void initPrincipalValues() {
        ckbEasy.setOnAction(e -> deseleccionarOtrasCasillas(ckbEasy));
        ckbMedium.setOnAction(e -> deseleccionarOtrasCasillas(ckbMedium));
        ckbHard.setOnAction(e -> deseleccionarOtrasCasillas(ckbHard));
    }

    @FXML
    void onActionDificil(MouseEvent event) {
        sound.playSound("src/main/resources/cr/ac/una/proyecto/resources/audio/Popup_SecondPage.mp3");
        this.dificultad = "Dificil";
    }

    @FXML
    void onActionFacil(MouseEvent event) {
        sound.playSound("src/main/resources/cr/ac/una/proyecto/resources/audio/Popup_SecondPage.mp3");
        this.dificultad = "Facil";
    }

    @FXML
    void onActionMedio(MouseEvent event) {
        sound.playSound("src/main/resources/cr/ac/una/proyecto/resources/audio/Popup_SecondPage.mp3");
        this.dificultad = "Media";
    }

}
