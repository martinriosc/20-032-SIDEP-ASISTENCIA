package cl.mineduc.sidep.asistenciaapi.exceptions;

public class SostenedorException extends RuntimeException {

    public SostenedorException(String message) {
        super(message);
    }

    public SostenedorException(String message, Throwable cause) {
        super(message, cause);
    }
}
