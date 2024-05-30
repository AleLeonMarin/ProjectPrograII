package cr.ac.una.proyecto.service;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.logging.Level;
import java.util.logging.Logger;
import cr.ac.una.proyecto.model.Jugador;
import cr.ac.una.proyecto.model.JugadorDto;
import cr.ac.una.proyecto.util.EntityManagerHelper;
import cr.ac.una.proyecto.util.Respuesta;

public class JugadorService {

    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public Respuesta guardarJugadores(List<JugadorDto> jugadoresDto) {
        et = em.getTransaction();
        try {
            et = em.getTransaction();
            et.begin();
            for (JugadorDto jugadorDto : jugadoresDto) {
                Jugador jugador;
                if (jugadorDto.getId() != null && jugadorDto.getId() > 0) {
                    jugador = em.find(Jugador.class, jugadorDto.getId());
                    if (jugador == null) {
                        return new Respuesta(false, "No se encontró el jugador a modificar.",
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
            return new Respuesta(true, "", "", "Jugadores", jugadoresDto);

        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            Logger.getLogger(JugadorService.class.getName()).log(Level.SEVERE, "Error guardando los jugadores.", ex);
            return new Respuesta(false, "Error guardando los jugadores.", "guardarJugadores " + ex.getMessage());
        }
    }

}
