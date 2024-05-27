package cr.ac.una.proyecto.util;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Animacion {

    private RotateTransition rotate;
    private String imagenReverso = "file:///C:/Users/justi/Desktop/Netbeans/pruebasAnimacionImagen/src/main/resources/cr/ac/una/pruebasanimacionimagen/reverso.png";

    public void saltoTarjeta(ImageView imageView, Stage stage) {
        double targetX = (stage.getWidth() - imageView.getBoundsInParent().getWidth()) / 2;
        double targetY = (stage.getHeight() - imageView.getBoundsInParent().getHeight()) / 2;
        System.out.println("Coordenadas del centro: (" + targetX + ", " + targetY + ")");

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1.2), imageView);
        translateTransition.setToX(targetX);
        translateTransition.setToY(targetY);

        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), imageView);
        rotateTransition.setByAngle(360);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.6), imageView);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(1.5);

        fadeOut.setOnFinished(e ->
        {
            imageView.setImage(new Image((imagenReverso)));
        });

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), imageView);
        fadeIn.setFromValue(0.5);
        fadeIn.setToValue(1);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), imageView);
        scaleTransition.setFromX(0.8);
        scaleTransition.setFromY(0.8);
        scaleTransition.setToX(2); // Aumenta el tamaño de la imagen
        scaleTransition.setToY(2); // Aumenta el tamaño de la imagen

        ParallelTransition parallelTransition = new ParallelTransition(rotateTransition, fadeOut, scaleTransition);
        SequentialTransition sequentialTransition = new SequentialTransition(translateTransition, parallelTransition, fadeIn);
        sequentialTransition.play();
        imageView.toFront();
    }

    public void animacionRuleta(ImageView ruletaImageView, double anguloFinal, Runnable onFinish) {

        RotateTransition rotate = new RotateTransition();
        rotate.setNode(ruletaImageView);
        ruletaImageView.setRotate(0);

        rotate.setDuration(Duration.seconds(3));
        rotate.setInterpolator(Interpolator.EASE_BOTH);
        rotate.setByAngle(360 * 10 + anguloFinal);
        rotate.setAxis(Rotate.Z_AXIS);

        rotate.setOnFinished(event -> onFinish.run());
        rotate.play();
    }

    public void animarFadeOut(Node node, Runnable onFinish) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1500));
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(event -> onFinish.run());
        fadeTransition.play();
    }

    public void animarFadeIn(Node node, Runnable onFinish) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1500));
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setOnFinished(event -> onFinish.run());
        fadeTransition.play();
    }

    public void simpleFadeIn(Node node) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1500));
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

}
