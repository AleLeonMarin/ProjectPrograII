package cr.ac.una.proyecto.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javafx.geometry.HPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Sector {

    private JugadorDto jugador;
    private int posicionX;
    private int posicionY;
    private int posActual;
    private int direccion;
    private String rutaImagenJugador;
    private ArrayList<Ayuda> ayudas;
    private ArrayList<Corona> coronas;

    boolean isOnCoronaPos;

    public Sector() {
        this.ayudas = new ArrayList<>();
        this.coronas = new ArrayList<>();
    }

    public Sector(JugadorDto jugador, int Xpos, int Ypos, int direccion, String rutaImagenJugador) {
        this.jugador = jugador;
        this.posicionX = Xpos;
        this.posicionY = Ypos;
        this.direccion = direccion;
        this.rutaImagenJugador = rutaImagenJugador;
        this.ayudas = new ArrayList<>();
        setActualPosInFirst();
        this.isOnCoronaPos = false;
        establecerCoronas();

    }

    public void setActualPosInFirst() {
        if (direccion == 1 || direccion == 2) {
            this.posActual = this.posicionY;
        } else {
            this.posActual = this.posicionX;
        }
    }


    public int getDireccion() {
        return direccion;
    }

    public JugadorDto getJugador() {
        return jugador;
    }

    public void setJugador(JugadorDto jugador) {
        this.jugador = jugador;
    }

    public int getPosicionFija() {
        return posicionX;
    }

    public void setPosicionFija(int posicionFija) {
        this.posicionX = posicionFija;
    }

    public int getPosicionInicial() {
        return posicionY;
    }

    public void setPosicionInicial(int posicionInicial) {
        this.posicionY = posicionInicial;
    }

    public int getPosActual() {
        return posActual;
    }

    public void setPosActual(int posActual) {
        this.posActual = posActual;
    }

    public String getRutaImagenJugador() {
        return rutaImagenJugador;
    }

    public void setRutaImagenJugador(String rutaImagenJugador) {
        this.rutaImagenJugador = rutaImagenJugador;
    }

    public ArrayList<Ayuda> getAyudas() {
        return this.ayudas;
    }

    public void setAyudas(ArrayList<Ayuda> ayudas) {
        this.ayudas = ayudas;
    }

    public boolean getIsOnCoronaPos() {
        return isOnCoronaPos;
    }

    public void setIsOnCoronaPos(boolean isOnCoronaPos) {
        this.isOnCoronaPos = isOnCoronaPos;
    }

    public ArrayList<Corona> getCoronas() {
        return coronas;
    }

    private void moverNodoA(ImageView imageView, int columna, int fila) {
        GridPane.setColumnIndex(imageView, columna);
        GridPane.setRowIndex(imageView, fila);
        GridPane.setHalignment(imageView, HPos.CENTER);
    }

    public void mostrarPeonTablero(ImageView imageView) {
        if (direccion == 1 || direccion == 2) {
            moverNodoA(imageView, posActual, posicionX);
        } else {
            moverNodoA(imageView, posicionY, posActual);
        }
    }


    private void checkCoronaPos(int posCorona) {

        if (posActual == posCorona) {
            isOnCoronaPos = true;
        } else {
            isOnCoronaPos = false;
        }
    }

    public int moverDerecha(ImageView imageView, GridPane grdPane) {
        if (posActual >= posicionY + 3) {
            posActual = posicionY;
        } else {
            posActual++;
        }
        moverNodoA(imageView, posActual, posicionX);
        checkCoronaPos(posicionY + 3);
        return posActual;
    }

    public int moverIzquierda(ImageView imageView, GridPane grdPane) {
        if (posActual <= posicionY - 3) {
            posActual = posicionY;
        } else {
            posActual--;

        }
        moverNodoA(imageView, posActual, posicionX);
        checkCoronaPos(posicionY - 3);
        return posActual;
    }

    public int moverAbajo(ImageView imageView, GridPane grdPane) {
        if (posActual >= posicionX + 3) {
            posActual = posicionX;
        } else {
            posActual++;

        }
        moverNodoA(imageView, posicionY, posActual);
        checkCoronaPos(posicionX + 3);
        return posActual;
    }

    public int moverArriba(ImageView imageView, GridPane grdPane) {
        if (posActual <= posicionX - 3) {
            posActual = posicionX;
        } else {
            posActual--;

        }
        moverNodoA(imageView, posicionY, posActual);
        checkCoronaPos(posicionX - 3);
        return posActual;
    }

    public int mover(ImageView imageView, GridPane grdPane) {
        switch (direccion) {
            case 1:
                return moverDerecha(imageView, grdPane);
            case 2:
                return moverIzquierda(imageView, grdPane);
            case 3:
                return moverAbajo(imageView, grdPane);
            case 4:
                return moverArriba(imageView, grdPane);
            default:
                throw new IllegalArgumentException("Dirección no válida: " + direccion);
        }
    }

    @Override
    public String toString() {
        return jugador.getNombre() + "," + posActual + "," + direccion + "," + rutaImagenJugador;
    }

    public void removerAyuda(String ayudaNombre) {
        for (Ayuda ayuda : ayudas) {
            if (ayuda.getNombre().equals(ayudaNombre)) {
                ayuda.setEstado(false);
            }
        }
    }

    public void setAyudasFacil() {
        this.ayudas = new ArrayList<>();
        this.ayudas.clear();
        this.ayudas.add(new Ayuda("Bomba", true));
        this.ayudas.add(new Ayuda("Pasar", true));
        this.ayudas.add(new Ayuda("DobleOportunidad", true));
        this.ayudas.add(new Ayuda("TirarRuleta", true));
        printAyudasInfo();
    }

    public void establecerCoronas() {
        this.coronas = new ArrayList<>();
        ArrayList<String> categorias = new ArrayList<>(Arrays.asList("Deportes", "Arte", "Geografia", "Ciencia", "Entretenimiento", "Historia"));

        for (String categoria : categorias) {
            coronas.add(new Corona(categoria));
        }

    }

    public void setEstadoCorona(String coronaNombre, boolean estado) {
        for (Corona corona : coronas) {
            if (corona.getNombre().equals(coronaNombre)) {
                corona.setEstado(estado);
            }
        }
    }

    public void printCoronasInfo() {
        for (Corona corona : coronas) {
            if (corona.getEstado()) {
                System.out.println("Corona Activa: " + corona.getNombre());
            }
        }
    }

    public void printAyudasInfo() {
        for (Ayuda ayuda : ayudas) {
            if (ayuda.getEstado()) {
                System.out.println("Ayuda Activa: " + ayuda.getNombre());
            }
        }

    }

    public void setAyudaRandom(int cantidadAyudasRandom) {
        int index = 0;
        Random random = new Random();
        while (index < cantidadAyudasRandom && !hasAllHints()) {
            int numeroAleatorioInt = random.nextInt(ayudas.size());

            if (!(ayudas.get(numeroAleatorioInt).getEstado())) {
                ayudas.get(numeroAleatorioInt).setEstado(true);
                index++;
            }

        }

    }

    private boolean hasAllHints() {
        for (Ayuda ayuda : ayudas) {
            if (!ayuda.getEstado()) {
                return false;
            }
        }
        return true;
    }

}
