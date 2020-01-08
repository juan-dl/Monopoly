package jugador;

import casilla.Propiedad;
import static tablero.Juego.consola;

public final class TratoDineroPorCasilla extends Trato
{
    private Propiedad demanda;
    private long dineroOfrecido;
    
    public TratoDineroPorCasilla(String id, String enunciado, Jugador emisor, Jugador receptor, Propiedad demanda, long dinero)
    {
        super(id, enunciado, emisor, receptor);
        
        //Permitimos regalar casillas
        if(demanda != null && dinero >= 0)
        {
            this.demanda = demanda;
            dineroOfrecido = dinero;
        }
        else
            consola.imprimir("Alguno de los atributos del constructor de TratoDineroPorCasilla no ha sido construido");
    }
    
    public Propiedad getDemanda()
    {
        return demanda;
    }
    
    @Override public boolean resolverTrato()
    {
        if(getReceptor().getPropiedades().contains(demanda))
            if(getEmisor().getDinero() - dineroOfrecido > 0)
            {
                getEmisor().setDinero(getEmisor().getDinero() - dineroOfrecido);
                getReceptor().setDinero(getReceptor().getDinero() + dineroOfrecido);
                
                demanda.setPropietario(getEmisor());
                
                getReceptor().getPropiedades().remove(demanda);
                getEmisor().getPropiedades().add(demanda);
                
                getEmisor().getTratosPropuestos().remove(this);
                getReceptor().getTratosRecibidos().remove(this);
                
                consola.imprimir("Se ha aceptado el siguiente trato con " + getReceptor().getNombre() + ": me da " + demanda.getNombre() + " y a " + getReceptor().getNombre() + " le doy " + dineroOfrecido + "€.");
                
                return true;
            }
            else
                consola.imprimir("El trato no puede ser aceptado: " + getEmisor().getNombre() + " no dispone de " + dineroOfrecido + "€.");
        else
            consola.imprimir("El trato no puede ser aceptado: " + demanda.getNombre() + " esta hipotecada o no pertenece a " + getReceptor().getNombre() + ".");
        
        return false;
    }
}