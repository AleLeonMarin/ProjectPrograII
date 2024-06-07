package cr.ac.una.proyecto;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import cr.ac.una.proyecto.util.FlowController;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class App extends Application {

    public static Scene scene; 
    private static String parameter = "";

    @Override
    public void start(Stage stage) throws IOException {


        FlowController.getInstance().InitializeFlow(stage, null);
        viewByAccessParameter(parameter);
        stage.setTitle("Preguntados Jr");
        stage.getIcons().add(new Image(getClass().getResource("/cr/ac/una/proyecto/resources/logo.jpg").toExternalForm()));
        FlowController.getInstance().goViewInWindow("DevLogIn");
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            parameter = args[0];
        }
        launch();
    }

    public void viewByAccessParameter(String accessParameter){
        if (accessParameter.equals("A")) {
            FlowController.getInstance().goMain("AdminView");
        } 
        if (accessParameter.equals("C")){
            FlowController.getInstance().goMain("GameLogIn");
        }
        
    }
}
