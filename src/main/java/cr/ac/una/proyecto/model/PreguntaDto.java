package cr.ac.una.proyecto.model;

import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;

public class PreguntaDto implements Serializable {

    public SimpleStringProperty id;
    public SimpleStringProperty enunciado;
    public SimpleStringProperty estado;
    public SimpleStringProperty aparicion;
    public SimpleStringProperty aciertos;
    private Long version;
    private boolean modificado;

    public PreguntaDto() {
        this.id = new SimpleStringProperty("");
        this.enunciado = new SimpleStringProperty("");
        this.estado = new SimpleStringProperty("");
        this.aparicion = new SimpleStringProperty("");
        this.aciertos = new SimpleStringProperty("");
        this.modificado = false;
    }

    public PreguntaDto(Pregunta pregunta) {
        this();
        this.id.set(pregunta.getId().toString());
        this.enunciado.set(pregunta.getEnunciado());
        this.estado.set(pregunta.getEstado());
        this.aparicion.set(pregunta.getAparicion());
        this.aciertos.set(pregunta.getAciertos());
        this.version = pregunta.getVersion();
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

    public String gerEnunciado() {
        return enunciado.get();
    }

    public void setEnunciado(String enunciado) {
        this.enunciado.set(enunciado);
    }

    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public String getAparicion() {
        return aparicion.get();
    }

    public void setAparicion(String aparicion) {
        this.aparicion.set(aparicion);
    }

    public String getAciertos() {
        return aciertos.get();
    }

    public void setAciertos(String aciertos) {
        this.aciertos.set(aciertos);
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
        return "cr.ac.una.proyecto.model.PreguntaDto[ preId=" + id + " ]";
    }
}
