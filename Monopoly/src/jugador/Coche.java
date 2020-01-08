package jugador;

import excepcion.JugadorException;
import static tablero.Juego.consola;
import tablero.Tablero;

public final class Coche extends Avatar
{  
    public Coche(Jugador jugador, Tablero tableroActual)
    {
        super(jugador, tableroActual);
    }
    
    @Override public boolean lanzarDados(short sacarDobles) throws JugadorException
    {
        boolean dobles = super.lanzarDados(sacarDobles);

        if(getMovimientoAvanzado() != -5)
            moverEnAvanzado();
        
        moverEnBasico(sacarDobles, dobles);
        
        return dobles;
    }
     
    @Override public void moverEnAvanzado()
    {
        if(getResultadoDados() > 4)
            setMovimientoAvanzado((short)(getMovimientoAvanzado() - 1));
        else
        {
            setMovimientoAvanzado((short)3);
            setResultadoDados(getResultadoDados() * -1);               
        }
    }
    
    @Override public String toString()
    {    
        return ("<br>Id: " + getID() + "<br>Tipo: Coche <br>Casilla: "+ getUbicacion().getNombre() +"<br>Jugador: "+ getJugador().getNombre() +"<br><br>");
    }
}
