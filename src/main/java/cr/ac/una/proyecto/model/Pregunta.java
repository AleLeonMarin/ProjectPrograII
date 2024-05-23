package cr.ac.una.proyecto.model;

/**
 *
 * @author justi
 */
public class Pregunta {

    private String enunciado;
    private String respuesta;
    private String categoria;
    private int id;

    public Pregunta(String enunciado, String respuesta, String categoria, int id) {
        this.enunciado = enunciado;
        this.respuesta = respuesta;
        this.categoria = categoria;
        this.id = id;
    }

    public Pregunta() {
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pregunta{"
                + "enunciado='" + enunciado + '\''
                + ", respuesta='" + respuesta + '\''
                + ", categoria='" + categoria + '\''
                + ", id=" + id
                + '}';
    }

}
