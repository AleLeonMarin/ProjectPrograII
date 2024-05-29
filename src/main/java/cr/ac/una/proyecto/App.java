package cr.ac.una.proyecto;

import cr.ac.una.proyecto.model.Jugador;
import cr.ac.una.proyecto.util.AppContext;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import cr.ac.una.proyecto.util.FlowController;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class App extends Application {

    ArrayList<Jugador> jugadores;
    ArrayList<ImageView> imagenesJugadores;

    public static Scene scene;
    public Image icon;
    String iconString;

    @Override
    public void start(Stage stage) throws IOException {

        iconString = "cr/ac/una/proyecto/resources/logo.jpg";
        icon = new Image(iconString);
        registros();
        FlowController.getInstance().InitializeFlow(stage, null);
        stage.getIcons().add(icon);
        stage.setTitle("Preguntados JR");
        FlowController.getInstance().goViewInWindow("DevLogIn");

    }

    public static void main(String[] args) {
        launch();
    }

    private void registros() {
        AppContext.getInstance().set("jugadores", jugadores);
        AppContext.getInstance().set("iconPlayers", imagenesJugadores);

    }

}
