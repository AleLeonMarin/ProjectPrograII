package cr.ac.una.proyecto;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import cr.ac.una.proyecto.util.FlowController;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class App extends Application {

    public static Scene scene;
    public Image icon;
    String iconString;

    @Override
    public void start(Stage stage) throws IOException {

//        iconString = "cr/ac/una/proyecto/resources/logo.jpg";
//        icon = new Image(iconString);
//        FlowController.getInstance().InitializeFlow(stage, null);
//        stage.getIcons().add(icon);
//        stage.setTitle("Preguntados JR");
        FlowController.getInstance().goViewInWindow("DevLogIn");

    }

    public static void main(String[] args) {
        launch();
    }
}
