package edificio;

import casilla.Solar;

public final class Hotel extends Edificio
{
    public Hotel(String identificador, Solar casilla)
    {
        super(identificador, casilla, casilla.getPrecioHotel());
    }
}
