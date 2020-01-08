package casilla;

import excepcion.EncarceladoException;
import excepcion.JugadorException;
import excepcion.SinDineroException;
import jugador.Avatar;
import jugador.Esfinge;
import jugador.Jugador;
import jugador.Sombrero;
import tablero.Constantes;
import tablero.Juego;
import static tablero.Juego.consola;
import tablero.Tablero;

public final class Especial extends Accion
{
    private long pago;
    private String tipo;
    
     public Especial(String nombre, String t)
    {
        super(nombre);
        tipo = t;
        
        if(tipo.equals("Salida"))
            pago = Constantes.PAGO_VUELTA;
        
        if(tipo.equals("Carcel"))
            pago = Constantes.PAGO_VUELTA/4;
    }
     
     public long getPago()
     {
         return pago;
     }

    public String getTipo() 
    {
        return tipo;
    }
    
    public void setPago(long p)
    {
        pago = p;
    }

    public void setTipo(String tipo) 
    {
        this.tipo = tipo;
    }
    
    @Override public void aplicarAccion(Jugador jugador) throws JugadorException
    {
        Avatar figura = jugador.getFigura();
        Tablero tablero = figura.getTablero();
        long premios,gastos;
        
        switch(tipo){
            case "Parking":
                jugador.setDinero(jugador.getDinero() + tablero.getBote());
                consola.imprimir(Juego.popup.getMensaje().substring(6, Juego.popup.getMensaje().length() - 7) + " El jugador gana el bote acumulado de " + tablero.getBote() + ".");
                
                premios = jugador.getEstadisticas().get(5) + tablero.getBote();
                
                jugador.getEstadisticas().remove(5);
                jugador.getEstadisticas().add(5,premios);
                
                if(figura instanceof Esfinge)
                {
                    gastos = ((Esfinge)figura).getPagos().get(1) + tablero.getBote();
                    ((Esfinge)figura).getPagos().remove(1);
                    ((Esfinge)figura).getPagos().add(1,gastos);
                }
                if(figura instanceof Sombrero)
                {
                    gastos = ((Sombrero)figura).getPagos().get(1) + tablero.getBote();
                    ((Sombrero)figura).getPagos().remove(1);
                    ((Sombrero)figura).getPagos().add(1,gastos);
                }
                
                tablero.setBote(0);
                break;
            case "Carcel":
                //Cuando se pague para salir se llama a este metodo(cuando se emplea salirCarcel en juego o cuando se obliga al jugador a pagar)
                if(figura.getJugador().getDinero() - pago > 0)
                {
                    figura.getJugador().setDinero(figura.getJugador().getDinero() - pago);

                    consola.imprimir(figura.getJugador().getNombre() + " con figura \'" + figura.getID() + "\' paga " + pago + " y sale de la carcel.");

                    if(figura.getEncarcelado() < Constantes.CARCEL_PAGO_FORZADO)
                        consola.imprimir( Juego.popup.getMensaje().substring(6, Juego.popup.getMensaje().length() - 7) + " Puede lanzar los dados.");

                    figura.setEncarcelado((short)0);
                }
                else
                    if(figura.getEncarcelado() == Constantes.CARCEL_PAGO_FORZADO)
                    {
                        throw(new SinDineroException(" No tiene dinero para salir de la carcel, queda en bancarrota.", tablero.getBanca()));
                    }
                    else
                        consola.imprimir(Juego.popup.getMensaje().substring(6, Juego.popup.getMensaje().length() - 7) + "El jugador " + figura.getJugador().getNombre() + " con figura \'" + figura.getID() + "\' no tiene suficiente dinero para salir de la carcel, pruebe a sacar dobles");

                break;
            case "IrCarcel":
                throw(new EncarceladoException(""));
            case "Salida":
                jugador.setDinero(jugador.getDinero() + pago);
                break;
        }
    }
}
