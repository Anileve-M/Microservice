package development.twomicroservices.exception;

public class HavingRole extends RuntimeException {
    public HavingRole(String message) {
        super(message);
    }
}