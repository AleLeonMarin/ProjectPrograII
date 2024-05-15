/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyecto.model;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author justi
 */
public class Juego {

    private ArrayList<Jugador> jugadores;
    private ArrayList<Pregunta> preguntas;
    private int turnoActual;
    private Scanner scanner;

    public Juego() {
        jugadores = new ArrayList<>();
        preguntas = new ArrayList<>();
        scanner = new Scanner(System.in);
        turnoActual = 0;
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public void agregarPregunta(Pregunta pregunta) {
        preguntas.add(pregunta);
    }

    public void iniciarJuego() {
        while (!hayGanador())
        {
            Jugador jugadorActual = jugadores.get(turnoActual);
            System.out.println("Turno de " + jugadorActual.getNombre());
            Pregunta preguntaActual = obtenerPreguntaAleatoria();
            System.out.println("Pregunta: " + preguntaActual.getPregunta());
            System.out.print("Respuesta: ");
            String respuesta = scanner.nextLine();
            if (respuesta.equalsIgnoreCase(preguntaActual.getRespuesta()))
            {
                jugadorActual.aumentarPuntos();
                System.out.println("Respuesta correcta. ¡Has ganado un punto!");
            } else
            {
                System.out.println("Respuesta incorrecta. Siguiente jugador.");
            }
            cambiarTurno();
        }
        mostrarGanador();
    }

    private boolean hayGanador() {
        for (Jugador jugador : jugadores)
        {
            if (jugador.getPuntos() >= 6)//cantidad de coronas
            {
                return true;
            }
        }
        return false;
    }

    private Pregunta obtenerPreguntaAleatoria() {
        int indicePregunta = (int) (Math.random() * preguntas.size());
        return preguntas.get(indicePregunta);
    }

    private void cambiarTurno() {
        turnoActual = (turnoActual + 1) % jugadores.size();
    }

    private void mostrarGanador() {
        for (Jugador jugador : jugadores)
        {
            if (jugador.getPuntos() >= 3)
            {
                System.out.println("¡El ganador es: " + jugador.getNombre() + " con " + jugador.getPuntos() + " puntos!");
                break;
            }
        }
    }
}
