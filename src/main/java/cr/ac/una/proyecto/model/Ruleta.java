package cr.ac.una.proyecto.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ruleta {

    private ArrayList<String> opciones = new ArrayList<>(Arrays.asList("Deporte", "Arte", "Geografía", "Entretenimiento", "Ciencia", "Historia"));
    private double anguloInicial = 0;

    public int generarAnguloAleatorio() {
        Random random = new Random();
        return random.nextInt(360);
    }

    public String determinarPosicionRuleta(double anguloDetenido) {
        double cienciaInicio = 29;
        double cienciaFin = 80;
        double geografiaInicio = 81;
        double geografiaFin = 134;
        double coronaInicio = 135;
        double coronaFin = 183;
        double entretenimientoInicio = 184;
        double entretenimientoFin = 233;
        double arteInicio = 234;
        double arteFin = 284;
        double deporteInicio = 285;
        double deporteFin = 336;
        double historiaInicio = 337;
        double historiaFin = 29;
        double finalGrados = 360;

        String categoria = "Angulo no encontrado" + String.valueOf(anguloDetenido);

        if (anguloDetenido >= deporteInicio && anguloDetenido <= deporteFin)
        {
            categoria = "Deporte";
        } else if (anguloDetenido >= arteInicio && anguloDetenido <= arteFin)
        {
            categoria = "Arte";
        } else if (anguloDetenido >= geografiaInicio && anguloDetenido <= geografiaFin)
        {
            categoria = "Geografía";
        } else if (anguloDetenido >= cienciaInicio && anguloDetenido <= cienciaFin)
        {
            categoria = "Ciencia";
        } else if (anguloDetenido >= coronaInicio && anguloDetenido <= coronaFin)
        {
            categoria = "Corona";
        } else if (anguloDetenido >= entretenimientoInicio && anguloDetenido <= entretenimientoFin)
        {
            categoria = "Entretenimiento";
        } else if ((anguloDetenido >= historiaInicio && anguloDetenido <= finalGrados) || (anguloDetenido >= anguloInicial && anguloDetenido <= historiaFin))
        {
            categoria = "Historia";
        }

        return categoria;
    }
}
