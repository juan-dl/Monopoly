package jugador;

import excepcion.JugadorException;
import tablero.Juego;
import tablero.Tablero;
import static tablero.Juego.consola;

public final class Pelota extends Avatar
{
    private short botes;
    
    public Pelota(Jugador jugador, Tablero tableroActual)
    {
        super(jugador, tableroActual);
        botes = 0;
    }
    
    public short getBotes()
    {
        return botes;
    }
    
    public void setBotes(short botesPelota)
    {
        if(botesPelota  >= 2 && botesPelota <= 12)
            botes = botesPelota;
        else
            consola.imprimir("Ha introducido un valor fuera de rango como argumento de setBotes");
    }
    
    @Override public void moverEnAvanzado() throws JugadorException
    {
        consola.imprimir("El avatar \'" + getID() + "\' bota desde \"" + getUbicacion().getNombre() + "\" hasta \"");

        setResultadoDados(botes);
        comprobarCasilla();

        if(botes == 1 || botes == -1)
            botes = 0;

        if(botes > 0)
            if(getEncarcelado() == 0)
                botes -= 2; 

        if(botes < 0)
            botes += 2;

        consola.imprimir( Juego.popup.getMensaje().substring(6,Juego.popup.getMensaje().length() - 7) + getTablero() + "<br>Escriba \"botar\" para continuar botando...");
    }
    
    @Override public boolean lanzarDados(short sacarDobles) throws JugadorException
    {
        boolean dobles = super.lanzarDados(sacarDobles);

        if(getMovimientoAvanzado() != -5)    
        {
            if(getResultadoDados() > 4) 
            {
                botes= (short) (getResultadoDados() - 5);
                setResultadoDados(5);
            } 
            else 
            {
                botes = (short) (getResultadoDados() * -1 + 1);
                setResultadoDados(-1);
            }
            
            moverEnBasico(sacarDobles, dobles);
        }
        else
            moverEnBasico(sacarDobles, dobles);
        
        return dobles;
    }
    
    @Override public String toString()
    {    
        return ("<br>Id: " + getID() + "<br>Tipo: Pelota <br>Casilla: "+ getUbicacion().getNombre() +"<br>Jugador: "+ getJugador().getNombre() +"<br><br>");
    }
}
