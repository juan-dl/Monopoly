package casilla;

import java.util.ArrayList;
import jugador.Jugador;
import tablero.Constantes;

public final class Servicio extends Propiedad
{
    public Servicio(String nombre, Jugador jugador, long precio)
    {
        super(nombre, jugador, precio, null);
    }
    
    @Override public String imprimirDatos()
    {
        return "Nombre: " +getNombre() + "<br>tipo: Servicio<br>propietario: " + getPropietario().getNombre() + "<br>valor: " + getPrecio();
    }
    
    @Override public long alquiler()
    {
        Casilla aux = null;
        int bonusServicioDefecto = 4;
        short numeroCasillas = 0 ;
            
        for(ArrayList<Casilla> lado : getPropietario().getFigura().getTablero().getCasillas())
            for(Casilla cas : lado)
                if(cas instanceof Especial && ((Especial)cas).getTipo().equals("Salida"))
                    aux = cas;
                
        for(Casilla casilla : getPropietario().getPropiedades())
            {                   
                if(casilla instanceof Servicio)
                    numeroCasillas++;
                    
                if(numeroCasillas == 2)
                {
                    bonusServicioDefecto = 10;
                    break;
                }
            }
        
        return (long)(bonusServicioDefecto * getPropietario().getFigura().getResultadoDados() * ((Especial)aux).getPago()/200);
       
    }    
}
