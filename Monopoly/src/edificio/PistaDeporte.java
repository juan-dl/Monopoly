package edificio;

import casilla.Solar;

public final class PistaDeporte extends Edificio
{
    public PistaDeporte(String identificador, Solar casilla)
    {
        super(identificador, casilla, casilla.getPrecioPista());
    }
}
