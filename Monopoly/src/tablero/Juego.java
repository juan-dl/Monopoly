package tablero;

import casilla.*;
import jugador.*;
import edificio.Edificio;
import excepcion.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Juego implements Comando
{
    public static VentanaJuego partida;
    private VentanaConfirmacion confirmacion;
    public static VentanaInformativa popup;
    private CreacionPersonajes creador;
    private Tablero tablero;
    private ArrayList<ArrayList<Casilla>> casillas;
    private ArrayList<Character> ordenTurnos;
    private HashMap<Character, Avatar> figuras;
    private boolean seEdifico, seCompro;
    private short turnoActual, sacarDobles;
    private String[] partesComando;
    private int contadorTratos;
    public static ConsolaNormal consola;
    
    public  Juego()
    {
        consola = new ConsolaNormal();
        tablero = new Tablero();
        figuras = tablero.getFiguras();
        ordenTurnos = new ArrayList<>();
        casillas = tablero.getCasillas();
        seEdifico = false;
        turnoActual = 0;
        seCompro = false;
        sacarDobles = 0;
        contadorTratos = 0;
        partida = new VentanaJuego(this);
        creador = new CreacionPersonajes(this, partida);
        popup = new VentanaInformativa("");
        confirmacion = new VentanaConfirmacion("");
    }
    
    public Juego(Tablero tablero, HashMap<Character, Avatar> figuras, ArrayList<Character> orden)
    {
        consola = new ConsolaNormal();
        this.tablero = tablero;
        this.figuras = figuras;
        ordenTurnos = orden;
        casillas = tablero.getCasillas();
        seEdifico = false;
        turnoActual = 0;
        seCompro = false;
        sacarDobles = 0;
        contadorTratos = 0;
        partida = new VentanaJuego(this);
        partida.setVisible(true);
        popup = new VentanaInformativa("");
        confirmacion = new VentanaConfirmacion("");
    }

    public CreacionPersonajes getCreador() 
    {
        return creador;
    }

    public ArrayList<Character> getOrdenTurnos() 
    {
        return ordenTurnos;
    }

    public short getTurnoActual() 
    {
        return turnoActual;
    }
    
    public Tablero getTablero() 
    {
        return tablero;
    }
    
    public HashMap<Character, Avatar> getFiguras()
    {
        return figuras;
    }
    
    public VentanaJuego getPartida()
    {
        return partida;
    }
    
    public VentanaInformativa getPopup()
    {
        return popup;
    }
    
    public VentanaConfirmacion getConfirmacion()
    {
        return confirmacion;
    }

    public void setTablero(Tablero tablero) 
    {
        this.tablero = tablero;
    }

    public void setOrdenTurnos(ArrayList<Character> ordenTurnos) 
    {
        this.ordenTurnos = ordenTurnos;
    }

    public void setFiguras(HashMap<Character, Avatar> figuras) 
    {
        this.figuras = figuras;
    }

    public void setPartida(VentanaJuego partida) 
    {
        Juego.partida = partida;
    }
    
    @Override public Character crearJugador(String nombre, String tipo) throws JugadorException
    {
        if(!nombre.equals(""))
        {
            for(char c : ordenTurnos)
                if(nombre.equals(figuras.get(c).getJugador().getNombre()))
                    throw(new NombreRepetidoException("Ya existe un jugador con ese nombre, escoja otro"));

            Jugador jugador = new Jugador(nombre, tipo, tablero);

            //Esta condición permite saber si se creó correctamente la figura(es válida)
            if (jugador.getFigura() != null) 
            {
                while (figuras.containsKey(jugador.getFigura().getID()))
                {
                    switch(tipo)
                    {
                        case "pelota":
                            jugador.setFigura(new Pelota(jugador ,tablero));
                            break;
                        case "sombrero":
                            jugador.setFigura(new Sombrero(jugador ,tablero));
                            break;
                        case "esfinge":
                            jugador.setFigura(new Esfinge(jugador ,tablero));
                            break;
                        case "coche":
                            jugador.setFigura(new Coche(jugador ,tablero));
                            break;
                    }
                }       

                figuras.put(jugador.getFigura().getID(), jugador.getFigura());
                ordenTurnos.add(jugador.getFigura().getID());

                consola.imprimir(jugador.identificarJugador());

                return jugador.getFigura().getID();
            }
            else
                consola.imprimir("Se introdujo un tipo de figura no valido");
        }
        else
            consola.imprimir("Introduzca un nombre");
        
        return null;
    }
    
    @Override public void lanzarDados() throws JugadorException
    {
        boolean resultado;
        
        Avatar figura = figuras.get(ordenTurnos.get(turnoActual));

        if (sacarDobles != -1 && (figura.getMovimientoAvanzado() == 0 || figura.getMovimientoAvanzado() == -5 || !(figura instanceof Pelota))) 
        {
            if(figura.getMovimientoAvanzado() <= 0)
            {

                resultado = figura.lanzarDados(sacarDobles);

                //De esta forma si se sacan dobles antes de caer en IrCarcel se interrumpe el siguiente lanzamiento
                if(figura.getUbicacion().equals(casillas.get(1).get(0)) && figura.getEncarcelado() > 0)
                    resultado = false;

                if(figura.getEncarcelado() >= 1)
                    {
                        figura.setEncarcelado((short)(figura.getEncarcelado() + 1));

                        if(figura.getEncarcelado() == Constantes.CARCEL_PAGO_FORZADO)
                            salirCarcel();

                        sacarDobles = -1;
                    }
                if(figura.getMovimientoAvanzado() == -5 || figura instanceof Pelota)
                {
                    if(resultado) 
                    {
                        sacarDobles++;

                        if (sacarDobles == 3) 
                            throw(new EncarceladoException(""));
                        else
                            if(figura.getEncarcelado() == 0)
                            {
                                consola.imprimir("Ha sacado dobles, puede volver a tirar");

                                if(figura.getMovimientoAvanzado() != -5 && figura instanceof Pelota)
                                    consola.imprimir(Juego.popup.getMensaje().substring(6, Juego.popup.getMensaje().length() - 7) + " cuando haya terminado de botar.");
                            }
                    } 
                    else
                        sacarDobles = -1;

                    tablero.modificarPreciosVuelta(ordenTurnos);

                    if(figura instanceof Pelota && figura.getMovimientoAvanzado() != -5 && figura.getMovimientoAvanzado() != 0)
                        consola.imprimir(Juego.popup.getMensaje().substring(6, Juego.popup.getMensaje().length() - 7) + "Escriba \"botar\" para continuar botando...");
                    }
                    else
                    {
                        tablero.modificarPreciosVuelta(ordenTurnos);

                        if(figura instanceof Coche)
                        {
                            seEdifico = false;
                            if(figura.getMovimientoAvanzado() == -4)
                            {
                                consola.imprimir(Juego.popup.getMensaje().substring(6, Juego.popup.getMensaje().length() - 7) + "Ha alcanzado el limite de tiradas en un turno");
                                sacarDobles = -1;
                                figura.setMovimientoAvanzado((short)0);
                            }
                        }
                        else
                            if(figura.getMovimientoAvanzado() == -3)
                            {
                                consola.imprimir(Juego.popup.getMensaje().substring(6, Juego.popup.getMensaje().length() - 7) + "Ha alcanzado el limite de tiradas en un turno");
                                sacarDobles = -1;
                                figura.setMovimientoAvanzado((short)0);
                            }
                    }
            }
            else
            {
                consola.imprimir("Todavia no has superado la penalizacion del coche, no podra tirar hasta "+ figura.getMovimientoAvanzado() +" turnos");
                sacarDobles = -1;             
            }

        } 
        else
            consola.imprimir("No puede volver a lanzar los dados");
    }
    
    @Override public void acabarTurno() throws GanadorException
    {
        Avatar figura;
       
            if(figuras.size() > 1)
                figura = figuras.get(ordenTurnos.get(turnoActual));
            else
                figura = figuras.get(ordenTurnos.get(0));

            if(sacarDobles < 0 || figura.getMovimientoAvanzado() > 0)
                if(!(figura instanceof Pelota) || figura.getMovimientoAvanzado() == 0 || figura.getMovimientoAvanzado() == -5)
                {
                    partida.getJugadores()[turnoActual].setBackground(Constantes.COLORES_JUGADORES_VENTANA[6]);
                    seEdifico = false;
                    
                    for(ArrayList<Casilla> lado : casillas)
                        for(Casilla casilla : lado)
                            if(casilla instanceof Propiedad)
                                if(figura.getJugador().getInmune().containsKey((Propiedad)casilla))
                                {
                                    int aux = figura.getJugador().getInmune().get((Propiedad)casilla);
                                    aux--;
                                    figura.getJugador().getInmune().remove((Propiedad)casilla);

                                    if(aux > 0)
                                    {
                                        figura.getJugador().getInmune().put((Propiedad)casilla, aux);
                                        consola.imprimir("Le quedan " + aux + " turnos de inmunidad para la casilla " + casilla.getNombre());
                                    }
                                    else
                                        consola.imprimir("Ha perdido la inmunidad al alquiler para la casilla " + casilla.getNombre() + ", si cae en ella se le cobrara");
                                }

                    if (turnoActual < figuras.size() - 1) 
                        turnoActual++;
                    else 
                        turnoActual = 0;

                    if(figura.getMovimientoAvanzado() > (short)0){
                        figura.setMovimientoAvanzado((short)(figura.getMovimientoAvanzado() - 1));
                    }

                    figura = figuras.get(ordenTurnos.get(turnoActual));
                    partida.getJugadores()[turnoActual].setBackground(Constantes.COLORES_JUGADORES_VENTANA[turnoActual]);

                    if(figuras.size() > 1)
                    {
                        sacarDobles = 0;

                        consola.imprimir("El jugador actual es " + figura.getJugador().getNombre() + " con figura \'" + figura.getID() + "\'");
                    }
                    else
                    {
                        throw(new GanadorException("", figura.getJugador()));
                    }
                }
                else
                    consola.imprimir("No puede acabar el turno hasta terminar de botar");
            else
                consola.imprimir("No ha lanzado los dados todavia");
    }
    
    @Override public void salirCarcel() throws JugadorException
    {
        Avatar figura = figuras.get(ordenTurnos.get(turnoActual));
        Especial cas = null;
        
        for(ArrayList<Casilla> lado : tablero.getCasillas())
            for(Casilla c : lado)
                if(c instanceof Especial && ((Especial)c).getTipo().equals("Carcel"))
                    cas = (Especial)c;

        if(figura.getEncarcelado() > 0)
            if(sacarDobles != -1)
                cas.aplicarAccion(figura.getJugador());
            else
                consola.imprimir("Ya ha lanzados los dados, no puede pagar hasta el siguiente turno");
        else
            consola.imprimir("El jugador " + figura.getJugador().getNombre() + " con figura \'" + figura.getID() + "\' no esta en la carcel");
    }
    
    @Override public void comprarCasilla(String nombre)
    {
            Avatar figura = figuras.get(ordenTurnos.get(turnoActual));
            if(sacarDobles != 0 || !seCompro && figura.getMovimientoAvanzado() > -5 && figura.getMovimientoAvanzado() < 0)  //Solo se puede comprar despues de lanzar los dados
            { 
                Propiedad casillaCompra = null;

                //Busqueda de la casilla a comprar en función de su nombre
                for(int i = 0; i < 40; i++)
                    if(tablero.getCasillas().get(i / 10).get(i % 10).getNombre().equals(nombre))
                        if(tablero.getCasillas().get(i / 10).get(i % 10) instanceof Propiedad)
                        {
                            casillaCompra = (Propiedad)tablero.getCasillas().get(i / 10).get(i % 10);
                            break;
                        }
                        else
                        {
                            consola.imprimir("La casilla escrita no se puede comprar, no es una propiedad");
                            return;                           
                        }

                if(casillaCompra != null)
                {
                    seCompro = casillaCompra.comprar(figura.getJugador());
                }
                else
                    consola.imprimir("La casilla escrita no existe.");
            }
            else
                consola.imprimir("Tiene que lanzar los dados para poder comprar");
    }
    
    @Override public void edificar(String tipoEdificio)
    {    
        Edificio edificio;
        
        if(sacarDobles != 0)
            if(!seEdifico)
            {
                Avatar figura = figuras.get(ordenTurnos.get(turnoActual));
                
                if(tipoEdificio.equals("casa") || tipoEdificio.equals("hotel") || tipoEdificio.equals("piscina") || tipoEdificio.equals("pista"))
                    if(figura.getUbicacion() instanceof Solar)
                    {
                        edificio = ((Solar)figura.getUbicacion()).edificar(tipoEdificio);

                        if(edificio != null)
                            seEdifico = true;
                        else
                            seEdifico = false;
                    }
                    else
                        consola.imprimir("No puede edificar en casillas que no sean solares");
                else
                    consola.imprimir("Ha escrito un tipo de edificacion que no existe"); 
            }
            else
               consola.imprimir("Ya edifico en este turno, debe esperar al siguiente.");
        else
            consola.imprimir("Antes de edificar debe lanzar los dados.");
        
    }
    
    @Override public String listarEnventa() 
    {
        Propiedad cas;
        String enVenta = "";
        
        for(ArrayList<Casilla> lado : casillas)
            for(Casilla casilla : lado) 
            {
                cas = null;
                
                //Si no ponemos != null saltará un NullPointerException debido a que algunas casillas no tienen propietario (las especiales)
                if(casilla instanceof Propiedad && ((Propiedad)casilla).getPropietario().getNombre().equals("banca"))
                    cas = (Propiedad)casilla;
                
                if(cas != null)
                    if(cas.getGrupo() == null) 
                        enVenta = enVenta.concat("<br>Nombre: " + cas.getNombre() + ",<br>Precio: " + cas.getPrecio() + "<br><br>");
                    else
                        enVenta = enVenta.concat("<br>Nombre: " + cas.getNombre() + ",<br>Grupo: " + Tablero.obtenerColorDeCodigo(cas.getGrupo().getNombre()) + ",<br>Precio: " + ((Propiedad)cas).getPrecio() + "<br><br>");
            }
        
        return enVenta;
    }
     
    @Override public void listarJugadores()
    {
        if(figuras.size() > 0)
        {
            for(Character clave: ordenTurnos)
                consola.imprimir(figuras.get(clave).getJugador().toString());
        }
        else
            consola.imprimir("No hay ningun jugador creado");
    }
    
    @Override public String listarFiguras()
    {
        String fig = "";
        if(figuras.size() > 0)
            for(Character clave: ordenTurnos)
                fig = fig.concat(figuras.get(clave).toString() + "<br>");
        
        return fig;
    }
    
    public String describirCasilla(String nombre)
    {
        Casilla cas=null;
        String aux="";
        
        for(ArrayList<Casilla> lado : casillas)
                    for(Casilla casilla : lado)
                        if(casilla.getNombre().equals(nombre))
                        {
                            cas = casilla;
                            break;
                        }
        
        if(cas instanceof CajaComunidad || cas instanceof Suerte|| (cas instanceof Especial && ((Especial)cas).getTipo().equals("IrCarcel")))
            return "Nombre: " + nombre + "<br>tipo: Especial";
        else if(cas instanceof Especial && ((Especial)cas).getTipo().equals("Salida"))
            return "Nombre: " + nombre + "<br>tipo: Especial<br>Pago por vuelta: " + ((Especial)cas).getPago();
        else if(cas instanceof Impuesto)    
            return "Nombre: " + nombre + "<br>tipo: Impuesto<br>A pagar: " + ((Impuesto)cas).getImpuesto();
        else if(cas instanceof Especial && ((Especial)cas).getTipo().equals("Parking"))
        {
            for(Character clave: ordenTurnos)
                aux = aux.concat(figuras.get(clave).getJugador().getNombre()+",");
                
            return "Nombre: " + nombre + "<br>Bote disponible: " + tablero.getBote() + ",<br>Jugadores: [" +aux.substring(0, aux.length()-1) + "]";
        }
        else if(cas instanceof Especial && ((Especial)cas).getTipo().equals("Carcel"))
        {
            for(Character clave: ordenTurnos)
                    if(figuras.get(clave).getEncarcelado() > 0)
                        aux = aux.concat(figuras.get(clave).getJugador().getNombre()+ "-" + (figuras.get(clave).getEncarcelado() - 2) + ",");
            
            if(aux.equals("")) aux= "-"; else aux = aux.substring(0, aux.length() - 1);

            return "Nombre: " + nombre + "<br>Pago para salir: " + ((Especial)cas).getPago() + ",<br>Jugadores: [" +aux + "]";
        }
        else
            if(cas != null)
                    return ((Propiedad)cas).imprimirDatos();
                else
                    return "El nombre introducido no pertenece a ninguna casillas existente";
        
    }
        
    @Override public void describirJugador(String nombre) 
    {
        boolean estaJ = false;
        
        for (Character clave : ordenTurnos)
            if (figuras.get(clave).getJugador().getNombre().equals(nombre))
            {
                consola.imprimir(figuras.get(clave).getJugador().toString());
                estaJ = true;
            }
        
        if (!estaJ)
            consola.imprimir("El jugador introducido no existe");
    }
    
    @Override public void describirFigura(char identificador) 
    {
        boolean estaF = false;
        
        for (Character clave : ordenTurnos)
            if (clave == identificador) 
            {
                consola.imprimir(figuras.get(clave).toString());
                estaF = true;
            }
        
        if (!estaF)
            consola.imprimir("La figura introducida no existe");
    }
    
    @Override public void cambiarModo()
    {
        Avatar figura = figuras.get(ordenTurnos.get(turnoActual));
        String tipoFigura;
        /*
        if(figura instanceof Coche)
            tipoFigura = "coche";
        else if(figura instanceof Pelota)
            tipoFigura = "pelota";
        else if(figura instanceof Sombrero)
            tipoFigura = "sombrero";
        else
            tipoFigura = "esfinge";*/
        
        //No se podra cambiar de modo en medio de los dobles ni cuando la pelota no haya acabado de botar
        if(sacarDobles < 1 && figura.getMovimientoAvanzado() <= 0)
        {
            if(figura.getMovimientoAvanzado() == -5)
            {
                //consola.imprimir("A partir de ahora el avatar \'" + figura.getID() + "\', de tipo \"" + tipoFigura + "\", se moverá en modo avanzado.");
                figura.setMovimientoAvanzado((short)0);
            }
            else
            {
                //consola.imprimir("A partir de ahora el avatar \'" + figura.getID() + "\', de tipo \"" + tipoFigura + "\", se moverá en modo basico.");
                figura.setMovimientoAvanzado((short)-5);
            }      
        }
        else
            consola.imprimir("El cambio de modo no esta disponible.");
    }
    
    @Override public void hipotecar(String nombreCasilla)
    {
        HashMap<String, Integer> numeroEdificios;
        long dineroRecaudado = 0;
        
            Avatar figura = figuras.get(ordenTurnos.get(turnoActual));
            Casilla cas = null;

            for(ArrayList<Casilla> lado : casillas)
                    for(Casilla casilla : lado)
                        if(casilla.getNombre().equals(nombreCasilla))
                        {
                            cas = casilla;
                            break;
                        }

            if(cas != null && cas instanceof Propiedad)
            {
                if(cas instanceof Solar)
                {
                    numeroEdificios = ((Solar)cas).getNumeroEdificios(); 

                    if(numeroEdificios.get("casa") != 0 || numeroEdificios.get("hotel") != 0 || numeroEdificios.get("piscina") != 0 || numeroEdificios.get("pista") != 0)
                    {
                        for(String tipoEdificio : Constantes.TIPO_EDIFICACIONES)
                            dineroRecaudado += aplicarVentaEdificios((Solar)cas, tipoEdificio, numeroEdificios.get(tipoEdificio));

                        consola.imprimir("La casilla hipotecada tenia edificaciones, estas fueron vendidas en el proceso recaudando " + dineroRecaudado + "€."); 
                    }
                }

                ((Propiedad)cas).hipotecar(figura.getJugador());
            }
            else
                consola.imprimir("La casilla escrita no existe.");
    }
    
    @Override public void deshipotecar(String nombreCasilla)
    {
            Avatar figura = figuras.get(ordenTurnos.get(turnoActual));
            Casilla cas = null;
            
            for(ArrayList<Casilla> lado : casillas)
                    for(Casilla casilla : lado)
                        if(casilla.getNombre().equals(nombreCasilla))
                        {
                            cas = casilla;
                            break;
                        }
            
            if(cas != null)
                ((Propiedad)cas).deshipotecar();
            else
                consola.imprimir("La casilla escrita no existe.");
    }
    
    @Override public String listarEdificios()
    {
        String edif = "";
        for(Grupo grupo : tablero.getGrupos())
            for(String tipoEdificio : Constantes.TIPO_EDIFICACIONES)
                for(Edificio edificio: grupo.getEdificaciones().get(tipoEdificio))
                    edif = edif.concat(edificio + ",");
        
        return edif;
    }
    
    @Override public String listarEdificiosGrupo(String grupoColor)
    {
        String edifColor = "";
        String codigoColor = Tablero.obtenerCodigoDeColor(grupoColor);
        
        for(Grupo grupo : tablero.getGrupos())
            if(grupo.getNombre().equals(codigoColor))
            {
                edifColor = edifColor.concat(grupo + "");
                return edifColor;
            }
        return null;
    }
    
    @Override public void venderEdificios(String tipoEdificio, String nombreCasilla, int cantidad)
    {
        int numeroEdificios, cantidadVendida;
        long precioEdificio;
        
        if(cantidad > 0 && cantidad < 5)
            if(tipoEdificio.equals("casa") || tipoEdificio.equals("hotel") || tipoEdificio.equals("piscina") || tipoEdificio.equals("pista"))
            {
                Avatar figura = figuras.get(ordenTurnos.get(turnoActual));
                Casilla casilla = null;

                for (ArrayList<Casilla> lado : casillas)
                    for (Casilla cas : lado)
                        if (cas.getNombre().equals(nombreCasilla)) 
                        {
                            casilla = cas;
                            break;
                        }

                if(casilla != null)
                    if(casilla instanceof Solar && ((Solar)casilla).getPropietario().equals(figura.getJugador()))
                    {
                        Solar solar = (Solar)casilla;
                        
                        precioEdificio = solar.getGrupo().getEdificaciones().get(tipoEdificio).get(0).getPrecio() / 2;
                        
                        if((numeroEdificios = solar.getNumeroEdificios().get(tipoEdificio)) == 0)
                        {
                            consola.imprimir("La casilla " + casilla.getNombre() + " no tiene edificaciones del tipo " + tipoEdificio + ".");
                            return;
                        }
                        else if(numeroEdificios >= cantidad)
                        {
                            cantidadVendida = cantidad;
                            consola.imprimir(figura.getJugador().getNombre() + " ha vendido " + cantidadVendida + " edificaciones de tipo " + tipoEdificio + " en " + casilla.getNombre() + ", recibiendo " + precioEdificio*cantidad + "€. En la propiedad queda " + (numeroEdificios - cantidad) + " edificaciones de tipo " + tipoEdificio + ".");
                        }
                        else
                        {
                            cantidadVendida = numeroEdificios;
                            consola.imprimir("Solamente se puede vender " + cantidadVendida + " edificaciones de tipo " + tipoEdificio + ", recibiendo " + numeroEdificios*precioEdificio + "€.");
                        }
                        
                        aplicarVentaEdificios(solar, tipoEdificio, cantidadVendida);
                    }
                    else
                        consola.imprimir("No se pueden vender edificaciones del tipo " + tipoEdificio + " en " + casilla.getNombre() + ". Esta propiedad no pertenece a " + figura.getJugador().getNombre() + ".");
                else
                    consola.imprimir("La casilla escrita no existe.");
            }
            else
                consola.imprimir("El tipo de edificacion escrito no es valido.");
        else
            consola.imprimir("La cantidad de edificaciones que puede vender esta entre 1 y 4.");
    }
    
    private long aplicarVentaEdificios(Solar casilla, String tipoEdificio, int cantidadVendida)
    {
        Avatar figura = figuras.get(ordenTurnos.get(turnoActual));
        int numeroEdificios = casilla.getNumeroEdificios().get(tipoEdificio), contador = 0;
        Iterator<Edificio> iter;
        long precioEdificio;
        
        if(!casilla.getGrupo().getEdificaciones().get(tipoEdificio).isEmpty())
        {
            precioEdificio = (casilla.getGrupo().getEdificaciones().get(tipoEdificio).get(0).getPrecio() / 2) * cantidadVendida;
            iter = casilla.getGrupo().getEdificaciones().get(tipoEdificio).iterator();

            while (iter.hasNext()) 
            {
                Edificio casa = iter.next();         

                if (contador < cantidadVendida && casa.getCasilla().equals(casilla))
                {
                    iter.remove();
                    contador++;
                }
            }

            casilla.getNumeroEdificios().remove(tipoEdificio);
            casilla.getNumeroEdificios().put(tipoEdificio, numeroEdificios - cantidadVendida);

            figura.getJugador().setDinero(figura.getJugador().getDinero() + precioEdificio);
            
            for(Solar solar : casilla.getGrupo().getCasillas())
                solar.setAlquilerActual(solar.alquiler());

            return precioEdificio;
        }
        
        return 0;
    }

    @Override public String estadisticasJuego()
    {           
        int i,j;
        String rentabilidadCas ="", rentabilidadGrupos = "",visitadas = "",vueltas = "",dados = "",fortunas = "";
        ArrayList <Long> estadisticas;
        ArrayList <Solar> casillasGrupo;
        Jugador jugadorActual;
        long maxRentabilidadCas = 0,maxRentabilidadGrupo = 0,masVisitada = 0,masVueltas = 0,masDados = 0,mayorFortuna = 0;
        long rentabilidadGrupo;

        //solo se podrán calcular las estadísticas cuando la partida haya empezado
            //Buscamos los valores maximos de todas las estadisticas
            for(ArrayList<Casilla> lado : casillas)
                for(Casilla cas : lado)
                {
                    estadisticas = cas.getEstadisticasGlobales();
                    if(estadisticas.get(0) > maxRentabilidadCas) maxRentabilidadCas = estadisticas.get(0);
                    if(estadisticas.get(1) > masVisitada) masVisitada = estadisticas.get(1);
                }

            for(i = 0; i < 8; i++)
            {
                rentabilidadGrupo = 0;
                casillasGrupo = tablero.getGrupos().get(i).getCasillas();
                for(Casilla cas : casillasGrupo) rentabilidadGrupo += cas.getEstadisticasGlobales().get(0);
                if(rentabilidadGrupo > maxRentabilidadGrupo) maxRentabilidadGrupo = rentabilidadGrupo;
            }
            
            for(Character clave: ordenTurnos)
            {
                jugadorActual = figuras.get(clave).getJugador();
                estadisticas = jugadorActual.getEstadisticasGlobales();
                if(estadisticas.get(0) > masVueltas) masVueltas = estadisticas.get(0);
                if(estadisticas.get(1) > masDados) masDados = estadisticas.get(1);
                if(estadisticas.get(2) + jugadorActual.getDinero() > mayorFortuna) mayorFortuna = estadisticas.get(2) +jugadorActual.getDinero();
            }

            //mostramos aquellos jugadores/casillas con las mayores estadisticas en cada campo
            
            for(ArrayList<Casilla> lado : casillas)
                for(Casilla cas : lado)
                {
                    estadisticas = cas.getEstadisticasGlobales();
                    if(maxRentabilidadCas != 0 && estadisticas.get(0) == maxRentabilidadCas) rentabilidadCas = rentabilidadCas.concat(cas.getNombre() + ", ");
                }
            if(rentabilidadCas.equals("")) rentabilidadCas = "-"; else rentabilidadCas = rentabilidadCas.substring(0, rentabilidadCas.length() - 2);
            
            for(i = 0;i<4;i++)
                for(j=1;j<10;j+=8)
                {
                    rentabilidadGrupo = 0;
                    casillasGrupo = ((Propiedad)casillas.get(i).get(j)).getGrupo().getCasillas();
                    for(Casilla cas : casillasGrupo) rentabilidadGrupo += cas.getEstadisticasGlobales().get(0);
                    if(maxRentabilidadGrupo != 0 && rentabilidadGrupo == maxRentabilidadGrupo) rentabilidadGrupos = rentabilidadGrupos.concat(Tablero.obtenerColorDeCodigo(casillasGrupo.get(0).getGrupo().getNombre()) + ", ");
                }
            if(rentabilidadGrupos.equals("")) rentabilidadGrupos = "-"; else rentabilidadGrupos = rentabilidadGrupos.substring(0, rentabilidadGrupos.length() - 2);
            
            for(ArrayList<Casilla> lado : casillas)
                for(Casilla cas : lado)
                {
                    estadisticas = cas.getEstadisticasGlobales();
                    if(masVisitada != 0 && estadisticas.get(1) == masVisitada) visitadas = visitadas.concat(cas.getNombre() + ", ");
                }
            if(visitadas.equals("")) visitadas = "-"; else  visitadas = visitadas.substring(0, visitadas.length() - 2);
            
            for(Character clave: ordenTurnos)
            {
                jugadorActual = figuras.get(clave).getJugador();
                estadisticas = jugadorActual.getEstadisticasGlobales();
                if(masVueltas != 0 && estadisticas.get(0) == masVueltas) vueltas = vueltas.concat(jugadorActual.getNombre() + "("+ jugadorActual.getFigura().getID() +"), ");
                if(masDados != 0 && estadisticas.get(1) == masDados) dados = dados.concat(jugadorActual.getNombre() + "("+ jugadorActual.getFigura().getID() +"), ");
                if(mayorFortuna != 0 && estadisticas.get(2) + jugadorActual.getDinero() == mayorFortuna) fortunas = fortunas.concat(jugadorActual.getNombre() + "("+ jugadorActual.getFigura().getID() +"), ");
            }
            
            if(vueltas.equals("")) vueltas = "-"; else vueltas = vueltas.substring(0, vueltas.length() - 2);
            if(dados.equals("")) dados = "-"; else dados = dados.substring(0, dados.length() - 2);
            if(fortunas.equals("")) fortunas = "-"; else fortunas = fortunas.substring(0, fortunas.length() - 2);
            

            return ("<html>casillaMasRentable: " + rentabilidadCas + "<br>  grupoMasRentable: " + rentabilidadGrupos + "<br>  casillaMasFrecuentada: " + visitadas + "<br>  jugadorMasVueltas: " + vueltas + "<br>  jugadorMasVecesDados: " + dados + "<br>  jugadorEnCabeza: " + fortunas + "</html>");
    }

    @Override public String estadisticasJugador(String nombreJugador)
    {
        Jugador jugador;
        boolean estaJ = false;
        String estatJugador = "";
        
        for (Character clave : ordenTurnos)
            if (figuras.get(clave).getJugador().getNombre().equals(nombreJugador))
            {
                jugador = figuras.get(clave).getJugador();
                ArrayList <Long> estadisticas = jugador.getEstadisticas();
            
                return("<html> <br>  dineroInvertido: " + estadisticas.get(0) + "<br>  pagoTasasEImpuestos: " + estadisticas.get(1) + "<br>  pagoDeAlquileres: " + estadisticas.get(2) + "<br>  cobroDeAlquileres: " + estadisticas.get(3) + "<br>  pasarPorCasillaDeSalida: " + estadisticas.get(4) + "<br>  premiosInversionesOBote: " + estadisticas.get(5) + "<br>  vecesEnLaCarcel: " + estadisticas.get(6) + "</html>");
                //estaJ = true;
            }

        if(!estaJ)
            return ("<html> El jugador introducido no existe </html>");
        
       return null;
    }
    
    @Override public Trato tratoTipo1(String nombreJugador, String propuesta, String demanda, String enunciado, long dineroDemandado, long dineroOfertado)
    {
        Trato trato;
        Avatar figura;
        Propiedad prop = null, dem = null;
        Jugador propuesto = null;
     
            figura = figuras.get(ordenTurnos.get(turnoActual));

            for(char idfigura : ordenTurnos)
                if(figuras.get(idfigura).getJugador().getNombre().equals(nombreJugador))
                    propuesto = figuras.get(idfigura).getJugador();
            
            if(!figura.getJugador().getNombre().equals(nombreJugador))
            {
                if(propuesto != null)
                {
                    for(Propiedad propiedad : figura.getJugador().getPropiedades())
                        if(propiedad.getNombre().equals(propuesta))
                            prop = propiedad;

                    if(prop != null)
                    {   
                        for(Propiedad propiedad : propuesto.getPropiedades())
                            if(propiedad.getNombre().equals(demanda))
                                dem = propiedad;

                        if(dem != null)
                        {
                            String aux = "trato";
                            aux = aux.concat("" + contadorTratos);

                            if(dineroDemandado != 0 && dineroOfertado == 0)
                            {
                                trato = new TratoCasillaPorCasillaYDinero(aux, enunciado, figura.getJugador(), propuesto, prop, dem, dineroDemandado);
                                consola.imprimir(propuesto.getNombre() + ", te doy " + prop.getNombre() + " y tú me das " + dem.getNombre() + " y " + dineroDemandado + "€?");
                            }
                            else if(dineroDemandado == 0 && dineroOfertado != 0)
                            {
                                trato = new TratoCasillaYDineroPorCasilla(aux, enunciado, figura.getJugador(), propuesto, prop, dem, dineroOfertado);
                                consola.imprimir(propuesto.getNombre() + ", te doy " + prop.getNombre() + " y " + dineroOfertado + "€ y tú me das " + dem.getNombre() + "?");
                            }
                            else
                            {  
                                trato = new TratoCasillaPorCasilla(aux, enunciado, figura.getJugador(), propuesto, prop, dem);
                                consola.imprimir(propuesto.getNombre() + ", te doy " + prop.getNombre() + " y tú me das " + dem.getNombre() + "?");
                            }

                            propuesto.getTratosRecibidos().add(trato);
                            figura.getJugador().getTratosPropuestos().add(trato);

                            contadorTratos++;
                            
                            return trato;
                        }
                        else
                            consola.imprimir("No se puede proponer el trato: la casilla demandada no pertenece a " + propuesto.getNombre());
                    }
                    else
                        consola.imprimir("No se puede proponer el trato: la casilla ofertada no es de su propiedad o esta hipotecada");
                }
                else
                    consola.imprimir("El jugador al que se quiere proponer el trato no existe");
            }
            else
                consola.imprimir("No se puede proponer tratos a uno mismo");
        
        return null;
    }
    
    @Override public Trato tratoTipo2(String nombreJugador, String propuesta, String demanda, String enunciado, long dinero)
    {
        Trato trato;
        Avatar figura;
        Propiedad prop = null;
        Jugador propuesto = null;
     
            figura = figuras.get(ordenTurnos.get(turnoActual));

            for(char idfigura : ordenTurnos)
                if(figuras.get(idfigura).getJugador().getNombre().equals(nombreJugador))
                    propuesto = figuras.get(idfigura).getJugador();

            if(!figura.getJugador().getNombre().equals(nombreJugador))
            {
                if(propuesto != null)
                {
                    if(propuesta != null)
                    {
                        for(Propiedad propiedad : figura.getJugador().getPropiedades())
                            if(propiedad.getNombre().equals(propuesta))
                                prop = propiedad;

                        if(prop != null)
                        {
                            String aux = "trato";
                            aux = aux.concat("" + contadorTratos);
                            trato = new TratoCasillaPorDinero(aux, enunciado, figura.getJugador(), propuesto, prop, dinero);
                            propuesto.getTratosRecibidos().add(trato);
                            figura.getJugador().getTratosPropuestos().add(trato);
                            contadorTratos++;

                            consola.imprimir(propuesto.getNombre() + ", te doy " + prop.getNombre() + " y tú me das " + dinero + "€?");
                        }
                        else
                            consola.imprimir("No se puede proponer el trato: la casilla ofertada no es de su propiedad o esta hipotecada");
                    }
                    else
                    {
                        for(Propiedad propiedad : propuesto.getPropiedades())
                            if(propiedad.getNombre().equals(demanda))
                                prop = propiedad;

                        if(prop != null)
                        {
                            String aux = "trato";
                            aux = aux.concat("" + contadorTratos);
                            trato = new TratoDineroPorCasilla(aux, enunciado, figura.getJugador(), propuesto, prop, dinero);  
                            propuesto.getTratosRecibidos().add(trato);
                            figura.getJugador().getTratosPropuestos().add(trato);
                            contadorTratos++;

                            consola.imprimir(propuesto.getNombre() + ", te doy " + dinero + "€ y tú me das " + prop.getNombre() + "?");
                        
                            return trato;
                        }                    
                        else
                            consola.imprimir("No se puede proponer el trato: la casilla demandada no pertenece a " + propuesto.getNombre());
                    }
                }
                else
                    consola.imprimir("El jugador al que se quiere proponer el trato no existe");
            }
            else
                consola.imprimir("No se puede proponer tratos a uno mismo");
        
        return null;
    }
    
    @Override public Trato tratoTipo3(String nombreJugador, String propuesta, String demanda, String enunciado, String sinAlquiler, int turnos)
    {
        Trato trato;
        Avatar figura;
        Propiedad prop = null, dem = null, alq = null;
        Jugador propuesto = null;
    
            figura = figuras.get(ordenTurnos.get(turnoActual));

            for(char idfigura : ordenTurnos)
                if(figuras.get(idfigura).getJugador().getNombre().equals(nombreJugador))
                    propuesto = figuras.get(idfigura).getJugador();
            
            if(!figura.getJugador().getNombre().equals(nombreJugador))
            {
                if(propuesto != null)
                {
                    for(Propiedad propiedad : figura.getJugador().getPropiedades())
                        if(propiedad.getNombre().equals(propuesta))
                            prop = propiedad;

                    if(prop != null)
                    {   
                        for(Propiedad propiedad : propuesto.getPropiedades())
                            if(propiedad.getNombre().equals(demanda))
                                dem = propiedad;

                        if(dem != null)
                        {
                            for(Propiedad propiedad : propuesto.getPropiedades())
                                if(propiedad.getNombre().equals(sinAlquiler))
                                    alq = propiedad;

                            if(alq != null)
                                if(turnos > 0)
                                {
                                    String aux = "trato";
                                    aux = aux.concat("" + contadorTratos);
                                    trato = new TratoNoAlquiler(aux, enunciado, figura.getJugador(), propuesto, prop, dem, alq, turnos);   
                                    propuesto.getTratosRecibidos().add(trato);
                                    figura.getJugador().getTratosPropuestos().add(trato);
                                    contadorTratos++;

                                    consola.imprimir(propuesto.getNombre() + ", te doy " + prop.getNombre() + " y tú me das " + dem.getNombre() + " y no pago alquiler en " + alq.getNombre() + " durante " + turnos + " turnos?");
                                
                                    return trato;
                                }
                                else
                                    consola.imprimir("La inmunidad al alquiler tiene que durar como minimo 1 turno");
                            else
                                consola.imprimir("No se puede proponer el trato: la casilla demandada no pertenece a " + propuesto.getNombre());
                        }
                        else
                            consola.imprimir("No se puede proponer el trato: la casilla demandada no pertenece a " + propuesto.getNombre());
                    }
                    else
                        consola.imprimir("No se puede proponer el trato: la casilla ofertada no es de su propiedad o esta hipotecada");
                }
                else
                    consola.imprimir("El jugador al que se quiere proponer el trato no existe");
            }
            else
                consola.imprimir("No se puede proponer tratos a uno mismo");
        
        return null;
    }
    
    @Override public void listarTratosJugadorActual()
    {
        figuras.get(ordenTurnos.get(turnoActual)).getJugador().listarTratos();
    }
    
    @Override public void aceptarTrato(String idTrato)
    {
        Trato trato = null;

        Avatar figura = figuras.get(ordenTurnos.get(turnoActual));

        for(Trato t : figura.getJugador().getTratosRecibidos())
            if(t.getID().equals(idTrato))
                trato = t;

        if(trato != null)
            trato.resolverTrato();
        else
            consola.imprimir("No se le ha propuesto ningun trato con identificador " + idTrato);
    }
    
    @Override public void eliminarTrato(String idTrato)
    {
        Trato trato = null;
        
        Avatar figura = figuras.get(ordenTurnos.get(turnoActual));

        for(Trato t : figura.getJugador().getTratosPropuestos())
            if(t.getID().equals(idTrato))
                trato = t;

        if(trato != null)
        {
            trato.getReceptor().getTratosRecibidos().remove(trato);
            figura.getJugador().getTratosPropuestos().remove(trato);
            consola.imprimir("Se elimino el trato con identificador " + trato.getID() + ".");
        }
        else
            consola.imprimir("No ha propuesto ningun trato cuyo identificador sea " + idTrato);
    }
    
    public void resolucionJugadorException(JugadorException excepcion)
    {
        if(excepcion instanceof NombreRepetidoException)
            consola.imprimir(excepcion.getMessage());

        if(excepcion instanceof EncarceladoException)
        {
            int posSalida, posCarcel,posAux;
            Casilla aux = null;
            long vecesCarcel;

            Avatar figura = figuras.get(ordenTurnos.get(turnoActual));

            for(ArrayList<Casilla> lado : tablero.getCasillas())
                for(Casilla cas : lado)
                    if(cas instanceof Especial && ((Especial)cas).getTipo().equals("Carcel"))
                        aux = cas;
            
            consola.imprimir("La figura \'" + figura.getID() + "\' va a la Carcel");
            figura.setUbicacion(aux);
            figura.setEncarcelado((short)1);

            if(figura.getMovimientoAvanzado() != -5)
                figura.setMovimientoAvanzado((short)0);

            sacarDobles = -1;

            //Cálculo de una de las estadísticas de jugador, se hace en IrCarcel, cartas y dobles
            vecesCarcel = figura.getJugador().getEstadisticas().get(6) + 1;

            figura.getJugador().getEstadisticas().remove(6);
            figura.getJugador().getEstadisticas().add(6, vecesCarcel);
        }

        if(excepcion instanceof SinDineroException)
        {
            Avatar figura = figuras.get(ordenTurnos.get(turnoActual));
            Jugador deuda = ((SinDineroException)excepcion).getDeuda();

            consola.imprimir(excepcion.getMessage());
            figura.getJugador().transferirPropiedades(deuda);

            figura.getUbicacion().getUbicados().remove((Character)(figura.getID()));
            figuras.remove((char)(figura.getID()));
            ordenTurnos.remove(turnoActual);
            sacarDobles = -1;

            try
            {
                acabarTurno();
            }
            catch(GanadorException e)
            {
                consola.imprimir("El jugador " + e.getGanador().getNombre() + " ha ganado la partida de Monopoly!");
            }
        }

        if(excepcion instanceof GanadorException)
        {
            consola.imprimir("El jugador " + ((GanadorException)excepcion).getGanador().getNombre() + " ha ganado la partida de Monopoly!");
        }
    }
    
    public void resolucionMalEscritoException(MalEscritoException excepcion)
    {
        consola.imprimir(excepcion.getMessage());
    }
}

