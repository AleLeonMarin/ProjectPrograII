package cr.ac.una.proyecto.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Sector {

    private JugadorDto jugador;
    private int posicionX;
    private int posicionY;
    private int posActual;
    private int direccion;
    private String rutaImagenJugador;
    private ArrayList<Ayuda> ayudas;
    private ArrayList<Corona> coronas;

    boolean isOnCoronaPos;

    public Sector() {
        this.ayudas = new ArrayList<>();
        this.coronas = new ArrayList<>();
    }

    public Sector(JugadorDto jugador, int Xpos, int Ypos, int direccion, String rutaImagenJugador) {
        this.jugador = jugador;
        this.posicionX = Xpos;
        this.posicionY = Ypos;
        this.direccion = direccion;
        this.rutaImagenJugador = rutaImagenJugador;
        this.ayudas = new ArrayList<>();
        this.isOnCoronaPos = false;
        establecerCoronas();
        establecerAyudas();
        setActualPosInFirst();
    }

    public Sector(int Xpos, int Ypos, int direccion) {
        this.jugador = new JugadorDto();
        this.posicionX = Xpos;
        this.posicionY = Ypos;
        this.direccion = direccion;
        this.rutaImagenJugador = "";
        this.ayudas = new ArrayList<>();
        this.isOnCoronaPos = false;
        establecerCoronas();
        establecerAyudas();
        setActualPosInFirst();
    }

    public void setActualPosInFirst() {
        if (direccion == 1 || direccion == 2) {
            this.posActual = this.posicionY;
        } else {
            this.posActual = this.posicionX;
        }
    }

    public int getDireccion() {
        return direccion;
    }

    public JugadorDto getJugador() {
        return jugador;
    }

    public void setJugador(JugadorDto jugador) {
        this.jugador = jugador;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionFija(int posicionFija) {
        this.posicionX = posicionFija;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionInicial(int posicionInicial) {
        this.posicionY = posicionInicial;
    }

    public int getPosActual() {
        return posActual;
    }

    public void setPosActual(int posActual) {
        this.posActual = posActual;
    }

    public String getRutaImagenJugador() {
        return rutaImagenJugador;
    }

    public void setRutaImagenJugador(String rutaImagenJugador) {
        this.rutaImagenJugador = rutaImagenJugador;
    }

    public ArrayList<Ayuda> getAyudas() {
        return this.ayudas;
    }

    public void setAyudas(ArrayList<Ayuda> ayudas) {
        this.ayudas = ayudas;
    }

    public boolean getIsOnCoronaPos() {
        return isOnCoronaPos;
    }

    public void setIsOnCoronaPos(boolean isOnCoronaPos) {
        this.isOnCoronaPos = isOnCoronaPos;
    }

    public ArrayList<Corona> getCoronas() {
        return coronas;
    }

    private void moverNodoA(ImageView imageView, int columna, int fila, GridPane gridPane) {//setea la posicion de la imagen dentro del gripPane
        gridPane.setColumnIndex(imageView, columna);
        gridPane.setRowIndex(imageView, fila);
    }

    public void mostrarPeonTablero(ImageView imageView, GridPane gridPane) {//muestra y mueve la imagen dentro del gripPane segun dirrecion.
        if (direccion == 1 || direccion == 2) {
            moverNodoA(imageView, posActual, posicionX, gridPane);
        } else {
            moverNodoA(imageView, posicionY, posActual, gridPane);
        }
    }

    private void checkCoronaPos(int posCorona) {//revisa si ha llegado a la posicion de corona.

        if (posActual == posCorona) {
            isOnCoronaPos = true;
        } else {
            isOnCoronaPos = false;
        }
    }

    public int moverDerecha(ImageView imageView, GridPane grdPane) {//mueve hacia la derecha la imagen del sector.
        if (posActual >= posicionY + 3) {
            posActual = posicionY;
        } else {
            posActual++;
        }
        moverNodoA(imageView, posActual, posicionX, grdPane);
        checkCoronaPos(posicionY + 3);
        return posActual;
    }

    public int moverIzquierda(ImageView imageView, GridPane grdPane) {
        if (posActual <= posicionY - 3) {
            posActual = posicionY;
        } else {
            posActual--;

        }
        moverNodoA(imageView, posActual, posicionX, grdPane);
        checkCoronaPos(posicionY - 3);
        return posActual;
    }

    public int moverAbajo(ImageView imageView, GridPane grdPane) {
        if (posActual >= posicionX + 3) {
            posActual = posicionX;
        } else {
            posActual++;
        }
        moverNodoA(imageView, posicionY, posActual, grdPane);
        checkCoronaPos(posicionX + 3);
        return posActual;
    }

    public int moverArriba(ImageView imageView, GridPane grdPane) {
        if (posActual <= posicionX - 3) {
            posActual = posicionX;
        } else {
            posActual--;

        }
        moverNodoA(imageView, posicionY, posActual, grdPane);
        checkCoronaPos(posicionX - 3);
        return posActual;
    }

    public int mover(ImageView imageView, GridPane grdPane) {//mueve la imagen dentro del gripPane de tableros controller segun la dirreccion de cada sector.
        switch (direccion) {
            case 1:
                return moverDerecha(imageView, grdPane);
            case 2:
                return moverIzquierda(imageView, grdPane);
            case 3:
                return moverAbajo(imageView, grdPane);
            case 4:
                return moverArriba(imageView, grdPane);
            default:
                throw new IllegalArgumentException("Dirección no válida: " + direccion);
        }
    }

    public void removerAyudaPorNombre(String ayudaNombre) {
        for (Ayuda ayuda : ayudas) {
            if (ayuda.getNombre().equals(ayudaNombre)) {
                ayuda.setEstado(false);
            }
        }
    }

    public void removerCoronaPorNombre(String coronaNombre) {
        for (Corona corona : coronas) {
            if (corona.getNombre().equals(coronaNombre)) {
                corona.setEstado(false);
            }
        }
    }

    public void habilitarAyudas(boolean valor) {
        for (Ayuda ayuda : ayudas) {
            ayuda.setEstado(valor);
        }
    }

    public void establecerCoronas() {
        this.coronas = new ArrayList<>();
        ArrayList<String> categorias = new ArrayList<>(
                Arrays.asList("Deportes", "Arte", "Geografia", "Ciencia", "Entretenimiento", "Historia"));

        for (String categoria : categorias) {
            this.coronas.add(new Corona(categoria));
        }

    }

    public void establecerAyudas() {
        this.ayudas = new ArrayList<>();
        ArrayList<String> ayudas = new ArrayList<>(
                Arrays.asList("Bomba", "Pasar", "DobleOportunidad", "TirarRuleta"));
        for (String ayuda : ayudas) {
            this.ayudas.add((new Ayuda(ayuda)));
        }

    }

    public void setEstadoCorona(String coronaNombre, boolean estado) {
        for (Corona corona : coronas) {
            if (corona.getNombre().equals(coronaNombre)) {
                corona.setEstado(estado);
            }
        }
    }

    public void setAyudaRandom(int cantidadAyudasRandom) {//Añade una cantidad de ayudas aleatoriamente, si el jugador ya tenia esa ayuda o tenia
        int index = 0;
        Random random = new Random();
        while (index < cantidadAyudasRandom && !hasAllHints()) {
            int numeroAleatorioInt = random.nextInt(ayudas.size());

            if (!(ayudas.get(numeroAleatorioInt).getEstado())) {
                ayudas.get(numeroAleatorioInt).setEstado(true);
                index++;
            }

        }

    }

    public boolean hasOneHint() {
        for (Ayuda ayuda : ayudas) {
            if (ayuda.getEstado()) {
                return true;
            }
        }
        return false;
    }

    private boolean hasAllHints() {
        for (Ayuda ayuda : ayudas) {
            if (!ayuda.getEstado()) {
                return false;
            }
        }
        return true;
    }

    public boolean findAyudaByName(String nombreAyuda) {
        for (Ayuda ayuda : ayudas) {
            if (ayuda.getNombre().equals(nombreAyuda) && ayuda.getEstado()) {
                return true;
            }

        }
        return false;
    }


    public void cargarCoronasAyudas(String input, int sectorPos) {//Carga  y habilita las coronas al sector  iterando sobre una hilera de caracteres.
        int indexTexto = 0;
        int contadorSectores = -1;
        boolean expectedCrown = true;
        boolean expectedHint = false;
        StringBuilder numberBuffer = new StringBuilder();

        while (contadorSectores < sectorPos) {
            char currentChar = input.charAt(indexTexto);
            if (currentChar == '{') {
                contadorSectores++;
            }
            indexTexto++;
        }

        if (contadorSectores == sectorPos) {
            boolean estado = true;
            while (estado) {
                char currentChar = input.charAt(indexTexto);
                if (currentChar == '-') {
                    estado = false;
                    expectedCrown = true;
                    numberBuffer.setLength(0);
                }
                indexTexto++;
            }

            while (expectedCrown) {
                char currentChar = input.charAt(indexTexto);
                if (Character.isAlphabetic(currentChar)) {
                    numberBuffer.append(currentChar);
                } else if (currentChar == ',' || currentChar == ']') {
                    for (Corona corona : coronas) {
                        if (corona.habilitarPorNombre(numberBuffer.toString())) {
                            numberBuffer.setLength(0);
                            break;
                        }

                    }
                } else if (currentChar == '-') {
                    expectedCrown = false;
                    expectedHint = true;
                    estado = true;
                    numberBuffer.setLength(0);
                }
                indexTexto++;
            }

            while (estado) {
                char currentChar = input.charAt(indexTexto);
                if (currentChar == '-') {
                    estado = false;
                }
                indexTexto++;
            }

            while (expectedHint) {
                char currentChar = input.charAt(indexTexto);
                if (Character.isAlphabetic(currentChar)) {
                    numberBuffer.append(currentChar);
                } else if (currentChar == ',') {
                    for (Ayuda ayuda : ayudas) {
                        if (ayuda.habilitarPorNombre(numberBuffer.toString())) {
                            numberBuffer.setLength(0);
                        }
                    }
                } else if (currentChar == '-') {
                    expectedHint = false;
                    estado = true;
                }
                indexTexto++;
            }
        }
    }


    @Override
    public String toString() {
        return "{" + jugador.getId() + "," + posActual + "," + rutaImagenJugador
                + "-" + coronas + "-" + "-" + ayudas + "-" + "}";

    }


}