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

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public RespuestaUtil getPreguntaRespuestas(Long preguntaId) {
        try
        {
            Query qryRespuesta = em.createNamedQuery("Respuesta.findByPreguntaId", Respuesta.class);
            qryRespuesta.setParameter("preguntaId", preguntaId);
            List<Respuesta> respuestasDb = (List<Respuesta>) qryRespuesta.getResultList();
            List<RespuestaDto> respuestasDto = new ArrayList<>();

            for (Respuesta respuesta : respuestasDb)
            {
                respuestasDto.add(new RespuestaDto(respuesta));
            }

            return new RespuestaUtil(true, "", "", "Respuestas", respuestasDto);
        } catch (NoResultException ex)
        {
            return new RespuestaUtil(false, "No existen respuestas el código de pregunta ingresado.", "getPreguntaRespuestas NoResultException");
        } catch (NonUniqueResultException ex)
        {
            Logger.getLogger(PreguntaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar las respuestas.", ex);
            return new RespuestaUtil(false, "Ocurrio un error al consultar las respuestas.", "getPreguntaRespuestas NonUniqueResultException");
        } catch (Exception ex)
        {
            Logger.getLogger(PreguntaService.class.getName()).log(Level.SEVERE, "Error obteniendo las respuestas de la pregunta [" + preguntaId + "]", ex);
            return new RespuestaUtil(false, "Error obteniendo las respuestas.", "getPreguntaRespuestas " + ex.getMessage());
        }
    }

    public RespuestaUtil guardarRespuestasPregunta(ArrayList<RespuestaDto> respuestasDto) {
        try
        {
            et = em.getTransaction();
            et.begin();
            for (int index = 0; index < respuestasDto.size(); index++)
            {
                Respuesta respuesta;
                if (respuestasDto.get(index).getId() != null && respuestasDto.get(index).getId() > 0)
                {
                    respuesta = em.find(Respuesta.class, respuestasDto.get(index).getId());
                    if (respuesta == null)
                    {
                        et.rollback();
                        return new RespuestaUtil(false, "No se encontró la respuesta a guardar", "guardarRespuestasPregunta noResultException");
                    }
                    respuesta.actualizar(respuestasDto.get(index));
                    respuesta = em.merge(respuesta);
                } else
                {
                    respuesta = new Respuesta(respuestasDto.get(index));
                    em.persist(respuesta);
                }
            }
            et.commit();
            return new RespuestaUtil(true, "", "", "GRespuestas", respuestasDto);
        } catch (Exception ex)
        {
            if (et.isActive())
            {
                et.rollback();
            }
            Logger.getLogger(PreguntaService.class.getName()).log(Level.SEVERE, "Error guardando las respuestas", ex);
            return new RespuestaUtil(false, "Error guardando las respuestas.", "guardarRespuestasPregunta " + ex.getMessage());
        }
    }

    public RespuestaUtil eliminarRespuestas(ArrayList<RespuestaDto> respuestasDto) {
        try
        {
            et = em.getTransaction();
            et.begin();
            for (int index = 0; index < respuestasDto.size(); index++)
            {
                Respuesta respuesta;
                RespuestaDto respuestaDto = respuestasDto.get(index);
                if (respuestaDto.getId() != null && respuestaDto.getId() > 0)
                {
                    respuesta = em.find(Respuesta.class, respuestaDto.getId());
                    if (respuesta == null)
                    {
                        return new RespuestaUtil(false, "No se encontraron algunas respuestas a eliminar", "eliminarRespuestas noResultExeption");
                    }

                    em.remove(respuesta);
                } else
                {
                    return new RespuestaUtil(false, "Favor consultar las respuestas a eliminar", "");

                }
            }
            et.commit();
            return new RespuestaUtil(true, "", "");

        } catch (Exception ex)
        {
            et.rollback();
            Logger.getLogger(RespuestaService.class.getName()).log(Level.SEVERE, "Error eliminando las respuestas ", ex);
            return new RespuestaUtil(false, "Error elimanando las respuestas.", "eliminarRespuestas" + ex.getMessage());
        }

    }

}
