package jugador;

import casilla.Propiedad;
import static tablero.Juego.consola;

public final class TratoCasillaPorDinero extends Trato
{
    private Propiedad oferta;
    private long dineroDemandado;
    
    public TratoCasillaPorDinero(String id, String enunciado, Jugador emisor, Jugador receptor, Propiedad oferta, long dinero)
    {
        super(id, enunciado, emisor, receptor);
        
        //Permitimos regalar casillas
        if(oferta != null && dinero >= 0)
        {
            this.oferta = oferta;
            dineroDemandado = dinero;
        }
        else
            consola.imprimir("Alguno de los atributos del constructor de TratoCasillaPorDinero no ha sido construido");
    }
    
    public Propiedad getOferta()
    {
        return oferta;
    }
    
    @Override public boolean resolverTrato()
    {
        if(getEmisor().getPropiedades().contains(oferta))
            if(getReceptor().getDinero() - dineroDemandado > 0)
            {
                getReceptor().setDinero(getReceptor().getDinero() - dineroDemandado);
                getEmisor().setDinero(getEmisor().getDinero() + dineroDemandado);
                
                oferta.setPropietario(getReceptor());
                
                getEmisor().getPropiedades().remove(oferta);
                getReceptor().getPropiedades().add(oferta);
                
                getEmisor().getTratosPropuestos().remove(this);
                getReceptor().getTratosRecibidos().remove(this);
                
                consola.imprimir("Se ha aceptado el siguiente trato con " + getReceptor().getNombre() + ": le doy " + oferta.getNombre() + " y " + getReceptor().getNombre() + " me da " + dineroDemandado + "€.");
                
                return true;
            }
            else
                consola.imprimir("El trato no puede ser aceptado: " + getReceptor().getNombre() + " no dispone de " + dineroDemandado + "€.");
        else
            consola.imprimir("El trato no puede ser aceptado: " + oferta.getNombre() + " esta hipotecada o no pertenece a " + getEmisor().getNombre() + ".");
        
        return false;
    }
}
