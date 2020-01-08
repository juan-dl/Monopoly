package tablero;

import java.awt.Color;

public class Constantes 
{
    public static final String CYAN = "\033[36m", AMARILLO = "\033[33m", ROSA = "\033[35m", ROJO = "\033[1;40;31m", VERDE = "\033[32m", AZUL = "\033[34m", NEGRO = "\033[30m", MARRON = "\033[0;40;31m", RESET = "\033[0m";
    
    //COLORES: CYAN - PISTACHO - NARANJA - ROSA - ROJO - AMARILLO - GRIS - AZUL
    public static final Color[] COLORES_JUGADORES_VENTANA = {new Color(197, 226, 254), new Color(119, 254, 132), new Color(253, 182, 108), new Color(254, 149, 227), new Color(253, 125, 108), new Color(254, 239, 75), new Color(197, 197, 197), new Color(0, 128, 255)};
    
    public static final long PRIMER_GRUPO = 1200;
    public static final long SEGUNDO_GRUPO = 100 * Math.round(1.3 * (PRIMER_GRUPO/100));
    public static final long TERCER_GRUPO = 100 * Math.round(1.3 * (SEGUNDO_GRUPO/100));
    public static final long CUARTO_GRUPO = 100 * Math.round(1.3 * (TERCER_GRUPO/100));
    public static final long QUINTO_GRUPO = 100 * Math.round(1.3 * (CUARTO_GRUPO/100));
    public static final long SEXTO_GRUPO = 100 * Math.round(1.3 * (QUINTO_GRUPO/100));
    public static final long SEPTIMO_GRUPO = 100 * Math.round(1.3 * (SEXTO_GRUPO/100));
    public static final long OCTAVO_GRUPO = 100 * Math.round(1.3 * (SEPTIMO_GRUPO/100));
    
    public static final long DINERO_INICIAL = 100 * Math.round(((PRIMER_GRUPO + SEGUNDO_GRUPO + TERCER_GRUPO + CUARTO_GRUPO + QUINTO_GRUPO + SEXTO_GRUPO + SEPTIMO_GRUPO + OCTAVO_GRUPO)/100)/3);
    public static final long PAGO_VUELTA = 100 * Math.round(((PRIMER_GRUPO + SEGUNDO_GRUPO + TERCER_GRUPO + CUARTO_GRUPO + QUINTO_GRUPO + SEXTO_GRUPO + SEPTIMO_GRUPO + OCTAVO_GRUPO)/100)/22);
    public static final long SERVICIOS = Math.round(PAGO_VUELTA * 0.75);
    
    //Número de cartas que hay en cada uno de los montones
    public static final int CARTAS_MONTON = 6;
    
    /*Por la lógica de nuestra implementación el valor que cuenta el número de turnos que estas en la carcel antes de
    ser forzado a pagar será 5, en la práctica serán 3 lanzamientos de dados*/
    public static final short CARCEL_PAGO_FORZADO = 5;
    
    public static final String[] NOMBRE_CASILLAS = {"Salida", "Bilbao", "Comunidad", 
        "Vitoria", "Impuesto1", "Puerto", "Leon", "Suerte", 
        "Salamanca", "Valladolid", "Carcel", "Cuenca", "ENDESA",
        "Toledo", "Albacete", "Ferrocarril", "Huesca", "Comunidad", 
        "Zaragoza", "Teruel", "Parking", "Jaen", "Suerte", "Granada", "Cadiz", 
        "Aeropuerto", "Castellon", "Valencia", "Movistar"
        , "Alicante", "IrCarcel", "Sevilla", "Malaga", "Comunidad", 
        "Almeria", "ViajesSolares", "Suerte", "Vigo", 
        "Impuesto2", "Santiago"};
    
    public static final String[] ENUNCIADOS_SUERTE = {"Has ganado el bote de la loteria! Recibe 3000€.;3000",
    "Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida " +
    "y sin cobrar los " + PAGO_VUELTA + ".;Carcel", "El aumento del impuesto sobre bienes inmuebles afecta "
    +"a todas tus propiedades. Paga 1200€ por casa, 3450€ por hotel, 600€ por piscina y "
    + "2250€ por pista de deportes.;-600", "Has sido elegido presidente de la junta directiva. Paga a cada jugador 760€.;-760",
    "Beneficio por la venta de tus acciones. Recibe 4500€.;4500", "Te multan por usar el móvil mientras conduces. Paga 460€.;-460"};
    
    public static final String[] ENUNCIADOS_COMUNIDAD = {"Te investigan por fraude de identidad. Ve a la Carcel. "
    + "Ve directamente sin pasar por la casilla de Salida y sin cobrar los " + PAGO_VUELTA + "€.;Carcel",
    "Paga 1520€ por un fin de semana en un balneario de 5 estrellas.;-1520", "Devolución de Hacienda. Cobra 1520€.;1520",
    "Recibe 3040€ de beneficios por alquilar los servicios de tu jet privado.;3040",
    "Alquilas a tus compañeros un pazo en " + NOMBRE_CASILLAS[39] + " durante una semana. Paga 608€ a cada jugador.;-608", 
    "Paga 3040€ por invitar a todos tus amigos a un viaje a Leon.;-3040"};
    
    public static final String[] TIPO_EDIFICACIONES = {"casa", "hotel", "piscina", "pista"};
}
