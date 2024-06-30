package baraja;

public abstract class Carta<T> {
    //ATRIBUTOS
    protected int numero;
    protected T color;

    //METODOS
    public Carta(int numero, T color) {
        this.numero = numero;
        this.color = color;
    }
    public int getNumero() {
        return numero;
    }

    public T getColor() {
        return color;
    }

}
