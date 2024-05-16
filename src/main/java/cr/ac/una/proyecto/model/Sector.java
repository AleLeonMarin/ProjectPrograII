package cr.ac.una.proyecto.model;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Sector {

    private Jugador jugador;
    private int posicionFija;
    private int posicionInicial;
    private int posicionCorona;
    private int posActual;
    private String rutaImagenJugador;

    public Sector() {
    }

    public Sector(Jugador jugador, int posicionFija, int posicionInicial, String rutaImagenJugador) {
        this.jugador = jugador;
        this.posicionFija = posicionFija;
        this.posicionInicial = posicionInicial;
        this.rutaImagenJugador = rutaImagenJugador;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public int getPosicionFija() {
        return posicionFija;
    }

    public void setPosicionFija(int posicionFija) {
        this.posicionFija = posicionFija;
    }

    public int getPosicionInicial() {
        return posicionInicial;
    }

    public void setPosicionInicial(int posicionInicial) {
        this.posicionInicial = posicionInicial;
    }

    public int getPosicionCorona() {
        return posicionCorona;
    }

    public void setPosicionCorona(int posicionCorona) {
        this.posicionCorona = posicionCorona;
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

  

    public int evaluarPos(ImageView imageView, GridPane grdPane) {

        eliminarNodoEnPosicion(posActual, posicionFija, grdPane );

        if (this.posActual >= posicionInicial + 3)
        {
            posActual = posicionInicial;
        } else
        {
            posActual++;
        }

        grdPane.add(imageView, posActual, posicionFija);
        GridPane.setHalignment(imageView, HPos.CENTER);
        return posActual;
    }

    private void eliminarNodoEnPosicion(int columna, int fila, GridPane grdPane) {
        ObservableList<Node> children = grdPane.getChildren();
        children.removeIf(node -> GridPane.getRowIndex(node) == fila && GridPane.getColumnIndex(node) == columna);
    }

}
