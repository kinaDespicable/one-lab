package one.lab.firstpractice.exception;

public class ResourceAlreadyExistsException extends RuntimeException{

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
