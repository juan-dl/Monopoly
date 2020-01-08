package casilla;

import java.util.ArrayList;
import static tablero.Juego.consola;

public abstract class Casilla 
{
    //------------------------ATRIBUTOS------------------------
    
    private String nombre;
    private ArrayList<Character> ubicados;
    private ArrayList <Long> estadisticasGlobalesC;
    
    //-------------------------CONSTRUCTORES-----------------------------
    
    //Constructor habitual (para casillas no especiales)
    public Casilla(String nombre)
    {
        //El grupo nos interesa que pueda valer nulo para las casillas que no sean solares
        if(nombre != null)
        {
            this.nombre = nombre;
            
            ubicados = new ArrayList<>();
            estadisticasGlobalesC = new ArrayList<>(2);
            
            for(int i = 0; i < 2; i++)
                estadisticasGlobalesC.add((long)0);
        }
        else
            consola.imprimir("Alguno de los argumentos pasados al constructor habitual de casilla no es valido");
    }
    
    //-----------------------------GETTERS------------------------------
    
    public final String getNombre()
    {
        return nombre;
    }
    
    public final ArrayList<Character> getUbicados()
    {
        return ubicados;
    }
    
    public final ArrayList<Long> getEstadisticasGlobales()
    {
        return estadisticasGlobalesC;
    }
    
    //--------------------------SETTERS-------------------------------
    
    public final void setUbicados(ArrayList<Character> ubicados)
    {
        if(ubicados != null)
            this.ubicados = ubicados;
        else
            consola.imprimir("El argumento pasado en setUbicados no ha sido creado");
    }
    
    //--------------------------RESTO DE MÉTODOS-----------------------
    
    public final boolean estaAvatar(char idAvatar)
    {
        return ubicados.contains(idAvatar);
    }
    
    public final long frecuenciaVisitas()
    {
        return estadisticasGlobalesC.get(1);
    }
    
    //Se redefine toString para que dibuje la casilla con las figuras que tenga encima
    
    @Override public String toString()
    {
        String figurasPresentes = "&";
        
        if(!ubicados.isEmpty())
            for(char c : ubicados)
                figurasPresentes = figurasPresentes.concat(Character.toString(c));
        
        if(getUbicados().isEmpty())
            return getNombre();
        else
            return getNombre() + " " + figurasPresentes;
    }
}    
 
