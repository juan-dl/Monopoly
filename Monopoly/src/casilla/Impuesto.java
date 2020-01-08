package casilla;

import jugador.Avatar;
import jugador.Esfinge;
import jugador.Jugador;
import jugador.Sombrero;
import tablero.Constantes;
import excepcion.SinDineroException;
import tablero.Juego;
import static tablero.Juego.consola;

public final class Impuesto extends Casilla
{
    private long impuesto;
    
    //-----------SI TIPO ES TRUE ES EL IMPUESTO CARO, SI ES FALSE ES EL BARATO----------------
    public Impuesto(String nombre, boolean tipo)
    {
        super(nombre);
        
        if(tipo)
            impuesto = Constantes.PAGO_VUELTA;
        else
            impuesto = Constantes.PAGO_VUELTA/2;
    }

    public long getImpuesto()
    {
        return impuesto;
    }

    public void setImpuesto(long impuesto) 
    {
        this.impuesto = impuesto;
    }

    public void cobrarImpuesto(Jugador jugador) throws SinDineroException
    {
        long pagoImp,gastos;
        Avatar figura = jugador.getFigura();
        
        if (jugador.getDinero() - impuesto > 0) 
        {
            pagoImp = jugador.getEstadisticas().get(1) + impuesto;

            jugador.getEstadisticas().remove(1);
            jugador.getEstadisticas().add(1,pagoImp);

            jugador.setDinero(jugador.getDinero() - impuesto);
            figura.getTablero().setBote(figura.getTablero().getBote() + impuesto);
            consola.imprimir(Juego.popup.getMensaje().substring(6, Juego.popup.getMensaje().length() - 7) + " El jugador paga " + impuesto + ".");

            if(figura instanceof Sombrero)
            {
                gastos = ((Sombrero)figura).getPagos().get(2) + impuesto;
                ((Sombrero)figura).getPagos().remove(2);
                ((Sombrero)figura).getPagos().add(2,gastos);
            }

            if(figura instanceof Esfinge)
            {
                gastos = ((Esfinge)figura).getPagos().get(2) + impuesto;
                ((Esfinge)figura).getPagos().remove(2);
                ((Esfinge)figura).getPagos().add(2,gastos);
            }
        } 
        else 
        {
            throw(new SinDineroException("No puede pagar el impuesto, queda en bancarrota", jugador.getFigura().getTablero().getBanca()));
        }
    }
}
