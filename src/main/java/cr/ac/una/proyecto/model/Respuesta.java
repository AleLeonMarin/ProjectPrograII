package cr.ac.una.proyecto.model;

public class Respuesta {

    private int idPadre;
    private String enunciado;
    private Integer aparicion;
    private Boolean isCorrect;// es string en la dabaBase

    public Respuesta(int idPadre, String enunciado, Boolean isCorrect) {
        this.idPadre = idPadre;
        this.enunciado = enunciado;
        this.aparicion = 0;
        this.isCorrect = isCorrect;
    }

    public Respuesta() {
    }

    public int getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(int idPadre) {
        this.idPadre = idPadre;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public Integer getAparicion() {
        return aparicion;
    }

    public void setAparicion(Integer aparicion) {
        this.aparicion = aparicion;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return "Respuesta{"
                + "idPadre=" + idPadre
                + ", enunciado='" + enunciado + '\''
                + ", aparicion=" + aparicion
                + ", isCorrect=" + isCorrect
                + '}';
    }

}
