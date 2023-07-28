package one.lab.firstpractice.exception.exceptions;

public class NoAuthorityException extends RuntimeException {

    public NoAuthorityException(String message) {
        super(message);
    }
}
