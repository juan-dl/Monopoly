package casilla;

import excepcion.JugadorException;
import jugador.Jugador;
import static tablero.Juego.consola;

public abstract class Accion extends Casilla
{
    private long bote;
    
    public Accion(String nombre)
    {
        super(nombre);
    }
    
    public final long getBote()
    {
        return bote;
    }
    
    public final void setBote(long dinero)
    {
        if(dinero > 0)
            bote = dinero;
        else
            consola.imprimir("La cantidad de dinero pasado a setBote no es valido");
    }
    
    public abstract void aplicarAccion(Jugador jugador) throws JugadorException;
}
