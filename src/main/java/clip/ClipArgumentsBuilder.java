package clip;

/**
 * The <code>ClipArgumentsBuilder</code> is used to build a
 * ClipArguments class, using the builder pattern.
 *
 * @author Guenael Thiriet
 */

public class ClipArgumentsBuilder {

    /**
     * A URL
     */
    private String url;

    /**
     * The algorithm used
     */
    private ClipArguments.Algorithm algorithm;

    /**
     * Default constructor
     */
    public ClipArgumentsBuilder() {
    }

    /**
     * This method sets the URL in this object instance.
     *
     * @param url A URL ton configure this object.
     * @return A reference to the current instance.
     */
    public ClipArgumentsBuilder url(final String url) {
        this.url = url;
        return this;
    }

    /**
     * This method sets the algorithm in this object instance.
     *
     * @param algorithm The algorithm to use.
     * @return A reference to the current instance.
     */
    public ClipArgumentsBuilder algorithm(final ClipArguments.Algorithm algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    /**
     * This method create a ClipArguments using values
     * provided to this object instance.
     *
     * @return a <code>ClipArguments</code> object instance.
     */
    public ClipArguments createClipArgument() {
        return new ClipArguments(algorithm, url);
    }
}