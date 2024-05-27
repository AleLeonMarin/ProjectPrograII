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
        cargarPreguntas();
        cargarRespuestas();
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
            jugadorActual.aumentarPuntos();
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

    private void cargarPreguntas() {
        agregarPregunta(new Pregunta("¿En qué año comenzó la Primera Guerra Mundial?", "1914", "Historia", 1));
        agregarPregunta(new Pregunta("¿Quién ganó la Copa del Mundo de la FIFA en 2018?", "Francia", "Deporte", 2));
        agregarPregunta(new Pregunta("¿Qué científica ganó dos premios Nobel en distintas disciplinas?", "Marie Curie", "Ciencia", 3));
        agregarPregunta(new Pregunta("¿Quién pintó la Mona Lisa?", "Leonardo da Vinci", "Arte", 4));
        agregarPregunta(new Pregunta("¿Cuál es la capital de Australia?", "Canberra", "Geografia", 5));
        agregarPregunta(new Pregunta("¿Quién protagonizó la película 'Titanic'?", "Leonardo DiCaprio", "Entretenimiento", 6));
        agregarPregunta(new Pregunta("¿Cuál es la piedra preciosa de la corona británica?", "Diamante Koh-i-Noor", "Corona", 7));

        AppContext.getInstance().set("preguntas", preguntas);

    }

    private void cargarRespuestas() {
        agregarRespuesta(new Respuesta(1, "1912", false));
        agregarRespuesta(new Respuesta(1, "1914", true));
        agregarRespuesta(new Respuesta(1, "1916", false));
        agregarRespuesta(new Respuesta(1, "1918", false));

        agregarRespuesta(new Respuesta(2, "Alemania", false));
        agregarRespuesta(new Respuesta(2, "Brasil", false));
        agregarRespuesta(new Respuesta(2, "Francia", true));
        agregarRespuesta(new Respuesta(2, "Argentina", false));

        agregarRespuesta(new Respuesta(3, "Rosalind Franklin", false));
        agregarRespuesta(new Respuesta(3, "Lise Meitner", false));
        agregarRespuesta(new Respuesta(3, "Marie Curie", true));
        agregarRespuesta(new Respuesta(3, "Dorothy Hodgkin", false));

        agregarRespuesta(new Respuesta(4, "Vincent van Gogh", false));
        agregarRespuesta(new Respuesta(4, "Leonardo da Vinci", true));
        agregarRespuesta(new Respuesta(4, "Pablo Picasso", false));
        agregarRespuesta(new Respuesta(4, "Claude Monet", false));

        agregarRespuesta(new Respuesta(5, "Sídney", false));
        agregarRespuesta(new Respuesta(5, "Melbourne", false));
        agregarRespuesta(new Respuesta(5, "Canberra", true));
        agregarRespuesta(new Respuesta(5, "Brisbane", false));

        agregarRespuesta(new Respuesta(6, "Brad Pitt", false));
        agregarRespuesta(new Respuesta(6, "Tom Cruise", false));
        agregarRespuesta(new Respuesta(6, "Leonardo DiCaprio", true));
        agregarRespuesta(new Respuesta(6, "Johnny Depp", false));

        agregarRespuesta(new Respuesta(7, "Rubí", false));
        agregarRespuesta(new Respuesta(7, "Esmeralda", false));
        agregarRespuesta(new Respuesta(7, "Diamante Koh-i-Noor", true));
        agregarRespuesta(new Respuesta(7, "Zafiro", false));

        AppContext.getInstance().set("respuestas", respuestas);
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
