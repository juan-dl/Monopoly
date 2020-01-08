package edificio;

import casilla.Solar;

public final class Casa extends Edificio
{
    public Casa(String identificador, Solar casilla)
    {
        super(identificador, casilla, casilla.getPrecioCasa());
    }
}
