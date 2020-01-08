package casilla;

import jugador.Jugador;
import java.util.HashMap;
import java.util.ArrayList;
import tablero.Constantes;
import edificio.Edificio;
import java.awt.Color;
import static tablero.Juego.consola;

public final class Grupo extends Casilla
{   
    //------------------------------ATRIBUTOS--------------------------------
    
    private Color color;
    private long precioGrupo;
    private Jugador propietario;
    private HashMap<String, ArrayList<Edificio>> edificaciones;
    private ArrayList<Solar> casillas;
    
    //-------------------------------CONSTRUCTORES------------------------------

    //Constructor para un grupo de 2 casillas
    public Grupo(Jugador propietario, String codigo, long precio, String casilla1, String casilla2, Color c)
    {
        super(codigo);
        
        if(propietario != null && precio > 0 && casilla1 != null && casilla2 != null && c != null)
        {

            precioGrupo = precio;
            casillas = new ArrayList<>();
            edificaciones = new HashMap<>();
            color = c;
            
            for(String tipoEdificio : Constantes.TIPO_EDIFICACIONES)
                edificaciones.put(tipoEdificio, new ArrayList<>());
            
            this.propietario = null;           
            
            casillas.add(new Solar(casilla1, propietario, precio/2, this));
            casillas.add(new Solar(casilla2, propietario, precio/2, this));
        }
        else
            consola.imprimir("Alguno de los argumentos pasados al constructo de grupo de 2 casillas no ha sido construido");
    }
    
    //Constructor para un grupo de 3 casillas
    public Grupo(Jugador propietario, String codigo, long precio, String casilla1, String casilla2, String casilla3, Color c)
    {
        super(codigo);
        
        if(propietario != null && precio > 0 && casilla1 != null && casilla2 != null && casilla3 != null && c != null)
        {
            precioGrupo = precio;
            casillas = new ArrayList<>();
            edificaciones = new HashMap<>();
            color = c;
            
            for(String tipoEdificio : Constantes.TIPO_EDIFICACIONES)
                edificaciones.put(tipoEdificio, new ArrayList<>());
            
            this.propietario = null;
            
            casillas.add(new Solar(casilla1, propietario, precio/2, this));
            casillas.add(new Solar(casilla2, propietario, precio/3, this));
            casillas.add(new Solar(casilla3, propietario, precio/3, this));
        }
        else
            consola.imprimir("Alguno de los argumentos pasados al constructo de grupo de 3 casillas no ha sido construido");
    }
    
    //--------------------------------------SETTERS-------------------------
    
    //Si una casilla del grupo cambia de valor se debe actualizar el valor del grupo para mantener la coherencia
    public void setPrecioGrupo(long precio)
    {
        if(precio > 0)
            precioGrupo = precio;
        else
            consola.imprimir("El precio pasado como argumento de setPrecioGrupo es menor o igual que 0");
    }
    
    public void setPropietario(Jugador propietario)
    {
        //Nos interesa que se admita el null para cuando una casilla del grupo ha sido hipotecada
        this.propietario = propietario;
    }
    
    /*No es necesario un setter para casillas y codigoColor porque una vez asignados en el constructor
    no tendrá sentido cambiarlos (son valores fijos una vez asignados)*/
    
    //--------------------------------------GETTERS--------------------------

    public Color getColor()
    {
        return color;
    }
    
    public long getPrecioGrupo()
    {
        return precioGrupo;
    }
    
    public Jugador getPropietario()
    {
        return propietario;
    }
    public ArrayList<Solar> getCasillas()
    {
        return casillas;
    }  
    
    public HashMap<String, ArrayList<Edificio>> getEdificaciones()
    {
        return edificaciones;
    }
    
    //--------------------OTROS MÉTODOS---------------------------
    
    @Override public String toString()
    {
        String casas, hoteles, piscinas, pistas, resultado = "";
        
        for(Propiedad sol : casillas)
        {
            casas = "["; 
            hoteles = "["; 
            piscinas = "["; 
            pistas = "[";
            
            for (String clave : Constantes.TIPO_EDIFICACIONES)
                for (Edificio edificio : edificaciones.get(clave))
                    if (edificio.getCasilla().equals(sol))
                        switch (clave) 
                        {
                            case "casa":
                                casas = casas.concat(edificio.getID() + ", ");
                                break;
                            case "hotel":
                                hoteles = hoteles.concat(edificio.getID() + ", ");
                                break;
                            case "piscina":
                                piscinas = piscinas.concat(edificio.getID() + ", ");
                                break;
                            case "pista":
                                pistas = pistas.concat(edificio.getID() + ", ");
                                break;
                        }
            
            if (casas.equals("["))
                casas = "-";
            else
                casas = casas.substring(0, casas.length() - 2).concat("]");

            if (hoteles.equals("["))
                hoteles = "-";
            else
                hoteles = hoteles.substring(0, hoteles.length() - 2).concat("]");

            if (piscinas.equals("["))
                piscinas = "-";
            else
                piscinas = piscinas.substring(0, piscinas.length() - 2).concat("]");
            
            if (pistas.equals("["))
                pistas = "-";
            else
                pistas = pistas.substring(0, pistas.length() - 2).concat("]");
           
            resultado = resultado.concat("<br>  propiedades: " + sol.getNombre() + ",<br>  casas: " + casas + ",<br>  hoteles: " + hoteles + ",<br>  piscinas: " + piscinas + ",<br>  pistas: " + pistas + ",<br>  alquiler: " + sol.getAlquilerActual() + "<br><br>");
        }
        
        return resultado;
    }
}
