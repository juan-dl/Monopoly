package edificio;

import casilla.Solar;

public final class Piscina extends Edificio
{
    public Piscina(String identificador, Solar casilla)
    {
        super(identificador, casilla, casilla.getPrecioPiscina());
    }
}
