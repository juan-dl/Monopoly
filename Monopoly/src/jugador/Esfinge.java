package jugador;
import casilla.Casilla;
import casilla.Propiedad;
import casilla.Solar;
import casilla.Transporte;
import excepcion.JugadorException;
import java.util.ArrayList;
import static tablero.Juego.consola;
import tablero.Tablero;

public final class Esfinge extends Avatar
{
    private ArrayList <Long> pagos;//ArrayList en el que se almacenan las transacciones de dinero que se realizan
                          // 1-->pago de alquileres y compra de casillas 2-->Dinero recibido(cartas,vueltas) 3-->Dinero perdido en impuestos y cartas
    private Propiedad adquisicion;
    
    public Esfinge(Jugador jugador, Tablero tableroActual)
    {
        super(jugador, tableroActual);
        adquisicion = null;
        
        pagos = new ArrayList<>(3);
        for(int i = 0; i < 3; i++)
            pagos.add((long)0);
    }
 
    public ArrayList<Long> getPagos()
    {
        return pagos;
    }
    
    public Propiedad getAdquision()
    {
        return adquisicion;
    }
    
    public void setAdquisicion(Propiedad adquisicion)
    {
        if(adquisicion != null)
            this.adquisicion=adquisicion;
        else
            consola.imprimir("El atributo pasado a setAdquisicion es nulo");
    }
    
    private void moverZigZag(int desplazamiento, int origen)
    {
        ArrayList <ArrayList<Casilla>> casillas = getTablero().getCasillas();
        int lado = origen;
        int posicion = casillas.get(lado).indexOf(getUbicacion());
        boolean darVuelta = false;
        do{
            if(lado == origen)
            {
                setUbicacion(casillas.get(2 - lado).get(9-posicion));
                posicion++;
                desplazamiento--;
                lado = 2 - lado;
            }
            else
            {
                setUbicacion(casillas.get(2 - lado).get(1+posicion));
                posicion++;
                desplazamiento--;
                lado = 2 - lado; 
            }
            
            if(desplazamiento != 0 && (getUbicacion().equals(casillas.get(2-origen).get(1)) || getUbicacion().equals(casillas.get(origen).get(9))))
            {
                desplazamiento--;
                setUbicacion(casillas.get(origen).get(1));
                lado = origen;
                posicion = 1;
                darVuelta = true;
            }
            
        }while(desplazamiento != 0);

        setResultadoDados(0);
        
        if(darVuelta)
            setResultadoDados(40);
    }

    @Override public void moverEnAvanzado() throws JugadorException
    {
        int desplazamiento = getResultadoDados();
        Casilla Ubicacion = getUbicacion();
        short lado = ladoUbicacion();
        
        if(desplazamiento > 4)
        {   
            for(int i = 2;i > -1;i--)
                pagos.remove(i);
            
            for(int i = 0;i < 3;i++)
                pagos.add((long)0);

            adquisicion = null;
            
            switch(lado){

                case 0:
                    moverZigZag(desplazamiento, 0);
                    break;

                case 1:
                    setUbicacion(getTablero().getCasillas().get(2).get(1));
                    moverZigZag(desplazamiento - 1, 2);
                    break;

                case 2:
                    moverZigZag(desplazamiento, 2);
                    break;

                case 3:
                    setUbicacion(getTablero().getCasillas().get(0).get(1)); 
                    moverZigZag(desplazamiento - 1, 0);
                    break;
            }
            setMovimientoAvanzado((short)(getMovimientoAvanzado() - 1));
        
            consola.imprimir("El avatar \'" + getID() + "\' avanza " + desplazamiento + " posiciones, desde \"" + Ubicacion.getNombre() + "\" hasta \"");

            comprobarCasilla();
        }
        else
        {
            Jugador jugador = getJugador();
            jugador.setDinero(jugador.getDinero() + pagos.get(0));
            jugador.setDinero(jugador.getDinero() - pagos.get(1));
            jugador.setDinero(jugador.getDinero() + pagos.get(2));
            getTablero().setBote(getTablero().getBote() - pagos.get(2));

            if(adquisicion != null)
            {
                if(jugador.getPropiedades().contains(adquisicion))
                    jugador.getPropiedades().remove(adquisicion);

                if(jugador.getHipotecas().contains(adquisicion))
                {
                    jugador.getHipotecas().remove(adquisicion);
                    jugador.setDinero(jugador.getDinero() - adquisicion.getHipoteca());
                }

                getTablero().getBanca().getPropiedades().add(adquisicion);
                adquisicion.setPropietario(getTablero().getBanca());

                //En caso de que se complete un grupo con esa casilla y se haya edificado es necesario realizar este proceso
                if(adquisicion instanceof Solar && adquisicion.getGrupo().getPropietario() != null && adquisicion.getGrupo().getPropietario().equals(jugador))
                {
                    adquisicion.getGrupo().setPropietario(null);

                    if(((Solar)(adquisicion)).getNumeroEdificios().get("casa") != 0)
                    {
                        ((Solar)adquisicion).getNumeroEdificios().put("casa", 0);
                        jugador.setDinero(jugador.getDinero() + ((Solar)adquisicion).getGrupo().getEdificaciones().get("casa").get(0).getPrecio());
                        ((Solar)adquisicion).getGrupo().getEdificaciones().get("casa").remove(0);//Solo se puede edificar una vez por turno
                    }


                    for(Solar solar : adquisicion.getGrupo().getCasillas())
                        solar.setAlquilerActual(solar.alquiler());

                    adquisicion.setAlquilerActual(0);

                }
                //En caso de que el jugador sea propietario de mas casillas de transporte
                if(adquisicion instanceof Transporte)
                {
                    for(Propiedad prop : jugador.getPropiedades())
                        if(prop instanceof  Transporte)
                            prop.setAlquilerActual(prop.alquiler());

                    for(Propiedad prop : jugador.getHipotecas())
                        if(prop instanceof  Transporte)
                            prop.setAlquilerActual(prop.alquiler());
                }   
            }
            setMovimientoAvanzado((short) -3);
            
            for(int i = 2;i > -1;i--)
                pagos.remove(i);
            
            for(int i = 0;i < 3;i++)
                pagos.add((long)0);

            adquisicion = null;
        }
        
        for(int i = 0;i < 3;i++)
        {
            pagos.remove(i);
            pagos.add((long)0);
        }
        
        adquisicion = null;
    }
    
    @Override public boolean lanzarDados(short sacarDobles) throws JugadorException
    {
        boolean dobles = super.lanzarDados(sacarDobles);
        
        if(getMovimientoAvanzado() != -5)
            moverEnAvanzado();
        else
        {
            for(int i = 2;i > -1;i--)
                pagos.remove(i);
            
            for(int i = 0;i < 3;i++)
                pagos.add((long)0);

            adquisicion = null;
            moverEnBasico(sacarDobles, dobles);
        }
        
        return dobles;
    }
    
    @Override public String toString()
    {    
        return ("<br> Id: " + getID() + "<br> Tipo: Esfinge <br>Casilla: "+ getUbicacion().getNombre() +"<br>Jugador: "+ getJugador().getNombre() +"<br><br>");
    }
}
