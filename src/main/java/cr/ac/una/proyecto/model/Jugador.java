package cr.ac.una.proyecto.model;

public class Jugador {

    private String nombre;
    private int puntos;

    public Jugador() {
    }

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntos = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void aumentarPuntos() {
        puntos++;
    }

    @Override
    public String toString() {
        return "Jugador: " + nombre + ", Puntos: " + puntos;
    }

}
