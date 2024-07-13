package cr.ac.una.proyecto.model;

/**
 * @author justi
 */
public class Corona {

    private boolean estado;
    private String nombre;

    public Corona() {

    }

    public Corona(String nombre) {
        this.nombre = nombre;
        this.estado = false;
    }

    public Corona(String nombre, boolean estado) {
        this.estado = estado;
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

    public boolean habilitarPorNombre(String nombre) {
        if (nombre != null && nombre.equals(this.nombre)) {
            this.estado = true;
            return true;
        }
        return false;
    }

    public String toString() {
        if (this.estado) {
            return nombre;
        } else {
            return "";
        }
    }

}
