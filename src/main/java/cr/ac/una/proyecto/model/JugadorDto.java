package cr.ac.una.proyecto.model;

import java.io.Serializable;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class JugadorDto implements Serializable {

    private static final long serialVersionUID = 1L;

    public SimpleStringProperty id;
    public SimpleStringProperty nombre;
    public SimpleIntegerProperty partidasGanadas;
    public SimpleIntegerProperty preguntasRespondidas;
    public SimpleIntegerProperty preRespondidasCorrectamente;
    public SimpleIntegerProperty contHis;
    public SimpleIntegerProperty contGeo;
    public SimpleIntegerProperty contDep;
    public SimpleIntegerProperty contCie;
    public SimpleIntegerProperty contEntre;
    public SimpleIntegerProperty contArte;
    public SimpleIntegerProperty corHis;
    public SimpleIntegerProperty corCie;
    public SimpleIntegerProperty corGeo;
    public SimpleIntegerProperty corDep;
    public SimpleIntegerProperty corEntre;
    public SimpleIntegerProperty corArte;
    private Long version;
    private boolean modificado;
    private List<Partida> partidas;
    private List<JugadorDto> jugadorDtos;

    public JugadorDto() {
        this.id = new SimpleStringProperty("");
        this.nombre = new SimpleStringProperty("");
        this.partidasGanadas = new SimpleIntegerProperty(0);
        this.preguntasRespondidas = new SimpleIntegerProperty(0);
        this.preRespondidasCorrectamente = new SimpleIntegerProperty(0);
        this.contHis = new SimpleIntegerProperty(0);
        this.contGeo = new SimpleIntegerProperty(0);
        this.contDep = new SimpleIntegerProperty(0);
        this.contCie = new SimpleIntegerProperty(0);
        this.contEntre = new SimpleIntegerProperty(0);
        this.contArte = new SimpleIntegerProperty(0);
        this.corHis = new SimpleIntegerProperty(0);
        this.corGeo = new SimpleIntegerProperty(0);
        this.corEntre = new SimpleIntegerProperty(0);
        this.corDep = new SimpleIntegerProperty(0);
        this.corCie = new SimpleIntegerProperty(0);
        this.corArte = new SimpleIntegerProperty(0);
        this.modificado = false;
    }

    public JugadorDto(Jugador jugador) {
        this();
        this.id.set(jugador.getId().toString());
        this.nombre.set(jugador.getNombre());
        this.partidasGanadas.set(jugador.getPartidasGanadas());
        this.preguntasRespondidas.set(jugador.getPreguntasRespondidas());
        this.preRespondidasCorrectamente.set(jugador.getPreRespondidasCorrectamente());
        this.contHis.set(jugador.getContHis());
        this.contGeo.set(jugador.getContGeo());
        this.contDep.set(jugador.getContDep());
        this.contCie.set(jugador.getContCien());
        this.contEntre.set(jugador.getContEntre());
        this.contArte.set(jugador.getContArte());
        this.corHis.set(jugador.getCorHis());
        this.corGeo.set(jugador.getCorGeo());
        this.corEntre.set(jugador.getCorEntre());
        this.corDep.set(jugador.getCorDep());
        this.corCie.set(jugador.getCorCien());
        this.corArte.set(jugador.getCorArte());
        System.out.println("JugadorVersion: " + jugador.getVersion());
        this.version = jugador.getVersion();
        System.out.println("JugadorDTOVersion: " + this.getVersion());
    }

    public JugadorDto(String nombre) {
        this();
        this.nombre.set(nombre);
    }

    public Long getId() {
        if (this.id.get() != null && !this.id.get().isBlank()) {
            return Long.valueOf(this.id.get());
        }
        return null;
    }

    public void setId(Long id) {
        this.id.set(id.toString());
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public Integer getPartidasGanadas() {
        return partidasGanadas.get();
    }

    public void setPartidasGanas(Integer partidasGanadas) {
        this.partidasGanadas.set(partidasGanadas);
    }

    public Integer getPRespondidasCorrectamente() {
        return preRespondidasCorrectamente.get();
    }

    public void setPRespondidasCorrectamente(Integer preRespondidasCorrectamente) {
        this.preRespondidasCorrectamente.set(preRespondidasCorrectamente);
    }

    public Integer getPreguntasRespondidas() {
        return preguntasRespondidas.get();
    }

    public void setPreguntasRespondidas(Integer preguntasRespondidas) {
        this.preguntasRespondidas.set(preguntasRespondidas);
    }

    public Integer getContadorHistoria() {
        return contHis.get();
    }

    public void setContadorHistoria(Integer contHis) {
        this.contHis.set(contHis);
    }

    public Integer getContadorGeografia() {
        return contGeo.get();
    }

    public void setContadorGeografia(Integer contGeo) {
        this.contGeo.set(contGeo);
    }

    public Integer getContadorDeportes() {
        return contDep.get();
    }

    public void setContadorDeportes(Integer contDep) {
        this.contDep.set(contDep);
    }

    public Integer getContadorCiencia() {
        return contCie.get();
    }

    public void setContadorCiencia(Integer contCie) {
        this.contCie.set(contCie);
    }

    public Integer getContadorEntretenimiento() {
        return contEntre.get();
    }

    public void setContadorEntretenimiento(Integer contEntre) {
        this.contEntre.set(contEntre);
    }

    public Integer getContadorArte() {
        return contArte.get();
    }

    public void setContadorArte(Integer contArte) {
        this.contArte.set(contArte);
    }

    public Integer getContadorCorrectasHistoria() {
        return corHis.get();
    }

    public void setContadorCorrectaHistoria(Integer corHis) {
        this.corHis.set(corHis);
    }

    public Integer getContadorCorrectasCiencia() {
        return corCie.get();
    }

    public void setContadorCorrectasCiencia(Integer corCie) {
        this.corCie.set(corCie);
    }

    public Integer getContadorCorrectasGeografia() {
        return corGeo.get();
    }

    public void setContadorCorrectasGeografia(Integer corGeo) {
        this.corGeo.set(corGeo);
    }

    public Integer getContadorCorrectasDeportes() {
        return corDep.get();
    }

    public void setContadorCorrectasDeportes(Integer corDep) {
        this.corDep.set(corDep);
    }

    public Integer getContadorCorrectasEntretenimiento() {
        return corEntre.get();
    }

    public void setContadorCorrectasEntretenimiento(Integer corEntre) {
        this.corEntre.set(corEntre);
    }

    public Integer getContadorCorrectasArte() {
        return corArte.get();
    }

    public void setContadorCorrectasArte(Integer corArte) {
        this.corArte.set(corArte);
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


    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }

    public List<JugadorDto> getJugadorDtos() {
        return jugadorDtos;
    }

    public void setJugadorDtos(List<JugadorDto> jugadorDtos) {
        this.jugadorDtos = jugadorDtos;
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
        if (!(object instanceof JugadorDto)) {
            return false;
        }
        JugadorDto other = (JugadorDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getInfoJugador() {
        return "Jugador: " + this.getNombre() + " ID: " + getId() + ", Partidas Ganadas: " + this.partidasGanadas + ", Preguntas Respondidas: " + this.preguntasRespondidas + ", preCorrectas: " + this.preRespondidasCorrectamente;
    }

    public String getInfoPotencial() {
        return "JugadorDTOID: " + getId() + ", DTO Nombre: " + getNombre() + ", Version del jugador: " + getVersion();
    }

    @Override
    public String toString() {
        return "Nombre" + id + " ]";
    }

    public void incrementarPartidasGanadas() {
        this.partidasGanadas.set(this.partidasGanadas.get() + 1);
    }

    public void incrementarPreguntasRespondidas() {
        this.preguntasRespondidas.set(this.preguntasRespondidas.get() + 1);
    }

    public void incrementarPreRespondidasCorrectamente() {
        this.preRespondidasCorrectamente.set(this.preRespondidasCorrectamente.get() + 1);
    }

    public void incrementarContHis() {
        this.contHis.set(this.contHis.get() + 1);
    }

    public void incrementarContGeo() {
        this.contGeo.set(this.contGeo.get() + 1);
    }

    public void incrementarContDep() {
        this.contDep.set(this.contDep.get() + 1);
    }

    public void incrementarContCie() {
        this.contCie.set(this.contCie.get() + 1);
    }

    public void incrementarContEntre() {
        this.contEntre.set(this.contEntre.get() + 1);
    }

    public void incrementarContArte() {
        this.contArte.set(this.contArte.get() + 1);
    }

    public void incrementarCorHis() {
        this.corHis.set(this.corHis.get() + 1);
    }

    public void incrementarCorCie() {
        this.corCie.set(this.corCie.get() + 1);
    }

    public void incrementarCorGeo() {
        this.corGeo.set(this.corGeo.get() + 1);
    }

    public void incrementarCorDep() {
        this.corDep.set(this.corDep.get() + 1);
    }

    public void incrementarCorEntre() {
        this.corEntre.set(this.corEntre.get() + 1);
    }

    public void incrementarCorArte() {
        this.corArte.set(this.corArte.get() + 1);
    }


}
