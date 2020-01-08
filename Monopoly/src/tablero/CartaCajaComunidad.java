package tablero;

import casilla.Casilla;
import jugador.*;
import excepcion.JugadorException;
import excepcion.SinDineroException;
import excepcion.EncarceladoException;
import static tablero.Juego.consola;

public final class CartaCajaComunidad extends Carta
{
    public CartaCajaComunidad(Tablero tablero, String enunciado, long valor)
    {
        super(tablero, enunciado, valor);
    }
    
    public CartaCajaComunidad(Tablero tablero, String enunciado, Casilla nuevaUbicacion)
    {
        super(tablero, enunciado, nuevaUbicacion);
    }    
    
    @Override public void accion(Jugador jugador) throws JugadorException
    {
        long valor = getValor(), gastos = 0, premios;
        Avatar figura = jugador.getFigura();
        
        if (valor == 0 && getNuevaUbicacion() != null) 
        {
            //En nuestro caso las casillas que cambian de ubicación solo llevan a la cárcel
            throw(new EncarceladoException(""));
        }
        else if(valor == -608)     //La carta que hay que pagar a todos los jugadores
        {
            if(jugador.getDinero() + valor * (figura.getTablero().getFiguras().size() - 1) > 0)
            {
                jugador.setDinero(jugador.getDinero() + valor * (figura.getTablero().getFiguras().size() - 1));

                gastos = valor;
                
                for (Character clave : figura.getTablero().getFiguras().keySet()) 
                {
                    Jugador competencia = figura.getTablero().getFiguras().get(clave).getJugador();

                    if (!competencia.equals(jugador))
                    {
                        competencia.setDinero(competencia.getDinero() - valor);

                        premios = competencia.getEstadisticas().get(5) - valor;

                        competencia.getEstadisticas().remove(5);
                        competencia.getEstadisticas().add(5,premios);
                    }
                }
            }
            else
                throw(new SinDineroException("No puede pagar la carta, queda en bancarrota.", figura.getTablero().getBanca()));
        }
        else 
        {
            if(valor > 0 || jugador.getDinero() + valor > 0)
            {
                jugador.setDinero(jugador.getDinero() + valor);
                
                gastos = valor;
                
                if (valor < 0)
                    valor *= -1;
                else
                {
                    premios = jugador.getEstadisticas().get(5) + valor;

                    jugador.getEstadisticas().remove(5);
                    jugador.getEstadisticas().add(5,premios);
                }

                figura.getTablero().setBote(figura.getTablero().getBote() + valor);
            }
            else
                throw(new SinDineroException("No puede pagar la carta, queda en bancarrota.", figura.getTablero().getBanca()));
        }

        if(gastos < 0)
        {
            gastos*=-1;
            
            if(figura instanceof Esfinge)
            {
                gastos += ((Esfinge)figura).getPagos().get(2);
                ((Esfinge)figura).getPagos().remove(2);
                ((Esfinge)figura).getPagos().add(2,gastos);
            }
            if(figura instanceof Sombrero)
            {
                gastos += ((Sombrero)figura).getPagos().get(2);
                ((Sombrero)figura).getPagos().remove(2);
                ((Sombrero)figura).getPagos().add(2,gastos);
            }
        }
        else
        {
            if(figura instanceof Esfinge)
            {
                gastos += ((Esfinge)figura).getPagos().get(1);
                ((Esfinge)figura).getPagos().remove(1);
                ((Esfinge)figura).getPagos().add(1,gastos);
            }
            if(figura instanceof Sombrero)
            {
                gastos += ((Sombrero)figura).getPagos().get(1);
                ((Sombrero)figura).getPagos().remove(1);
                ((Sombrero)figura).getPagos().add(1,gastos);
            }
        }
    }
}
