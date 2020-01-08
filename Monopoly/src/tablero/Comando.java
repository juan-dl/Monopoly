package tablero;

import excepcion.*;
import jugador.Trato;

public interface Comando 
{
    public void acabarTurno() throws GanadorException;
    public void aceptarTrato(String idTrato);
    public void cambiarModo();
    public void comprarCasilla(String nombre);
    public Character crearJugador(String nombre, String tipo) throws JugadorException;
    public void describirFigura(char identificador);
    public void describirJugador(String nombre);
    public void deshipotecar(String nombreCasilla);
    public void edificar(String tipoEdificio);
    public void eliminarTrato(String idTrato);
    public String estadisticasJuego();
    public String estadisticasJugador(String nombreJugador);
    public void hipotecar(String nombreCasilla);
    public void lanzarDados() throws Exception;
    public String listarEdificios();
    public String listarEdificiosGrupo(String grupoColor);
    public String listarEnventa();
    public String listarFiguras();
    public void listarJugadores();
    public void listarTratosJugadorActual();
    public void salirCarcel() throws JugadorException;
    public Trato tratoTipo1(String nombreJugador, String propuesta, String demanda, String enunciado, long dineroDemandado, long dineroOfertado);
    public Trato tratoTipo2(String nombreJugador, String propuesta, String demanda, String enunciado, long dinero);
    public Trato tratoTipo3(String nombreJugador, String propuesta, String demanda, String enunciado, String sinAlquiler, int turnos);
    public void venderEdificios(String tipoEdificio, String nombreCasilla, int cantidad);
}
