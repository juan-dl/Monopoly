package casilla;

import jugador.Jugador;
import tablero.Constantes;

public final class Transporte extends Propiedad
{
    private long pagoTransporte;
    
    public Transporte(String nombre, Jugador jugador, long precio)
    {
        super(nombre, jugador, precio, null);
        
        pagoTransporte = (long)(Constantes.PAGO_VUELTA * 0.25);
    }

    public long getPagoTransporte() 
    {
        return pagoTransporte;
    }

    public void setPagoTransporte(long pagoTransporte)
    {
        this.pagoTransporte = pagoTransporte;
    }
    
    @Override public String imprimirDatos()
    {
        if(getPropietario().getNombre().equals("banca"))
            return "Nombre: " + getNombre() + "<br>tipo: Transporte<br>propietario: " + getPropietario().getNombre() + "<br>valor: " + getPrecio() + "<br>alquiler: -";
        else
            return "Nombre: " + getNombre() + "<br>tipo: Transporte<br>propietario: " + getPropietario().getNombre() + "<br>valor: " + getPrecio() + "<br>alquiler: " + getAlquilerActual();
    }
    
    @Override public long alquiler()
    {
        long precio = 0;
            
        for(Casilla casilla : getPropietario().getPropiedades())
            if(casilla instanceof Transporte)
                precio += ((Transporte) casilla).getPagoTransporte();
        
        return precio;
    }
}

