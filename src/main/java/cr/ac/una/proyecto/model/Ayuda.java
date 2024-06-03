package cr.ac.una.proyecto.model;

/**
 *
 * @author justi
 */
public class Ayuda {

    private boolean ayudaActiva;
    private String nombre;

    public Ayuda() {

    }

    public Ayuda(String nombre, boolean ayuda) {
        this.ayudaActiva = ayuda;
        this.nombre = nombre;
    }

    public boolean isAyudaActiva() {
        return ayudaActiva;
    }

    public void setAyudaActiva(boolean ayudaActiva) {
        this.ayudaActiva = ayudaActiva;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
