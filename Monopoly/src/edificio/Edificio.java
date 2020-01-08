package edificio;

import casilla.Solar;
import static tablero.Juego.consola;
import tablero.Tablero;

public abstract class Edificio 
{
    //------------------------------ATRIBUTOS-------------------------------
    
    private String id;
    private Solar casilla;
    private long precio;
    
    //-----------------------------CONSTRUCTORES--------------------------
    
    public Edificio(String identificador, Solar casilla, long precio)
    {
        if(identificador != null && casilla != null)
        {
                id = identificador;
                this.casilla = casilla;
                this.precio = precio;
        }
        else
            consola.imprimir("Alguno de los argumentos pasados al constructor de Edificacion no ha sido construido");
    }
    
    //---------------------------SETTERS---------------------------------
    
    /*No tiene sentido hacer un setter para la ID porque una vez puesta no se volverá a cambiar.
    No hay setCasilla porque no se va a modificar, el único cambio que podría tener es que valga 
    NULL después de ser vendida, en ese caso se llamará a un método pendiente de hacer encargado de eliminarla.*/
    
    //--------------------------GETTERS---------------------------------------
    
    public final String getID()
    {
        return id;
    }
    
    public final Solar getCasilla()
    {
        return casilla;
    }
    
    public final long getPrecio()
    {
        return precio;
    }
    
    public final void setPrecio(long precio)
    {
        this.precio = precio;
    }
    
    @Override public final String toString()
    {
        return " <br>id: " + id + " ,<br>  propietario: " + casilla.getPropietario().getNombre() + ",<br>  casilla: " + casilla.getNombre() + ",<br>  grupo: " + Tablero.obtenerColorDeCodigo(casilla.getGrupo().getNombre()) + ",<br>  coste: " + precio + ",<br>";
    }
}
