package development.twomicroservices.exception;

public class InvalidInputExpiredException extends RuntimeException {
    public InvalidInputExpiredException(String message) {
        super(message);
    }
}