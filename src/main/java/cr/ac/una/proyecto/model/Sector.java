package cr.ac.una.proyecto.model;

import javafx.geometry.HPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Sector {

    private Jugador jugador;
    private int posicionX;
    private int posicionY;
    private int posActual;
    private int direccion;
    private String rutaImagenJugador;

    public Sector() {
    }

    public Sector(Jugador jugador, int Xpos, int Ypos, int direccion, String rutaImagenJugador) {
        this.jugador = jugador;
        this.posicionX = Xpos;
        this.posicionY = Ypos;
        this.direccion = direccion;
        this.rutaImagenJugador = rutaImagenJugador;
        setActualPosition();

    }

    private void setActualPosition() {
        if (direccion == 1 || direccion == 2) {
            this.posActual = this.posicionY;
        } else {
            this.posActual = this.posicionX;
        }
    }

    public int getDireccion() {
        return direccion;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
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

    private void moverNodoA(ImageView imageView, int columna, int fila) {
        GridPane.setColumnIndex(imageView, columna);
        GridPane.setRowIndex(imageView, fila);
        GridPane.setHalignment(imageView, HPos.CENTER);
    }

    public int moverDerecha(ImageView imageView, GridPane grdPane) {
        if (posActual >= posicionY + 3) {
            posActual = posicionY;
        } else {
            posActual++;
        }
        moverNodoA(imageView, posActual, posicionX);
        return posActual;
    }

    public int moverIzquierda(ImageView imageView, GridPane grdPane) {
        if (posActual <= posicionY - 3) {
            posActual = posicionY;
        } else {
            posActual--;
        }
        moverNodoA(imageView, posActual, posicionX);
        return posActual;
    }

    public int moverAbajo(ImageView imageView, GridPane grdPane) {
        if (posActual >= posicionX + 3) {
            posActual = posicionX;
        } else {
            posActual++;
        }
        moverNodoA(imageView, posicionY, posActual);
        return posActual;
    }

    public int moverArriba(ImageView imageView, GridPane grdPane) {
        if (posActual <= posicionX - 3) {
            posActual = posicionX;
        } else {
            posActual--;
        }
        moverNodoA(imageView, posicionY, posActual);
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
        return "Sector{"
                + "jugador=" + (jugador != null ? jugador.toString() : "null")
                + ", posicionFija=" + posicionX
                + ", posicionInicial=" + posicionY
                + ", posActual=" + posActual
                + ", direccion=" + direccion
                + ", rutaImagenJugador='" + rutaImagenJugador + '\''
                + '}';
    }
}
