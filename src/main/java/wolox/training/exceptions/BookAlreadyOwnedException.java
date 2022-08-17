package wolox.training.exceptions;

public class BookAlreadyOwnedException extends RuntimeException{

    public BookAlreadyOwnedException(){super();}

    public BookAlreadyOwnedException(String message, Throwable error) {
        super(message, error);
    }

    public BookAlreadyOwnedException(String message) {
        super(message);
    }

    public BookAlreadyOwnedException(Throwable error) {
        super(error);
    }

}
