package cr.ac.una.proyecto.model;

import cr.ac.una.proyecto.service.JugadorService;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.FlowController;
import cr.ac.una.proyecto.util.RespuestaUtil;
import cr.ac.una.proyecto.util.Ruleta;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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

    public Juego(String datos, int slider) {
        ruleta = new Ruleta();
        sectores = new ArrayList<>();
        imagenesPeones = new ArrayList<>();
        turnoActual = 0;
        rondas = 1;
        dificultad = "";
        cargarDemasDatos(datos, slider);
    }

    private void cargarDemasDatos(String datos, int slider) {
        List<JugadorDto> jugadores = new ArrayList<>();
        List<Long> jugadoresIds = new ArrayList<>();
        List<Integer> turnos = new ArrayList<>();
        extractNumbersFromCurlyBraces(datos, jugadoresIds, turnos);
        jugadores = getJugadoresFromDataBase(jugadoresIds);
        System.out.println("Jugadores ids: " + jugadoresIds);
        System.out.println("Jugadores turnos: " + turnos);
        System.out.println("Jugadores: " + jugadores);
        sectoresCargar(slider);

        if (jugadoresIds.size() == slider && turnos.size() == slider) {
            for (int index = 0; index < slider; index++) {
                sectores.get(index).setJugador(jugadores.get(index));
                sectores.get(index).setPosActual(turnos.get(index));
                //sectores.get(index).setRutaImagenJugador(rutasImagenes.get(index));
            }
        }

    }

    private List<JugadorDto> getJugadoresFromDataBase(List<Long> jugadoresIds) {
        List<JugadorDto> jugadores = new ArrayList<>();
        JugadorService jugadorService = new JugadorService();
        for (Long id : jugadoresIds) {
            JugadorDto jugador = new JugadorDto();
            RespuestaUtil respuesta = jugadorService.getJugador(id);
            jugador = (JugadorDto) respuesta.getResultado("Jugador");
            if (jugador != null) {

            } else {
//crear Jugador por defecto
            }
            jugadores.add(jugador);
        }
        return jugadores;
    }

    public static void extractNumbersFromCurlyBraces(String input, List<Long> ids, List<Integer> turnos) {
        boolean expectId = false;
        boolean expectTurno = false;
        StringBuilder numberBuffer = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            if (currentChar == '{') {
                expectId = true;  // set the flag to expect an id next
                numberBuffer.setLength(0);  // clear the buffer just in case
            } else if (expectId && Character.isDigit(currentChar)) {
                numberBuffer.append(currentChar);
            } else if (expectId && currentChar == ',') {
                if (numberBuffer.length() > 0) {
                    ids.add(Long.parseLong(numberBuffer.toString()));
                    numberBuffer.setLength(0);  // clear the buffer
                    expectId = false;
                    expectTurno = true;  // now expect the turno number
                }
            } else if (expectTurno && Character.isDigit(currentChar)) {
                numberBuffer.append(currentChar);
            } else if (expectTurno && !Character.isDigit(currentChar)) {
                if (numberBuffer.length() > 0) {
                    turnos.add(Integer.parseInt(numberBuffer.toString()));
                    numberBuffer.setLength(0);  // clear the buffer
                    expectTurno = false;  // reset the flag
                }
            } else {
                expectId = false;
                expectTurno = false;
            }
        }

        if (expectTurno && numberBuffer.length() > 0) {
            turnos.add(Integer.parseInt(numberBuffer.toString()));
        }

    }


    public void agregarSector(Sector sector) {
        sectores.add(sector);
    }

    public void cargarDatosImagenes(GridPane grdpTablero) {// cargar las imagenes del jugadorPeon que estan dentro de
        // los sectores y meterlos en el gridPane
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
    }

    public void cargarSectorActualFromAppContext() {
        sectores.set(turnoActual, (Sector) AppContext.getInstance().get("preguntaSector"));
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

    public ArrayList<Sector> getSectores() {
        return sectores;
    }

    private void cargarDificultadFromAppContext() {
        dificultad = (String) AppContext.getInstance().get("dificultad");
    }

    private void setSectorJugadorDtoAppContext(Sector sector) {
        AppContext.getInstance().set("preguntaSector", sector);
    }

    public void cargarAyudasSegunDificultad() {
        if (dificultad.equals("Facil")) {
            for (Sector sector : sectores) {
                sector.setAyudasFacil();
            }
        } else if (dificultad.equals("Media")) {
            for (Sector sector : sectores) {
                sector.setAyudasFacil();
                sector.habilitarAyudas(false);
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

    public void mostrarGanador(Sector sector, AnchorPane anchorPane) {
        FlowController.getInstance().goViewInWindow("WinnerView");
        ((Stage) anchorPane.getScene().getWindow()).close();
    }


    public void valdidarCoronasGanador(AnchorPane anchorPane) {
        int limiteRondasGanador = 25;
        boolean coronasActivas = true;
        if (rondas > limiteRondasGanador) {
            validarGanadorPorRondas(anchorPane);
        } else {
            Sector sectorActual = sectores.get(turnoActual);
            for (Corona c : sectorActual.getCoronas()) {
                if (!(c.getEstado())) {
                    coronasActivas = false;
                }
            }
            if (coronasActivas) {
                String nombre = sectorActual.getJugador().getNombre();
                AppContext.getInstance().set("nombreGanador", nombre);
                mostrarGanador(sectorActual, anchorPane);
            }
        }
    }

    public void validarGanadorPorRondas(AnchorPane anchorPane) {
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
            mostrarGanador(sectoresEmpatados.get(0), anchorPane);
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
        // comparar la cantidad preguntas respondidas y respondidas correctamente para
        // determinar un ganador
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
        return turnoActual + ", " + dificultad + "," + sectores.toString();
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

    public void sectoresCargar(int slider) {
        sectores.clear();
        switch (slider) {
            case 2:
                sectores.add(new Sector());
                sectores.add(new Sector());

                break;
            case 3:
                sectores.add(new Sector());
                sectores.add(new Sector());
                sectores.add(new Sector());


                break;
            case 4:
                sectores.add(new Sector());
                sectores.add(new Sector());
                sectores.add(new Sector());
                sectores.add(new Sector());

                break;
            case 5:
                sectores.add(new Sector());
                sectores.add(new Sector());
                sectores.add(new Sector());
                sectores.add(new Sector());
                sectores.add(new Sector());

                break;
            case 6:
                sectores.add(new Sector());
                sectores.add(new Sector());
                sectores.add(new Sector());
                sectores.add(new Sector());
                sectores.add(new Sector());
                sectores.add(new Sector());
                break;
        }
    }

}
