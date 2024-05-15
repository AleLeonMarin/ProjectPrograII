package cr.ac.una.proyecto;

import cr.ac.una.proyecto.model.Jugador;
import cr.ac.una.proyecto.util.AppContext;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import cr.ac.una.proyecto.util.FlowController;
import java.util.ArrayList;
import javafx.scene.image.ImageView;

public class App extends Application {

    // ArrayList<Jugador> jugadores;
    ArrayList<ImageView> imagenesJugadores;
    private int cantidadJugadoresSlider = 8;

    @Override
    public void start(Stage stage) throws IOException {
        registros();
        FlowController.getInstance().InitializeFlow(stage, null);
        stage.setTitle("Preguntados Jr");
        FlowController.getInstance().goViewInWindow("RegistryNewGame");

    }

    public static void main(String[] args) {
        launch();
    }

    private void registros() {
        //  AppContext.getInstance().set("jugadores", jugadores);
        AppContext.getInstance().set("iconPlayers", imagenesJugadores);
        AppContext.getInstance().set("cantJugadoresSlider", cantidadJugadoresSlider);
    }

}
