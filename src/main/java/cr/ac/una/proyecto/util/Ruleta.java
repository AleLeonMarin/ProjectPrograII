package cr.ac.una.proyecto.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ruleta {

    private ArrayList<String> categorias;
    private double anguloInicial;
    private double anguloDetenido;

    public Ruleta() {
        categorias = new ArrayList<>(Arrays.asList("Deportes", "Arte", "Geografia", "Ciencia", "Corona", "Entretenimiento", "Historia"));
        anguloInicial = 0;
        anguloDetenido = 0;

        AppContext.getInstance().set("categoriasRuleta", categorias);
    }

    private int generarAnguloAleatorio() {
        Random random = new Random();
        return random.nextInt(360);
    }

    public double getAnguloDetenido() {
        return anguloDetenido;
    }

    public String determinarPosicionRuleta() {
        anguloDetenido = generarAnguloAleatorio();
        double cienciaInicio = 25;
        double cienciaFin = 76;
        double geografiaInicio = 77;
        double geografiaFin = 127;
        double coronaInicio = 128;
        double coronaFin = 178;
        double entretenimientoInicio = 179;
        double entretenimientoFin = 231;
        double arteInicio = 232;
        double arteFin = 280;
        double deporteInicio = 281;
        double deporteFin = 331;
        double historiaInicio = 332;
        double historiaFin = 24;
        double finalGrados = 360;

        String categoria = "Angulo no encontrado: " + anguloDetenido;

        if (anguloDetenido >= deporteInicio && anguloDetenido <= deporteFin) {
            categoria = categorias.get(0);
        } else if (anguloDetenido >= arteInicio && anguloDetenido <= arteFin) {
            categoria = categorias.get(1);
        } else if (anguloDetenido >= geografiaInicio && anguloDetenido <= geografiaFin) {
            categoria = categorias.get(2);
        } else if (anguloDetenido >= cienciaInicio && anguloDetenido <= cienciaFin) {
            categoria = categorias.get(3);
        } else if (anguloDetenido >= coronaInicio && anguloDetenido <= coronaFin) {
            categoria = categorias.get(4);
        } else if (anguloDetenido >= entretenimientoInicio && anguloDetenido <= entretenimientoFin) {
            categoria = categorias.get(5);
        } else if ((anguloDetenido >= historiaInicio && anguloDetenido <= finalGrados) || (anguloDetenido >= anguloInicial && anguloDetenido <= historiaFin)) {
            categoria = categorias.get(6);
        }
        return categorias.get(4);
    }
}
