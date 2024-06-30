/**

 Package game contiene las clases principales que gestionan la lógica del juego UNO.
 */
package game;

import baraja.CartaUno;
import java.util.ArrayList;

/**

 Representa a un jugador en el juego UNO.

 @param nombre Nombre del jugador

 @param puntos Puntos acumulados por el jugador

 @param mano Cartas en mano del jugador
 */
public class Jugador implements Comparable<Jugador> {

    public String nombre;
    private int puntos;
    private ArrayList<CartaUno> mano;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntos = 0;
        this.mano = new ArrayList<>();
    }
    /**

     Devuelve el nombre del jugador.
     @return Nombre del jugador
     */
    public String getNombre() {
        return nombre;
    }
    /**

     Devuelve los puntos del jugador.
     @return Puntos del jugador
     */
    public int getPuntos() {
        return puntos;
    }
    /**

     Devuelve la mano del jugador.
     @return Mano del jugador
     */
    public ArrayList<CartaUno> getMano() {
        return mano;
    }

    public void aumentarPuntos(int puntos) {
        this.puntos += puntos;
    }

    public int numCartas() {
        return this.mano.size();
    }

    public void mostrarMano() {
        System.out.println("Mano de " + this.nombre);
        for (int i = 0; i < this.numCartas(); i++) {
            CartaUno carta = this.mano.get(i);
            System.out.println(i + ". " + carta);
        }
    }

    public CartaUno getCartaAt(int pos) {
        return this.mano.get(pos);
    }

    public void removeCartaAt(int pos) {
        this.mano.remove(pos);
    }

    public boolean sinCartas() {
        return this.mano.isEmpty();
    }

    public void setCartas(ArrayList<CartaUno> cartas) {
        this.mano = cartas;
    }


    @Override
    public String toString() {
        return nombre + " tiene " + puntos + " puntos";
    }

    public void agregarCartas(ArrayList<CartaUno> cartas) {
        for (CartaUno c : cartas) {
            this.mano.add(c);
        }
    }

    public void agregarCartas(CartaUno carta) {
        this.mano.add(carta);
    }

    @Override
    public int compareTo(Jugador j) {
        if (j.getPuntos() > puntos) {
            return 1;
        } else if (j.getPuntos() == puntos) {
            return 0;
        } else {
            return -1;
        }
    }
}
