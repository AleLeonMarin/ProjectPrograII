package cr.ac.una.proyecto.service;

import cr.ac.una.proyecto.model.RespuestaDto;
import cr.ac.una.proyecto.model.Respuesta;
import cr.ac.una.proyecto.util.EntityManagerHelper;
import cr.ac.una.proyecto.util.RespuestaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RespuestaService {

    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public RespuestaUtil getPreguntaRespuestas(Long preguntaId) {
        try {
            Query qryRespuesta = em.createNamedQuery("Respuesta.findByPreguntaId", Respuesta.class);
            qryRespuesta.setParameter("preguntaId", preguntaId);
            List<Respuesta> respuestasDb = (List<Respuesta>) qryRespuesta.getResultList();
            List<RespuestaDto> respuestasDto = new ArrayList<>();

            for (Respuesta respuesta : respuestasDb) {
                respuestasDto.add(new RespuestaDto(respuesta));
            }

            return new RespuestaUtil(true, "", "", "Respuestas", respuestasDto);
        } catch (NoResultException ex) {
            return new RespuestaUtil(false, "No existen respuestas el código de pregunta ingresado.", "getPreguntaRespuestas NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(PreguntaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar las respuestas.", ex);
            return new RespuestaUtil(false, "Ocurrio un error al consultar las respuestas.", "getPreguntaRespuestas NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(PreguntaService.class.getName()).log(Level.SEVERE, "Error obteniendo las respuestas de la pregunta [" + preguntaId + "]", ex);
            return new RespuestaUtil(false, "Error obteniendo las respuestas.", "getPreguntaRespuestas " + ex.getMessage());
        }
    }
}
