package baraja;
/**

 Representa una carta del juego UNO.

 @param efecto Efecto especial de la carta (si tiene)
 */
public class CartaUno extends Carta<ColoresBarajaUno> {
    //ATRIBUTOS
    private EfectosBarajaUno efecto;

    //METODOS
    public CartaUno(int numero, ColoresBarajaUno color){
        super(numero,color);};

    public CartaUno(ColoresBarajaUno color, EfectosBarajaUno efecto) {
        super(-1, color);
        this.efecto = efecto;
    }

    public EfectosBarajaUno getEfecto() {
        return efecto;
    }

    public boolean isEspecial() {
        return this.efecto != null;
    }
    
    public boolean compatible(CartaUno c){
        return this.getColor() == ColoresBarajaUno.NEGRO
                || this.getColor() == c.getColor()
                || (this.getNumero() == c.getNumero() && !this.isEspecial() && !c.isEspecial())
                || (this.isEspecial() && c.isEspecial() && this.efecto == c.efecto);
        
    }

    @Override
    public String toString() {

        String estado = "";

        if (this.isEspecial()) {
            switch (this.efecto) {
                case MASDOS:
                    estado = "+2 " + color;
                    break;
                case MASCUATRO:
                    estado = "+4";
                    break;
                case SALTO:
                    estado = "Salto turno " + color;
                    break;
                case REVERSO:
                    estado = "reverso " + color;
                    break;
                case CAMBIOCOLOR:

                    estado = "Cambio color";
                    break;
            }
        } else {
            estado = numero + " " + color;
        }

        return estado;
    }

}
