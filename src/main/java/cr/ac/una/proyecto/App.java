package cr.ac.una.proyecto;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import cr.ac.una.proyecto.util.FlowController;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FlowController.getInstance().InitializeFlow(stage, null);
        stage.setTitle("Preguntados Jr");
        FlowController.getInstance().goViewInWindow("Tablero2jugadores");
        
    }

   
    public static void main(String[] args) {
        launch();
    }

}