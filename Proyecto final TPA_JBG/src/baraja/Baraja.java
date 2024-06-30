/**

 Package baraja contiene las clases relacionadas con la gestión de la baraja de cartas y las cartas individuales.
 */
package baraja;

import java.util.ArrayList;
import pila.PilaDinamica;


public abstract class Baraja<T extends Carta> {

    //Atributos
    protected PilaDinamica<T> cartas;
    protected PilaDinamica<T> cartasMonton;
    protected int numCartas;
    protected int cartasPorPalo;

    public Baraja() {
        this.cartas = new PilaDinamica<>();
        this.cartasMonton = new PilaDinamica<>();
    }
    public abstract void crearBaraja();
    public void barajar() {

        int posAleatoria = 0;
        T carta;

        while (!this.cartasMonton.isEmpty()) {
            this.cartas.push(this.cartasMonton.pop());
        }

        ArrayList<T> cartasBarajar = new ArrayList<>();

        while (!this.cartas.isEmpty()) {
            cartasBarajar.add(this.cartas.pop());
        }

        for (int i = 0; i < cartasBarajar.size(); i++) {

            do {
                posAleatoria = Metodos.generaNumeroEnteroAleatorio(0, cartasBarajar.size() - 1);
                carta = cartasBarajar.get(posAleatoria);
            } while (carta == null);

            this.cartas.push(carta);
            cartasBarajar.set(posAleatoria, null);

        }

    }
    public T siguienteCarta(boolean monton) {

        T carta = null;

        if (!this.cartas.isEmpty()) {
            carta = cartas.pop();
            if (monton) {
                this.cartasMonton.push(carta);
            }
        }

        return carta;

    }
    /**

     Devuelve una lista de cartas para ser repartidas a un jugador.
     @param numCartas Número de cartas a repartir
     @param inicial Indica si es la repartición inicial
     @return Lista de cartas repartidas
     */
    public ArrayList<T> darCartas(int numCartas, boolean monton) {

        if (numCartas > this.numCartas || cartasDisponible() < numCartas) {
            return null;
        } else {

            ArrayList<T> cartasDar = new ArrayList<>();

            //recorro el array que acabo de crear para rellenarlo
            for (int i = 0; i < numCartas; i++) {
                cartasDar.add(siguienteCarta(monton)); //uso el metodo anterior
            }

            //Lo devuelvo
            return cartasDar;

        }

    }

    public int cartasDisponible() {
        return this.cartas.size();
    }

    public void cartasMonton() {

        if (cartasDisponible() == numCartas) {
            System.out.println("No se ha sacado ninguna carta");
        } else {
            System.out.println(this.cartasMonton.toString());
        }

    }
    
    public void agregarCartaMonton(T carta){
        this.cartasMonton.push(carta);
    }

}
