
package cr.ac.una.proyecto.model;

/**
 *
 * @author justi
 */
public class Pregunta {

    private String enunciado;
    private String respuesta;
    private String categoria;

    public Pregunta(String pregunta, String respuesta, String categoria) {
        this.enunciado = pregunta;
        this.respuesta = respuesta;
        this.categoria = categoria;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public String getCategoria() {
        return categoria;
    }
    
    
  
}
