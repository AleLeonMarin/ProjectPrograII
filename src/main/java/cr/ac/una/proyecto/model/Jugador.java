package cr.ac.una.proyecto.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "JUGADOR", schema = "PREGUNTADOS")
@NamedQueries({
        @NamedQuery(name = "Jugador.findByJugNombre", query = "SELECT j FROM Jugador j WHERE UPPER(j.nombre) = :nombre"),
        @NamedQuery(name = "Jugador.findAll", query = "SELECT j FROM Jugador j"),
        @NamedQuery(name = "Jugador.findByJugId", query = "SELECT j FROM Jugador j WHERE j.id = :jugId"),
        /*
         * @NamedQuery(name = "Jugador.findByJugId", query =
         * "SELECT j FROM Jugador j WHERE j.jugId = :id"),
         *
         *
         *
         * @NamedQuery(name = "Jugador.findByJugPartidasganadas", query =
         * "SELECT j FROM Jugador j WHERE j.jugPartidasganadas = :jugPartidasganadas"),
         *
         * @NamedQuery(name = "Jugador.findByJugPreguntasrespondidas", query =
         * "SELECT j FROM Jugador j WHERE j.jugPreguntasrespondidas = :jugPreguntasrespondidas"
         * ),
         *
         * @NamedQuery(name = "Jugador.findByPreResCorrectamente", query =
         * "SELECT j FROM Jugador j WHERE j.preResCorrectamente = :preResCorrectamente"
         * ),
         *
         * @NamedQuery(name = "Jugador.findByJugVersion", query =
         * "SELECT j FROM Jugador j WHERE j.jugVersion = :jugVersion")
         */
})
public class Jugador implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?) @Min(value=?)//if you know range of your decimal fields
    // consider using these annotations to enforce field validation

    @Id
    @SequenceGenerator(name = "JUGADOR_JUG_ID_GENERATOR", sequenceName = "PREG_JUGADOR_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JUGADOR_JUG_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "JUG_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "JUG_NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "JUG_PARTIDASGANADAS")
    private Integer partidasGanadas;
    @Basic(optional = false)
    @Column(name = "JUG_PREGUNTASRESPONDIDAS")
    private Integer preguntasRespondidas;
    @Basic(optional = false)
    @Column(name = "PRE_RES_CORRECTAMENTE")
    private Integer preRespondidasCorrectamente;
    @Basic(optional = false)
    @Column(name = "JUG_CONT_HIS")
    private Integer contHis;
    @Basic(optional = false)
    @Column(name = "JUG_CONT_GEO")
    private Integer contGeo;
    @Basic(optional = false)
    @Column(name = "JUG_CONT_DEP")
    private Integer contDep;
    @Basic(optional = false)
    @Column(name = "JUG_CONT_CIEN")
    private Integer contCie;
    @Basic(optional = false)
    @Column(name = "JUG_CONT_ENTRE")
    private Integer contEntre;
    @Basic(optional = false)
    @Column(name = "JUG_CONT_ARTE")
    private Integer contArte;
    @Basic(optional = false)
    @Column(name = "JUG_COR_HIS")
    private Integer corHis;
    @Basic(optional = false)
    @Column(name = "JUG_COR_GEO")
    private Integer corGeo;
    @Basic(optional = false)
    @Column(name = "JUG_COR_DEP")
    private Integer corDep;
    @Basic(optional = false)
    @Column(name = "JUG_COR_CIEN")
    private Integer corCien;
    @Basic(optional = false)
    @Column(name = "JUG_COR_ENTRE")
    private Integer corEntre;
    @Basic(optional = false)
    @Column(name = "JUG_COR_ARTE")
    private Integer corArte;
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
        this.contArte = jugadorDto.getContadorArte();
        this.contCie = jugadorDto.getContadorCiencia();
        this.contDep = jugadorDto.getContadorDeportes();
        this.contEntre = jugadorDto.getContadorEntretenimiento();
        this.contGeo = jugadorDto.getContadorGeografia();
        this.contHis = jugadorDto.getContadorHistoria();
        this.corArte = jugadorDto.getContadorCorrectasArte();
        this.corCien = jugadorDto.getContadorCorrectasCiencia();
        this.corDep = jugadorDto.getContadorCorrectasDeportes();
        this.corEntre = jugadorDto.getContadorCorrectasEntretenimiento();
        this.corGeo = jugadorDto.getContadorCorrectasGeografia();
        this.corHis = jugadorDto.getContadorCorrectasHistoria();
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

    public Integer getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(Integer partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    public Integer getPreguntasRespondidas() {
        return preguntasRespondidas;
    }

    public void setPreguntasRespondidas(Integer preguntasRespondidas) {
        this.preguntasRespondidas = preguntasRespondidas;
    }

    public Integer getPreRespondidasCorrectamente() {
        return preRespondidasCorrectamente;
    }

    public void setPreRespondidasCorrectamente(Integer preRespondidasCorrectamente) {
        this.preRespondidasCorrectamente = preRespondidasCorrectamente;
    }

    public Integer getContHis() {
        return contHis;
    }

    public void setContHis(Integer jugContHis) {
        this.contHis = jugContHis;
    }

    public Integer getContGeo() {
        return contGeo;
    }

    public void setContGeo(Integer corArte) {
        this.corArte = corArte;
    }

    public Integer getContDep() {
        return contDep;
    }

    public void setContDep(Integer corArte) {
        this.corArte = corArte;
    }

    public Integer getContCien() {
        return contCie;
    }

    public void setContCien(Integer corArte) {
        this.corArte = corArte;
    }

    public Integer getContEntre() {
        return contEntre;
    }

    public void setContEntre(Integer corArte) {
        this.corArte = corArte;
    }

    public Integer getContArte() {
        return contArte;
    }

    public void setContArte(Integer corArte) {
        this.corArte = corArte;
    }

    public Integer getCorHis() {
        return corHis;
    }

    public void setCorHis(Integer corArte) {
        this.corArte = corArte;
    }

    public Integer getCorGeo() {
        return corGeo;
    }

    public void setCorGeo(Integer corArte) {
        this.corArte = corArte;
    }

    public Integer getCorDep() {
        return corDep;
    }

    public void setCorDep(Integer corArte) {
        this.corArte = corArte;
    }

    public Integer getCorCien() {
        return corCien;
    }

    public void setCorCien(Integer corArte) {
        this.corArte = corArte;
    }

    public Integer getCorEntre() {
        return corEntre;
    }

    public void setCorEntre(Integer corArte) {
        this.corArte = corArte;
    }

    public Integer getCorArte() {
        return corArte;
    }

    public void setCorArte(Integer corArte) {
        this.corArte = corArte;
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
        if (!(object instanceof Jugador)) {
            return false;
        }
        Jugador other = (Jugador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "cr.ac.una.proyecto.model.Jugador[ jugId=" + id + " ]";
    }

    public String getInfoPotencial() {
        return "JugadorID: " + getId() + ", Jugador Nombre: " + getNombre() + ", Version del jugador: " + getVersion();
    }

}
