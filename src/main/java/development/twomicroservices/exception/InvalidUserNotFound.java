package development.twomicroservices.exception;

public class InvalidUserNotFound extends RuntimeException {
    public InvalidUserNotFound(String message) {
        super(message);
    }
}