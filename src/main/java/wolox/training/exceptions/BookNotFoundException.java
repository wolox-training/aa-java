package wolox.training.exceptions;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String message, Throwable error) {
        super(message, error);
    }

    public BookNotFoundException(String message) {
        super(message);
    }

    public BookNotFoundException(Throwable error) {
        super(error);
    }
    public BookNotFoundException() {
        super();
    }
}
