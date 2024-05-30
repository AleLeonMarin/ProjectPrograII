package cr.ac.una.proyecto.service;

import cr.ac.una.proyecto.util.EntityManagerHelper;
import cr.ac.una.proyecto.util.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class RespuestaService {

    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public Respuesta getPreguntaRespuestas(Long preguntaid) {//to do
        return null;
    }
}
