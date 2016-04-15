package storage;

/**
 * The <code>InvalidStateException</code> is used when
 * an invalid state is detected  in <code>{@link Storage}</code>.
 *
 * @author Guenael Thiriet
 */

public class InvalidStateException extends Exception {
    public InvalidStateException() {
        super();
    }

    public InvalidStateException(String message) {
        super(message);
    }

    public InvalidStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidStateException(Throwable cause) {
        super(cause);
    }
}