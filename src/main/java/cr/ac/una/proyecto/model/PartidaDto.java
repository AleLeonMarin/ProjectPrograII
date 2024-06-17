package cr.ac.una.proyecto.model;

import java.io.Serializable;
import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class PartidaDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public SimpleStringProperty parId;
    public SimpleStringProperty parPartida;
    public SimpleStringProperty parDuenio;
    public Long parVersion;
    private boolean modificado;
    public SimpleStringProperty ronda;
    public ObjectProperty<LocalDate> fecha;

    public PartidaDto() {
        this.parId = new SimpleStringProperty("");
        this.parPartida = new SimpleStringProperty("");
        this.parDuenio = new SimpleStringProperty("");
        this.modificado = false;
        this.ronda = new SimpleStringProperty("0");
        this.fecha = new SimpleObjectProperty(LocalDate.now());
    }

    public PartidaDto(Partida partida) {

        this();
        this.parId.set(partida.getParId().toString());
        this.parPartida.set(partida.getParPartida());
        this.parDuenio.set(partida.getParDuenio());
        this.parVersion = partida.getParVersion();
        this.ronda.set(partida.getParRonda().toString());
        this.fecha.set(partida.getParFecha());

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

    public int getRonda() {
        return Integer.parseInt(ronda.get());
    }

    public void setRonda(Integer ronda) {
        this.ronda.set(ronda.toString());
    }

    public LocalDate getFecha() {
        return fecha.get();
    }

    public ObjectProperty<LocalDate> fechaProperty() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha.set(fecha);
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
