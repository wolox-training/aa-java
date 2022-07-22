package wolox.training.exceptions;

public class BookIdMismatchException extends RuntimeException{
    public BookIdMismatchException() {
        super();
    }

    public BookIdMismatchException(String message, Throwable error) {
        super(message, error);
    }

    public BookIdMismatchException(String message) {
        super(message);
    }

    public BookIdMismatchException(Throwable error) {
        super(error);
    }
}
