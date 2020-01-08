package jugador;

import static tablero.Juego.consola;

public abstract class Trato 
{
    //----------------------------ATRIBUTOS-------------------------------------
    
    private String id, enunciado;
    private Jugador emisor, receptor;
    
    //----------------------------CONSTRUCTORES---------------------------------
    
    public Trato(String id, String enunciado, Jugador emisor, Jugador receptor)
    {
        if(id != null && emisor != null && enunciado != null && receptor != null)
        {
            this.id = id;
            this.enunciado = enunciado;
            this.emisor = emisor;
            this.receptor = receptor;
        }
        else
            consola.imprimir("Alguno de los atributos del constructor de Trato no ha sido construido");
    }
    
    //------------------------------GETTERS--------------------------------
       
    public String getID()
    {
        return id;
    }
    
    public String getEnunciado()
    {
        return enunciado;
    }
    
    public Jugador getEmisor()
    {
        return emisor;
    }
    
    public Jugador getReceptor()
    {
        return receptor;
    }
    
    ///------------------------------RESTO DE MÉTODOS-----------------------------
    
    public abstract boolean resolverTrato();
    
    @Override public final String toString()
    {
        return "\n  idTrato: " + id + "\n  jugadorPropone: " + emisor.getNombre() + "\n jugadorRecibe: " + receptor.getNombre() + "\n  trato: " + enunciado;
    }
}
