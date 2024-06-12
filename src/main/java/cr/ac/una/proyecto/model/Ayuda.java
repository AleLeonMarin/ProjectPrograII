package cr.ac.una.proyecto.model;

/**
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

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString() {
        if (this.estado) {
            return nombre + "-" + estado;
        } else {
            return "";
        }
    }

}
