package cr.ac.una.proyecto.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;

import java.util.logging.Level;
import java.util.logging.Logger;

import cr.ac.una.proyecto.model.Jugador;
import cr.ac.una.proyecto.model.JugadorDto;
import cr.ac.una.proyecto.util.EntityManagerHelper;
import cr.ac.una.proyecto.util.RespuestaUtil;

public class JugadorService {

    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public RespuestaUtil guardarJugadores(List<JugadorDto> jugadoresDto) {
        try {
            et = em.getTransaction();
            et.begin();
            for (JugadorDto jugadorDto : jugadoresDto) {
                Jugador jugador;
                if (jugadorDto.getId() != null && jugadorDto.getId() > 0) {
                    jugador = em.find(Jugador.class, jugadorDto.getId());
                    if (jugador == null) {
                        return new RespuestaUtil(false, "No se encontró el jugador a modificar.",
                                "guardarJugador NoResultException");
                    }
                    jugador.actualizar(jugadorDto);
                    jugador = em.merge(jugador);
                } else {
                    jugador = new Jugador(jugadorDto);
                    em.persist(jugador);
                }
            }
            // Asegura que todas las operaciones se sincronicen con la base de datos
            em.flush();
            em.clear();

            et.commit();
            return new RespuestaUtil(true, "", "", "Jugadores", jugadoresDto);

        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            Logger.getLogger(JugadorService.class.getName()).log(Level.SEVERE, "Error guardando los jugadores.", ex);
            return new RespuestaUtil(false, "Error guardando los jugadores.", "guardarJugadores " + ex.getMessage());
        }
    }

    public RespuestaUtil guardarJugador(JugadorDto jugadorDto) {
        try {
            et = em.getTransaction();
            et.begin();
            Jugador jugador;
            if (jugadorDto.getId() != null && jugadorDto.getId() > 0) {
                jugador = em.find(Jugador.class, jugadorDto.getId());
                if (jugador == null) {
                    return new RespuestaUtil(false, "No se encontro en el jugador a guardar", "guardarJugador noResultExeption");
                }
                jugador.actualizar(jugadorDto);
                jugador = em.merge(jugador);
            } else {
                jugador = new Jugador(jugadorDto);
                em.persist(jugador);
            }
            et.commit();
            return new RespuestaUtil(true, "", "", "Jugador", new JugadorDto(jugador));

        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(JugadorService.class.getName()).log(Level.SEVERE, "Error guardando el jugador ", ex);
            return new RespuestaUtil(false, "Error guardando el jugador.", "guardarJugador" + ex.getMessage());
        }
    }


    public RespuestaUtil getAll() {
        try {
            Query qryPregunta = em.createNamedQuery("Jugador.findAll", Jugador.class);
            List<Jugador> jugadorDB = (List<Jugador>) qryPregunta.getResultList();
            List<JugadorDto> jugadorDto = new ArrayList<>();
            for (Jugador jugador : jugadorDB) {
                jugadorDto.add(new JugadorDto(jugador));
            }
            return new RespuestaUtil(true, "", "", "Jugadores", jugadorDto);
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(JugadorService.class.getName()).log(Level.SEVERE,
                    "Ocurrio un error al consultar el jugador.", ex);
            return new RespuestaUtil(false, "Ocurrio un error al consultar los jugadores.",
                    "getJugador NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(JugadorService.class.getName()).log(Level.SEVERE, "Error obteniendo los jugadores ",
                    ex);
            return new RespuestaUtil(false, "Error obteniendo los jugadores.", "getJugadores " + ex.getMessage());
        }
    }

    public RespuestaUtil getJugador(Long id) {
        try {
            Query qryPregunta = em.createNamedQuery("Jugador.findByJugId", Jugador.class);
            qryPregunta.setParameter("jugId", id);

            Jugador jugador = (Jugador) qryPregunta.getSingleResult();
            JugadorDto jugadorDto = new JugadorDto(jugador);

            return new RespuestaUtil(true, "", "", "Jugador", jugadorDto);
        } catch (NoResultException ex) {
            return new RespuestaUtil(false, "No existe un jugador con el código ingresado.",
                    "getJugador NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(JugadorService.class.getName()).log(Level.SEVERE,
                    "Ocurrio un error al consultar el jugador.", ex);
            return new RespuestaUtil(false, "Ocurrio un error al consultarel jugador.",
                    "getJugador NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(JugadorService.class.getName()).log(Level.SEVERE,
                    "Error obteniendo la pregunta [" + id + "]", ex);
            return new RespuestaUtil(false, "Error obteniendo el jugador.", "getJugador " + ex.getMessage());
        }
    }

    public RespuestaUtil actualizarJugador(JugadorDto jugadorDto) {
        try {
            et = em.getTransaction();
            et.begin();
            Jugador jugador = new Jugador();
            if (jugadorDto.getId() != null && jugadorDto.getId() > 0) {
                jugador = em.find(Jugador.class, jugadorDto.getId());
                System.out.println(jugador.getInfoPotencial());
                if (jugador == null) {
                    return new RespuestaUtil(false, "No se encontro en el jugador a guardar", "guardarJugador noResultExeption");
                }
                jugador.actualizar(jugadorDto);
                jugador = em.merge(jugador);
            }
            et.commit();
            System.out.println("ENTIDAD DESPUES DEL MERGE: "+jugador.getVersion());
            return new RespuestaUtil(true, "", "", "AJugador", new JugadorDto(jugador));

        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(JugadorService.class.getName()).log(Level.SEVERE, "Error guardando el jugador ", ex);
            return new RespuestaUtil(false, "Error guardando el jugador.", "guardarJugador" + ex.getMessage());
        }
    }


}
