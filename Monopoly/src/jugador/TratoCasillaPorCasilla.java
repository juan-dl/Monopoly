package jugador;

import casilla.Propiedad;
import static tablero.Juego.consola;

public final class TratoCasillaPorCasilla extends Trato
{
    private Propiedad oferta, demanda;
    
    public TratoCasillaPorCasilla(String id, String enunciado, Jugador emisor, Jugador receptor, Propiedad oferta, Propiedad demanda)
    {
        super(id, enunciado, emisor, receptor);
        
        if(oferta != null && demanda != null)
        {
            this.oferta = oferta;
            this.demanda = demanda;
        }
        else
            consola.imprimir("Alguno de los atributos del constructor de TratoCasillaPorCasilla no ha sido construido");
    }
    
    public Propiedad getOferta()
    {
        return oferta;
    }
    
    public Propiedad getDemanda()
    {
        return demanda;
    }
    
    @Override public boolean resolverTrato()
    {
        if(getEmisor().getPropiedades().contains(oferta))
            if(getReceptor().getPropiedades().contains(demanda))
            {   
                oferta.setPropietario(getReceptor());
                demanda.setPropietario(getEmisor());
                
                getEmisor().getPropiedades().remove(oferta);
                getReceptor().getPropiedades().remove(demanda);
                
                getEmisor().getPropiedades().add(demanda);
                getReceptor().getPropiedades().add(oferta);
                
                getEmisor().getTratosPropuestos().remove(this);
                getReceptor().getTratosRecibidos().remove(this);
                
                consola.imprimir("Se ha aceptado el siguiente trato con " + getReceptor().getNombre() + ": le doy " + oferta.getNombre() + " y " + getReceptor().getNombre() + " me da " + demanda.getNombre() + ".");
                
                return true;
            }
            else
                consola.imprimir("El trato no puede ser aceptado: " + demanda.getNombre() + " esta hipotecada o no pertenece a " + getReceptor().getNombre() + ".");
        else
            consola.imprimir("El trato no puede ser aceptado: " + oferta.getNombre() + " esta hipotecada o no pertenece a " + getEmisor().getNombre() + ".");
        
        return false;
    }
}