package casilla;

import jugador.Jugador;
import java.util.HashMap;
import java.util.Iterator;
import edificio.Casa;
import tablero.Constantes;
import edificio.Edificio;
import edificio.Hotel;
import edificio.Piscina;
import edificio.PistaDeporte;
import tablero.Juego;
import tablero.Tablero;
import static tablero.Juego.consola;

public final class Solar extends Propiedad
{
    //------------------------ATRIBUTOS------------------------
    
    private long precioCasa, precioHotel, precioPiscina, precioPista, alquilerHotel, alquilerPiscina, alquilerPista;
    private long alquilerCasa1, alquilerCasa2, alquilerCasa3, alquilerCasa4;
    private HashMap <String, Integer> numeroEdificios;
    private int visitasPropietario; //Este atributo cuenta la cantidad de veces que el propietario cayo en la casilla desde que la compra
    
    //------------------------------CONSTRUCTORES--------------------------------------
    
    public Solar(String nombre, Jugador jugador, long precio, Grupo grupo)
    {
        super(nombre, jugador, precio, grupo);
        visitasPropietario = 0;
        numeroEdificios = new HashMap<>();
        
        for(String tipoEdificio : Constantes.TIPO_EDIFICACIONES)
            numeroEdificios.put(tipoEdificio, 0);
        
        precioCasa = (long)(0.6 * precio);
        precioHotel = (long)(0.6 * precio);
        precioPiscina = (long)(precio * 0.25);
        precioPista = (long)(precio * 1.25);
        alquilerCasa1 = (long)(getAlquilerBase() * 5);
        alquilerCasa2 = (long)(getAlquilerBase() * 15);
        alquilerCasa3 = (long)(getAlquilerBase() * 35);
        alquilerCasa4 = (long)(getAlquilerBase() * 50);
        alquilerHotel = (long)(getAlquilerBase() * 70);
        alquilerPiscina = (long)(getAlquilerBase() * 25);
        alquilerPista = alquilerPiscina;
    }
    
    //------------------------------GETTERS--------------------------------------
    
    public HashMap<String, Integer> getNumeroEdificios()
    {
        return numeroEdificios;
    }
    
    public int getVisitasPropietario()
    {
        return visitasPropietario;
    }

    public long getPrecioCasa() 
    {
        return precioCasa;
    }

    public long getPrecioHotel() 
    {
        return precioHotel;
    }

    public long getPrecioPiscina() 
    {
        return precioPiscina;
    }

    public long getPrecioPista() 
    {
        return precioPista;
    }

    public long getAlquilerHotel() 
    {
        return alquilerHotel;
    }

    public long getAlquilerPiscina() 
    {
        return alquilerPiscina;
    }

    public long getAlquilerPista() 
    {
        return alquilerPista;
    }

    public long getAlquilerCasa1() 
    {
        return alquilerCasa1;
    }

    public long getAlquilerCasa2() 
    {
        return alquilerCasa2;
    }

    public long getAlquilerCasa3()
    {
        return alquilerCasa3;
    }

    public long getAlquilerCasa4() 
    {
        return alquilerCasa4;
    }
    
    //------------------------------SETTERS--------------------------------------

    public void setPrecioCasa(long precioCasa) 
    {
        this.precioCasa = precioCasa;
    }

    public void setPrecioHotel(long precioHotel)
    {
        this.precioHotel = precioHotel;
    }

    public void setPrecioPiscina(long precioPiscina)
    {
        this.precioPiscina = precioPiscina;
    }

    public void setPrecioPista(long precioPista)
    {
        this.precioPista = precioPista;
    }

    public void setAlquilerHotel(long alquilerHotel) 
    {
        this.alquilerHotel = alquilerHotel;
    }

    public void setAlquilerPiscina(long alquilerPiscina) 
    {
        this.alquilerPiscina = alquilerPiscina;
    }

    public void setAlquilerPista(long alquilerPista) 
    {
        this.alquilerPista = alquilerPista;
    }

    public void setAlquilerCasa1(long alquilerCasa1) 
    {
        this.alquilerCasa1 = alquilerCasa1;
    }

    public void setAlquilerCasa2(long alquilerCasa2) 
    {
        this.alquilerCasa2 = alquilerCasa2;
    }

    public void setAlquilerCasa3(long alquilerCasa3) 
    {
        this.alquilerCasa3 = alquilerCasa3;
    }

    public void setAlquilerCasa4(long alquilerCasa4) 
    {
        this.alquilerCasa4 = alquilerCasa4;
    }
    
    public void setVisitasPropietario(int visitas)
    {
        if(visitas > 0)
            visitasPropietario = visitas;
        else
            consola.imprimir("El atributo pasado a setVisitasPropietario es menor o igual que 0");
    }
    
    //----------------------------OTROS MÉTODOS-------------------------------------
    
    public String obtenerEdificios()
    {
        String edif = "[ ";
        
        if(getGrupo().getPropietario() != null && getGrupo().getPropietario().equals(getPropietario()))
            if(numeroEdificios.get("casa") != 0 ||numeroEdificios.get("hotel") != 0 ||numeroEdificios.get("piscina") != 0 ||numeroEdificios.get("pista") != 0)
                for(Grupo grupoAux : getPropietario().obtenerGruposEdificados())
                    for(String tipoEdificio : Constantes.TIPO_EDIFICACIONES)
                        for(Edificio edificio : grupoAux.getEdificaciones().get(tipoEdificio))
                            if(grupoAux.equals(getGrupo()) && edificio.getCasilla().equals(this))
                            {
                                if(edificio instanceof Casa)
                                    edif = edif.concat(edificio.getID()+", ");
                                else if(edificio instanceof Hotel)
                                    edif = edif.concat(edificio.getID()+", ");
                                else if(edificio instanceof Piscina)
                                    edif = edif.concat(edificio.getID()+", ");
                                else
                                    edif = edif.concat(edificio.getID()+", ");
                            }
                               
        if(edif.equals("[ "))
            edif = "-";
        else
            edif = edif.substring(0, edif.length() - 2).concat(" ]");
        
        return edif;
    }
    
    @Override public String imprimirDatos()
    {        
        long precio = getPrecio();
      
        return "Nombre: " + getNombre() + "<br>tipo: Solar<br>grupo: " + Tablero.obtenerColorDeCodigo(getGrupo().getNombre()) + "<br>propietario: " + getPropietario().getNombre() + "<br>valor: " + precio + "<br>alquiler: " + getAlquilerActual() +
                "<br>valor casa: " + precioCasa + "<br>valor hotel: " + precioHotel + "<br>valor piscina: " + precioPiscina + "<br>valor pista de deporte: " + precioPista + 
                "<br>alquiler una casa: " + alquilerCasa1 + "<br>alquiler dos casa: " + alquilerCasa2 + "<br>alquiler tres casa: " + alquilerCasa3 + "<br>alquiler cuatro casa: " + alquilerCasa4 + 
                "<br>alquiler un hotel: " + alquilerHotel + "<br>alquiler una piscina: " + alquilerPiscina + "<br>alquiler una pista: " + alquilerPista + "<br>edificios: "+ obtenerEdificios();
    }
    
    
    @Override public long alquiler()
    {
        int nEdificios;
        long valor = 0;
        
        if((getGrupo().getPropietario() != null && getGrupo().getPropietario().equals(getPropietario())) || visitasPropietario >= 2)
        {
            if(numeroEdificios.get("casa") == 0 && numeroEdificios.get("hotel") == 0 && numeroEdificios.get("piscina") == 0 && numeroEdificios.get("pista") == 0)
                valor = getAlquilerBase() * 2;
            else
            {
                for(String clave : Constantes.TIPO_EDIFICACIONES)
                    if(numeroEdificios.get(clave) != 0)
                    {
                        nEdificios = numeroEdificios.get(clave);

                        if(getAlquilerActual() == getAlquilerBase() * 2)
                            valor = 0;

                        switch (clave) 
                        {
                            case "casa":
                                switch (nEdificios) 
                                {
                                    case 1:
                                        valor = alquilerCasa1;
                                        break;
                                    case 2:
                                        valor = alquilerCasa2;
                                        break;
                                    case 3:
                                        valor = alquilerCasa3;
                                        break;
                                    default:
                                        valor = alquilerCasa4;
                                }

                                break;

                            case "hotel":
                                valor += alquilerHotel * numeroEdificios.get(clave);
                                break;

                            case "piscina": case "pista":
                                valor += alquilerPiscina * numeroEdificios.get(clave);
                                break;
                        }
                    }
            }
        }
        else
            valor = getAlquilerBase();
        
        return valor;
    }
    
    public Edificio edificar(String tipoEdificio)
    {
        Edificio edificio = null;
        long precio, dineroAcumulado, fortuna;
        int aux;
            
        Grupo grupoActual = getGrupo();
           
        if ((grupoActual.getPropietario() != null && grupoActual.getPropietario().equals(getPropietario())) || visitasPropietario >= 2)
        {    
            if((edificio = validacionEdificaciones(tipoEdificio)) != null)
            {
                precio = edificio.getPrecio();

                if(getPropietario().getDinero() - precio > 0)
                {
                    aux = getPropietario().getFigura().getTablero().getContadoresIDedificaciones().get(tipoEdificio);
                    grupoActual.getEdificaciones().get(tipoEdificio).add(edificio);
                    getPropietario().setDinero(getPropietario().getDinero() - precio);

                    fortuna = getPropietario().getEstadisticasGlobales().get(2) + precio;
                    
                    getPropietario().getEstadisticasGlobales().remove(2);
                    getPropietario().getEstadisticasGlobales().add(2,fortuna);

                    dineroAcumulado = getPropietario().getEstadisticas().get(0) + precio;

                    getPropietario().getEstadisticas().remove(0);
                    getPropietario().getEstadisticas().add(0, dineroAcumulado);

                    consola.imprimir("Se ha construido un edifico de tipo " + tipoEdificio + " en " + getNombre() + ". La fortuna de " + getPropietario().getNombre() + " se reduce en " + precio + "€.");

                    aux++;

                    getPropietario().getFigura().getTablero().getContadoresIDedificaciones().remove(tipoEdificio);
                    getPropietario().getFigura().getTablero().getContadoresIDedificaciones().put(tipoEdificio, aux);
                    long au = alquiler();
                    setAlquilerActual(au);
                }
                else
                    consola.imprimir("La fortuna de " + getPropietario().getNombre() + " no es suficiente para construir un edificio de tipo " + tipoEdificio + " en la casilla " + getNombre() + ".");
            }
        } 
        else
            consola.imprimir("Debe tener el grupo de casillas comprado y sin hipotecar o bien haber caido 2 veces como minimo en esta casilla siendo su propietario");
            
        return edificio;
    }
    
    private Edificio validacionEdificaciones(String tipoEdificio)
    {
        int limiteEdificacion = getGrupo().getCasillas().size(), numero = getPropietario().getFigura().getTablero().getContadoresIDedificaciones().get(tipoEdificio);
        int nEdificios = getGrupo().getEdificaciones().get(tipoEdificio).size();
        String idEdificio = tipoEdificio + "-" + numero;
        Edificio edificio = null;

        switch (tipoEdificio) 
        {
            case "casa":
                if(numeroEdificios.get(tipoEdificio) == 4)
                    consola.imprimir("En esta casilla ya hay 4 casas construidas, pruebe a construir un hotel");
                else if(getGrupo().getEdificaciones().get("hotel").size() == limiteEdificacion && numeroEdificios.get(tipoEdificio) == limiteEdificacion)
                    consola.imprimir("Ha alcanzado el limite de casas y hoteles edificables en el grupo");
                else
                    edificio = new Casa(idEdificio, this);
                break;
            case "hotel":
                if(nEdificios == limiteEdificacion)
                    consola.imprimir("Ha alcanzado el limite de hoteles edificables en todo el grupo"); 
                else if(numeroEdificios.get("casa") != 4)
                    consola.imprimir("Tiene que tener edificadas 4 casas como minimo en esta casilla");
                //Entrar en este else supone haber superado todas las restricciones
                else
                {
                    //Usamos un iterator para evitar el ConcurrentModificationException al eliminar los elementos
                    //de un ArrayList a la vez que lo estamos recorriendo
                    Iterator<Edificio> iter = getGrupo().getEdificaciones().get("casa").iterator();

                    while (iter.hasNext()) 
                    {
                        Edificio casa = iter.next();

                        if (casa.getCasilla().equals(this))
                            iter.remove();
                    }
                    
                    edificio = new Hotel(idEdificio, this);
                    
                    numeroEdificios.remove("casa");
                    numeroEdificios.put("casa", 0);                  
                }
                break;
            case "piscina":
                if(nEdificios == limiteEdificacion)
                    consola.imprimir("Ha alcanzado el limite de piscinas edificables en todo el grupo");
                else if((numeroEdificios.get("casa") < 2 || numeroEdificios.get("hotel") != 1) &&  numeroEdificios.get("hotel") < 2)
                    consola.imprimir("Tiene que tener edificados 1 hotel y 2 casas como minimo en esta casilla");
                else
                    edificio = new Piscina(idEdificio, this);
                break;
            case "pista":
                if(nEdificios == limiteEdificacion)
                    consola.imprimir("Ha alcanzado el limite de pistas edificables en todo el grupo");
                else if(numeroEdificios.get("hotel") < 2)
                    consola.imprimir("Tiene que tener edificados 2 hoteles como minimo en esta casilla");
                else
                    edificio = new PistaDeporte(idEdificio, this);
                break;
            default:
                consola.imprimir("Ha escrito un tipo de edificacion que no existe");
        }
        
        if(edificio != null)
        {
            int aux = numeroEdificios.get(tipoEdificio) + 1;

            numeroEdificios.remove(tipoEdificio);
            numeroEdificios.put(tipoEdificio, aux);
        }
        
        return edificio;
    }
    
    @Override public boolean hipotecar(Jugador jugador)
    {
        boolean resultado;
        
        resultado = super.hipotecar(jugador);
        
        if(resultado)
        {
            getGrupo().setPropietario(null);
            Juego.popup.setMensaje(Juego.popup.getMensaje().substring(6, Juego.popup.getMensaje().length() - 7) +"Tampoco podra edificar en el grupo " + Tablero.obtenerColorDeCodigo(getGrupo().getNombre()) + ".");
        }
        
        return resultado;
    }
    
    @Override public boolean deshipotecar()
    {
        boolean resultado, casillasGrupoHipotecas = true;
        
        resultado = super.deshipotecar();
        
        if(resultado)
        {  
         for(Casilla comprobacion : getGrupo().getCasillas())
                if(getPropietario().getHipotecas().contains(comprobacion))
                {
                    casillasGrupoHipotecas = false;
                    break;
                }

            if(casillasGrupoHipotecas)
            {
                getGrupo().setPropietario(getPropietario());
                consola.imprimir("Ya puede volver a edificar en el grupo " + Tablero.obtenerColorDeCodigo(getGrupo().getNombre()) + ".");
            }
        }
        
        return resultado;
    }
}
