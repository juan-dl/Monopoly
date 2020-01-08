package jugador;

import casilla.Propiedad;
import static tablero.Juego.consola;

public final class TratoNoAlquiler extends Trato
{
    private Propiedad oferta, demanda, sinAlquiler;
    private int turnos;
    
    public TratoNoAlquiler(String id, String enunciado, Jugador emisor, Jugador receptor, Propiedad oferta, Propiedad demanda, Propiedad sinAlquiler, int turnos)
    {
        super(id, enunciado, emisor, receptor);
        
        if(oferta != null && demanda != null && sinAlquiler != null && turnos > 0)
        {
            this.oferta = oferta;
            this.demanda = demanda;
            this.sinAlquiler = sinAlquiler;
            this.turnos = turnos;
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
    
    public Propiedad getSinAlquiler()
    {
        return sinAlquiler;
    }
    
    public int getTurnos()
    {
        return turnos;
    }
    
    @Override public boolean resolverTrato()
    {
        if(getEmisor().getPropiedades().contains(oferta))
            if(getReceptor().getPropiedades().contains(demanda))
                if(getReceptor().getPropiedades().contains(sinAlquiler))
                {   
                    oferta.setPropietario(getReceptor());   
                    demanda.setPropietario(getEmisor());

                    getEmisor().getPropiedades().remove(oferta);
                    getReceptor().getPropiedades().remove(demanda);

                    getEmisor().getPropiedades().add(demanda);
                    getReceptor().getPropiedades().add(oferta);
                    
                    if(getEmisor().getInmune().containsKey(sinAlquiler))
                    {
                        int aux = getEmisor().getInmune().get(sinAlquiler);
                        getEmisor().getInmune().remove(sinAlquiler);
                        
                        //Si un jugador es inmune al cobro de un alquiler y se vuelve a solicitar inmunidad se suma la cantidad pendiente con la nueva petición
                        getEmisor().getInmune().put(sinAlquiler, turnos + aux);
                    }
                    else
                        getEmisor().getInmune().put(sinAlquiler, turnos);

                    getEmisor().getTratosPropuestos().remove(this);
                    getReceptor().getTratosRecibidos().remove(this);

                    consola.imprimir("Se ha aceptado el siguiente trato con " + getReceptor().getNombre() + ": le doy " + oferta.getNombre() + " y " + getReceptor().getNombre() + " me da " + demanda.getNombre() + " y no pago alquiler en " + sinAlquiler.getNombre() + " durante " + turnos + " turnos.");

                    return true;
                }
                else
                    consola.imprimir("El trato no puede ser aceptado: " + sinAlquiler.getNombre() + " esta hipotecada o no pertenece a " + getReceptor().getNombre() + ".");
            else
                consola.imprimir("El trato no puede ser aceptado: " + demanda.getNombre() + " esta hipotecada o no pertenece a " + getReceptor().getNombre() + ".");
        else
            consola.imprimir("El trato no puede ser aceptado: " + oferta.getNombre() + " esta hipotecada o no pertenece a " + getEmisor().getNombre() + ".");
        
        return false;
    }
}