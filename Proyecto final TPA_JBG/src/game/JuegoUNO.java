/**

 Package game contiene las clases principales que gestionan la lógica del juego UNO.
 */
package game;

import baraja.BarajaUno;
import baraja.CartaUno;
import baraja.Metodos;
import baraja.ColoresBarajaUno;
import java.util.ArrayList;
import java.util.Random;

/**

 Clase principal del juego UNO.

 Gestiona el flujo del juego y sus reglas.

 @param ronda Número de la ronda actual

 @param baraja La baraja de cartas del juego

 @param turno Índice del jugador cuyo turno es el actual

 @param jugadores Array de jugadores participando en el juego

 */
public class JuegoUNO {

    private int ronda;
    BarajaUno baraja;
    private int turno;
    private Jugador[] jugadores;

    /**

     Selecciona aleatoriamente el jugador inicial.
     @return El índice del jugador inicial
     */
    public JuegoUNO(Jugador[] jugadores) {
        this.jugadores = jugadores;
        this.ronda = 1;
        this.turnoInicial();
        this.repartirCartas();

        if (this.baraja.getUltimaCarta().isEspecial()) {
            this.aplicarEfectoCarta(true);
        }

    }
    /**

     Selecciona aleatoriamente el jugador inicial.
     @return El índice del jugador inicial
     */
    public void turnoInicial() {
        this.turno = Metodos.generaNumeroEnteroAleatorio(0, this.jugadores.length - 1);
    }

    /**

     Reparte las cartas iniciales a los jugadores.
     */
    public void repartirCartas() {
        this.baraja = new BarajaUno();
        for (Jugador j : this.jugadores) {
            j.setCartas(this.baraja.darCartas(5, false));
        }
    }


    public Jugador jugadorActual() {
        return this.jugadores[this.turno];
    }

    public void cambioTurno() {

        if (this.baraja.isSentido() && turno == this.jugadores.length - 1) {
            turno = 0;
        } else if (!this.baraja.isSentido() && turno == 0) {
            this.turno = this.jugadores.length - 1;
        } else {
            if (this.baraja.isSentido()) {
                turno++;
            } else {
                turno--;
            }
        }

    }


    public boolean finJuego(Jugador jugador) {
        return jugador.sinCartas();
    }



    public boolean cartaCompatible(int posCarta) {

        CartaUno cartaJ = this.jugadorActual().getCartaAt(posCarta);
        CartaUno cartaM = this.baraja.getUltimaCarta();

        if (cartaJ.compatible(cartaM) || baraja.getColorActual() == cartaJ.getColor()) {
            this.baraja.setUltimaCarta(cartaJ);
            this.baraja.agregarCartaMonton(cartaJ);
            this.jugadorActual().removeCartaAt(posCarta);

            if (cartaJ.isEspecial()) {
                this.aplicarEfectoCarta(false);
            } else {
                this.baraja.actualizarColor();
            }

            return true;
        } else {
            return false;
        }

    }

    public Jugador siguienteJugador() {
        if (this.baraja.isSentido()) {
            if (this.turno == this.jugadores.length - 1) {
                return this.jugadores[0];
            } else {
                return this.jugadores[this.turno + 1];
            }
        } else {
            if (this.turno == 0) {
                return this.jugadores[this.jugadores.length - 1];
            } else {
                return this.jugadores[this.turno - 1];
            }
        }
    }

    public void elegirColor() {

        ColoresBarajaUno[] colores = ColoresBarajaUno.values();
        Random random = new Random();

        // Excluimos el último color, que asumimos es el negro
        int posColor = random.nextInt(colores.length - 1);

        this.baraja.setColorActual(colores[posColor]);

    }

    public void pasarTurno() {
        this.cambioTurno();
    }

    public void cartaCentro() {
        System.out.println("Carta en el centro: ");
        System.out.println(this.baraja.getUltimaCarta() + " (" + this.baraja.getColorActual() + ")");
    }

    public void robarCarta() {
        CartaUno carta = this.baraja.siguienteCarta(false);
        if (carta != null) {
            this.jugadorActual().agregarCartas(carta);
        } else {
            this.baraja.barajar();
            carta = this.baraja.siguienteCarta(false);
            if (carta != null) {
                this.jugadorActual().agregarCartas(carta);
            } else {
                System.out.println("Ya no hay mas cartas que robar");
            }
        }
    }

    public void aplicarEfectoCarta(boolean inicio) {

        CartaUno cartaM = this.baraja.getUltimaCarta();
        Jugador siguienteJ = null;
        ArrayList<CartaUno> cartas;
        switch (cartaM.getEfecto()) {
            case MASDOS:
                if (inicio) {
                    siguienteJ = this.jugadorActual();
                } else {
                    siguienteJ = this.siguienteJugador();
                }
                cartas = this.baraja.darCartas(2, false);
                if (cartas == null) {
                    this.baraja.barajar();
                    cartas = this.baraja.darCartas(2, false);
                }
                siguienteJ.agregarCartas(cartas);
                System.out.println("2 cartas mas para el jugador " + siguienteJ.getNombre());
                this.pasarTurno();
                this.baraja.actualizarColor();
                break;
            case MASCUATRO:

                if (inicio) {
                    siguienteJ = this.jugadorActual();
                } else {
                    siguienteJ = this.siguienteJugador();
                }
                cartas = this.baraja.darCartas(4, false);
                if (cartas == null) {
                    this.baraja.barajar();
                    cartas = this.baraja.darCartas(4, false);
                }
                siguienteJ.agregarCartas(cartas);
                System.out.println("4 cartas mas para el jugador " + siguienteJ.getNombre());
                this.elegirColor();
                System.out.println("Color cambiado");
                this.pasarTurno();
                break;
            case SALTO:
                this.pasarTurno();
                System.out.println("Se salta al siguiente jugador");
                this.baraja.actualizarColor();
                break;
            case REVERSO:
                this.baraja.cambiarSentido();
                System.out.println("Se cambia el sentido");
                this.baraja.actualizarColor();
                break;
            case CAMBIOCOLOR:
                this.elegirColor();
                System.out.println("Color cambiado");
                break;
        }

    }

}
