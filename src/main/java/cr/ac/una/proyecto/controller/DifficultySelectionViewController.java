package cr.ac.una.proyecto.controller;

import java.net.URL;
import java.util.ResourceBundle;
import cr.ac.una.proyecto.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class DifficultySelectionViewController extends Controller implements Initializable {

    @FXML
    private MFXButton btnPlay;

    @FXML
    private MFXCheckbox ckbDuel;

    @FXML
    private MFXCheckbox ckbEasy;

    @FXML
    private MFXCheckbox ckbHard;

    @FXML
    private MFXCheckbox ckbMedium;

    @FXML
    void onActionBtnPlay(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("Tablero2jugadores");
        ((Stage) btnPlay.getScene().getWindow()).close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }

    @Override
    public void initialize() {

        ckbEasy.selectedProperty().addListener((obs, wasSelected, isNowSelected) ->
        {
            if (isNowSelected)
            {
                ckbMedium.setSelected(false);
                ckbHard.setSelected(false);
                ckbDuel.setSelected(false);
            }
        });

        ckbMedium.selectedProperty().addListener((obs, wasSelected, isNowSelected) ->
        {
            if (isNowSelected)
            {
                ckbEasy.setSelected(false);
                ckbHard.setSelected(false);
                ckbDuel.setSelected(false);
            }
        });

        ckbHard.selectedProperty().addListener((obs, wasSelected, isNowSelected) ->
        {
            if (isNowSelected)
            {
                ckbEasy.setSelected(false);
                ckbMedium.setSelected(false);
                ckbDuel.setSelected(false);
            }
        });

        ckbDuel.selectedProperty().addListener((obs, wasSelected, isNowSelected) ->
        {
            if (isNowSelected)
            {
                ckbEasy.setSelected(false);
                ckbMedium.setSelected(false);
                ckbHard.setSelected(false);
            }
        });
    }

}
