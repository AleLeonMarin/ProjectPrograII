package cr.ac.una.proyecto.service;

import cr.ac.una.proyecto.model.Pregunta;
import cr.ac.una.proyecto.model.PreguntaDto;
import cr.ac.una.proyecto.util.EntityManagerHelper;
import cr.ac.una.proyecto.util.Respuesta;
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

    public Respuesta getAll() {
        try
        {
            Query qryPregunta = em.createNamedQuery("Pregunta.findAll", Pregunta.class);
            List<Pregunta> preguntasDB = (List<Pregunta>) qryPregunta.getResultList();
            List<PreguntaDto> preguntasDto = new ArrayList<>();
            for (Pregunta pregunta : preguntasDB)
            {
                preguntasDto.add(new PreguntaDto(pregunta));
            }
            return new Respuesta(true, "", "", "Preguntas", preguntasDto);
        } catch (NoResultException ex)
        {
            return new Respuesta(false, "No existe una tipoPlanilla con el código ingresado.", "getTipoPlanilla NoResultException");
        } catch (NonUniqueResultException ex)
        {
            Logger.getLogger(CategoriaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar la tipoPlanilla.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar la tipoPlanilla.", "getTipoPlanilla NonUniqueResultException");
        } catch (Exception ex)
        {
            Logger.getLogger(CategoriaService.class.getName()).log(Level.SEVERE, "Error obteniendo la tipoPlanilla ", ex);
            return new Respuesta(false, "Error obteniendo la tipoPlanilla.", "getTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta getPregunta(Long id) {
        try
        {
            Query qryPregunta = em.createNamedQuery("Pregunta.findByPreId", Pregunta.class);
            qryPregunta.setParameter("id", id);
            PreguntaDto preguntaDto = new PreguntaDto((Pregunta) qryPregunta.getSingleResult());
            return new Respuesta(true, "", "", "Pregunta", preguntaDto);
        } catch (NoResultException ex)
        {
            return new Respuesta(false, "No existe una pregunta con el código ingresado.", "getPregunta NoResultException");
        } catch (NonUniqueResultException ex)
        {
            Logger.getLogger(PreguntaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar la pregunta.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar la pregunta.", "getPregunta NonUniqueResultException");
        } catch (Exception ex)
        {
            Logger.getLogger(PreguntaService.class.getName()).log(Level.SEVERE, "Error obteniendo la pregunta [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo la pregunta.", "getPregunta " + ex.getMessage());
        }
    }
}
