package cr.ac.una.proyecto.model;

public class Difficulty {

    public String facil;
    public String intermedio;
    public String dificil;
    

    public Difficulty() {
        this.facil = "Fácil";
        this.intermedio = "Intermedio";
        this.dificil = "Difícil";
    }

    public String getFacil() {
        return facil;
    }

    public void setFacil(String facil) {
        this.facil = facil;
    }

    public String getIntermedio() {
        return intermedio;
    }

    public void setIntermedio(String intermedio) {
        this.intermedio = intermedio;
    }

    public String getDificil() {
        return dificil;
    }

    public void setDificil(String dificil) {
        this.dificil = dificil;
    }

    public void setGameDifficulty(String gameDifficulty) {
        if (gameDifficulty == facil){

        }
        if(gameDifficulty == intermedio){

        }

        if(gameDifficulty == dificil){

        }
    }

    
}
