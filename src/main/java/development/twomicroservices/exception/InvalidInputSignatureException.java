package development.twomicroservices.exception;

public class InvalidInputSignatureException extends RuntimeException {
    public InvalidInputSignatureException(String message) {
        super(message);
    }
}