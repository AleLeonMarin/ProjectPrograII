package cr.ac.una.proyecto.model;

/**
 *
 * @author justi
 */
public class Ayuda {

    private boolean estado;
    private String nombre;

    public Ayuda() {

    }

    public Ayuda(String nombre, boolean ayuda) {
        this.estado = ayuda;
        this.nombre = nombre;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean ayudaActiva) {
        this.estado = ayudaActiva;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
