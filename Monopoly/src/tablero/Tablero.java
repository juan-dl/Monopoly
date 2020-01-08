package tablero;

import casilla.*;
import jugador.Jugador;
import jugador.Avatar;

import java.util.ArrayList;
import java.util.HashMap;
import static tablero.Juego.consola;

public class Tablero 
{
    //-------------------------ATRIBUTOS----------------------
    
    private int ubicacionSalida;
    private Jugador banca;
    private ArrayList<Grupo> grupos; 
    private ArrayList<ArrayList<Casilla>> casillas;
    private HashMap<Character, Avatar> figuras;
    private long bote;
    private ArrayList<Carta> cartasSuerte, cartasComunidad;
    private HashMap <String, Integer> contadoresIDedificaciones;
    
    //------------------------CONSTRUCTORES---------------------
    
    public Tablero()
    {
        
        String partesEnunciadoSuerte[],partesEnunciadoComunidad[];
        short i,j;
        
        banca = new Jugador();
        grupos = new ArrayList<>();
        casillas = new ArrayList<>();
        figuras = new HashMap<>();
        cartasSuerte = new ArrayList<>(Constantes.CARTAS_MONTON);
        cartasComunidad = new ArrayList<>(Constantes.CARTAS_MONTON);
        bote = 0;
        
        contadoresIDedificaciones = new HashMap<>();

        for(String tipoEdificio : Constantes.TIPO_EDIFICACIONES)
            contadoresIDedificaciones.put(tipoEdificio, 1);
     
        grupos.add(new Grupo(banca, Constantes.NEGRO, Constantes.PRIMER_GRUPO, Constantes.NOMBRE_CASILLAS[1], Constantes.NOMBRE_CASILLAS[3], Constantes.COLORES_JUGADORES_VENTANA[6]));
        grupos.add(new Grupo(banca, Constantes.CYAN, Constantes.SEGUNDO_GRUPO, Constantes.NOMBRE_CASILLAS[6], Constantes.NOMBRE_CASILLAS[8], Constantes.NOMBRE_CASILLAS[9], Constantes.COLORES_JUGADORES_VENTANA[0]));
        grupos.add(new Grupo(banca, Constantes.ROSA, Constantes.TERCER_GRUPO, Constantes.NOMBRE_CASILLAS[11], Constantes.NOMBRE_CASILLAS[13], Constantes.NOMBRE_CASILLAS[14], Constantes.COLORES_JUGADORES_VENTANA[3]));
        grupos.add(new Grupo(banca, Constantes.AMARILLO, Constantes.CUARTO_GRUPO, Constantes.NOMBRE_CASILLAS[16], Constantes.NOMBRE_CASILLAS[18], Constantes.NOMBRE_CASILLAS[19], Constantes.COLORES_JUGADORES_VENTANA[5]));
        grupos.add(new Grupo(banca, Constantes.ROJO, Constantes.QUINTO_GRUPO, Constantes.NOMBRE_CASILLAS[21], Constantes.NOMBRE_CASILLAS[23], Constantes.NOMBRE_CASILLAS[24], Constantes.COLORES_JUGADORES_VENTANA[4]));
        grupos.add(new Grupo(banca, Constantes.MARRON, Constantes.SEXTO_GRUPO, Constantes.NOMBRE_CASILLAS[26], Constantes.NOMBRE_CASILLAS[27], Constantes.NOMBRE_CASILLAS[29], Constantes.COLORES_JUGADORES_VENTANA[2]));
        grupos.add(new Grupo(banca, Constantes.VERDE, Constantes.SEPTIMO_GRUPO, Constantes.NOMBRE_CASILLAS[31], Constantes.NOMBRE_CASILLAS[32], Constantes.NOMBRE_CASILLAS[34], Constantes.COLORES_JUGADORES_VENTANA[1]));
        grupos.add(new Grupo(banca, Constantes.AZUL, Constantes.OCTAVO_GRUPO, Constantes.NOMBRE_CASILLAS[37], Constantes.NOMBRE_CASILLAS[39], Constantes.COLORES_JUGADORES_VENTANA[7]));
        
        ArrayList<Casilla> abajo = new ArrayList<>();
        ArrayList<Casilla> izquierda = new ArrayList<>();
        ArrayList<Casilla> arriba = new ArrayList<>();
        ArrayList<Casilla> derecha = new ArrayList<>();
        
        abajo.add(new Especial(Constantes.NOMBRE_CASILLAS[0], "Salida"));
        abajo.add(grupos.get(0).getCasillas().get(0));
        abajo.add(new CajaComunidad(Constantes.NOMBRE_CASILLAS[2]));
        abajo.add(grupos.get(0).getCasillas().get(1));
        //-----------------------------------------------------------------------
        abajo.add(new Impuesto(Constantes.NOMBRE_CASILLAS[4], true));
        //------------------------------------------------------------------------
        abajo.add(new Transporte(Constantes.NOMBRE_CASILLAS[5], banca, Constantes.PAGO_VUELTA));
        abajo.add(grupos.get(1).getCasillas().get(0));
        abajo.add(new Suerte(Constantes.NOMBRE_CASILLAS[7]));
        abajo.add(grupos.get(1).getCasillas().get(1));
        abajo.add(grupos.get(1).getCasillas().get(2));
        
        izquierda.add(new Especial(Constantes.NOMBRE_CASILLAS[10], "Carcel"));
        izquierda.add(grupos.get(2).getCasillas().get(0));
        izquierda.add(new Servicio(Constantes.NOMBRE_CASILLAS[12], banca, Constantes.SERVICIOS));
        izquierda.add(grupos.get(2).getCasillas().get(1));
        izquierda.add(grupos.get(2).getCasillas().get(2));
        izquierda.add(new Transporte(Constantes.NOMBRE_CASILLAS[15], banca, Constantes.PAGO_VUELTA));
        izquierda.add(grupos.get(3).getCasillas().get(0));
        izquierda.add(new CajaComunidad(Constantes.NOMBRE_CASILLAS[17]));
        izquierda.add(grupos.get(3).getCasillas().get(1));
        izquierda.add(grupos.get(3).getCasillas().get(2));
        
        arriba.add(new Especial(Constantes.NOMBRE_CASILLAS[20], "Parking"));  
        arriba.add(grupos.get(4).getCasillas().get(0));
        arriba.add(new Suerte(Constantes.NOMBRE_CASILLAS[22]));
        arriba.add(grupos.get(4).getCasillas().get(1));
        arriba.add(grupos.get(4).getCasillas().get(2));
        arriba.add(new Transporte(Constantes.NOMBRE_CASILLAS[25], banca, Constantes.PAGO_VUELTA));
        arriba.add(grupos.get(5).getCasillas().get(0));
        arriba.add(grupos.get(5).getCasillas().get(1));
        arriba.add(new Servicio(Constantes.NOMBRE_CASILLAS[28], banca, Constantes.SERVICIOS));
        arriba.add(grupos.get(5).getCasillas().get(2));
        
        derecha.add(new Especial(Constantes.NOMBRE_CASILLAS[30], "IrCarcel"));
        derecha.add(grupos.get(6).getCasillas().get(0));
        derecha.add(grupos.get(6).getCasillas().get(1));
        derecha.add(new CajaComunidad(Constantes.NOMBRE_CASILLAS[33]));
        derecha.add(grupos.get(6).getCasillas().get(2));
        derecha.add(new Transporte(Constantes.NOMBRE_CASILLAS[35], banca, Constantes.PAGO_VUELTA));
        derecha.add(new Suerte(Constantes.NOMBRE_CASILLAS[36]));
        derecha.add(grupos.get(7).getCasillas().get(0));
        //-----------------------------------------------------------------------
        derecha.add(new Impuesto(Constantes.NOMBRE_CASILLAS[38], false));
        //-----------------------------------------------------------------------
        derecha.add(grupos.get(7).getCasillas().get(1));
        
        casillas.add(abajo);
        casillas.add(izquierda);
        casillas.add(arriba);
        casillas.add(derecha);
        
        for(i = 0; i < Constantes.CARTAS_MONTON; i++)
        {
            partesEnunciadoSuerte = Constantes.ENUNCIADOS_SUERTE[i].split(";");
            partesEnunciadoComunidad = Constantes.ENUNCIADOS_COMUNIDAD[i].split(";");
            
            Casilla ubicacionCartaSuerte = null, ubicacionCartaComunidad = null;
            
            for(j = 0; j < Constantes.NOMBRE_CASILLAS.length; j++)
            {
                if(Constantes.NOMBRE_CASILLAS[j].equals(partesEnunciadoSuerte[1]))
                    ubicacionCartaSuerte = casillas.get(j / 10).get(j % 10);
                if(Constantes.NOMBRE_CASILLAS[j].equals(partesEnunciadoComunidad[1]))
                    ubicacionCartaComunidad = casillas.get(j / 10).get(j % 10);
            }
                 
            if(ubicacionCartaSuerte != null)
                cartasSuerte.add(new CartaSuerte(this, partesEnunciadoSuerte[0], ubicacionCartaSuerte));
            else
                cartasSuerte.add(new CartaSuerte(this, partesEnunciadoSuerte[0], Long.parseLong(partesEnunciadoSuerte[1])));
            
            if(ubicacionCartaComunidad != null)
                cartasComunidad.add(new CartaCajaComunidad(this, partesEnunciadoComunidad[0], ubicacionCartaComunidad));
            else
                cartasComunidad.add(new CartaCajaComunidad(this, partesEnunciadoComunidad[0], Long.parseLong(partesEnunciadoComunidad[1])));
        }
        
        for(ArrayList<Casilla> lado : casillas)
            for(Casilla cas : lado)
            {
                if(cas instanceof Especial && ((Especial)cas).getTipo().equals("Salida"))
                    ubicacionSalida = casillas.indexOf(lado) * 10 + lado.indexOf(cas);
                
                if(cas instanceof Especial && ((Especial)cas).getTipo().equals("Carcel"))
                    ubicacionSalida = casillas.indexOf(lado) * 10 + lado.indexOf(cas);
            }
    }
    
    //------------------------GETTERS-------------------------
    
    public Jugador getBanca()
    {
        return banca;
    }
    
    public ArrayList<Grupo> getGrupos()
    {
        return grupos;
    }
    
    public HashMap<Character, Avatar> getFiguras()
    {
        return figuras; 
    }
    
    public ArrayList<ArrayList<Casilla>> getCasillas()
    {
        return casillas;
    }
    
    public long getBote()
    {
        return bote;
    }
    
    public ArrayList<Carta> getCartasSuerte()
    {
        return cartasSuerte;
    }
    
    public ArrayList<Carta> getCartasComunidad()
    {
        return cartasComunidad;
    }
    
    public HashMap<String, Integer> getContadoresIDedificaciones()
    {
        return contadoresIDedificaciones;
    }

    public int getUbicacionSalida() 
    {
        return ubicacionSalida;
    }

    
    
    
    //----------------------------SETTERS--------------------------------------

    //-----------------------------------------------------------------------
    public void setGrupos(ArrayList<Grupo> grupos)
    {
        this.grupos = grupos;
    }

    public void setCasillas(ArrayList<ArrayList<Casilla>> casillas)
    {
        this.casillas = casillas;
    }

    public void setFiguras(HashMap<Character, Avatar> figuras) 
    {
        this.figuras = figuras;
    }
    //-----------------------------------------------------------------------

    public void setBote(long dinero)
    {
        if(dinero >= 0)
            bote = dinero;
        else
            consola.imprimir("El argumento pasado a setBote es negativo");
    }
    
    /*No tiene sentido poner más setters porque el único atributo que se va a modificar una vez iniciada la partida
    a parte del bote serán las figuras (cuando alguien esté en bancarrota), la modificación de ese atributo se hará 
    usando el aliasing a través del getter, por lo tanto no es necesario hacer el setter de ese atributo.*/
    
    //-----------------------------RESTO DE MÉTODOS------------------------------
    
    //Función encargada de comprobar si todos los usuarios han dado las vueltas necesarias 
    //para aumentar los precios de los solares que no hayan sido comprados
    public void modificarPreciosVuelta(ArrayList<Character> claves)
    {
        boolean resultado = true;
        
        for(Character c: claves)
        {
            Avatar figura=figuras.get(c);
            
            if(figura.getVuelta() % 4 == 0 && figura.getVuelta() != 0)
                resultado = true;
            else
            {
                resultado = false;
                break;
            }
        }
        
        if(resultado)
        {  
            for(Grupo grupo : grupos)
                for(Propiedad casilla : grupo.getCasillas())
                    if(casilla.getPropietario().getNombre().equals("banca"))
                        casilla.setPrecio((long) (casilla.getPrecio() * 1.05));
        }
                
    }
    
    public static String obtenerColorDeCodigo(String codigoColor)
    {
        String color;
        
        switch(codigoColor)
        {
            case "\033[36m":
                color = "cyan";
                break;
            case "\033[33m":
                color = "amarillo";
                break;
            case "\033[35m":
                color = "rosa";
                break;
            case "\033[1;40;31m":
                color = "rojo";
                break;
            case "\033[30m":
                color = "negro";
                break;
            case "\033[0;40;31m":
                color = "marron";
                break;
            case "\033[32m":
                color = "verde";
                break;
            case "\033[34m":
                color = "azul";
                break;
            default:
                consola.imprimir("No existe un grupo con el color indicado.");
                return null;
        }
        
        return color;
    }
    
    public static String obtenerCodigoDeColor(String grupoColor)
    {
        String codigoColor;
        
        switch(grupoColor)
        {
            case "negro":
                codigoColor = Constantes.NEGRO;
                break;
            case "cyan":
                codigoColor = Constantes.CYAN;
                break;
            case "rosa":
                codigoColor = Constantes.ROSA;
                break;
            case "amarillo":
                codigoColor = Constantes.AMARILLO;
                break;
            case "rojo":
                codigoColor = Constantes.ROJO;
                break;
            case "marron":
                codigoColor = Constantes.MARRON;
                break;
            case "verde":
                codigoColor = Constantes.VERDE;
                break;
            case "azul":
                codigoColor = Constantes.AZUL;
                break;
            default:
                consola.imprimir("No existe un grupo con el color indicado.");
                return null;
        }
        
        return codigoColor;
    }
    
    //Se redefine toString para que se muestre el aspecto del tablero en pantalla
    @Override public String toString()
    {
        String retorno = "";
        int i;
        
        for(i = 0; i < 10; i++)
            retorno = retorno.concat(casillas.get(2).get(i).toString());
        
        retorno = retorno.concat(casillas.get(3).get(0).toString() + "|");
        
        for(i = 0; i < 9; i++)
        {
            String cadena;
            cadena = String.format("%s|%-197s%s|", casillas.get(1).get(9 - i), "", casillas.get(3).get(i + 1));
            retorno = retorno.concat(cadena);
        }
        
        retorno = retorno.concat(casillas.get(1).get(0).toString());
        
        for(i = 0; i < 10; i++)
            retorno = retorno.concat(casillas.get(0).get(9 - i).toString());
        
        retorno = retorno.concat("|");
        
        return retorno;
    }
}
