package casilla;

import jugador.Esfinge;
import jugador.Jugador;
import jugador.Sombrero;
import excepcion.SinDineroException;
import tablero.Juego;
import static tablero.Juego.consola;

public abstract class Propiedad extends Casilla
{
    //------------------------------ATRIBUTOS--------------------------------------
    
    private Jugador propietario;
    private long precio;
    private long hipoteca;
    private long alquilerBase, alquilerActual;
    private Grupo grupo;
    
    //------------------------------CONSTRUCTORES--------------------------------------
    
    public Propiedad(String nombre, Jugador jugador, long precio,Grupo grupo)
    {
        super(nombre);
        
        propietario = jugador;
        this.precio = precio;
        hipoteca = precio/2;
        alquilerBase = precio/10;
        alquilerActual = 0;
        this.grupo = grupo;
        
        propietario.getPropiedades().add(this);
    }
    
    //------------------------------GETTERS--------------------------------------
    
    public final Jugador getPropietario()
    {
        return propietario;
    }
    
    public final long getPrecio()
    {
        return precio;
    }
    
    public final long getAlquilerBase()
    {
        return alquilerBase;
    }
    
    public final long getAlquilerActual()
    {
        return alquilerActual;
    }
    
    public final long getHipoteca()
    {
       return hipoteca; 
    }
    
    public final Grupo getGrupo()
    {
        return grupo;
    }
    
    //------------------------------SETTERS--------------------------------------
    
    public final void setPrecio(long precio)
    {
        if(precio >= 0)     
            this.precio = precio;
        else
            consola.imprimir("El valor  pasado como argumento en setPrecio no es valido");
    }
    
    public final void setPropietario(Jugador jugador)
    {
        if(jugador != null)
            propietario = jugador;
        else
            consola.imprimir("El argumento jugador de setPropietario no ha sido construido");
    }
    
    public final void setAlquilerActual(long alquiler)
    {
        if(alquiler >= 0)
            this.alquilerActual = alquiler;
        else
            consola.imprimir("El argumento pasado a setAlquilerActual no es valido (<= 0)");
    }

    public void setAlquilerBase(long alquilerBase)
    {
        this.alquilerBase = alquilerBase;
    }
    
    //------------------------------OTROS MÉTODOS--------------------------------------

    public final boolean perteneceAJugador(Jugador jugador)
    {
        return propietario.equals(jugador);
    }
    
    public final long valor()
    {
        return precio;
    }
    
    public final void cobrarAlquiler(Jugador jugador) throws SinDineroException
    {
        long casillaRentable = getEstadisticasGlobales().get(0), alquilerPagado, alquilerRecibido,gastos;
        
        if(!propietario.getHipotecas().contains(this))
            if(!propietario.getNombre().equals("banca") && !propietario.equals(jugador))
            {
                if(this instanceof Servicio)
                    alquilerActual = alquiler();
                
                if(!jugador.getInmune().containsKey(this))
                {
                    if (jugador.getDinero() - alquilerActual > 0) 
                    {
                        jugador.setDinero(jugador.getDinero() - alquilerActual);
                        propietario.setDinero(propietario.getDinero() + alquilerActual);
                        consola.imprimir(" Se han pagado " + alquilerActual + " de alquiler a " + propietario.getNombre() + ".");

                        alquilerPagado = jugador.getEstadisticas().get(2) + alquilerActual;
                        alquilerRecibido = propietario.getEstadisticas().get(3) + alquilerActual;

                        //Cálculo de una de las estadísticas de jugador
                        jugador.getEstadisticas().remove(2);
                        jugador.getEstadisticas().add(2, alquilerPagado);

                        //Cálculo de una de las estadísticas de jugador
                        propietario.getEstadisticas().remove(3);
                        propietario.getEstadisticas().add(3, alquilerRecibido); 

                        casillaRentable += alquilerActual;

                        getEstadisticasGlobales().remove(0);
                        getEstadisticasGlobales().add(0, casillaRentable);

                        if(jugador.getFigura() instanceof Esfinge)
                        {
                            gastos = ((Esfinge)jugador.getFigura()).getPagos().get(0) + getAlquilerActual();
                            ((Esfinge)jugador.getFigura()).getPagos().remove(0);
                            ((Esfinge)jugador.getFigura()).getPagos().add(0,gastos);
                        }
                        if(jugador.getFigura() instanceof Sombrero)
                        {
                            gastos = ((Sombrero)jugador.getFigura()).getPagos().get(0) + getAlquilerActual();
                            ((Sombrero)jugador.getFigura()).getPagos().remove(0);
                            ((Sombrero)jugador.getFigura()).getPagos().add(0,gastos);
                        }
                    }
                    else 
                    {
                        throw(new SinDineroException(" No puede pagar el alquiler, queda en bancarrota y las propiedades pasan a pertenecer a " + propietario.getNombre() +  ".", propietario));
                    }
                }
            }
    }
    
    public final boolean comprar(Jugador jugador)
    {   
        long gastos = 0;
        if(jugador.getFigura().getUbicacion().equals(this))
            if(getPropietario().equals(jugador.getFigura().getTablero().getBanca()))
                if(jugador.getDinero() - getPrecio() > 0)
                {
                    jugador.setDinero(jugador.getDinero() - getPrecio());
                    jugador.anhadirCasilla(this);
                    setPropietario(jugador);
                    consola.imprimir("El jugador \"" + jugador.getNombre() + "\" compra la casilla \"" + getNombre() + "\" por " + getPrecio() + ".Su fortuna actual es " + jugador.getDinero() + ".");

                    boolean grupoDominado = true;

                    if(getGrupo() != null)
                        for(Propiedad casilla : getGrupo().getCasillas())
                            if(!casilla.getPropietario().equals(jugador))
                                grupoDominado = false;

                    if(getGrupo() != null && grupoDominado)
                    {
                        getGrupo().setPropietario(jugador);

                        for(Propiedad casilla : getGrupo().getCasillas())
                            casilla.setAlquilerActual(alquiler());
                    }

                   if(jugador.getFigura() instanceof Esfinge)
                    {
                        gastos = ((Esfinge)jugador.getFigura()).getPagos().get(0) + getPrecio();
                        ((Esfinge)jugador.getFigura()).getPagos().remove(0);
                        ((Esfinge)jugador.getFigura()).getPagos().add(0,gastos);
                        ((Esfinge)jugador.getFigura()).setAdquisicion(this);
                    }
                    if(jugador.getFigura() instanceof Sombrero)
                    {
                        gastos = ((Sombrero)jugador.getFigura()).getPagos().get(0) + getPrecio();
                        ((Sombrero)jugador.getFigura()).getPagos().remove(0);
                        ((Sombrero)jugador.getFigura()).getPagos().add(0,gastos);
                        ((Sombrero)jugador.getFigura()).setAdquisicion(this);
                    }
                
                    alquilerActual = alquiler();
                    return true;
                }    
                else
                    consola.imprimir("No tiene el suficiente dinero para comprar la casilla");
            else
                consola.imprimir("La casilla ya pertenece al jugador \"" + getPropietario().getNombre() + "\" cuya figura es \'" + getPropietario().getFigura().getID() + "\'");
        else
            consola.imprimir("No puede comprar una casilla sobre la que no esta");
        
        return false;
    }
    
    public boolean hipotecar(Jugador jugador)
    {
        if(!getPropietario().equals(jugador.getFigura().getTablero().getBanca()))
            if(jugador.equals(getPropietario()) && getPropietario().getPropiedades().contains(this))
            {
                getPropietario().getPropiedades().remove(this);
                getPropietario().getHipotecas().add(this);

                consola.imprimir(getPropietario().getNombre() + " recibe " + hipoteca + " € por la hipoteca de " + getNombre() + ". No puede recibir alquileres.");

                getPropietario().setDinero(getPropietario().getDinero() + hipoteca);

                return true;
            }
        
        consola.imprimir(jugador.getNombre() + " no puede hipotecar " + getNombre() + ". ");

        if(jugador.getHipotecas().contains(this))
            consola.imprimir(Juego.popup.getMensaje().substring(6,Juego.popup.getMensaje().length() - 7) + "Ya está hipotecada.");
        else
            consola.imprimir(Juego.popup.getMensaje().substring(6,Juego.popup.getMensaje().length() - 7) + "No es una propiedad que le pertenece.");

        return false;   
    }
    
    public boolean deshipotecar()
    {
        if(getPropietario().getHipotecas().contains(this))
        {
            getPropietario().getHipotecas().remove(this);
            getPropietario().getPropiedades().add(this);
            
            consola.imprimir(getPropietario().getNombre() + " paga " + hipoteca + " € para deshipotecar " + getNombre() + ". Ya puede volver a recibir alquileres.");
            
            getPropietario().setDinero(getPropietario().getDinero() - hipoteca);
            
            return true;
        }
        else
        {
            consola.imprimir(getPropietario().getNombre() + " no puede deshipotecar " + getNombre() + ". ");
            
            if(getPropietario().getPropiedades().contains(this))
                consola.imprimir(Juego.popup.getMensaje().substring(6,Juego.popup.getMensaje().length() - 7) + "No está hipotecada.");
            else
                consola.imprimir(Juego.popup.getMensaje().substring(6,Juego.popup.getMensaje().length() - 7) + "No es una propiedad que le pertenece.");
            
            return false;
        }
    }
    
    public abstract String imprimirDatos();
    
    public abstract long alquiler();
}

