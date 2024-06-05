package cr.ac.una.proyecto.model;

import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.Ruleta;

import java.util.ArrayList;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Juego {

    private ArrayList<Sector> sectores;
    private ArrayList<ImageView> imagenesPeones;
    private int turnoActual;
    private Ruleta ruleta;
    private Boolean valorRespuesta;
    private Boolean valorCoronaRuleta;
    private String dificultad;

    public Juego() {
        ruleta = new Ruleta();
        sectores = new ArrayList<>();
        imagenesPeones = new ArrayList<>();
        turnoActual = 0;
        dificultad = "";
        cargarDificultadFromAppContext();
    }

    public void agregarSector(Sector sector) {
        sectores.add(sector);

    }

    public void cargarDatosImagenes(GridPane grdpTablero) {// cargar las imagenes del jugadorPeon que estan dentro de los sectores y meterlos en el gridPane
        for (Sector sectorActual : sectores) {
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

    public void cargarSectorActualAppContext() {
        Sector sectorActual = sectores.get(turnoActual);
        setSectorJugadorDtoAppContext(sectorActual);
    }

    public void jugar(GridPane grdpTablero) {
        Sector sectorActual = sectores.get(turnoActual);
        ImageView imagenActual = imagenesPeones.get(turnoActual);
        JugadorDto jugadorActual = sectorActual.getJugador();
        cargarPreguntaViewValorRespuesta();
        sectorActual = (Sector) AppContext.getInstance().get("preguntaSector");
        sectores.set(turnoActual, sectorActual);

        if (valorRespuesta) {
            System.out.println("Respuesta correcta. ¡Has ganado un punto!, puedes girar de nuevo" + sectorActual.getJugador().getNombre());
            sectorActual.setPosActual(sectorActual.mover(imagenActual, grdpTablero));

        } else {
            System.out.println("Respuesta incorrecta. Siguiente jugador.");
            cambiarTurno();
        }
    }

    public Sector getSectorActual() {

        if (sectores.get(turnoActual) != null) {
            return sectores.get(turnoActual);
        } else {
            return null;
        }
    }

    private void cargarRuletaCoronaPos() {
        valorCoronaRuleta = (Boolean) AppContext.getInstance().get("valorCoronaRuleta");
    }

    private void cargarPreguntaViewValorRespuesta() {
        valorRespuesta = (Boolean) AppContext.getInstance().get("valorRespuesta");
    }

    private void cargarDificultadFromAppContext() {
        dificultad = (String) AppContext.getInstance().get("dificultad");
    }

    public ArrayList<Ayuda> getAllAyudas() {
        ArrayList<Ayuda> ayudas = new ArrayList<>();
        ArrayList<String> nombresAyudas = new ArrayList<>();

        nombresAyudas.add("Bomba");
        nombresAyudas.add("Pasar");
        nombresAyudas.add("DobleOportunidad");
        nombresAyudas.add("TirarRuleta");

        ayudas.add(new Ayuda(nombresAyudas.get(0), true));
        ayudas.add(new Ayuda(nombresAyudas.get(1), true));
        ayudas.add(new Ayuda(nombresAyudas.get(2), true));
        ayudas.add(new Ayuda(nombresAyudas.get(3), true));
        return ayudas;

    }

    private void setSectorJugadorDtoAppContext(Sector sector) {
        AppContext.getInstance().set("preguntaSector", sector);
    }

    public void cambiarTurno() {
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

    public JugadorDto getJugadorPregunta() {
        return sectores.get(turnoActual).getJugador();
    }

    public String toString() {
        return sectores.toString() + turnoActual;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public void setTurnoActual(int turnoActual) {
        this.turnoActual = turnoActual;
    }

}
