# PREGUNTADOS JR

## Introducción

Este proyecto fue realizado en el curso de Programación II en la Universidad Nacional de Costa Rica, con el objetivo de aprender el uso e implementación de JavaFX y bases de datos. Utilizamos JavaFX 21, Oracle 21c XE, Docker, y herramientas como VSCode, IntelliJ, NetBeans y Scene Builder.

## ¿En qué consiste?

"Preguntados JR" es un juego de preguntas y respuestas desarrollado por la empresa argentina Etermax. Los jugadores deben responder preguntas de diversas categorías como historia, geografía, ciencia, entretenimiento, arte y deportes. Acumulan puntos al contestar correctamente y pueden competir contra amigos o desconocidos. El objetivo es responder una cantidad determinada de preguntas correctamente para ganar el juego. Es popular por su simplicidad y capacidad para desafiar el conocimiento general de los jugadores.

## Características 

- Permite de 2 a 6 jugadores con tablero redimensionable.
- Incluye animaciones que no interrumpen la ejecución del sistema.
- Contiene la funcionalidad de duelo entre jugadores.
- Permite editar los detalles de los jugadores antes de iniciar la partida.
- Permite guardar y cargar partidas desde la base de datos.
- Ofrece castigos a los jugadores en caso de seleccionar duelo con un jugador sin coronas.
- Ofrece diferentes modos de juego que afectan las ayudas disponibles para responder preguntas.

## Uso

Para utilizar "Preguntados JR", sigue estos pasos:

1. Asegúrate de tener Java JDK 21 o superior instalado.
2. Asegúrate de tener Oracle DB 21c XE instalado, ya sea mediante Docker o en tu sistema.
3. Ejecuta el script proporcionado en el proyecto para obtener las preguntas.
4. Asegúrate de tener Apache Maven instalado.
5. En tu terminal, ejecuta el siguiente comando para compilar y construir el proyecto:

    ```bash
    mvn clean install
    ```

6. Ve a la carpeta `target`:

    ```bash
    cd target
    ```

7. Para ingresar al juego como jugador regular, ejecuta este comando en tu terminal:

    ```bash
    java -jar proyecto-1.0-shaded.jar C
    ```

8. Para acceder a la parte administrativa, ejecuta el siguiente comando:

    ```bash
    java -jar proyecto-1.0-shaded.jar A
    ```

9. ¡Disfruta del juego!

## Desarrolladores

- Alejandro Leon Marin 
- Justin Mendez Mena 

## Créditos 

Créditos a Etermax por la creación y distribución del juego.

## Contacto 

Correo electrónico: aleleonmarin01@gmail.com
