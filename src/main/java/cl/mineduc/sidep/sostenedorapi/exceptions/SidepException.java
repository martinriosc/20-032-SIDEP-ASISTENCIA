package cl.mineduc.sidep.sostenedorapi.exceptions;

public class SidepException extends RuntimeException {

    public SidepException() {
        super();
    }

    public SidepException(String message) {
        super(message);
    }

    public SidepException(String message, Throwable cause) {
        super(message, cause);
    }

    public SidepException(Throwable cause) {
        super(cause);
    }
}
