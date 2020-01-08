package excepcion;

import jugador.Jugador;

public final class SinDineroException extends JugadorException
{
    private Jugador deuda;
    
    public SinDineroException(String error, Jugador deuda)
    {
        super(error);
        this.deuda = deuda;
    }
    
    public Jugador getDeuda()
    {
        return deuda;
    }
}
