package excepcion;

public final class MalEscritoException extends ComandoException
{
    public MalEscritoException(String error)
    {
        super(error);
    }
}
