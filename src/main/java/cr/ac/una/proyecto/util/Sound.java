package cr.ac.una.proyecto.util;

import java.nio.file.Paths;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {

    String path;

    MediaPlayer mediaPlayer;

    public void playSound(String path) {
        String sound = path;
        Media h = new Media(Paths.get(sound).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
    }
    
}
