package cr.ac.una.proyecto.util;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Animacion {

    private RotateTransition rotate;

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

    public void zoomInOut(Node node) {
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setNode(node);
        scaleTransition.setDuration(Duration.millis(1000));
        scaleTransition.setCycleCount(TranslateTransition.INDEFINITE);
        scaleTransition.setInterpolator(Interpolator.LINEAR);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.5);  // Define a scaling target to notice the zoom effect
        scaleTransition.setToY(1.5);  // Define a scaling target to notice the zoom effect
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }

}
