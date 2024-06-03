package cr.ac.una.proyecto.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.io.Serializable;

/**
 *
 * @author justi
 */
@Entity
@Table(name = "RESPUESTA")
@NamedQueries(
        {
            @NamedQuery(name = "Respuesta.findAll", query = "SELECT r FROM Respuesta r"),
            @NamedQuery(name = "Respuesta.findByPreguntaId", query = "SELECT r FROM Respuesta r WHERE r.preguntaId.id  = :preguntaId")
        /*  @NamedQuery(name = "Respuesta.findByResId", query = "SELECT r FROM Respuesta r WHERE r.resId = :resId"),
            @NamedQuery(name = "Respuesta.findByResEnunciado", query = "SELECT r FROM Respuesta r WHERE r.resEnunciado = :resEnunciado"),
            @NamedQuery(name = "Respuesta.findByResContador", query = "SELECT r FROM Respuesta r WHERE r.resContador = :resContador"),
            @NamedQuery(name = "Respuesta.findByResEstado", query = "SELECT r FROM Respuesta r WHERE r.resEstado = :resEstado"),
            @NamedQuery(name = "Respuesta.findByResVersion", query = "SELECT r FROM Respuesta r WHERE r.resVersion = :resVersion"),
            @NamedQuery(name = "Respuesta.findByResCorrecta", query = "SELECT r FROM Respuesta r WHERE r.resCorrecta = :resCorrecta")*/
        })
public class Respuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "RESPUESTA_RES_ID_GENERATOR", sequenceName = "PREG_RESPUESTA_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESPUESTA_RES_ID_GENERATOR")
    @Column(name = "RES_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "RES_ENUNCIADO")
    private String enunciado;
    @Basic(optional = false)
    @Column(name = "RES_CONTADOR")
    private Integer contador;
    @Basic(optional = false)
    @Column(name = "RES_ESTADO")
    private String estado;
    @Version
    @Column(name = "RES_VERSION")
    private Long version;
    @Basic(optional = false)
    @Column(name = "RES_CORRECTA")
    private String isCorrect;
    @JoinColumn(name = "PRE_ID", referencedColumnName = "PRE_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pregunta preguntaId;

    public Respuesta() {
    }

    public Respuesta(RespuestaDto respuestarDto) {
        this.id = respuestarDto.getId();
        actualizar(respuestarDto);
    }

    public void actualizar(RespuestaDto respuestaDto) {
        this.enunciado = respuestaDto.getEnunciado();
        this.contador = respuestaDto.getContador();
        this.estado = respuestaDto.getEstado();
        this.isCorrect = respuestaDto.getIsCorrect();
        this.version = respuestaDto.getVersion();
        this.preguntaId = new Pregunta(respuestaDto.getPreguntaId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(String isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Pregunta getPreguntaId() {
        return preguntaId;
    }

    public void setPreguntaId(Pregunta preguntaId) {
        this.preguntaId = preguntaId;
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
        if (!(object instanceof Respuesta))
        {
            return false;
        }
        Respuesta other = (Respuesta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyecto.model.Respuesta[ resId=" + id + " ]";
    }

}
