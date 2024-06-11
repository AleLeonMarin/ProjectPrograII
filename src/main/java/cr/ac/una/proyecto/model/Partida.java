package cr.ac.una.proyecto.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import java.io.Serializable;


/**
 *
 * @author aletr
 */
@Entity
@Table(name = "PARTIDA")
@NamedQueries({
    @NamedQuery(name = "Partida.findAll", query = "SELECT p FROM Partida p"),
    @NamedQuery(name = "Partida.findByParId", query = "SELECT p FROM Partida p WHERE p.parId = :parId"),
    @NamedQuery(name = "Partida.findByParDuenio", query = "SELECT p FROM Partida p WHERE p.parDuenio = :parDuenio"),
    @NamedQuery(name = "Partida.findByParVersion", query = "SELECT p FROM Partida p WHERE p.parVersion = :parVersion")})
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "PARTIDA_PAR_ID_GENERATOR" , sequenceName = "PREG_PARTIDA_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTIDA_PAR_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "PAR_ID")
    private Long parId;
    @Basic(optional = false)
    @Lob
    @Column(name = "PAR_PARTIDA")
    private String parPartida;
    @Basic(optional = false)
    @Column(name = "PAR_DUENIO")
    private String parDuenio;
    @Version
    @Column(name = "PAR_VERSION")
    private Long parVersion;

    public Partida() {
    }

    public Partida(Long parId) {
        this.parId = parId;
    }

    public Partida(PartidaDto partidaDto){
        this.parId = partidaDto.getParId();
        actualizar(partidaDto);
    }

    public void actualizar(PartidaDto partidaDto){
        this.parId = partidaDto.getParId();
        this.parPartida = partidaDto.getParPartida();
        this.parDuenio = partidaDto.getParDuenio();
        this.parVersion = partidaDto.getParVersion();
    }

    public Long getParId() {
        return parId;
    }

    public void setParId(Long parId) {
        this.parId = parId;
    }

    public String getParPartida() {
        return parPartida;
    }

    public void setParPartida(String parPartida) {
        this.parPartida = parPartida;
    }

    public String getParDuenio() {
        return parDuenio;
    }

    public void setParDuenio(String parDuenio) {
        this.parDuenio = parDuenio;
    }

    public Long getParVersion() {
        return parVersion;
    }

    public void setParVersion(Long parVersion) {
        this.parVersion = parVersion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parId != null ? parId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partida)) {
            return false;
        }
        Partida other = (Partida) object;
        if ((this.parId == null && other.parId != null) || (this.parId != null && !this.parId.equals(other.parId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyecto.model.Partida[ parId=" + parId + " ]";
    }
    
}
