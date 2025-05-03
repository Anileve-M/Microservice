package development.twomicroservices.exception;

public class InvalidUserAlreadyExists extends RuntimeException {
    public InvalidUserAlreadyExists(String message) {
        super(message);
    }
}