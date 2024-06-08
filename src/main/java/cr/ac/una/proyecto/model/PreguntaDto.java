package cr.ac.una.proyecto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class    PreguntaDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public SimpleStringProperty id;
    public SimpleStringProperty nombreCategoria;
    public SimpleStringProperty enunciado;
    public SimpleBooleanProperty estado;
    public SimpleIntegerProperty aparicion;
    public SimpleIntegerProperty aciertos;
    private List<RespuestaDto> respuestas;
    private Long version;
    private boolean modificado;

    public PreguntaDto() {
        this.id = new SimpleStringProperty("");
        this.nombreCategoria = new SimpleStringProperty("");
        this.enunciado = new SimpleStringProperty("");
        this.estado = new SimpleBooleanProperty(true);
        this.aparicion = new SimpleIntegerProperty(0);
        this.aciertos = new SimpleIntegerProperty(0);
        this.modificado = false;
        this.respuestas = new ArrayList<>();
    }

    public PreguntaDto(Pregunta pregunta) {
        this();
        this.id.set(pregunta.getId().toString());
        this.nombreCategoria = new SimpleStringProperty(pregunta.getNombreCategoria().getNombre());
        this.enunciado.set(pregunta.getEnunciado());
        this.estado.set(pregunta.getEstado().equals("A"));
        this.aparicion.set(pregunta.getAparicion());
        this.aciertos.set(pregunta.getAciertos());
        this.version = pregunta.getVersion();
    }

    public Long getId() {
        if (id.get() != null && !id.get().isEmpty())
        {
            return Long.valueOf(id.get());
        } else
        {
            return null;
        }
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

    public String getNombreCategoria() {
        return nombreCategoria.get();
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria.set(nombreCategoria);
    }

    public String getEstado() {
        return estado.get() ? "A" : "I";
    }

    public void setEstado(String estado) {
        this.estado.set(estado.equalsIgnoreCase("A"));
    }

    public Integer getAparicion() {
        return aparicion.get();
    }

    public void setAparicion(Integer aparicion) {
        this.aparicion.set(aparicion);
    }

    public Integer getAciertos() {
        return aciertos.get();
    }

    public void setAciertos(Integer aciertos) {
        this.aciertos.set(aciertos);
    }

    public List<RespuestaDto> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<RespuestaDto> respuestas) {
        this.respuestas = respuestas;
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

    public SimpleStringProperty nombreCat(){
        return nombreCategoria;
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

    public void sumarAparicion(){
        System.out.println("PreguntaContadorAparicion: "+aparicion);
        this.aparicion.set(this.aparicion.get() + 1);
        System.out.println("PreguntaContadorAparicion++: "+aparicion);
    }

    public void sumarAcierto(){
        System.out.println("PreguntaContadorACiertos: "+aciertos);
        this.aciertos.set(this.aciertos .get() + 1);
        System.out.println("PreguntaContadorAciertos++: "+aciertos);
    }
}
