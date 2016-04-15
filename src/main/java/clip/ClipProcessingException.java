package clip;

/**
 * The <code>ClipProcessingException</code> is used when
 * errors happen while Clip is being used.
 *
 * @author Guenael Thiriet
 */

public class ClipProcessingException extends Exception {

    public ClipProcessingException() {
        super();
    }

    public ClipProcessingException(String message) {
        super(message);
    }

    public ClipProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClipProcessingException(Throwable cause) {
        super(cause);
    }
}
