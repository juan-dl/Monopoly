package jugador;

import casilla.Accion;
import casilla.CajaComunidad;
import tablero.Tablero;
import casilla.Casilla;
import casilla.Especial;
import casilla.Impuesto;
import casilla.Propiedad;
import casilla.Solar;
import casilla.Suerte;
import excepcion.JugadorException;
import java.util.Random;
import java.util.ArrayList;
import tablero.Constantes;
import tablero.Juego;
import static tablero.Juego.consola;
import static tablero.Juego.partida;

public abstract class Avatar
{
    //---------------------ATRIBUTOS-------------------------------

    private  int contadorRelativo;
    private Jugador jugador;
    private Casilla ubicacion;
    private short encarcelado;  //Este atributo administra el estado de una figura en la cárcel
    private Tablero tablero;
    private short vuelta;       //Este atributo se encarga de contar la cantidad de vueltas que ha dado una figura
    private char id;
    private int resultadoDados;
    private short movimientoAvanzado;
    
    //---------------------CONSTRUCTORES----------------------------
    
    public Avatar(Jugador jugador, Tablero tableroActual)
    {
        if(jugador != null && tableroActual != null)
        {
            Random identificador = new Random();
            ArrayList<ArrayList<Casilla>> casillas = tableroActual.getCasillas();

            contadorRelativo = 0;
            this.jugador = jugador;
            tablero = tableroActual;
            encarcelado = 0;
            vuelta = 0;
            resultadoDados = 0;
            movimientoAvanzado = -5;

            id = (char) (identificador.nextInt(126 - 33 + 1) + 33);

            setUbicacion(casillas.get(0).get(0));
            
        }
        else
            consola.imprimir("Alguno de los argumentos pasados al constructor de Figura no ha sido construido");
    }
    
    //--------------------------------GETTERS------------------------------ 
    
    public final Jugador getJugador()
    {
        return jugador;
    }
    
    
    public final char getID()
    {
        return id;
    }
    
    public final Casilla getUbicacion()
    {
        return ubicacion;
    }
    
    public final short getVuelta()
    {
        return vuelta;
    }
    
    public final short getEncarcelado()
    {
        return encarcelado;
    }
    
    public final Tablero getTablero()
    {
        return tablero;
    }
    
    public final int getResultadoDados()
    {
        return resultadoDados;
    }
    
    public final short getMovimientoAvanzado()
    {
        return movimientoAvanzado;
    }

    public int getContadorRelativo() 
    {
        return contadorRelativo;
    }
    
    
    //--------------------------------SETTERS---------------------------
    
    public final void setUbicacion(Casilla casilla)
    {
        ArrayList<Character> ubicados;
        long visitas;
        
        if(casilla != null)
        {   
            if(ubicacion != null)
            {   
                ubicados = ubicacion.getUbicados();

                if (ubicados.contains(id)) 
                    ubicados.remove((Character) id);
                
                partida.setNombre(ladoUbicacion(), tablero.getCasillas().get(ladoUbicacion()).indexOf(ubicacion), ubicacion.toString());
            }
            
            if(!casilla.getNombre().equals(Constantes.NOMBRE_CASILLAS[0]))
            {
                visitas = casilla.getEstadisticasGlobales().get(1) +1;
                casilla.getEstadisticasGlobales().remove(1);
                casilla.getEstadisticasGlobales().add(1,visitas);
            }
            
            ubicacion = casilla;
            
            ubicados = ubicacion.getUbicados();
            

            ubicados.add(id);
            
            partida.setNombre(ladoUbicacion(), tablero.getCasillas().get(ladoUbicacion()).indexOf(casilla), ubicacion.toString());    
        }
        else
            consola.imprimir("La casilla pasada como argumento en setUbicacion no ha sido creada");
    }
    
    public final void setVuelta(short vuelta)
    {
        if(vuelta >= 0)
            this.vuelta = vuelta;
        else
            consola.imprimir("El argumento de setVuelta en Figura no esta construido");
    }        
    
    public final void setEncarcelado(short encarcelado)
    {
        if(encarcelado >= 0 && encarcelado <= Constantes.CARCEL_PAGO_FORZADO)
            this.encarcelado = encarcelado;
        else
            consola.imprimir("El argumento pasado a setEncarcelado toma un valor no valido"); 
    }
    
    public final void setResultadoDados(int valor)
    {
        resultadoDados = valor;
    }
    
    public final void setMovimientoAvanzado(short valor)
    {
        movimientoAvanzado = valor;
    }

    public void setJugador(Jugador jugador) 
    {
        this.jugador = jugador;
    }

    public void setId(char id) 
    {
        this.id = id;
    }

    public void setTablero(Tablero tablero)
    {
        this.tablero = tablero;
        ubicacion = null;
    }

    public void setContadorRelativo(int contadorRelativo) 
    {
        this.contadorRelativo = contadorRelativo;
    }
    
    
    
    /*No tiene sentido hacer más setters porque el resto de atributos una vez que se inicialicen
    no podrán modificarse, por ejemplo no se puede cambiar el tipo de la figura, su jugador, el identificador....*/
        
    //---------------------------RESTO DE MÉTODOS----------------------------------
    
    public final short ladoUbicacion()
    {
        ArrayList<ArrayList<Casilla>> casillas = tablero.getCasillas();
        
        if(casillas.get(0).contains(ubicacion))
            return 0;
        else if(casillas.get(1).contains(ubicacion))
            return 1;
        else if(casillas.get(2).contains(ubicacion))
            return 2;
        else
            return 3;
    }
    
    //Este método actualiza la ubicación de la figura sabiendo el resultado de la suma de los dados
    private boolean actualizarUbicacion()
    {
        boolean darVuelta = false, condicionAntes = false, condicionDespues = false;
        int posicionActual, movimientoTotal, nuevaPosicion;
        long visitas;
        short lado;
        ArrayList<Character> ubicados;

        ubicados = ubicacion.getUbicados();
            
        if(ubicados.contains(id))
            ubicados.remove((Character)id);     //Si no hacemos el casteo detecta que es un entero y busca un índice inexistente (IndexOutOfBoundsException)
            
        partida.setNombre(ladoUbicacion(), tablero.getCasillas().get(ladoUbicacion()).indexOf(ubicacion), ubicacion.toString());
        
        ArrayList<ArrayList<Casilla>> casillas = tablero.getCasillas();
        
        lado = ladoUbicacion();
        posicionActual = casillas.get(lado).indexOf(ubicacion);
        
        movimientoTotal = posicionActual + resultadoDados;
        
        while(movimientoTotal >= 10)
        {
            movimientoTotal = movimientoTotal-10;
            lado++;
                
            if(lado >= 4)
            {
                lado=0;
                darVuelta = true;
            }
          
        }
        
        if(movimientoTotal < 0)
        {
            movimientoTotal = 10 + movimientoTotal;
            lado--;
            
            if(lado < 0)
                lado = 3;
        }
            
        nuevaPosicion = movimientoTotal;
            
        ubicacion=casillas.get(lado).get(nuevaPosicion);
        
        visitas = ubicacion.getEstadisticasGlobales().get(1);
        ubicacion.getEstadisticasGlobales().remove(1);
        ubicacion.getEstadisticasGlobales().add(1,visitas+1);
            
        ubicados = ubicacion.getUbicados();
        ubicados.add(id);
        
        partida.setNombre(ladoUbicacion(), tablero.getCasillas().get(ladoUbicacion()).indexOf(ubicacion), ubicacion.toString());
        
        return darVuelta;
    }
    
    public final void comprobarCasilla() throws JugadorException
    {   
        Casilla aux = tablero.getCasillas().get(0).get(0);
        long pagoVueltaAcumulado, dineroV;
        boolean hayVuelta;
        
        if (hayVuelta=actualizarUbicacion()) 
        {
            for(ArrayList<Casilla> lado : tablero.getCasillas())
                for(Casilla cas : lado)
                    if(cas instanceof Especial && ((Especial)cas).getTipo().equals("Salida"))
                        aux = cas;
            
            ((Accion)aux).aplicarAccion(jugador);
            
            consola.imprimir(Juego.popup.getMensaje().substring(6, Juego.popup.getMensaje().length() - 7) + ubicacion.getNombre() + "\". Ha pasado por la casilla de salida, cobra " + ((Especial)aux).getPago() + ".");
            vuelta++;  
            
            //Cada vez que el propietario de la casilla caiga en ella se incrementara las visitas del propietario
            //De esta forma podemos saber cuando la casilla puede ser edificada (si ha sido visitada 2 veces como mínimo)
            if(ubicacion instanceof Solar && ((Solar)ubicacion).getPropietario().equals(jugador))
                ((Solar)ubicacion).setVisitasPropietario(((Solar)ubicacion).getVisitasPropietario() + 1);  
            
            //Cálculo de una de las estadísticas de jugador
            
            pagoVueltaAcumulado = jugador.getEstadisticas().get(4) + ((Especial)aux).getPago();
            
            jugador.getEstadisticasGlobales().remove(0);
            jugador.getEstadisticasGlobales().add(0,(long)vuelta);
            
            jugador.getEstadisticas().remove(4);
            jugador.getEstadisticas().add(4, pagoVueltaAcumulado);
            
            if(this instanceof Esfinge)
            {
                dineroV = ((Esfinge)this).getPagos().get(1) + ((Especial)aux).getPago();
                ((Esfinge)this).getPagos().remove(1);
                ((Esfinge)this).getPagos().add(1,dineroV);
            }
            if(this instanceof Sombrero)
            {
                dineroV = ((Sombrero)this).getPagos().get(1) + ((Especial)aux).getPago();
                ((Sombrero)this).getPagos().remove(1);
                ((Sombrero)this).getPagos().add(1,dineroV);
            }
        } 
        else 
            consola.imprimir( Juego.popup.getMensaje().substring(6, Juego.popup.getMensaje().length() - 7) + ubicacion.getNombre() + "\".");
        
        if(ubicacion instanceof Especial)
            switch(((Especial)ubicacion).getTipo())
            {
                case "Salida":
                    if(!hayVuelta)
                        ((Accion)ubicacion).aplicarAccion(jugador);
                    break;

                case "Carcel":
                    break;

                case "IrCarcel":
                    ((Accion)ubicacion).aplicarAccion(jugador);
                    break;
                    
                case "Parking":
                    ((Accion)ubicacion).aplicarAccion(jugador);
                break;  
            }
        else if(ubicacion instanceof Impuesto)
            ((Impuesto)ubicacion).cobrarImpuesto(jugador);
        else if(ubicacion instanceof CajaComunidad)
        {
            partida.getVentanaCartas().setTipo(true);
            partida.getVentanaCartas().setVisible(true);       
        }
        else if(ubicacion instanceof Suerte)
        {
            partida.getVentanaCartas().setTipo(false);
            partida.getVentanaCartas().setVisible(true);      
        }
        else
            //La opción por defecto se reserva para las casillas que puedan tener un alquiler
            ((Propiedad)ubicacion).cobrarAlquiler(jugador);
    }
    
    public boolean lanzarDados(short sacarDobles) throws JugadorException
    {
        boolean dobles;
        long dados;
        int dado1, dado2;
        Random aleatorio = new Random();
        
        dado1 = aleatorio.nextInt(6 - 1 + 1) + 1;
        dado2 = aleatorio.nextInt(6 - 1 + 1) + 1;
        
        partida.setDados(dado1, dado2);

        resultadoDados = dado1 + dado2;
        
        dados = jugador.getEstadisticasGlobales().get(1) + 1;
        jugador.getEstadisticasGlobales().remove(1);
        jugador.getEstadisticasGlobales().add(1,dados);
        
        if((dobles = dado1 == dado2))
            encarcelado = 0;
        
        return dobles;
    }
    
    public final void moverEnBasico(short sacarDobles, boolean dobles) throws JugadorException
    {
        if(sacarDobles != 2 || dobles == false)
            if(encarcelado == 0)
            { 
                consola.imprimir("El avatar \'" + id + "\' avanza " + resultadoDados + " posiciones, desde \"" + ubicacion.getNombre() + "\" hasta \"");

                comprobarCasilla();
            }
            else
                consola.imprimir("No ha sacado dobles, sigue en la carcel");
    }
    
    public abstract void moverEnAvanzado() throws JugadorException;
}
