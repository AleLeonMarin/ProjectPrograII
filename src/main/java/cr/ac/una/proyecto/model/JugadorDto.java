package cr.ac.una.proyecto.model;

import java.io.Serializable;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;

public class JugadorDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public SimpleStringProperty id;
    public SimpleStringProperty nombre;
    public SimpleStringProperty partidasGanadas;
    public SimpleStringProperty preguntasRespondidas;
    public SimpleStringProperty preRespondidasCorrectamente;
    private Long version;
    private boolean modificado;
    private List<Partida> partidas;

    public JugadorDto() {
        this.id = new SimpleStringProperty("");
        this.nombre = new SimpleStringProperty("");
        this.partidasGanadas = new SimpleStringProperty("");
        this.preguntasRespondidas = new SimpleStringProperty("");
        this.preRespondidasCorrectamente = new SimpleStringProperty("");
        this.modificado = false;
    }

    public JugadorDto(Jugador jugador) {
        this();
        this.id.set(jugador.getId().toString());
        this.nombre.set(jugador.getNombre());
        this.partidasGanadas.set(jugador.getPartidasGanadas());
        this.preguntasRespondidas.set(jugador.getPreguntasRespondidas());
        this.preRespondidasCorrectamente.set(jugador.getPreRespondidasCorrectamente());
        this.version = jugador.getVersion();
    }

    public JugadorDto(String nombre) {
        this.nombre = new SimpleStringProperty(nombre);
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

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getPartidasGanadas() {
        return partidasGanadas.get();
    }

    public void setPartidasGanas(String partidasGanadas) {
        this.partidasGanadas.set(partidasGanadas);
    }

    public String getPRespondidasCorrectamente() {
        return preRespondidasCorrectamente.get();
    }

    public void setPRespondidasCorrectamente(String preRespondidasCorrectamente) {
        this.preRespondidasCorrectamente.set(preRespondidasCorrectamente);
    }

    public String getPreguntasRespondidas() {
        return preguntasRespondidas.get();
    }

    public void setPreguntasRespondidas(String preguntasRespondidas) {
        this.preguntasRespondidas.set(preguntasRespondidas);
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JugadorDto))
        {
            return false;
        }
        JugadorDto other = (JugadorDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyecto.model.JugadorDto[ jugId=" + id + " ]";
    }

}
