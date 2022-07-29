package wolox.training.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message, Throwable error) {
        super(message, error);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Throwable error) {
        super(error);
    }
    public UserNotFoundException() {
        super();
    }
}
