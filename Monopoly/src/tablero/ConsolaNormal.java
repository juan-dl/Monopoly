package tablero;

//import java.util.Scanner;
import static tablero.Juego.popup;

public class ConsolaNormal implements Consola
{
    @Override public void imprimir(String mensaje)
    {
        popup.setMensaje(mensaje);
        popup.setVisible(true);
    }
    /*
    @Override public String leer(String descripcion)
    {
        Scanner lectura = new Scanner(System.in);
        
        imprimir(descripcion);
        
        return lectura.nextLine();   
    }*/
}
