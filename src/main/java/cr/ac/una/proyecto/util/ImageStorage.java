package cr.ac.una.proyecto.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Justin Mendez & Alejandro Leon
 *
 */
public class ImageStorage {
    // Map para almacenar Nombre -> Ruta

    private Map<String, String> nameToPathMap = new HashMap<>();

    // Método para agregar una imagen
    public void addImage(String name, String path) {
        nameToPathMap.put(name, path);
    }

    // Método para obtener la ruta mediante el nombre
    public String getPathByName(String name) {
        return nameToPathMap.get(name);
    }
}
