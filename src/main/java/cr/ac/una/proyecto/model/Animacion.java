package cr.ac.una.proyecto.model;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Animacion {

    private RotateTransition rotate;

    private String imagenCara = "file:///C:/Users/justi/Desktop/Netbeans/pruebasAnimacionImagen/src/main/resources/cr/ac/una/pruebasanimacionimagen/cara.png";
    private String imagenReverso = "file:///C:/Users/justi/Desktop/Netbeans/pruebasAnimacionImagen/src/main/resources/cr/ac/una/pruebasanimacionimagen/reverso.png";

    private boolean caraVisible = true;

    public void saltoTarjeta(ImageView imageView, Stage stage) {
        double targetX = (stage.getWidth() - imageView.getBoundsInParent().getWidth()) / 2;
        double targetY = (stage.getHeight() - imageView.getBoundsInParent().getHeight()) / 2;
        System.out.println("Coordenadas del centro: (" + targetX + ", " + targetY + ")");

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), imageView);
        translateTransition.setToX(targetX);
        translateTransition.setToY(targetY);

        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), imageView);
        rotateTransition.setByAngle(360);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), imageView);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0.5);

        fadeOut.setOnFinished(e ->
        {
            if (caraVisible)
            {
                imageView.setImage(new Image(imagenReverso));
            } else
            {
                imageView.setImage(new Image(imagenCara));
            }
            caraVisible = !caraVisible;
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

    public void animacionRuleta(ImageView ruleta) {
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(ruleta);
        ruleta.setRotate(0);

        rotate.setDuration(Duration.seconds(3));
        rotate.setInterpolator(Interpolator.EASE_BOTH);

        Ruleta ruletaLogic = new Ruleta(); // Instancia de la clase Ruleta para generar el ángulo aleatorio
        double nuevoAnguloDetenido = ruletaLogic.generarAnguloAleatorio();

        rotate.setByAngle(360 * 6 + nuevoAnguloDetenido);
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setOnFinished(e ->
        {
            String categoria = ruletaLogic.determinarPosicionRuleta(nuevoAnguloDetenido);
            System.out.println("La ruleta se detuvo en la categoría de: " + categoria + "Angulo: " + String.valueOf(nuevoAnguloDetenido));
        });
        rotate.play();
    }

}
