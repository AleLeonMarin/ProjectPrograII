package cr.ac.una.proyecto.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author justi
 */
@Entity
@Table(name = "CATEGORIA")
@NamedQueries(
        {
            @NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c"),
        /*@NamedQuery(name = "Categoria.findByCatNombre", query = "SELECT c FROM Categoria c WHERE c.catNombre = :catNombre"),
            @NamedQuery(name = "Categoria.findByCatId", query = "SELECT c FROM Categoria c WHERE c.catId = :catId"),
            @NamedQuery(name = "Categoria.findByCatVersion", query = "SELECT c FROM Categoria c WHERE c.catVersion = :catVersion")*/
        })
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CAT_NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "CAT_ID")
    private Long id;
    @Version
    @Column(name = "CAT_VERSION")
    private Long version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nombreCategoria", fetch = FetchType.LAZY)  // Corrected here
    private List<Pregunta> preguntas;

    public Categoria() {
    }

    public Categoria(String catNombre) {
        this.nombre = catNombre;
    }

    public Categoria(Long id) {
        this.id = id;
    }

    public Categoria(CategoriaDto categoriaDto) {
        this.id = categoriaDto.getId();
        this.nombre = categoriaDto.getNombre();
        this.version = categoriaDto.getVersion();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoria))
        {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyecto.model.Categoria[ catNombre=" + nombre + " ]";
    }
}
