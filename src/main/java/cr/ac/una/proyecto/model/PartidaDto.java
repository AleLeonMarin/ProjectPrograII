package cr.ac.una.proyecto.model;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;

public class PartidaDto {

    private static final long serialVersionUID = 1L;

    public SimpleStringProperty id;
    public SimpleStringProperty rondas;
    public SimpleStringProperty dificultad;
    public SimpleStringProperty infoPartida;
    private Long version;
    private boolean modificado;
    private List<Jugador> jugadores;

    public PartidaDto() {
        this.id = new SimpleStringProperty("");
        this.rondas = new SimpleStringProperty("");
        this.dificultad = new SimpleStringProperty("");
        this.infoPartida = new SimpleStringProperty("");
        this.modificado = false;
    }

    public PartidaDto(Partida partida) {
        this();
        this.id.set(partida.getId().toString());
        this.rondas.set(partida.getRondas());
        this.dificultad.set(partida.getDificultad());
        this.infoPartida.set(partida.getInfoPartida());
        this.version = partida.getVersion();
    }

    public Long getId() {
        if (this.id.get() != null && !this.id.get().isBlank())
        {
            return Long.valueOf(this.id.get());
        }
        return null;
    }

    public void setId(Long id) {
        this.id.set(id.toString());
    }

    public String getRondas() {
        return rondas.get();
    }

    public void setRondas(String rondas) {
        this.rondas.set(rondas);
    }

    public String getDificultad() {
        return dificultad.get();
    }

    public void setDificultad(String dificultad) {
        this.dificultad.set(dificultad);
    }

    public String getInfoPartidas() {
        return infoPartida.get();
    }

    public void setInfoPartidas(String infoPartida) {
        this.infoPartida.set(infoPartida);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean isModificado() {
        return modificado;
    }

    public void setModificado(boolean modificado) {
        this.modificado = modificado;
    }

}
