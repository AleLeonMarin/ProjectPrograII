package cr.ac.una.proyecto.service;

import cr.ac.una.proyecto.model.Pregunta;
import cr.ac.una.proyecto.model.PreguntaDto;
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

public class PreguntaService {

    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public RespuestaUtil getAll() {
        try
        {
            Query qryPregunta = em.createNamedQuery("Pregunta.findAll", Pregunta.class);
            List<Pregunta> preguntasDB = (List<Pregunta>) qryPregunta.getResultList();
            List<PreguntaDto> preguntasDto = new ArrayList<>();
            for (Pregunta pregunta : preguntasDB)
            {
                preguntasDto.add(new PreguntaDto(pregunta));
            }
            return new RespuestaUtil(true, "", "", "Preguntas", preguntasDto);
        } catch (NoResultException ex)
        {
            return new RespuestaUtil(false, "No existe una tipoPlanilla con el código ingresado.", "getTipoPlanilla NoResultException");
        } catch (NonUniqueResultException ex)
        {
            Logger.getLogger(CategoriaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar la tipoPlanilla.", ex);
            return new RespuestaUtil(false, "Ocurrio un error al consultar la tipoPlanilla.", "getTipoPlanilla NonUniqueResultException");
        } catch (Exception ex)
        {
            Logger.getLogger(CategoriaService.class.getName()).log(Level.SEVERE, "Error obteniendo la tipoPlanilla ", ex);
            return new RespuestaUtil(false, "Error obteniendo la tipoPlanilla.", "getTipoPlanilla " + ex.getMessage());
        }
    }

    public RespuestaUtil getPregunta(Long id) {
        try
        {
            Query qryPregunta = em.createNamedQuery("Pregunta.findByPreId", Pregunta.class);
            qryPregunta.setParameter("preId", id);
            PreguntaDto preguntaDto = new PreguntaDto((Pregunta) qryPregunta.getSingleResult());
            return new RespuestaUtil(true, "", "", "Pregunta", preguntaDto);
        } catch (NoResultException ex)
        {
            return new RespuestaUtil(false, "No existe una pregunta con el código ingresado.", "getPregunta NoResultException");
        } catch (NonUniqueResultException ex)
        {
            Logger.getLogger(PreguntaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar la pregunta.", ex);
            return new RespuestaUtil(false, "Ocurrio un error al consultar la pregunta.", "getPregunta NonUniqueResultException");
        } catch (Exception ex)
        {
            Logger.getLogger(PreguntaService.class.getName()).log(Level.SEVERE, "Error obteniendo la pregunta [" + id + "]", ex);
            return new RespuestaUtil(false, "Error obteniendo la pregunta.", "getPregunta " + ex.getMessage());
        }
    }

    public RespuestaUtil getPreguntasActivasPorCategoria(String nombreCat) {
        try
        {
            Query qryPregunta = em.createNamedQuery("Pregunta.findByPreCat", Pregunta.class);
            qryPregunta.setParameter("nombreCategoria", nombreCat);
            qryPregunta.setParameter("estadoPregunta", "A");
            List<Pregunta> preguntasDb = (List<Pregunta>) qryPregunta.getResultList();
            List<PreguntaDto> preguntasDto = new ArrayList<>();

            for (Pregunta preguntaDb : preguntasDb)
            {
                preguntasDto.add(new PreguntaDto(preguntaDb));
            }

            return new RespuestaUtil(true, "", "", "Preguntas", preguntasDto);
        } catch (NoResultException ex)
        {
            return new RespuestaUtil(false, "No existen preguntas con el nombre de categoria ingresado.", "getPreguntasPorCategoria NoResultException");
        } catch (NonUniqueResultException ex)
        {
            Logger.getLogger(PreguntaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar las preguntas.", ex);
            return new RespuestaUtil(false, "Ocurrio un error al consultar las preguntas.", "getPreguntasPorCategoria NonUniqueResultException");
        } catch (Exception ex)
        {
            Logger.getLogger(PreguntaService.class.getName()).log(Level.SEVERE, "Error obteniendo las preguntas de la categoria [" + nombreCat + "]", ex);
            return new RespuestaUtil(false, "Error obteniendo las preguntas.", "getPreguntasPorCategoria " + ex.getMessage());
        }
    }

    public RespuestaUtil getPreguntasByFiltros(String preguntaId, String preCategoria, String preEnunciado) {
        try
        {
            Query query = em.createNamedQuery("Pregunta.findByFilters", Pregunta.class);
            query.setParameter("preId", ("%" + preguntaId + "%"));
            query.setParameter("preCat", "%" + preCategoria + "%");
            query.setParameter("preEnun", "%" + preEnunciado + "%");
            List<Pregunta> preguntasDb = (List<Pregunta>) query.getResultList();
            List<PreguntaDto> preguntasDto = new ArrayList<>();
            for (Pregunta preguntaDb : preguntasDb)
            {
                preguntasDto.add(new PreguntaDto(preguntaDb));
            }
            return new RespuestaUtil(true, "", "", "Preguntas", preguntasDto);
        } catch (NoResultException ex)
        {
            return new RespuestaUtil(false, "No existen preguntas con esos detalles.", "getPreguntasByFiltros NoResultException");
        } catch (Exception ex)
        {
            Logger.getLogger(PreguntaService.class.getName()).log(Level.SEVERE, "Error obteniendo las preguntas.", ex);
            return new RespuestaUtil(false, "Error obteniendo Preguntas.", "getPreguntasByFiltros " + ex.getMessage());
        }
    }

    public RespuestaUtil guardarPregunta(PreguntaDto preguntaDto) {
        try
        {
            et = em.getTransaction();
            et.begin();
            Pregunta pregunta;
            if (preguntaDto.getId() != null && preguntaDto.getId() > 0)
            {
                pregunta = em.find(Pregunta.class, preguntaDto.getId());
                if (pregunta == null)
                {
                    return new RespuestaUtil(false, "No se encontro en la tipoPlanilla a guardar", "guardarTipoPlanilla noResultExeption");
                }
                pregunta.actualizar(preguntaDto);
                pregunta = em.merge(pregunta);
            } else
            {
                pregunta = new Pregunta(preguntaDto);
                em.persist(pregunta);
            }
            et.commit();
            return new RespuestaUtil(true, "", "", "Pregunta", new PreguntaDto(pregunta));

        } catch (Exception ex)
        {
            et.rollback();
            Logger.getLogger(PreguntaService.class.getName()).log(Level.SEVERE, "Error guardando la pregunta", ex);
            return new RespuestaUtil(false, "Error guardando la pregunta.", "guardarPregunta" + ex.getMessage());
        }
    }
}
