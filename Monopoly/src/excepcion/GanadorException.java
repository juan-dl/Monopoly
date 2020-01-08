package excepcion;

import jugador.Jugador;

public final class GanadorException extends JugadorException 
{
    private Jugador ganador;
    
    public GanadorException(String error, Jugador ganador)
    {
        super(error);
        this.ganador = ganador;
    }
    
    public Jugador getGanador()
    {
        return ganador;
    }
}
