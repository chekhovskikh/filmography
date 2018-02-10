package entitiy;

public class NotFoundEntitiesException extends Exception {
    public NotFoundEntitiesException() {
        super();
    }
    public NotFoundEntitiesException(String message) {
        super(message);
    }
}