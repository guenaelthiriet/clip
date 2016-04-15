package storage;

/**
 * The <code>InvalidArgumentException</code> is used when
 * invalid arguments are provided to the <code>{@link Storage}</code>.
 *
 * @author Guenael Thiriet
 */

public class InvalidArgumentException extends Exception {
    public InvalidArgumentException() {
        super();
    }

    public InvalidArgumentException(String message) {
        super(message);
    }

    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidArgumentException(Throwable cause) {
        super(cause);
    }
}