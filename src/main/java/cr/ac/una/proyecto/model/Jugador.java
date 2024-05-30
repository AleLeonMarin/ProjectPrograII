package cr.ac.una.proyecto.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "JUGADOR")
@NamedQueries(
        {
            @NamedQuery(name = "Jugador.findAll", query = "SELECT j FROM Jugador j"),
            @NamedQuery(name = "Jugador.findByJugId", query = "SELECT j FROM Jugador j WHERE j.jugId = :jugId"),
            @NamedQuery(name = "Jugador.findByJugNombre", query = "SELECT j FROM Jugador j WHERE j.jugNombre = :jugNombre"),
            @NamedQuery(name = "Jugador.findByJugPartidasganadas", query = "SELECT j FROM Jugador j WHERE j.jugPartidasganadas = :jugPartidasganadas"),
            @NamedQuery(name = "Jugador.findByJugPreguntasrespondidas", query = "SELECT j FROM Jugador j WHERE j.jugPreguntasrespondidas = :jugPreguntasrespondidas"),
            @NamedQuery(name = "Jugador.findByPreResCorrectamente", query = "SELECT j FROM Jugador j WHERE j.preResCorrectamente = :preResCorrectamente"),
            @NamedQuery(name = "Jugador.findByJugVersion", query = "SELECT j FROM Jugador j WHERE j.jugVersion = :jugVersion")
        })
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "JUG_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "JUG_NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "JUG_PARTIDASGANADAS")
    private String partidasGanadas;
    @Basic(optional = false)
    @Column(name = "JUG_PREGUNTASRESPONDIDAS")
    private String preguntasRespondidas;
    @Basic(optional = false)
    @Column(name = "PRE_RES_CORRECTAMENTE")
    private String preRespondidasCorrectamente;
    @Version
    @Column(name = "JUG_VERSION")
    private Long version;
    @ManyToMany(mappedBy = "jugadores", fetch = FetchType.LAZY)
    private List<Partida> partidas;

    public Jugador() {
    }

    public Jugador(Long id) {
        this.id = id;
    }

    public Jugador(JugadorDto jugadorDto) {
        this.id = jugadorDto.getId();
        actualizar(jugadorDto);
    }

    public void actualizar(JugadorDto jugadorDto) {
        this.nombre = jugadorDto.getNombre();
        this.partidasGanadas = jugadorDto.getPartidasGanadas();
        this.preRespondidasCorrectamente = jugadorDto.getPRespondidasCorrectamente();
        this.preguntasRespondidas = jugadorDto.getPreguntasRespondidas();
        this.version = jugadorDto.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(String partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    public String getPreguntasRespondidas() {
        return preguntasRespondidas;
    }

    public void setPreguntasRespondidas(String preguntasRespondidas) {
        this.preguntasRespondidas = preguntasRespondidas;
    }

    public String getPreRespondidasCorrectamente() {
        return preRespondidasCorrectamente;
    }

    public void setPreRespondidasCorrectamente(String preRespondidasCorrectamente) {
        this.preRespondidasCorrectamente = preRespondidasCorrectamente;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
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
        if (!(object instanceof Jugador))
        {
            return false;
        }
        Jugador other = (Jugador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyecto.model.Jugador[ jugId=" + id + " ]";
    }

}
