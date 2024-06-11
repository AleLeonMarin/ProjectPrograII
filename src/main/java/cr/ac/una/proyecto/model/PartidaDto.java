package cr.ac.una.proyecto.model;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;

public class PartidaDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public SimpleStringProperty parId;
    public SimpleStringProperty parPartida;
    public SimpleStringProperty parDuenio;
    public Long parVersion;
    private boolean modificado;

    public PartidaDto() {
        this.parId = new SimpleStringProperty("");
        this.parPartida = new SimpleStringProperty("");
        this.parDuenio = new SimpleStringProperty("");
        this.modificado = false;
    }

    public PartidaDto(Partida partida) {

        this();
        this.parId.set(partida.getParId().toString());
        this.parPartida.set(partida.getParPartida());
        this.parDuenio.set(partida.getParDuenio());
        this.parVersion = partida.getParVersion();

    }

    public Long getParId() {

        if (parId.get() != null && !parId.get().isEmpty()) {
            return Long.valueOf(parId.get());
        } else {
            return null;
        }
    }

    public void setParId(Long parId) {
        this.parId.set(parId.toString());
    }

    public String getParPartida() {
        return parPartida.get();
    }

    public void setParPartida(String parPartida) {
        this.parPartida.set(parPartida);
    }

    public String getParDuenio() {
        return parDuenio.get();
    }

    public void setParDuenio(String parDuenio) {
        this.parDuenio.set(parDuenio);
    }

    public Long getParVersion() {
        return parVersion;
    }

    public void setParVersion(Long parVersion) {
        this.parVersion = parVersion;
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
        hash += (parId != null ? parId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof PartidaDto)) {
            return false;
        }
        PartidaDto other = (PartidaDto) object;
        if ((this.parId == null && other.parId != null) || (this.parId != null && !this.parId.equals(other.parId))) {
            return false;
        }
        return true;
    }

}
