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
    private int rondas;
    private Ruleta ruleta;
    private Boolean valorRespuesta;
    private Boolean valorCoronaRuleta;
    private String dificultad;

    public Juego() {
        ruleta = new Ruleta();
        sectores = new ArrayList<>();
        imagenesPeones = new ArrayList<>();
        turnoActual = 0;
        rondas = 1;
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

    public void setSectorActualAppContext() {
        Sector sectorActual = sectores.get(turnoActual);
        setSectorJugadorDtoAppContext(sectorActual);
    }

    public void setSectoresAppContext() {
        AppContext.getInstance().set("JuegoSectores", sectores);
        System.out.println("Juego setSectorActualtoAppContextJugadorVersion: " + sectores.get(turnoActual).getJugador().getVersion());
    }

    public void cargarSectorActualFromAppContext() {
        sectores.set(turnoActual, (Sector) AppContext.getInstance().get("preguntaSector"));
        System.out.println("Juego cargarSectorActualFromAppContextJugadorVersion: " + sectores.get(turnoActual).getJugador().getVersion());
    }

    public void jugar(GridPane grdpTablero, boolean valorRespuesta, boolean isJugadorOnCrow) {
        cargarSectorActualFromAppContext();
        Sector sectorActual = sectores.get(turnoActual);
        ImageView imagenActual = imagenesPeones.get(turnoActual);
        JugadorDto jugadorActual = sectorActual.getJugador();

        if (isJugadorOnCrow) {
            sectores.get(turnoActual).setActualPosInFirst();
            sectores.get(turnoActual).mostrarPeonTablero(imagenActual);
            if (!valorRespuesta) {
                cambiarTurno();
            }
        } else {
            if (valorRespuesta) {
                sectorActual.setPosActual(sectorActual.mover(imagenActual, grdpTablero));
            } else {
                cambiarTurno();
            }
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

    private void cargarDificultadFromAppContext() {
        dificultad = (String) AppContext.getInstance().get("dificultad");
    }

    private void setSectorJugadorDtoAppContext(Sector sector) {
        AppContext.getInstance().set("preguntaSector", sector);
    }

    public void cargarAyudasFacil() {
        if (dificultad.equals("Facil")) {
            for (Sector sector : sectores) {
                sector.setAyudasFacil();
            }
        }
    }

    public void cambiarTurno() {
        turnoActual = (turnoActual + 1) % sectores.size();
        if (turnoActual == 0) {
            rondas++;
        }
    }

    public void cambiarPrimerTurno() {
        turnoActual = (turnoActual + 1) % sectores.size();
    }

    private void mostrarGanador(Sector sector) {
        System.out.println("Jugador: " + sector.getJugador().getNombre() + ", ha ganado la partida");
        System.out.println("Cantidad de rondas jugadas: " + rondas);
    }

    public void valdidarCoronasGanador() {
        int limiteRondasGanador = 25;
        boolean coronasActivas = true;
        if (rondas > limiteRondasGanador) {
            validarGanadorPorRondas();
        } else {
            Sector sectorActual = sectores.get(turnoActual);
            for (Corona c : sectorActual.getCoronas()) {
                if (!(c.getEstado())) {
                    coronasActivas = false;
                }
            }
            if (coronasActivas) {
                mostrarGanador(sectorActual);
            }
        }
    }

    public void validarGanadorPorRondas() {
        Sector ganador = null;
        int maxCoronasActivas = 0;
        ArrayList<Sector> sectoresEmpatados = new ArrayList<>();

        for (Sector sector : sectores) {
            int coronasActivas = contarCoronasActivas(sector);
            if (coronasActivas > maxCoronasActivas) {
                ganador = sector;
                maxCoronasActivas = coronasActivas;
                sectoresEmpatados.clear();
                sectoresEmpatados.add(sector);
            } else if (coronasActivas == maxCoronasActivas) {
                sectoresEmpatados.add(sector);
            }
        }

        if (sectoresEmpatados.size() == 1) {
            mostrarGanador(sectoresEmpatados.get(0));
        } else {
            manejarEmpate(sectoresEmpatados);
        }
    }

    private int contarCoronasActivas(Sector sector) {
        int contador = 0;
        for (Corona corona : sector.getCoronas()) {
            if (corona.getEstado()) {
                contador++;
            }
        }
        return contador;
    }

    public boolean validarPrimerTurnoObtencionDeCoronas(Sector sector) {
        if (rondas == 1) {
            int contador = contarCoronasActivas(sector);
            int limiteCoronas = 3;
            if (contador >= limiteCoronas) {
                cambiarTurno();
                return true;
            }
        }
        return false;
    }

    private void manejarEmpate(ArrayList<Sector> sectoresEmpatados) {
        // Función vacía que recibiría los sectores empatados
        // Implementación pendiente
        // comparar la cantidad preguntas respondidas y respondidas correctamente para determinar un ganador
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

    public Integer getRondas() {
        return this.rondas;
    }

    public String getDificultad() {
        return this.dificultad;
    }

}
