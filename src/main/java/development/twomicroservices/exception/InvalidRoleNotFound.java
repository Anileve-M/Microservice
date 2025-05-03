package development.twomicroservices.exception;

public class InvalidRoleNotFound extends RuntimeException {
    public InvalidRoleNotFound(String message) {
        super(message);
    }
}