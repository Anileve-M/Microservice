package development.twomicroservices.exception;

public class InvalidInputTokenException extends RuntimeException {
    public InvalidInputTokenException(String message) {
        super(message);
    }
}