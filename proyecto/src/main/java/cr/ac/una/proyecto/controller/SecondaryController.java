package cr.ac.una.proyecto.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import cr.ac.una.proyecto.App;
import cr.ac.una.proyecto.util.FlowController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class SecondaryController extends Controller implements Initializable {

    @FXML
    private void switchToPrimary() throws IOException {
        FlowController.getInstance().goView("primary");
        FlowController.getInstance().salir();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        
    }
}