package cr.ac.una.proyecto.model;

import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.Ruleta;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Juego {

    private ArrayList<Sector> sectores;
    private ArrayList<Pregunta> preguntas;
    private ArrayList<Respuesta> respuestas;
    private ArrayList<ImageView> imagenesPeones;
    private int turnoActual;

    private String resultadoRuleta;
    private Ruleta ruleta;
    private Boolean valorRespuesta;

    public Juego() {
        ruleta = new Ruleta();
        sectores = new ArrayList<>();
        preguntas = new ArrayList<>();
        respuestas = new ArrayList<>();
        imagenesPeones = new ArrayList<>();
        turnoActual = 0;
    }

    public void agregarSector(Sector sector) {
        sectores.add(sector);
    }

    public void agregarPregunta(Pregunta pregunta) {
        preguntas.add(pregunta);
    }

    private void agregarRespuesta(Respuesta respuesta) {
        respuestas.add(respuesta);
    }

    public void cargarDatosImagenes(GridPane grdpTablero) {// cargar las imagenes del jugadorPeon que estan dentro de los sectores y meterlos en el gridPane
        for (Sector sectorActual : sectores)
        {
            ImageView imvPeon = new ImageView();
            Image imagenPeon = new Image(getClass().getResourceAsStream(sectorActual.getRutaImagenJugador()));
            System.out.println("Ruta de la imagen: " + sectorActual.getRutaImagenJugador());
            imvPeon.setImage(imagenPeon);
            imvPeon.setFitWidth(100);
            imvPeon.setFitHeight(100);
            imagenesPeones.add(imvPeon);
            grdpTablero.add(imvPeon, sectorActual.getPosicionInicial(), sectorActual.getPosicionFija());

            GridPane.setHalignment(imvPeon, HPos.CENTER);
            GridPane.setValignment(imvPeon, VPos.CENTER);
        }
    }

    public void jugar(GridPane grdpTablero) {
        Sector sectorActual = sectores.get(turnoActual);
        ImageView imagenActual = imagenesPeones.get(turnoActual);
        Jugador jugadorActual = sectorActual.getJugador();
        cargarPreguntaViewValorRespuesta();
        if (valorRespuesta)
        {
            //jugadorActual sumarCorona
            System.out.println("Respuesta correcta. ¡Has ganado un punto!, puedes girar de nuevo" + sectorActual.getJugador().getNombre());
            sectorActual.setPosActual(sectorActual.mover(imagenActual, grdpTablero));
        } else
        {
            System.out.println("Respuesta incorrecta. Siguiente jugador.");
            cambiarTurno();
        }

    }

    private void cargarPreguntaViewValorRespuesta() {
        valorRespuesta = (Boolean) AppContext.getInstance().get("valorRespuesta");
    }

    private Pregunta obtenerPreguntaAleatoria() {
        int indicePregunta = (int) (Math.random() * preguntas.size());
        return preguntas.get(indicePregunta);
    }

    private void cambiarTurno() {
        turnoActual = (turnoActual + 1) % sectores.size();
    }

    private void mostrarGanador() {

    }

    public double getRuletaAngulo() {
        return this.ruleta.getAnguloDetenido();
    }

    public String obtenerPosicionRuleta() {
        return this.ruleta.determinarPosicionRuleta();
    }

    public Jugador getJugadorPregunta() {
        return sectores.get(turnoActual).getJugador();
    }
}
