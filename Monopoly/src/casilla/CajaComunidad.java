package casilla;

import excepcion.JugadorException;
import java.util.ArrayList;
import jugador.Avatar;
import jugador.Jugador;
import tablero.Carta;
import static tablero.Juego.consola;
import static tablero.Juego.partida;

public final class CajaComunidad extends Accion
{
    public CajaComunidad(String nombre)
    {
        super(nombre);
    }
    
    @Override public void aplicarAccion(Jugador jugador) throws JugadorException
    {
        ArrayList<Carta> cartas;
        Carta carta;
        Avatar figura = jugador.getFigura();
     
        cartas = Carta.barajarMonton(figura.getTablero().getCartasComunidad());
        carta = cartas.get(partida.getVentanaCartas().getValor());
        
        partida.getVentanaCartas().getEnunciado().setMensaje(carta.getEnunciado());
        
        carta.accion(jugador);
    }
}
