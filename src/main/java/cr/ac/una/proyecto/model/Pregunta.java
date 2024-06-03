package cr.ac.una.proyecto.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Justin Mendez & Alejandro Leon
 */
@Entity
@Table(name = "PREGUNTA")
@NamedQueries(
        {
            @NamedQuery(name = "Pregunta.findAll", query = "SELECT p FROM Pregunta p"),
            @NamedQuery(name = "Pregunta.findByPreId", query = "SELECT p FROM Pregunta p WHERE p.id = :preId"),
            @NamedQuery(name = "Pregunta.findByPreCat", query = "SELECT p FROM Pregunta p WHERE p.nombreCategoria.nombre = :nombreCategoria AND p.estado = :estadoPregunta"),
            @NamedQuery(name = "Pregunta.findByFilters", query = "SELECT p FROM Pregunta p WHERE p.id like :preId and UPPER(p.nombreCategoria.nombre) like :preCat and UPPER(p.enunciado) like :preEnun")
        /* @NamedQuery(name = "Pregunta.findByPreEnunciado", query = "SELECT p FROM Pregunta p WHERE p.enunciado = :preEnunciado"),
            @NamedQuery(name = "Pregunta.findByPreEstado", query = "SELECT p FROM Pregunta p WHERE p.estado = :preEstado"),
            @NamedQuery(name = "Pregunta.findByPreAparicion", query = "SELECT p FROM Pregunta p WHERE p.aparicion = :preAparicion"),
            @NamedQuery(name = "Pregunta.findByPreAciertos", query = "SELECT p FROM Pregunta p WHERE p.aciertos = :preAciertos"),
            @NamedQuery(name = "Pregunta.findByPreVersion", query = "SELECT p FROM Pregunta p WHERE p.version = :preVersion")*/
        })
public class Pregunta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "PREGUNTA_PRE_ID_GENERATOR", sequenceName = "PREG_PREGUNTA_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PREGUNTA_PRE_ID_GENERATOR")
    @Column(name = "PRE_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "PRE_ENUNCIADO")
    private String enunciado;
    @Basic(optional = false)
    @Column(name = "PRE_ESTADO")
    private String estado;
    @Basic(optional = false)
    @Column(name = "PRE_APARICION")
    private Integer aparicion;
    @Basic(optional = false)
    @Column(name = "PRE_ACIERTOS")
    private Integer aciertos;
    @Version
    @Column(name = "PRE_VERSION")
    private Long version;
    @JoinColumn(name = "CAT_NOMBRE", referencedColumnName = "CAT_NOMBRE")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Categoria nombreCategoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preguntaId", fetch = FetchType.LAZY)
    private List<Respuesta> respuestas;

    public Pregunta() {
    }

    public Pregunta(Long id) {
        this.id = id;
    }

    public Pregunta(PreguntaDto preguntaDto) {
        this.id = preguntaDto.getId();
        actualizar(preguntaDto);
    }

    public void actualizar(PreguntaDto preguntaDto) {
        this.enunciado = preguntaDto.getEnunciado();
        this.nombreCategoria = new Categoria(preguntaDto.getNombreCategoria());
        this.estado = preguntaDto.getEstado();
        this.aparicion = preguntaDto.getAparicion();
        this.aciertos = preguntaDto.getAciertos();
        this.version = preguntaDto.getVersion();
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getAparicion() {
        return aparicion;
    }

    public void setAparicion(Integer aparicion) {
        this.aparicion = aparicion;
    }

    public Integer getAciertos() {
        return aciertos;
    }

    public void setAciertos(Integer aciertos) {
        this.aciertos = aciertos;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Categoria getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(Categoria nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pregunta))
        {
            return false;
        }
        Pregunta other = (Pregunta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyecto.model.Pregunta[ preId=" + id + " ]";
    }
}
