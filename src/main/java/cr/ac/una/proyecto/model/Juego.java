package cr.ac.una.proyecto.model;

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
    private ArrayList<ImageView> imagenesPeones;
    private int turnoActual;
    private Scanner scanner;

    private String resultadoRuleta;
    private Ruleta ruleta;

    public Juego() {
        ruleta = new Ruleta();
        sectores = new ArrayList<>();
        preguntas = new ArrayList<>();
        imagenesPeones = new ArrayList<>();
        scanner = new Scanner(System.in);
        turnoActual = 0;
        cargarPreguntas();
    }

    public void agregarSector(Sector sector) {
        sectores.add(sector);
    }

    public void agregarPregunta(Pregunta pregunta) {
        preguntas.add(pregunta);
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

    public void jugar(GridPane grdpTablero) {// manejo turnos de los jugadores->Sectores->Imagenes
        Sector sectorActual = sectores.get(turnoActual);
        ImageView imagenActual = imagenesPeones.get(turnoActual);
        Jugador jugadorActual = sectorActual.getJugador();
        System.out.println("Turno de " + jugadorActual.getNombre());
        Pregunta preguntaActual = obtenerPreguntaAleatoria();
        System.out.println("Pregunta: " + preguntaActual.getEnunciado());
        System.out.print("Respuesta: ");
        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase(preguntaActual.getRespuesta()))
        {
            jugadorActual.aumentarPuntos();
            System.out.println("Respuesta correcta. ¡Has ganado un punto!, puedes girar de nuevo" + sectorActual.getJugador().getNombre());
            sectorActual.setPosActual(sectorActual.mover(imagenActual, grdpTablero));
        } else
        {
            System.out.println("Respuesta incorrecta. Siguiente jugador.");
            cambiarTurno();
        }

    }

    private boolean hayGanador() {
        for (Sector sector : sectores)
        {
            Jugador jugador = sector.getJugador();
            if (jugador.getPuntos() >= 6)
            {
                return true;
            }
        }
        return false;
    }

    private Pregunta obtenerPreguntaAleatoria() {
        int indicePregunta = (int) (Math.random() * preguntas.size());
        return preguntas.get(indicePregunta);
    }

    private void cambiarTurno() {
        turnoActual = (turnoActual + 1) % sectores.size();
    }

    private void mostrarGanador() {
        for (Sector sector : sectores)
        {
            Jugador jugador = sector.getJugador();
            if (jugador.getPuntos() >= 6)
            {
                System.out.println("¡El ganador es: " + jugador.getNombre() + " con " + jugador.getPuntos() + " puntos!");
                break;
            }
        }
    }

    public void obtenerInfoSectores() {
        System.out.println("Informacion clase juego sectores: ");
        for (Sector sector : sectores)
        {
            System.out.println(sector.getJugador().toString());
        }
    }

    private void cargarPreguntas() {
        agregarPregunta(new Pregunta("¿1?", "1", "Historia"));
        agregarPregunta(new Pregunta("¿2?", "2", "Deporte"));
        agregarPregunta(new Pregunta("¿3?", "3", "Arte"));
        agregarPregunta(new Pregunta("¿4?", "4", "Entretenimiento"));
        agregarPregunta(new Pregunta("¿5?", "5", "Geografia"));
        agregarPregunta(new Pregunta("¿6?", "6", "Ciencia"));
        agregarPregunta(new Pregunta("¿7?", "7", "Corona"));
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
