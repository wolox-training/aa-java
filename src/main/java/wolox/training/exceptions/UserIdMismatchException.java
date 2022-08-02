package wolox.training.exceptions;

public class UserIdMismatchException extends RuntimeException{
    public UserIdMismatchException() {
        super();
    }

    public UserIdMismatchException(String message, Throwable error) {
        super(message, error);
    }

    public UserIdMismatchException(String message) {
        super(message);
    }

    public UserIdMismatchException(Throwable error) {
        super(error);
    }
}
