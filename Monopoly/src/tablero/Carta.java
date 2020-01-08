package tablero;

import casilla.Casilla;
import excepcion.JugadorException;
import jugador.Jugador;
import java.util.ArrayList;
import java.util.Random;
import static tablero.Juego.consola;

public abstract class Carta 
{
    //--------------------------ATRIBUTOS----------------------------------
    
    private Tablero tablero;
    private String enunciado;
    private long valor;                 //Puedes pagar o recibir dinero
    private Casilla nuevaUbicacion;     //Puedes tener que desplazarte a otra casilla
    /*En la siguiente entrega los enunciados de las cartas se guardarán en un array de String en Constantes.java, a la hora de
    construír las cartas desde el constructor de tablero se aplicará un split a cada enunciado.
    Los enunciados tendrán un formato de "enunciado;desplazamiento o valor". Independientemente de que lo que haya después
    del enunciado sea un valor o un desplazamiento se pasará como último argumento a los constructores*/
    
    //------------------------CONSTRUCTORES----------------------------------
    
    //Este constructor es para las casillas que tengan un valor monetario (tanto para cobrar como para dar)
    public Carta(Tablero tablero, String enunciado, long valor)
    {
        if(enunciado != null)
        {
            this.tablero = tablero;
            this.enunciado = enunciado;
            this.valor = valor;
            nuevaUbicacion = null;
        }
        else
            consola.imprimir("Alguno de los argumentos del constructor de Carta con valor no ha sido construido o tiene un valor no valido");
    }
    
    //Este constructor es para las casillas que obliguen a ir a una casilla en particular
    public Carta(Tablero tablero, String enunciado, Casilla nuevaUbicacion)
    {
        if(enunciado != null && nuevaUbicacion != null)
        {
            this.tablero = tablero;
            this.enunciado = enunciado;
            this.valor = 0;
            this.nuevaUbicacion = nuevaUbicacion;
        }
        else
            consola.imprimir("Alguno de los argumentos del constructor de Carta con desplazamiento no ha sido construido o tiene un valor no valido");
    }
    
    //Por la información que tenemos suponemos que no hay cartas que tengan un valor y desplacen la figura a la vez
 
    //------------------------------SETTERS------------------------------------
    
    /*Para esta clase no hacen falta setters porque una vez que la carta ha sido creada no se 
    va a poder modificar ninguno de sus atributos*/
    
    //------------------------------GETTERS-----------------------------------
    
    public final String getEnunciado()
    {
        return enunciado;
    }
    
    public final long getValor()
    {
        return valor;
    }
    
    public final Casilla getNuevaUbicacion()
    {
        return nuevaUbicacion;
    }
    
    //-------------------------RESTOS DE MÉTODOS------------------------------
    
    public final static ArrayList<Carta> barajarMonton(ArrayList<Carta> monton)
    {
        int aleatorioActual, i;
        Random aleatorio = new Random();
        ArrayList<Carta> aux; 
        ArrayList<Integer> aleatorios;
        
        if(monton != null)
        {
            aux = new ArrayList<>();
            aleatorios = new ArrayList<>();
            
            for(i = 0; i < monton.size(); i++)
            {
                aleatorioActual = aleatorio.nextInt(5+1);
                
                while(aleatorios.contains(aleatorioActual))
                    aleatorioActual = aleatorio.nextInt(5+1);
                
                aleatorios.add(aleatorioActual);
                aux.add(monton.get(aleatorioActual));
            }
            
            for(i = 0; i < aux.size(); i++)
            {
                monton.remove(i);
                monton.add(i, aux.get(i));
            }

            return monton;
        }
        else
        {
            consola.imprimir("El argumento pasado a barajarMonton no ha sido construido");
            return null;
        }    
    }
     
    public abstract void accion(Jugador jugador) throws JugadorException;
}
