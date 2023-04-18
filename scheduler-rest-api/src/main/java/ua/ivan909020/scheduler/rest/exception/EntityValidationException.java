package ua.ivan909020.scheduler.rest.exception;

public class EntityValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntityValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityValidationException(String message) {
        super(message);
    }

    public EntityValidationException(Throwable cause) {
        super(cause);
    }

}
