package cr.ac.una.proyecto.model;

public class Partida {

    private Long id;
    private int rondas;
    private String dificultad;
    private Long version;
    private String jsonDatosPartida; // Atributo para almacenar el JSON como String

    public Partida(Long id, int rondas, String dificultad, Long version, String jsonDatosPartida) {
        this.id = id;
        this.rondas = rondas;
        this.dificultad = dificultad;
        this.version = version;
        this.jsonDatosPartida = jsonDatosPartida;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRondas() {
        return rondas;
    }

    public void setRondas(int rondas) {
        this.rondas = rondas;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getJsonDatosPartida() {
        return jsonDatosPartida;
    }

    public void setJsonDatosPartida(String jsonDatosPartida) {
        this.jsonDatosPartida = jsonDatosPartida;
    }
}
