package cr.ac.una.proyecto.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    boolean isOnCoronaPos;

    public Sector() {
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
        return ayudas;
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

    private void moverNodoA(ImageView imageView, int columna, int fila) {
        GridPane.setColumnIndex(imageView, columna);
        GridPane.setRowIndex(imageView, fila);
        GridPane.setHalignment(imageView, HPos.CENTER);
    }

    private void checkCoronaPos(int posCorona) {

        System.out.println("POSICION CORONA ES: " + posCorona);
        System.out.println("POSICION ACTUAL: ES: " + posActual);
        if (posActual == posCorona) {
            isOnCoronaPos = true;
        } else {
            isOnCoronaPos = false;
        }
    }

    public int moverDerecha(ImageView imageView, GridPane grdPane) {
        System.out.println("POSACTUAL: " + posActual);
        if (posActual >= posicionY + 3) {
            posActual = posicionY;
        } else {
            posActual++;
        }
        moverNodoA(imageView, posActual, posicionX);
        System.out.println("POSACTUAL: " + posActual);
        checkCoronaPos(posicionY + 3);
        return posActual;
    }

    public int moverIzquierda(ImageView imageView, GridPane grdPane) {
        System.out.println("POSDESPUESACTUAL: " + posActual);

        if (posActual <= posicionY - 3) {
            posActual = posicionY;
        } else {
            posActual--;

        }
        moverNodoA(imageView, posActual, posicionX);
        System.out.println("POSDESPUESACTUAL: " + posActual);

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
        Iterator<Ayuda> iterator = ayudas.iterator();
        while (iterator.hasNext()) {
            Ayuda ayuda = iterator.next();
            if (ayudaNombre.equals(ayuda.getNombre())) {
                iterator.remove();
            }
        }
    }

}
