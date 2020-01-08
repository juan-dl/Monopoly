package jugador;

import casilla.Propiedad;
import static tablero.Juego.consola;

public final class TratoCasillaPorCasillaYDinero extends Trato
{
    private Propiedad oferta, demanda;
    private long dineroDemandado;
    
    public TratoCasillaPorCasillaYDinero(String id, String enunciado, Jugador emisor, Jugador receptor, Propiedad oferta, Propiedad demanda, long dinero)
    {
        super(id, enunciado, emisor, receptor);
        
        if(oferta != null && demanda != null && dinero > 0)
        {
            this.oferta = oferta;
            this.demanda = demanda;
            dineroDemandado = dinero;
        }
        else
            consola.imprimir("Alguno de los atributos del constructor de TratoNoAlquiler no ha sido construido");
    }
    
    public Propiedad getOferta()
    {
        return oferta;
    }
    
    public Propiedad getDemanda()
    {
        return demanda;
    }
    
    public long getDineroDemandado()
    {
        return dineroDemandado;
    }
    
    @Override public boolean resolverTrato()
    {
        if(getEmisor().getPropiedades().contains(oferta))
            if(getReceptor().getPropiedades().contains(demanda))
                if(getReceptor().getDinero() - dineroDemandado > 0)
                {   
                    getReceptor().setDinero(getReceptor().getDinero() - dineroDemandado);
                    getEmisor().setDinero(getEmisor().getDinero() + dineroDemandado);
                    
                    oferta.setPropietario(getReceptor());
                    demanda.setPropietario(getEmisor());

                    getEmisor().getPropiedades().remove(oferta);
                    getReceptor().getPropiedades().remove(demanda);

                    getEmisor().getPropiedades().add(demanda);
                    getReceptor().getPropiedades().add(oferta);
                    
                    getEmisor().getTratosPropuestos().remove(this);
                    getReceptor().getTratosRecibidos().remove(this);

                    consola.imprimir("Se ha aceptado el siguiente trato con " + getEmisor().getNombre() + ": le doy " + oferta.getNombre() + " y " + getReceptor().getNombre() + " me da " + demanda.getNombre() + " y " + dineroDemandado + "€.");

                    return true;
                }
                else
                    consola.imprimir("El trato no puede ser aceptado: " + getReceptor().getNombre() + " no dispone de " + dineroDemandado + "€.");
            else
                consola.imprimir("El trato no puede ser aceptado: " + demanda.getNombre() + " esta hipotecada o no pertenece a " + getReceptor().getNombre() + ".");
        else
            consola.imprimir("El trato no puede ser aceptado: " + oferta.getNombre() + " esta hipotecada o no pertenece a " + getEmisor().getNombre() + ".");
        
        return false;
    }
}
