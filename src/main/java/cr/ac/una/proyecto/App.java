package cr.ac.una.proyecto;

import cr.ac.una.proyecto.model.Jugador;
import cr.ac.una.proyecto.util.AppContext;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import cr.ac.una.proyecto.util.FlowController;
import java.util.ArrayList;

public class App extends Application {

    ArrayList<Jugador> jugadores;

    @Override
    public void start(Stage stage) throws IOException {

        FlowController.getInstance().InitializeFlow(stage, null);
        stage.setTitle("Preguntados Jr");
        FlowController.getInstance().goViewInWindow("Tablero2jugadores");

    }

    public static void main(String[] args) {
        launch();
    }

    private void registros() {
        AppContext.getInstance().set("jugadores", jugadores);
    }

}
