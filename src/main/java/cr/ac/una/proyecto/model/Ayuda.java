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

    public Ayuda(String nombre) {
        this.nombre = nombre;
        this.estado = false;
    }

    public boolean habilitarPorNombre(String nombre) {
        if (nombre != null && nombre.equals(this.nombre)) {
            this.estado = true;
            return true;
        }
        return false;
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
            return nombre;
        } else {
            return "";
        }
    }

}
