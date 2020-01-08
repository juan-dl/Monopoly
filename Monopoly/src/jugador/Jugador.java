package jugador;

import tablero.Tablero;
import casilla.Solar;
import casilla.Propiedad;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;
import javax.swing.JLabel;
import tablero.Constantes;
import casilla.Grupo;
import edificio.Edificio;
import static tablero.Juego.consola;
import static tablero.Juego.partida;

public class Jugador
{
    //-----------------------ATRIBUTOS------------------------------
    
    private String nombre;
    private Avatar figura;
    private long dinero;
    private ArrayList <Propiedad> propiedades, hipotecas;
    private HashMap <Propiedad, Integer> inmune;
    private ArrayList <Trato> tratosPropuestos, tratosRecibidos;
    private ArrayList <Long> estadisticas;
    private ArrayList <Long> estadisticasGlobalesJ;
    
    //-----------------------CONSTRUCTORES---------------------------
    
    //Constructor por defecto, si no se le pasa ningún argumento se supondrá que se está creando la banca
    public Jugador()
    {
        nombre = "banca";
        figura = null;
        dinero = -1;    //Valor especial que representa la cantidad infinita de dinero de la banca
        propiedades = new ArrayList<>();
        hipotecas = new ArrayList<>();
    }
    
    //Constructor habitual de un jugador normal
    public Jugador(String nombre, String tipoFigura, Tablero tableroActual)
    {
        if(nombre != null && tipoFigura != null && tableroActual != null)
        {
            this.nombre = nombre;
            
            switch(tipoFigura)
            {
                case "pelota":
                    this.figura = new Pelota(this, tableroActual);
                    break;
                    
                case "coche":
                    this.figura = new Coche(this, tableroActual);
                    break;
                    
                case "sombrero":
                    this.figura = new Sombrero(this, tableroActual);
                    break;
                    
                case "esfinge":
                    this.figura = new Esfinge(this, tableroActual);
                    break;
            }
        
            dinero = Constantes.DINERO_INICIAL;
        
            propiedades = new ArrayList<>();
            hipotecas = new ArrayList<>();
            inmune = new HashMap<>();
            estadisticas = new ArrayList<>(7);
            tratosRecibidos = new ArrayList<>();
            tratosPropuestos = new ArrayList<>();
            
            for(int i = 0; i < 7; i++)
                estadisticas.add((long)0);
            
            estadisticasGlobalesJ = new ArrayList<>(3);
            
            for(int i = 0; i < 3; i++)
                estadisticasGlobalesJ.add((long)0);
        }
        else
            consola.imprimir("Alguno de los argumentos pasados al constructor habitual de Jugador no ha sido construido");
    }   
    
    //---------------------------GETTERS---------------------------
    
    public String getNombre()
    {
        return nombre;
    }
    
    public Avatar getFigura()
    {
        return figura;
    }
    
    public long getDinero()
    {
        return dinero;
    }
    
    public ArrayList<Propiedad> getPropiedades()
    {
        return propiedades;
    }
    
    public ArrayList<Propiedad> getHipotecas()
    {
        return hipotecas;
    }
    
    public HashMap<Propiedad, Integer> getInmune()
    {
        return inmune;
    }
    
    public ArrayList<Trato> getTratosPropuestos()
    {
        return tratosPropuestos;
    }
    
    public ArrayList<Trato> getTratosRecibidos()
    {
        return tratosRecibidos;
    }
    
    public ArrayList<Long> getEstadisticas()
    {
        return estadisticas;
    }
    
    public ArrayList<Long> getEstadisticasGlobales()
    {
        return estadisticasGlobalesJ;
    }
    
    //---------------------------SETTERS---------------------------
    
    /*Este setter tiene sentido porque si hay figuras con IDs iguales es necesario cambiar la ID de la figura
    para ello se reconstruye la figura (el ID  de la figura se asigna en su constructor)*/
    public void setFigura(Avatar figura)
    {
        if(figura != null)
            this.figura = figura;
        else
            consola.imprimir("La figura pasada como argumento en el setFigura no ha sido creada");
    }
    
    public void setDinero(long cantidad)
    {
        if(cantidad >= 0)
        {
            dinero = cantidad;
            
            for(JPanel p : partida.getJugadores())
                if(((JLabel)p.getComponent(0)).getText().equals(nombre))
                    ((JLabel)p.getComponent(4)).setText(Long.toString(dinero));
        }
        else
            consola.imprimir("La cantidad pasada como argumento en setDinero es negativa");
    }
    
    /*Los atributos propiedades e hipotecas no necesitan setters porque a partir de sus getters y
    haciendo aliasing se pueden modificar desde fuera*/
    
    /*Set estadisticas no tiene sentido porque se va a modificar a traves del aliasing*/
    
    //-----------------------------RESTO DE MÉTODOS------------------------------------
    
    /*Este método se encarga de eliminar las propiedades del jugador actual y dárselas al receptor,
    tiene utilidad cuando un jugador se declara en bancarrota y queda endeudado con otro*/
    public void transferirPropiedades(Jugador receptor)
    {
        short i;
        
        if(receptor != null)
        {
            for(i = 0; i < propiedades.size(); i++)
            {
                propiedades.get(i).setPropietario(receptor);
                receptor.anhadirCasilla(propiedades.get(i));
                propiedades.remove(propiedades.get(i));
            }
        
            for(i = 0; i < hipotecas.size(); i++)
            {
                hipotecas.get(i).setPropietario(receptor);
                receptor.anhadirCasilla(hipotecas.get(i));
                hipotecas.remove(hipotecas.get(i));
            }
        }
        else
            consola.imprimir("El argumento pasado a transferirPropiedades no ha sido construido");
    }
    
    //Método que muestra el nombre y la ID de la figura del jugador
    public String identificarJugador()
    {
        return "<br>nombre:  " + nombre + ",<br>figura: " + figura.getID() + "<br>";
    }
    
    //Este método añade una determinada casillas a las propiedades de un usuario cuando la compra
    public void anhadirCasilla(Propiedad casilla)
    {
        if(casilla != null)
        {
            propiedades.add(casilla);
            
            if(!nombre.equals("banca"))
            {
                long dineroInvertido = estadisticas.get(0);

                //Cálculo de una de las estadísticas de jugador
                estadisticas.remove(0);
                estadisticas.add(0, dineroInvertido + casilla.getPrecio());
            }
        }
        else
            consola.imprimir("El argumento pasado a anhadirCasilla no ha sido construido");
    }
    
    public void listarTratos()
    {   
        consola.imprimir("-----------------Tratos Propuestos-------------------");
        
        if(!tratosPropuestos.isEmpty())
            for(Trato trato: tratosPropuestos)
                consola.imprimir(trato + ",<br>");
        else
            consola.imprimir("No tiene tratos propuestos disponibles actualmente<br>");

        
        consola.imprimir("-----------------Tratos Recibidos-------------------");
        
        if(!tratosRecibidos.isEmpty())
            for(Trato trato: tratosRecibidos)
                consola.imprimir(trato + ",<br>");
        else
            consola.imprimir("No tiene tratos disponibles actualmente");
    }
    
     public ArrayList<Grupo> obtenerGruposEdificados()
     {
        ArrayList <Grupo> Edificados = new ArrayList <>();
            
        for(Propiedad cas : propiedades)
            if(cas instanceof Solar && !Edificados.contains(cas.getGrupo()))
                Edificados.add(cas.getGrupo());

        return Edificados;
    }
     
    //Se redefine toString para que muestre toda la información del jugador
    @Override public String toString()
    {
        String prop = "[ ", hip = "[ ", edif = "[ ", fig = "";
        ArrayList <Grupo> Edificados = obtenerGruposEdificados();
        
        for(int i=0;i<propiedades.size();i++)
            prop = prop.concat(propiedades.get(i).getNombre()+", ");
        
        for(int i=0;i<hipotecas.size();i++)
            hip = hip.concat(hipotecas.get(i).getNombre()+", ");
        
        for(Grupo grupo : Edificados)
            for(String tipoEdificio : Constantes.TIPO_EDIFICACIONES)
                for(Edificio edificio : grupo.getEdificaciones().get(tipoEdificio))
                edif = edif.concat(edificio.getID() + ", ");
        
        if(prop.equals("[ "))
            prop = "-";
        else
            prop = prop.substring(0, prop.length() - 2).concat(" ]");
        
        if(hip.equals("[ "))
            hip = "-";
        else
            hip = hip.substring(0, hip.length() - 2).concat(" ]");
              
        if(edif.equals("[ "))
            edif = "-";
        else
            edif = edif.substring(0, edif.length() - 2).concat(" ]");
        
        if(figura instanceof Esfinge)
            fig = "Esfinge";
        else if(figura instanceof Coche)
            fig = "Coche";
        else if(figura instanceof Pelota)
            fig = "Pelota";
        else
            fig = "Sombrero";
            
            
        return "nombre:  " + nombre + "<br>figura: " + figura.getID() +"<br>Tipo figura: " + fig + "<br>Dinero: " + dinero + "<br>Propiedades: " + prop + "<br>Hipotecas: " + hip + "<br>Edificios: " + edif;
    }
}
