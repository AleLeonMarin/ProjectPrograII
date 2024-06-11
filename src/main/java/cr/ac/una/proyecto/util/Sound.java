package cr.ac.una.proyecto.util;

import java.nio.file.Paths;

import cr.ac.una.proyecto.App;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {

    String path;

    MediaPlayer mediaPlayer;

    public void playSound(String path) {
        String sound = App.class.getResource("/cr/ac/una/proyecto/resources/audio/" + path).toExternalForm();
        Media h = new Media(sound);
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }

    public void disableForButtons(MFXButton btn1, MFXButton btn2,Label lbl, String path) {

        PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(0.5));
        String sound = App.class.getResource("/cr/ac/una/proyecto/resources/audio/" + path).toExternalForm();
        Media h = new Media(sound);
        MediaPlayer mediaPlayer = new MediaPlayer(h);

        pause.setOnFinished(events -> {

            mediaPlayer.setOnEndOfMedia(() -> {
                btn1.setDisable(false);
                btn2.setDisable(false);
                lbl.setDisable(false);
            });
        });
        pause.play();
        mediaPlayer.play();

    }

}
