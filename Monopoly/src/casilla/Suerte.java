package casilla;

import excepcion.JugadorException;
import java.util.ArrayList;
import jugador.Avatar;
import jugador.Jugador;
import tablero.Carta;
import static tablero.Juego.consola;
import static tablero.Juego.partida;

public final class Suerte extends Accion
{
    public Suerte(String nombre)
    {
        super(nombre);
    }
    
    @Override public void aplicarAccion(Jugador jugador) throws JugadorException
    {
        ArrayList<Carta> cartas;
        Carta carta;
        Avatar figura = jugador.getFigura();

        cartas = Carta.barajarMonton(figura.getTablero().getCartasSuerte());
        
        partida.getVentanaCartas().setTipo(false);
        carta = cartas.get(partida.getVentanaCartas().getValor());
        
        partida.getVentanaCartas().getEnunciado().setMensaje(carta.getEnunciado());
        
        carta.accion(jugador);
    }
}
