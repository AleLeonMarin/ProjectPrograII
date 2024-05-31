package cr.ac.una.proyecto.model;

import javafx.beans.property.SimpleStringProperty;

public class RespuestaDto {

    private static final long serialVersionUID = 1L;

    public SimpleStringProperty id;
    public SimpleStringProperty enunciado;
    public SimpleStringProperty contador;
    public SimpleStringProperty estado;//change to simpleBooleanProperty
    public SimpleStringProperty isCorrect;
    public SimpleStringProperty preguntaId;
    private Long version;
    private boolean modificado;

    public RespuestaDto() {
        this.id = new SimpleStringProperty("");
        this.enunciado = new SimpleStringProperty("");
        this.contador = new SimpleStringProperty("");
        this.estado = new SimpleStringProperty("");
        this.isCorrect = new SimpleStringProperty("");
        this.preguntaId = new SimpleStringProperty("");
        this.modificado = false;
    }

    public RespuestaDto(Respuesta respuesta) {
        this();
        this.id.set(respuesta.getId().toString());
        this.preguntaId.set(respuesta.getPreguntaId().toString());
        this.enunciado.set(respuesta.getEnunciado());
        this.contador.set(respuesta.getContador());
        this.estado.set(respuesta.getEstado());
        this.isCorrect.set(respuesta.getIsCorrect());
        this.version = respuesta.getVersion();
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

    public String getEnunciado() {
        return enunciado.get();
    }

    public void setEnunciado(String enunciado) {
        this.enunciado.set(enunciado);
    }

    public String getContador() {
        return contador.get();
    }

    public void setContador(String contador) {
        this.contador.set(contador);
    }

    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public String getIsCorrect() {
        return isCorrect.get();
    }

    public void setIsCorrect(String preguntasRespondidas) {
        this.isCorrect.set(preguntasRespondidas);
    }

    public String getPreguntaId() {
        return preguntaId.get();
    }

    public void setPreguntaId(String preguntaId) {
        this.preguntaId.set(preguntaId);
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
        if (!(object instanceof RespuestaDto))
        {
            return false;
        }
        RespuestaDto other = (RespuestaDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyecto.model.RespuestaDto[ resId=" + id + " ]";
    }

}
