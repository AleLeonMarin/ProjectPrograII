package cr.ac.una.proyecto.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "PARTIDA")
@NamedQueries(
        {
            @NamedQuery(name = "Partida.findAll", query = "SELECT p FROM Partida p"),
            @NamedQuery(name = "Partida.findByParId", query = "SELECT p FROM Partida p WHERE p.parId = :parId"),
            @NamedQuery(name = "Partida.findByParRondas", query = "SELECT p FROM Partida p WHERE p.parRondas = :parRondas"),
            @NamedQuery(name = "Partida.findByParDificultad", query = "SELECT p FROM Partida p WHERE p.parDificultad = :parDificultad"),
            @NamedQuery(name = "Partida.findByParVersion", query = "SELECT p FROM Partida p WHERE p.parVersion = :parVersion")
        })
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PAR_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "PAR_RONDAS")
    private String rondas;
    @Basic(optional = false)
    @Column(name = "PAR_DIFICULTAD")
    private String dificultad;
    @Basic(optional = false)
    @Lob
    @Column(name = "PAR_PARTIDA")
    private String infoPartida;
    @Version
    @Column(name = "PAR_VERSION")
    private Long version;
    @JoinTable(name = "PARTIDAJUGADOR", joinColumns =
    {
        @JoinColumn(name = "EXP_IDPAR", referencedColumnName = "PAR_ID")
    }, inverseJoinColumns =
    {
        @JoinColumn(name = "EXP_IDJUG", referencedColumnName = "JUG_ID")
    })
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Jugador> jugadores;

    public Partida() {
    }

    public Partida(Long id) {
        this.id = id;
    }

    public Partida(PartidaDto partidaDto) {
        this.id = partidaDto.getId();
        actualizar(partidaDto);
    }

    public void actualizar(PartidaDto partidaDto) {
        this.id = partidaDto.getId();
        this.dificultad = partidaDto.getDificultad();
        this.infoPartida = partidaDto.getInfoPartidas();
        this.rondas = partidaDto.getRondas();
        this.version = partidaDto.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRondas() {
        return rondas;
    }

    public void setRondas(String rondas) {
        this.rondas = rondas;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getInfoPartida() {
        return infoPartida;
    }

    public void setInfoPartida(String infoPartida) {
        this.infoPartida = infoPartida;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
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
        if (!(object instanceof Partida))
        {
            return false;
        }
        Partida other = (Partida) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyecto.model.Partida[ parId=" + id + " ]";
    }

}
