## Blackjack Game
El juego de Blackjack es una aplicación de consola que permite a un jugador jugar al famoso juego de cartas conocido como Blackjack. El juego se implementa en Java y utiliza la biblioteca Swing para crear una interfaz gráfica simple.

## Características
El jugador puede introducir su nombre al inicio del juego.
El jugador comienza con 1000 créditos.
El jugador puede realizar apuestas y jugar con créditos o sin ellos.
El crupier reparte las cartas iniciales.
El jugador puede pedir una carta o plantarse.
El crupier juega su turno después del jugador.
El resultado de la ronda se muestra al jugador.
Los créditos del jugador se actualizan según el resultado de la ronda.
El jugador puede volver al menú principal después de cada ronda.
El jugador puede salir del juego en cualquier momento.
Requisitos previos
Para ejecutar la aplicación, necesitarás tener instalado Java Development Kit (JDK) en tu sistema.

## Cómo ejecutar la aplicación
Clona o descarga los archivos de la aplicación en tu sistema.
Abre una terminal o línea de comandos y navega hasta el directorio donde se encuentran los archivos de la aplicación.
Compila los archivos de la aplicación ejecutando el siguiente comando:
javac BlackjackGame.java
Una vez compilados los archivos, ejecuta la aplicación con el siguiente comando:
java BlackjackGame
Sigue las instrucciones en la interfaz gráfica para jugar al Blackjack.
Para ingresar al juego deberás introducir un usuario cualquiera y la contraseña "1234".
Pruebas unitarias
La aplicación incluye pruebas unitarias para verificar el funcionamiento de algunas clases clave. Estas pruebas se encuentran en los archivos BlackjackGameTest.java, CardTest.java, DeckTest.java y HandTest.java. Para ejecutar las pruebas, sigue los siguientes pasos:

Abre una terminal o línea de comandos y navega hasta el directorio donde se encuentran los archivos de la aplicación.
Compila los archivos de prueba ejecutando el siguiente comando:
javac -cp junit-4.x.jar:. *.java
Asegúrate de reemplazar 4.x con la versión de JUnit que tienes instalada en tu sistema.

Una vez compilados los archivos, ejecuta las pruebas con el siguiente comando:
java -cp junit-4.x.jar:. org.junit.runner.JUnitCore <nombre_de_la_clase_de_prueba>
Reemplaza <nombre_de_la_clase_de_prueba> con el nombre de una de las clases de prueba, como BlackjackGameTest, CardTest, DeckTest o HandTest.

## Créditos
Este juego de Blackjack fue desarrollado por Ramón García del Prado, Pablo Lliso y Daniel Elvira Sánchez.
El programa se ha elaborado entre los integrantes del grupo en conjunto, no ha habido partes individuales, cada uno ha ido mejorando, corrigiendo y elaborando elaborando el código.

