package cr.ac.una.proyecto.service;

import cr.ac.una.proyecto.model.Partida;
import cr.ac.una.proyecto.model.PartidaDto;
import cr.ac.una.proyecto.util.EntityManagerHelper;
import cr.ac.una.proyecto.util.RespuestaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PartidaService {

    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public RespuestaUtil getAll() {//Obtiene todas las partidas en la base de datos.
        try {
            Query qryPartida = em.createNamedQuery("Partida.findAll");
            List<Partida> partidas = (List<Partida>) qryPartida.getResultList();
            List<PartidaDto> partidasDto = new ArrayList<>();
            for (Partida partida : partidas) {
                partidasDto.add(new PartidaDto(partida));
            }
            return new RespuestaUtil(true, "", "", "Partidas", partidasDto);

        } catch (NonUniqueResultException ex) {
            Logger.getLogger(PartidaService.class.getName()).log(Level.SEVERE, "Error obteniendo las partidas.", ex);
            return new RespuestaUtil(false, "Error obteniendo las partidas.", "getAll NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(PartidaService.class.getName()).log(Level.SEVERE, "Error obteniendo las partidas.", ex);
            return new RespuestaUtil(false, "Error obteniendo las partidas.", "getAll " + ex.getMessage());
        }
    }

    public RespuestaUtil guardarPartida(PartidaDto partidaDto) {//Guarda/Actualiza una partida en la base de datos.
        try {
            et = em.getTransaction();
            et.begin();
            Partida partida;
            if (partidaDto.getParId() != null && partidaDto.getParId() > 0) {
                partida = em.find(Partida.class, partidaDto.getParId());
                if (partida == null) {
                    return new RespuestaUtil(false, "No se encontró la partida a modificar.",
                            "guardarPartida NoResultException");
                }
                partida.actualizar(partidaDto);
                partida = em.merge(partida);
            } else {
                partida = new Partida(partidaDto);
                em.persist(partida);
            }

            et.commit();
            return new RespuestaUtil(true, "", "", "Partida", new PartidaDto(partida));

        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            Logger.getLogger(PartidaService.class.getName()).log(Level.SEVERE, "Error guardando la partida.", ex);
            return new RespuestaUtil(false, "Error guardando la partida.", "guardarPartida " + ex.getMessage());
        }

    }

}
