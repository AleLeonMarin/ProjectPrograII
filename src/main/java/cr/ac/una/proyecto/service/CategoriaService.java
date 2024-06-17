package cr.ac.una.proyecto.service;

import cr.ac.una.proyecto.model.Categoria;
import cr.ac.una.proyecto.model.CategoriaDto;
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

public class CategoriaService {

    EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public RespuestaUtil getAll() {//Obtiene todas las categorias en la base de datos.
        try
        {
            Query qryCategoria = em.createNamedQuery("Categoria.findAll", Categoria.class);
            List<Categoria> categorias = (List<Categoria>) qryCategoria.getResultList();
            List<CategoriaDto> categoriaDto = new ArrayList<>();
            for (Categoria categoria : categorias)
            {
                categoriaDto.add(new CategoriaDto(categoria));
            }
            return new RespuestaUtil(true, "", "", "Categorias", categoriaDto);
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

}
